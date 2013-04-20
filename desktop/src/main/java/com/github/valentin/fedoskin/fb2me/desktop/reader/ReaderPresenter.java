package com.github.valentin.fedoskin.fb2me.desktop.reader;

import java.util.UUID;

import com.github.valentin.fedoskin.fb2me.desktop.Place;

public class ReaderPresenter implements ReaderView.Presenter {

    public static class ReaderPlace implements Place {

        @Override
        public ReaderPresenter getPresenter() {
            return new ReaderPresenter();
        }

        @Override
        public Class<?> getPresenterType() {
            return ReaderPresenter.class;
        }
    }

    public ReaderPresenter() {
        // try {
        // Thread.sleep(5000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }

    @Override
    public String getText() {
        return UUID.randomUUID().toString();
    }
}