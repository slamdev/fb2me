package com.github.valentin.fedoskin.fb2me.desktop;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public abstract class AbstractView<T, V> implements View<T, V> {

    private final FXMLLoader loader;

    private T presenter;

    private Stage stage;

    public AbstractView(FXMLLoader loader) {
        this.loader = loader;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterReload(ApplicationContext context, Map<String, Object> viewData) {
        presenter = (T) viewData.get("P");
        stage = (Stage) viewData.get("ST");
    }

    @Override
    public Map<String, Object> beforeReload(ApplicationContext context) {
        Map<String, Object> viewData = new HashMap<>();
        viewData.put("P", presenter);
        viewData.put("ST", stage);
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
    public Stage getStage() {
        return stage;
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

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}