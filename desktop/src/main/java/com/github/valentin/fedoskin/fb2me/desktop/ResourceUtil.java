package com.github.valentin.fedoskin.fb2me.desktop;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ResourceUtil {

    public static final Locale[] LOCALES = { Locale.forLanguageTag("en"), Locale.forLanguageTag("ru") };

    private static final String DEFAULT_CONFIG_RESOURCE = Main.class.getPackage().getName() + ".fb2me";

    private static Locale locale = Locale.getDefault();

    /**
     * match ${ENV_VAR_NAME} or $ENV_VAR_NAME
     */
    private static final Pattern SYSTEM_VARIABLE_PATTERN = Pattern.compile("\\$\\{(\\S+)\\}");
    static {
        Preferences p = Preferences.userRoot().node("com.github.valentin.fedoskin.fb2me");
        String l = p.get("language", Locale.getDefault().getLanguage());
        locale = Locale.forLanguageTag(l);
    }

    public static ResourceBundle getConfig(String name) {
        URL userConfig = ResourceUtil.class.getResource("/" + name + ".properties");
        if (userConfig != null) {
            return ResourceBundle.getBundle(name);
        }
        return ResourceBundle.getBundle(name);
    }

    public static String getConfigProperty(String key) {
        return getConfigProperty(DEFAULT_CONFIG_RESOURCE, key);
    }

    public static String getConfigProperty(String configName, String key) {
        ResourceBundle c = getConfig(configName);
        if (!c.containsKey(key)) {
            return null;
        }
        String v = getConfig(configName).getString(key);
        return resolveSystemVariables(v);
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

    /**
     * Returns input string with system variables references expanded, e.g. ${SOME_VAR}
     */
    private static String resolveSystemVariables(String input) {
        if (input == null) {
            return null;
        }
        String value = input;
        Matcher m = SYSTEM_VARIABLE_PATTERN.matcher(value);
        while (m.find()) {
            String variableName = null == m.group(1) ? m.group(2) : m.group(1);
            String variableValue = System.getProperty(variableName);
            if (variableValue != null) {
                value = value.replace("${" + variableName + "}", variableValue);
            }
        }
        return value;
    }

    private ResourceUtil() {
    }
}