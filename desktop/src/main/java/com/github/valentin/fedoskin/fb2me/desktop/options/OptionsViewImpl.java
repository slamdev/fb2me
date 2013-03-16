package com.github.valentin.fedoskin.fb2me.desktop.options;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class OptionsViewImpl implements OptionsView {

    @FXML
    private AnchorPane content;

    @SuppressWarnings("unused")
    private Presenter presenter;

    @Override
    public AnchorPane getParent() {
        return content;
    }

    @Override
    public String getTitle() {
        return "Options";
    }

    @Override
    public void refresh() {
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}