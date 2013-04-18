package com.github.valentin.fedoskin.fb2me.desktop;

import java.util.ArrayDeque;
import java.util.Deque;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellView;

public class NavigationController {

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

    public NavigationController(ApplicationContext context) {
        this.context = context;
        context.stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            private final KeyCombination backwardKeyCombination = new KeyCodeCombination(KeyCode.LEFT,
                    KeyCombination.ALT_DOWN);

            private final KeyCombination forwardKeyCombination = new KeyCodeCombination(KeyCode.RIGHT,
                    KeyCombination.ALT_DOWN);

            @Override
            public void handle(KeyEvent e) {
                if (backwardKeyCombination.match(e)) {
                    goBackward();
                } else if (forwardKeyCombination.match(e)) {
                    goForward();
                }
            }
        });
    }

    private final Deque<Object> backwardPresenters = new ArrayDeque<>();

    private final Deque<Object> forwardPresenters = new ArrayDeque<>();

    public void goBackward() {
        if (!backwardPresenters.isEmpty()) {
            // Object p = backwardPresenters.pop();
            Object p = backwardPresenters.pollLast();
            goTo2(p);
            // forwardPresenters.push(p);
            forwardPresenters.offer(p);
            System.out.println("back");
        }
    }

    public void goForward() {
        if (!forwardPresenters.isEmpty()) {
            // Object p = forwardPresenters.pop();
            Object p = forwardPresenters.pollLast();
            goTo2(p);
            // backwardPresenters.push(p);
            backwardPresenters.offer(p);
            System.out.println("forward");
        }
    }

    private void goTo2(Object presenter) {
        View newView = context.getView(getViewClass(presenter));
        newView.setPresenter(presenter);
        context.getView(ShellView.class).setContent(newView.getRoot());
        newView.refresh();
    }

    private Object lastPresenter;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void goTo(Object presenter) {
        View newView = context.getView(getViewClass(presenter));
        newView.setPresenter(presenter);
        context.getView(ShellView.class).setContent(newView.getRoot());
        newView.refresh();
        if (lastPresenter != null) {
            backwardPresenters.offer(lastPresenter);
            // backwardPresenters.push(presenter);
        }
        forwardPresenters.clear();
        lastPresenter = presenter;
    }
}