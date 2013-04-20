package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.application.Application;
import javafx.stage.Stage;

import com.github.valentin.fedoskin.fb2me.desktop.reader.ReaderPresenter;
import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellPresenter;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final ApplicationContext context = new ApplicationContext(primaryStage);
        context.dialogController.show(new ShellPresenter.ShellPlace(context), false);
        context.navigationController.goTo(new ReaderPresenter.ReaderPlace());
    }
}