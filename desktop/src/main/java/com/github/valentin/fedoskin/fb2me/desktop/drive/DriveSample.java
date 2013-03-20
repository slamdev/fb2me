/*
 * Copyright (c) 2012 Google Inc.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.valentin.fedoskin.fb2me.desktop.drive;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.common.base.Preconditions;

/**
 * A sample application that runs multiple requests against the Drive API. The requests this sample
 * makes are:
 * <ul>
 * <li>Does a resumable media upload</li>
 * <li>Updates the uploaded file by renaming it</li>
 * <li>Does a resumable media download</li>
 * <li>Does a direct media upload</li>
 * <li>Does a direct media download</li>
 * </ul>
 * 
 * @author rmistry@google.com (Ravi Mistry)
 */
public class DriveSample {

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "product";

    private static final String DIR_FOR_DOWNLOADS = "d:/incoming/download";

    /** Global Drive API client. */
    private static Drive drive;

    /** Global instance of the HTTP transport. */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private static final String UPLOAD_FILE_PATH = "d:\\incoming\\upload.jpg";

    private static final java.io.File UPLOAD_FILE = new java.io.File(UPLOAD_FILE_PATH);

    public static void main(String[] args) {
        Preconditions.checkArgument(!UPLOAD_FILE_PATH.startsWith("Enter ") && !DIR_FOR_DOWNLOADS.startsWith("Enter "),
                "Please enter the upload file path and download directory in %s", DriveSample.class);
        try {
            try {
                // authorization
                Credential credential = authorize();
                // set up the global Drive instance
                drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                        .setApplicationName(APPLICATION_NAME).build();
                // run commands
                DriveView.header1("Starting Resumable Media Upload");
                File uploadedFile = uploadFile(false);
                DriveView.header1("Updating Uploaded File Name");
                File updatedFile = updateFileWithTestSuffix(uploadedFile.getId());
                DriveView.header1("Starting Resumable Media Download");
                downloadFile(false, updatedFile);
                DriveView.header1("Starting Simple Media Upload");
                uploadedFile = uploadFile(true);
                DriveView.header1("Starting Simple Media Download");
                downloadFile(true, uploadedFile);
                DriveView.header1("Success!");
                return;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(1);
    }

    /** Authorizes the installed application to access user's protected data. */
    private static Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                DriveSample.class.getResourceAsStream("/client_secrets.json"));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println("Enter Client ID and Secret from https://code.google.com/apis/console/?api=drive "
                    + "into drive-cmdline-sample/src/main/resources/client_secrets.json");
            System.exit(1);
        }
        // set up file credential store
        FileCredentialStore credentialStore = new FileCredentialStore(new java.io.File(System.getProperty("user.home"),
                ".credentials/drive.json"), JSON_FACTORY);
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, Collections.singleton(DriveScopes.DRIVE_FILE)).setCredentialStore(credentialStore)
                .build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    /** Downloads a file using either resumable or direct media download. */
    private static void downloadFile(boolean useDirectDownload, File uploadedFile) throws IOException {
        // create parent directory (if necessary)
        java.io.File parentDir = new java.io.File(DIR_FOR_DOWNLOADS);
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("Unable to create parent directory");
        }
        OutputStream out = new FileOutputStream(new java.io.File(parentDir, uploadedFile.getTitle()));
        Drive.Files.Get get = drive.files().get(uploadedFile.getId());
        MediaHttpDownloader downloader = get.getMediaHttpDownloader();
        downloader.setDirectDownloadEnabled(useDirectDownload);
        downloader.setProgressListener(new FileDownloadProgressListener());
        downloader.download(new GenericUrl(uploadedFile.getDownloadUrl()), out);
    }

    /** Updates the name of the uploaded file to have a "drivetest-" prefix. */
    private static File updateFileWithTestSuffix(String id) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setTitle("drivetest-" + UPLOAD_FILE.getName());
        Drive.Files.Update update = drive.files().update(id, fileMetadata);
        return update.execute();
    }

    /** Uploads a file using either resumable or direct media upload. */
    private static File uploadFile(boolean useDirectUpload) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setTitle(UPLOAD_FILE.getName());
        InputStreamContent mediaContent = new InputStreamContent("image/jpeg", new BufferedInputStream(
                new FileInputStream(UPLOAD_FILE)));
        mediaContent.setLength(UPLOAD_FILE.length());
        Drive.Files.Insert insert = drive.files().insert(fileMetadata, mediaContent);
        MediaHttpUploader uploader = insert.getMediaHttpUploader();
        uploader.setDirectUploadEnabled(useDirectUpload);
        uploader.setProgressListener(new FileUploadProgressListener());
        return insert.execute();
    }
}