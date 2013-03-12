package com.github.valentin.fedoskin.fb2me.desktop;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.github.valentin.fedoskin.fb2me.desktop.shelf.ShelfViewImpl;
import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellViewImpl;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("FB2ME");
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getWidth() / 4);
        primaryStage.setY(bounds.getHeight() / 4);
        primaryStage.setWidth(bounds.getWidth() / 2);
        primaryStage.setHeight(bounds.getHeight() / 2);
        BorderPane pane = FXMLLoader.load(ResourceUtil.getFxml(ShellViewImpl.class));
        pane.setCenter(FXMLLoader.<Node> load(ResourceUtil.getFxml(ShelfViewImpl.class)));
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}