package com.github.valentin.fedoskin.fb2me.desktop.shell;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class ShellViewImpl implements ShellView {

    @FXML
    private BorderPane contentPane;

    private Presenter presenter;

    @Override
    public Parent getParent() {
        return contentPane;
    }

    @Override
    public void setContent(Node content) {
        contentPane.setCenter(content);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @FXML
    private void openReader() {
        presenter.goToReader();
    }

    @FXML
    private void openShelf() {
        presenter.goToShelf();
    }
}