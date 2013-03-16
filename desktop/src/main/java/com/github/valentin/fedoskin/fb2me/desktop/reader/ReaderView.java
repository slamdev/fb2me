package com.github.valentin.fedoskin.fb2me.desktop.reader;

import javafx.scene.Node;

public interface ReaderView {

    interface Presenter {

        String getText();
    }

    Node getContent();

    void refresh();

    void setPresenter(Presenter presenter);
}