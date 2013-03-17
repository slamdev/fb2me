package com.github.valentin.fedoskin.fb2me.desktop.shell;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import com.github.valentin.fedoskin.fb2me.desktop.View;

public interface ShellView extends View<ShellView.Presenter, BorderPane> {

    interface Presenter {

        void close();

        void goToReader();

        void goToShelf();

        void showOptions();
    }

    void setContent(Node content);
}