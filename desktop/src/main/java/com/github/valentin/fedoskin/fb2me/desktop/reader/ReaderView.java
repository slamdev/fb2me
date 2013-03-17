package com.github.valentin.fedoskin.fb2me.desktop.reader;

import javafx.scene.layout.AnchorPane;

import com.github.valentin.fedoskin.fb2me.desktop.View;

public interface ReaderView extends View<ReaderView.Presenter, AnchorPane> {

    interface Presenter {

        String getText();
    }

    @Override
    void refresh();
}