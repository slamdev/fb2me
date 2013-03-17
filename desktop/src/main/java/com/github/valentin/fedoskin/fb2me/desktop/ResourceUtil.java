package com.github.valentin.fedoskin.fb2me.desktop;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public final class ResourceUtil {

    private static Locale locale = Locale.getDefault();

    public static URL getFxml(Class<?> type) {
        return type.getResource(type.getSimpleName() + ".fxml");
    }

    public static Locale getLocale() {
        return locale;
    }

    public static ResourceBundle getLocalizationBundle(Class<?> type) {
        return ResourceBundle.getBundle(type.getName(), locale);
    }

    public static void setLocale(Locale l) {
        locale = l;
    }

    private ResourceUtil() {
    }
}