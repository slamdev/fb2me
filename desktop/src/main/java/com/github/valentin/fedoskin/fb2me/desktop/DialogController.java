package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.github.valentin.fedoskin.fb2me.desktop.options.OptionsPresenter;

public class DialogController {

    private final ApplicationContext context;

    public DialogController(ApplicationContext context) {
        this.context = context;
    }

    public void show(Object presenter) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context.stage);
        Parent parent;
        String title;
        if (presenter instanceof OptionsPresenter) {
            parent = context.optionsView.getParent();
            context.optionsView.setPresenter((OptionsPresenter) presenter);
            context.optionsView.refresh();
            title = context.optionsView.getTitle();
        } else {
            throw new RuntimeException();
        }
        stage.setScene(new Scene(parent));
        stage.setTitle(title);
        stage.showAndWait();
    }
}