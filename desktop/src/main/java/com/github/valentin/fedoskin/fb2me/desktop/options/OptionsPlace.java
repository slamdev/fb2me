package com.github.valentin.fedoskin.fb2me.desktop.options;

import com.github.valentin.fedoskin.fb2me.desktop.AbstractPlace;
import com.github.valentin.fedoskin.fb2me.desktop.ApplicationContext;

public class OptionsPlace extends AbstractPlace {

    public OptionsPlace(ApplicationContext context) {
        super(context);
    }

    @Override
    public Class<?> getPresenterType() {
        return OptionsPresenter.class;
    }

    @Override
    protected OptionsPresenter getPresenter() {
        return new OptionsPresenter(context);
    }
}