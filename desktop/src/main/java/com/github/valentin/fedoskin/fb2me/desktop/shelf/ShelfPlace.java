package com.github.valentin.fedoskin.fb2me.desktop.shelf;

import com.github.valentin.fedoskin.fb2me.desktop.AbstractPlace;
import com.github.valentin.fedoskin.fb2me.desktop.ApplicationContext;

public class ShelfPlace extends AbstractPlace {

    public ShelfPlace(ApplicationContext context) {
        super(context);
    }

    @Override
    public Class<?> getPresenterType() {
        return ShelfPresenter.class;
    }

    @Override
    protected ShelfPresenter getPresenter() {
        return new ShelfPresenter();
    }
}