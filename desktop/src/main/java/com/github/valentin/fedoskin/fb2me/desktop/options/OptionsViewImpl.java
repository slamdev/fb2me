package com.github.valentin.fedoskin.fb2me.desktop.options;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import com.github.valentin.fedoskin.fb2me.desktop.AbstractView;

public class OptionsViewImpl extends AbstractView<OptionsView.Presenter, AnchorPane> implements OptionsView {

    public OptionsViewImpl(FXMLLoader loader) {
        super(loader);
    }

    @Override
    public String getTitle() {
        return getResources().getString("title");
    }
}