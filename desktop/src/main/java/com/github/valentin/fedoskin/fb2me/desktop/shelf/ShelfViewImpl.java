package com.github.valentin.fedoskin.fb2me.desktop.shelf;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class ShelfViewImpl implements ShelfView {

    @FXML
    private AnchorPane content;

    @SuppressWarnings("unused")
    private Presenter presenter;

    @Override
    public Node getContent() {
        return content;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}