package com.github.valentin.fedoskin.fb2me.desktop;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.Callback;

import com.github.valentin.fedoskin.fb2me.desktop.options.OptionsViewImpl;
import com.github.valentin.fedoskin.fb2me.desktop.reader.ReaderViewImpl;
import com.github.valentin.fedoskin.fb2me.desktop.shelf.ShelfViewImpl;
import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellViewImpl;

public class ViewController {

    private static class ControllerFactory implements Callback<Class<?>, Object> {

        private final FXMLLoader loader;

        public ControllerFactory(FXMLLoader loader) {
            this.loader = loader;
        }

        @Override
        public Object call(Class<?> type) {
            Constructor<?>[] constructors = type.getConstructors();
            for (Constructor<?> c : constructors) {
                if (c.getParameterTypes().length == 1 && c.getParameterTypes()[0].equals(loader.getClass())) {
                    try {
                        return c.newInstance(loader);
                    } catch (ReflectiveOperationException | IllegalArgumentException e) {
                    }
                }
            }
            return null;
        }
    }

    private static final Class<?>[] CLASSES = { ShellViewImpl.class, ShelfViewImpl.class, OptionsViewImpl.class,
            ReaderViewImpl.class };

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Class getViewImplementationClass(Class type) {
        for (Class t : CLASSES) {
            if (type.isAssignableFrom(t)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unable to find corresponding implementation for the provided view " + type);
    }

    private final ApplicationContext context;

    private final Set<View<?, ?>> views = new HashSet<>();

    public ViewController(ApplicationContext context) {
        this.context = context;
        load(Arrays.asList(CLASSES));
    }

    @SuppressWarnings("unchecked")
    public <T extends View<?, ?>> T getView(Class<T> type) {
        if (type.isInterface()) {
            type = getViewImplementationClass(type);
        }
        for (View<?, ?> view : views) {
            if (view.getClass().equals(type)) {
                return type.cast(view);
            }
        }
        return createView(type);
    }

    public View<?, ?> getView(Node node) {
        for (View<?, ?> view : views) {
            if (view.getRoot().equals(node)) {
                return view;
            }
        }
        throw new IllegalArgumentException("Unable to find view by the passed root element " + node);
    }

    public void reload() {
        Map<Class<?>, Map<String, Object>> viewsData = new HashMap<>();
        List<Class<?>> viewsClasses = new ArrayList<>();
        for (View<?, ?> view : views) {
            viewsData.put(view.getClass(), view.beforeReload(context));
            viewsClasses.add(view.getClass());
        }
        views.clear();
        load(viewsClasses);
        for (View<?, ?> view : views) {
            view.afterReload(context, viewsData.get(view.getClass()));
        }
        for (View<?, ?> view : views) {
            view.refresh();
        }
    }

    private <T extends View<?, ?>> T createView(Class<T> type) {
        FXMLLoader loader = new FXMLLoader(ResourceUtil.getFxml(type), ResourceUtil.getLocalizationBundle(type));
        loader.setControllerFactory(new ControllerFactory(loader));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        T view = loader.getController();
        view.setStage(context.stage);
        views.add(view);
        return view;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void load(List<Class<?>> classes) {
        for (Class type : classes) {
            createView(type);
        }
    }
}