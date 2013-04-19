package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellView;

public abstract class AbstractPlace {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPlace.class);

    protected ApplicationContext context;

    public AbstractPlace(ApplicationContext context) {
        this.context = context;
    }

    public void assignPresenter(final Callback<Object, Void> receiver) {
        Task<Object> task = new Task<Object>() {

            @Override
            protected Object call() {
                LOGGER.debug(getPresenterType() + " : start creation");
                return getPresenter();
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent e) {
                LOGGER.debug(getPresenterType() + " : end   creation");
                receiver.call(e.getSource().getValue());
                context.getView(ShellView.class).getProgressProperty().unbind();
                context.getView(ShellView.class).getProgressProperty().setValue(1);
            }
        });
        context.getView(ShellView.class).getProgressProperty().unbind();
        context.getView(ShellView.class).getProgressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public abstract Class<?> getPresenterType();

    protected abstract Object getPresenter();
}