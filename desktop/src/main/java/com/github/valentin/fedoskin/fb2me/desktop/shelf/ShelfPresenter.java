package com.github.valentin.fedoskin.fb2me.desktop.shelf;

import com.github.valentin.fedoskin.fb2me.desktop.Place;

public class ShelfPresenter implements ShelfView.Presenter {

    public static class ShelfPlace implements Place {

        @Override
        public ShelfPresenter getPresenter() {
            return new ShelfPresenter();
        }

        @Override
        public Class<?> getPresenterType() {
            return ShelfPresenter.class;
        }
    }

    public ShelfPresenter() {
        // try {
        // Thread.sleep(5000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }
}