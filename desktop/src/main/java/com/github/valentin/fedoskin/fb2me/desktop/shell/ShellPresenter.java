package com.github.valentin.fedoskin.fb2me.desktop.shell;

import com.github.valentin.fedoskin.fb2me.desktop.ApplicationContext;
import com.github.valentin.fedoskin.fb2me.desktop.options.OptionsPresenter;
import com.github.valentin.fedoskin.fb2me.desktop.reader.ReaderPresenter;
import com.github.valentin.fedoskin.fb2me.desktop.shelf.ShelfPresenter;

public class ShellPresenter implements ShellView.Presenter {

    private final ApplicationContext context;

    public ShellPresenter(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    @Override
    public void close() {
        context.stage.close();
    }

    @Override
    public void goToReader() {
        context.navigationController.goTo(new ReaderPresenter());
    }

    @Override
    public void goToShelf() {
        context.navigationController.goTo(new ShelfPresenter());
    }

    @Override
    public void showOptions() {
        context.dialogController.show(new OptionsPresenter(context));
    }
}