package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellView;

public final class NavigationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationUtil.class);

    public static void assignPresenter(final ApplicationContext context, final Place place,
            final Callback<Object, Void> receiver) {
        Task<Object> task = new Task<Object>() {

            @Override
            protected Object call() {
                LOGGER.debug(place.getPresenterType() + " : start creation");
                return place.getPresenter();
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent e) {
                LOGGER.debug(place.getPresenterType() + " : end   creation");
                receiver.call(e.getSource().getValue());
                context.getView(ShellView.class).getProgressProperty().unbind();
                context.getView(ShellView.class).getProgressProperty().setValue(1);
            }
        });
        context.getView(ShellView.class).getProgressProperty().unbind();
        context.getView(ShellView.class).getProgressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private NavigationUtil() {
    }
}