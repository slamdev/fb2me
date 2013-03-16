package com.github.valentin.fedoskin.fb2me.desktop.options;

import javafx.scene.layout.AnchorPane;

public interface OptionsView {

    interface Presenter {
    }

    AnchorPane getParent();

    String getTitle();

    void refresh();

    void setPresenter(Presenter presenter);
}