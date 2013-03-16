package com.github.valentin.fedoskin.fb2me.desktop;

import com.github.valentin.fedoskin.fb2me.desktop.reader.ReaderPresenter;
import com.github.valentin.fedoskin.fb2me.desktop.shelf.ShelfPresenter;

public class NavigationController {

    private final ApplicationContext context;

    public NavigationController(ApplicationContext context) {
        this.context = context;
    }

    public void goTo(Object presenter) {
        if (presenter instanceof ShelfPresenter) {
            context.shelfView.setPresenter((ShelfPresenter) presenter);
            context.shellView.setContent(context.shelfView.getContent());
            context.shelfView.refresh();
        } else if (presenter instanceof ReaderPresenter) {
            context.readerView.setPresenter((ReaderPresenter) presenter);
            context.shellView.setContent(context.readerView.getContent());
            context.readerView.refresh();
        } else {
            throw new RuntimeException(presenter.toString());
        }
    }
}