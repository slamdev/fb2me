package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.application.Application;
import javafx.stage.Stage;

import com.github.valentin.fedoskin.fb2me.desktop.reader.ReaderPlace;
import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellPlace;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final ApplicationContext context = new ApplicationContext(primaryStage);
        context.dialogController.show(new ShellPlace(context), false);
        context.navigationController.goTo(new ReaderPlace(context));
    }
}