package com.github.valentin.fedoskin.fb2me.desktop;

import java.net.URL;

public final class ResourceUtil {

    public static URL getFxml(Class<?> type) {
        return type.getResource(type.getSimpleName() + ".fxml");
    }

    private ResourceUtil() {
    }
}