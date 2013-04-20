package com.github.valentin.fedoskin.fb2me.desktop;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellView;

public class NavigationController {

    private static class NavigationHistory {

        private final List<Object> historyPresenters = new ArrayList<>();

        private int nextPosition;

        public NavigationHistory(ApplicationContext context) {
            context.eventsController.addEventHandler(NavigationEvent.CHANGED, new EventHandler<NavigationEvent>() {

                @Override
                public void handle(NavigationEvent e) {
                    historyPresenters.add(e.getPresenter());
                    nextPosition = historyPresenters.size() - 1;
                }
            });
        }

        public Object getNext() {
            if (historyPresenters.size() > nextPosition + 1) {
                nextPosition++;
                return historyPresenters.get(nextPosition);
            }
            return null;
        }

        public Object getPrevious() {
            if (nextPosition - 1 >= 0 && historyPresenters.size() > nextPosition - 1) {
                nextPosition--;
                return historyPresenters.get(nextPosition);
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends View<?, ?>> getViewClass(Class<?> type) {
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
        throw new IllegalArgumentException("Passed presenter should implement any View.Presenter interface");
    }

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

    private final NavigationHistory history;

    public NavigationController(ApplicationContext context) {
        this.context = context;
        history = new NavigationHistory(context);
        context.stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            private final KeyCombination backwardKeyCombination = new KeyCodeCombination(KeyCode.LEFT,
                    KeyCombination.ALT_DOWN);

            private final KeyCombination forwardKeyCombination = new KeyCodeCombination(KeyCode.RIGHT,
                    KeyCombination.ALT_DOWN);

            @Override
            public void handle(KeyEvent e) {
                if (backwardKeyCombination.match(e)) {
                    goByHistory(false);
                } else if (forwardKeyCombination.match(e)) {
                    goByHistory(true);
                }
            }
        });
    }

    private void goByHistory(boolean forward) {
        Object presenter = forward ? history.getNext() : history.getPrevious();
        if (presenter != null) {
            goToInternal(presenter);
        }
    }

    public void goTo(Object presenter) {
        goToInternal(presenter);
        context.eventsController.fire(new NavigationEvent(presenter, NavigationEvent.CHANGED));
    }

    public void goTo(Place place) {
        final View newView = context.getView(getViewClass(place.getPresenterType()));
        context.getView(ShellView.class).setContent(newView.getRoot());
        NavigationUtil.assignPresenter(context, place, new Callback<Object, Void>() {

            @Override
            public Void call(Object presenter) {
                newView.setPresenter(presenter);
                newView.refresh();
                context.eventsController.fire(new NavigationEvent(presenter, NavigationEvent.CHANGED));
                return null;
            }
        });
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void goToInternal(Object presenter) {
        View newView = context.getView(getViewClass(presenter));
        newView.setPresenter(presenter);
        context.getView(ShellView.class).setContent(newView.getRoot());
        newView.refresh();
    }
}