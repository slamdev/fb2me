package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.github.valentin.fedoskin.fb2me.desktop.reader.ReaderPresenter;
import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FB2ME");
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getWidth() / 4);
        primaryStage.setY(bounds.getHeight() / 4);
        primaryStage.setWidth(bounds.getWidth() / 2);
        primaryStage.setHeight(bounds.getHeight() / 2);
        ApplicationContext context = new ApplicationContext(primaryStage);
        context.navigationController.goTo(new ReaderPresenter());
        Scene scene = new Scene(context.getView(ShellView.class).getRoot());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}