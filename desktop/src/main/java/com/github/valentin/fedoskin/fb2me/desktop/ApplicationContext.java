package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.stage.Stage;

import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellPresenter;
import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellView;

public class ApplicationContext {

    public final DialogController dialogController = new DialogController(this);

    public final NavigationController navigationController = new NavigationController(this);

    public final Stage stage;

    public final ViewController viewController = new ViewController(this);

    public ApplicationContext(Stage stage) {
        this.stage = stage;
        viewController.getView(ShellView.class).setPresenter(new ShellPresenter(this));
    }

    public <T extends View<?, ?>> T getView(Class<T> type) {
        return viewController.getView(type);
    }
}