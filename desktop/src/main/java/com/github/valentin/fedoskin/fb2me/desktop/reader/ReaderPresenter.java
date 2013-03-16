package com.github.valentin.fedoskin.fb2me.desktop.reader;

import java.util.UUID;

public class ReaderPresenter implements ReaderView.Presenter {

    @Override
    public String getText() {
        return UUID.randomUUID().toString();
    }
}