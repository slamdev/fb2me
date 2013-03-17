package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.scene.Node;

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
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void goTo(Object presenter) {
        View newView = context.getView(getViewClass(presenter));
        newView.setPresenter(presenter);
        context.getView(ShellView.class).setContent((Node) newView.getRoot());
        newView.refresh();
    }
}