package com.github.valentin.fedoskin.fb2me.desktop.shell;

import com.github.valentin.fedoskin.fb2me.desktop.ApplicationContext;
import com.github.valentin.fedoskin.fb2me.desktop.Place;
import com.github.valentin.fedoskin.fb2me.desktop.options.OptionsPresenter;
import com.github.valentin.fedoskin.fb2me.desktop.reader.ReaderPresenter;
import com.github.valentin.fedoskin.fb2me.desktop.shelf.ShelfPresenter;

public class ShellPresenter implements ShellView.Presenter {

    public static class ShellPlace implements Place {

        private final ApplicationContext context;

        public ShellPlace(ApplicationContext context) {
            this.context = context;
        }

        @Override
        public ShellPresenter getPresenter() {
            return new ShellPresenter(context);
        }

        @Override
        public Class<?> getPresenterType() {
            return ShellPresenter.class;
        }
    }

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
        context.navigationController.goTo(new ReaderPresenter.ReaderPlace());
    }

    @Override
    public void goToShelf() {
        context.navigationController.goTo(new ShelfPresenter.ShelfPlace());
    }

    @Override
    public void showOptions() {
        context.dialogController.show(new OptionsPresenter.OptionsPlace(context));
    }
}