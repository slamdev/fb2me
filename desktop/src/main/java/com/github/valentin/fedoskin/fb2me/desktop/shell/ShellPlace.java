package com.github.valentin.fedoskin.fb2me.desktop.shell;

import com.github.valentin.fedoskin.fb2me.desktop.AbstractPlace;
import com.github.valentin.fedoskin.fb2me.desktop.ApplicationContext;

public class ShellPlace extends AbstractPlace {

    public ShellPlace(ApplicationContext context) {
        super(context);
    }

    @Override
    public Class<?> getPresenterType() {
        return ShellPresenter.class;
    }

    @Override
    protected ShellPresenter getPresenter() {
        return new ShellPresenter(context);
    }
}