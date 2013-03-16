package com.github.valentin.fedoskin.fb2me.desktop.shelf;

import javafx.scene.Node;

public interface ShelfView {

    interface Presenter {
    }

    Node getContent();

    void refresh();

    void setPresenter(Presenter presenter);
}