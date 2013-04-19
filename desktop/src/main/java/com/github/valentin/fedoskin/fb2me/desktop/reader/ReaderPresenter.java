package com.github.valentin.fedoskin.fb2me.desktop.reader;

import java.util.UUID;

public class ReaderPresenter implements ReaderView.Presenter {

    public ReaderPresenter() {
        // try {
        // Thread.sleep(5000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }

    @Override
    public String getText() {
        return UUID.randomUUID().toString();
    }
}