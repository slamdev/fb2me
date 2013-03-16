package com.github.valentin.fedoskin.fb2me.desktop.reader;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ReaderViewImpl implements ReaderView {

    @FXML
    private AnchorPane content;

    @FXML
    private Label label;

    private Presenter presenter;

    @Override
    public Node getContent() {
        return content;
    }

    @Override
    public void refresh() {
        label.textProperty().set(presenter.getText());
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}