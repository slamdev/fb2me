package com.github.valentin.fedoskin.fb2me.desktop.options;

import java.util.Locale;

import com.github.valentin.fedoskin.fb2me.desktop.ApplicationContext;
import com.github.valentin.fedoskin.fb2me.desktop.Place;

public class OptionsPresenter implements OptionsView.Presenter {

    public static class OptionsPlace implements Place {

        private final ApplicationContext context;

        public OptionsPlace(ApplicationContext context) {
            this.context = context;
        }

        @Override
        public Object getPresenter() {
            return new OptionsPresenter(context);
        }

        @Override
        public Class<?> getPresenterType() {
            return OptionsPresenter.class;
        }
    }

    private final ApplicationContext context;

    public OptionsPresenter(ApplicationContext context) {
        this.context = context;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeLanguageOption(Locale value) {
        context.optionsController.setLanguage(value);
    }

    @Override
    public Locale getLanguage() {
        return context.optionsController.getLanguage();
    }

    @Override
    public void reloadViews() {
        context.viewController.reload();
    }

    @Override
    public void resetOptions() {
        context.optionsController.resetOptions();
    }
}