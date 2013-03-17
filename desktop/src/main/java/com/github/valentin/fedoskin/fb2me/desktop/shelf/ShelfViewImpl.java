package com.github.valentin.fedoskin.fb2me.desktop.shelf;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import com.github.valentin.fedoskin.fb2me.desktop.AbstractView;

public class ShelfViewImpl extends AbstractView<ShelfView.Presenter, AnchorPane> implements ShelfView {

    public ShelfViewImpl(FXMLLoader loader) {
        super(loader);
    }
}