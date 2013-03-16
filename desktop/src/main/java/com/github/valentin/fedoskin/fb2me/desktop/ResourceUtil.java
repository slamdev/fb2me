package com.github.valentin.fedoskin.fb2me.desktop;

import java.net.URL;
import java.util.ResourceBundle;

public final class ResourceUtil {

    public static URL getFxml(Class<?> type) {
        return type.getResource(type.getSimpleName() + ".fxml");
    }

    public static ResourceBundle getLocalizationBundle(Class<?> type) {
        return ResourceBundle.getBundle(type.getName());
    }

    private ResourceUtil() {
    }
}