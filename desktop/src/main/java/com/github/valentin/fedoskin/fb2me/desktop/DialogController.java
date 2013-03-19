package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogController {

    @SuppressWarnings("unchecked")
    private static Class<? extends View<?, ?>> getViewClass(Object presenter) {
        Class<?>[] interfaces = presenter.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new IllegalArgumentException("Passed presenter should implement any View.Presenter interface");
        }
        for (Class<?> t : interfaces) {
            if (t.getDeclaringClass() != null) {
                if (View.class.isAssignableFrom(t.getDeclaringClass())) {
                    return (Class<? extends View<?, ?>>) t.getDeclaringClass();
                }
            }
        }
        throw new IllegalArgumentException("Passed presenter should implement any View.Presenter interface");
    }

    private final ApplicationContext context;

    public DialogController(ApplicationContext context) {
        this.context = context;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void show(Object presenter) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context.stage);
        final View view = context.getView(getViewClass(presenter));
        view.setPresenter(presenter);
        view.setStage(stage);
        Parent parent = view.getRoot();
        if (parent.getScene() != null) {
            // TODO: need to find a proper way to deattach node from the old scene
            parent.getScene().setRoot(new Label());
        }
        // set bounds
        double h = context.optionsController.getStageH(view);
        double w = context.optionsController.getStageW(view);
        double x = context.optionsController.getStageX(view);
        double y = context.optionsController.getStageY(view);
        if (h != 0) {
            stage.setHeight(h);
        }
        if (w != 0) {
            stage.setWidth(w);
        }
        if (x != 0) {
            stage.setX(x);
        }
        if (y != 0) {
            stage.setY(y);
        }
        // /
        stage.setScene(new Scene(parent));
        stage.setTitle(view.getTitle());
        view.refresh();
        ChangeListener<Number> changeListener = new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                context.optionsController.setStageSize(view);
            }
        };
        stage.widthProperty().addListener(changeListener);
        stage.heightProperty().addListener(changeListener);
        stage.xProperty().addListener(changeListener);
        stage.yProperty().addListener(changeListener);
        stage.showAndWait();
    }
}