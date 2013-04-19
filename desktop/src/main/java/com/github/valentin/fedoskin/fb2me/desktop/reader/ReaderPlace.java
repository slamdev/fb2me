package com.github.valentin.fedoskin.fb2me.desktop.reader;

import com.github.valentin.fedoskin.fb2me.desktop.AbstractPlace;
import com.github.valentin.fedoskin.fb2me.desktop.ApplicationContext;

public class ReaderPlace extends AbstractPlace {

    public ReaderPlace(ApplicationContext context) {
        super(context);
    }

    @Override
    public Class<?> getPresenterType() {
        return ReaderPresenter.class;
    }

    @Override
    protected ReaderPresenter getPresenter() {
        return new ReaderPresenter();
    }
}