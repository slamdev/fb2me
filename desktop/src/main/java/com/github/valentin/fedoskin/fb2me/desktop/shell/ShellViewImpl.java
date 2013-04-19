package com.github.valentin.fedoskin.fb2me.desktop.shell;

import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;

import com.github.valentin.fedoskin.fb2me.desktop.ApplicationContext;
import com.github.valentin.fedoskin.fb2me.desktop.StageView;
import com.github.valentin.fedoskin.fb2me.desktop.View;

public class ShellViewImpl extends StageView<ShellView.Presenter, BorderPane> implements ShellView {

    @FXML
    private ProgressIndicator progressIndicator;

    public ShellViewImpl(FXMLLoader loader) {
        super(loader);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterReload(ApplicationContext context, Map<String, Object> viewData) {
        super.afterReload(context, viewData);
        View<?, ?> view = context.getView((Class<View<?, ?>>) viewData.get("CONTENT"));
        setContent(view.getRoot());
    }

    @Override
    public Map<String, Object> beforeReload(ApplicationContext context) {
        Map<String, Object> viewData = super.beforeReload(context);
        Node node = getRoot().getCenter();
        viewData.put("CONTENT", context.viewController.getView(node).getClass());
        return viewData;
    }

    @Override
    public DoubleProperty getProgressProperty() {
        return progressIndicator.progressProperty();
    }

    @Override
    public void refresh() {
        super.refresh();
        progressIndicator.setProgress(1.0);
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