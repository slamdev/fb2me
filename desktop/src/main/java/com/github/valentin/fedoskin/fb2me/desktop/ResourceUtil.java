package com.github.valentin.fedoskin.fb2me.desktop;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public final class ResourceUtil {

    public static final Locale[] LOCALES = { Locale.forLanguageTag("en"), Locale.forLanguageTag("ru") };

    private static Locale locale = Locale.getDefault();
    static {
        Preferences p = Preferences.userRoot().node("com.github.valentin.fedoskin.fb2me");
        String l = p.get("language", Locale.getDefault().getLanguage());
        locale = Locale.forLanguageTag(l);
    }

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