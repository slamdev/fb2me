package com.github.valentin.fedoskin.fb2me.desktop.shell;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.github.valentin.fedoskin.fb2me.desktop.AbstractView;
import com.github.valentin.fedoskin.fb2me.desktop.ApplicationContext;
import com.github.valentin.fedoskin.fb2me.desktop.View;

public class ShellViewImpl extends AbstractView<ShellView.Presenter, BorderPane> implements ShellView {

    public ShellViewImpl(FXMLLoader loader) {
        super(loader);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterReload(ApplicationContext context, Map<String, Object> viewData) {
        super.afterReload(context, viewData);
        ((Scene) viewData.get("S")).setRoot(getRoot());
        View<?, ?> view = context.getView((Class<View<?, ?>>) viewData.get("V"));
        setContent((Node) view.getRoot());
    }

    @Override
    public Map<String, Object> beforeReload(ApplicationContext context) {
        Map<String, Object> viewData = super.beforeReload(context);
        Scene scene = getRoot().getScene();
        // TODO: need to find a proper way to deattach node from the old scene
        scene.setRoot(new Label());
        viewData.put("S", scene);
        Node node = getRoot().getCenter();
        viewData.put("V", context.viewController.getView(node).getClass());
        return viewData;
    }

    @Override
    public void setContent(Node content) {
        getRoot().setCenter(content);
    }

    @FXML
    private void close() {
        getPresenter().close();
    }

    @FXML
    private void openOptions() {
        getPresenter().showOptions();
    }

    @FXML
    private void openReader() {
        getPresenter().goToReader();
    }

    @FXML
    private void openShelf() {
        getPresenter().goToShelf();
    }
}