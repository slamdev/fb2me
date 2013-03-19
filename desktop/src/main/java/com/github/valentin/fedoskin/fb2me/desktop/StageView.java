package com.github.valentin.fedoskin.fb2me.desktop;

import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public abstract class StageView<T, V extends Pane> extends AbstractView<T, V> {

    public StageView(FXMLLoader loader) {
        super(loader);
    }

    @Override
    public void afterReload(ApplicationContext context, Map<String, Object> viewData) {
        super.afterReload(context, viewData);
        ((Scene) viewData.get("SCENE")).setRoot(getRoot());
        getStage().setTitle(getTitle());
    }

    @Override
    public Map<String, Object> beforeReload(ApplicationContext context) {
        Map<String, Object> viewData = super.beforeReload(context);
        Scene scene = getRoot().getScene();
        // TODO: need to find a proper way to deattach node from the old scene
        scene.setRoot(new Label());
        viewData.put("SCENE", scene);
        return viewData;
    }
}