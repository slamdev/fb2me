package com.github.valentin.fedoskin.fb2me.desktop;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;

public abstract class AbstractView<T, V> implements View<T, V> {

    private final FXMLLoader loader;

    private T presenter;

    public AbstractView(FXMLLoader loader) {
        this.loader = loader;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterReload(ApplicationContext context, Map<String, Object> viewData) {
        presenter = (T) viewData.get("P");
    }

    @Override
    public Map<String, Object> beforeReload(ApplicationContext context) {
        Map<String, Object> viewData = new HashMap<>();
        viewData.put("P", presenter);
        return viewData;
    }

    @Override
    public T getPresenter() {
        return presenter;
    }

    @Override
    public ResourceBundle getResources() {
        return loader.getResources();
    }

    @Override
    public V getRoot() {
        return loader.getRoot();
    }

    @Override
    public String getTitle() {
        if (getResources().containsKey("title")) {
            return getResources().getString("title");
        }
        return "";
    }

    public FXMLLoader loader() {
        return loader;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }
}