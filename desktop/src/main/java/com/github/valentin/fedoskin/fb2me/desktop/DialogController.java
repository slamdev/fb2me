package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class DialogController {

    @SuppressWarnings("unchecked")
    private static Class<? extends View<?, ?>> getViewClass(Object presenter) {
        Class<?> type;
        if (presenter instanceof Class) {
            type = (Class<?>) presenter;
        } else {
            type = presenter.getClass();
        }
        Class<?>[] interfaces = type.getInterfaces();
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
        throw new IllegalArgumentException("Passed presenter [" + presenter.getClass()
                + "] should implement any View.Presenter interface");
    }

    private final ApplicationContext context;

    public DialogController(ApplicationContext context) {
        this.context = context;
    }

    public void show(AbstractPlace place) {
        show(place, true);
    }

    public void show(AbstractPlace place, boolean modal) {
        show(modal ? new Stage() : context.stage, place, modal);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void show(final Stage stage, final AbstractPlace place, final boolean modal) {
        final View view = context.getView(getViewClass(place.getPresenterType()));
        stage.setTitle(view.getTitle());
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
            // stage.setX(x);
        }
        if (y != 0) {
            // stage.setY(y);
        }
        // /
        stage.setScene(new Scene(parent));
        ChangeListener<Number> changeListener = new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                context.optionsController.setStageSize(view);
            }
        };
        stage.showingProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                if (arg2 == true) {
                    context.eventsController.fire(new ViewEvent(view, ViewEvent.VIEW_SHOWN));
                } else {
                    context.eventsController.fire(new ViewEvent(view, ViewEvent.VIEW_HIDDEN));
                }
            }
        });
        stage.widthProperty().addListener(changeListener);
        stage.heightProperty().addListener(changeListener);
        stage.xProperty().addListener(changeListener);
        stage.yProperty().addListener(changeListener);
        stage.setOnShown(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent e) {
                place.assignPresenter(new Callback<Object, Void>() {

                    @Override
                    public Void call(Object presenter) {
                        view.setPresenter(presenter);
                        view.refresh();
                        return null;
                    }
                });
            }
        });
        if (modal) {
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(context.stage);
            stage.showAndWait();
        } else {
            stage.show();
        }
    }
}