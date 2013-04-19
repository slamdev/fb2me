package com.github.valentin.fedoskin.fb2me.desktop.shell;

import com.github.valentin.fedoskin.fb2me.desktop.ApplicationContext;
import com.github.valentin.fedoskin.fb2me.desktop.options.OptionsPlace;
import com.github.valentin.fedoskin.fb2me.desktop.reader.ReaderPlace;
import com.github.valentin.fedoskin.fb2me.desktop.shelf.ShelfPlace;

public class ShellPresenter implements ShellView.Presenter {

    private final ApplicationContext context;

    public ShellPresenter(ApplicationContext applicationContext) {
        context = applicationContext;
        // try {
        // Thread.sleep(5000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }

    @Override
    public void close() {
        context.stage.close();
    }

    @Override
    public void goToReader() {
        context.navigationController.goTo(new ReaderPlace(context));
    }

    @Override
    public void goToShelf() {
        context.navigationController.goTo(new ShelfPlace(context));
    }

    @Override
    public void showOptions() {
        context.dialogController.show(new OptionsPlace(context));
    }
}