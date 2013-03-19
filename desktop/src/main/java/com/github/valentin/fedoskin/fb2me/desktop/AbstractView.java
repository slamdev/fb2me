package com.github.valentin.fedoskin.fb2me.desktop;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class AbstractView<T, V extends Pane> implements View<T, V> {

    private final FXMLLoader loader;

    private T presenter;

    private Stage stage;

    public AbstractView(FXMLLoader loader) {
        this.loader = loader;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterReload(ApplicationContext context, Map<String, Object> viewData) {
        presenter = (T) viewData.get("PRESENTER");
        stage = (Stage) viewData.get("STAGE");
    }

    @Override
    public Map<String, Object> beforeReload(ApplicationContext context) {
        Map<String, Object> viewData = new HashMap<>();
        viewData.put("PRESENTER", presenter);
        viewData.put("STAGE", stage);
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