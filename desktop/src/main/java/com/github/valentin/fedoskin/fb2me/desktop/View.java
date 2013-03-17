package com.github.valentin.fedoskin.fb2me.desktop;

import java.util.Map;
import java.util.ResourceBundle;

import javafx.stage.Stage;

public interface View<T, V> {

    void afterReload(ApplicationContext context, Map<String, Object> viewData);

    Map<String, Object> beforeReload(ApplicationContext context);

    T getPresenter();

    ResourceBundle getResources();

    V getRoot();

    Stage getStage();

    String getTitle();

    void refresh();

    void setPresenter(T presenter);

    void setStage(Stage stage);
}