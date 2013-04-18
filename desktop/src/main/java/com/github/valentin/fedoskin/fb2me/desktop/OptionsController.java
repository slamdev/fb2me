package com.github.valentin.fedoskin.fb2me.desktop;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import java.util.prefs.Preferences;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.valentin.fedoskin.fb2me.desktop.Options.StageSize;

public class OptionsController {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String OPTIONS_NODE = "options";
    static {
        MAPPER.setSerializationInclusion(Include.NON_EMPTY);
        MAPPER.setSerializationInclusion(Include.NON_DEFAULT);
    }

    private static Options unserializeOptions() {
        Preferences preferences = Preferences.userRoot().node(ResourceUtil.getConfigProperty("preferences-node"));
        String serializedOptions = preferences.get(OPTIONS_NODE, "");
        if (serializedOptions.isEmpty()) {
            return new Options();
        }
        try {
            return MAPPER.readValue(serializedOptions, Options.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    private final ApplicationContext context;

    private Options options;

    public OptionsController(ApplicationContext context) {
        this.context = context;
        options = unserializeOptions();
    }

    public Locale getLanguage() {
        if (options.getLanguage().isEmpty()) {
            return Locale.getDefault();
        }
        return Locale.forLanguageTag(options.getLanguage());
    }

    public double getStageH(View<?, ?> view) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double max = bounds.getHeight();
        StageSize stageSize = getStageSizeForView(view);
        return stageSize.h * max;
    }

    public double getStageW(View<?, ?> view) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double max = bounds.getWidth();
        StageSize stageSize = getStageSizeForView(view);
        return stageSize.w * max;
    }

    public double getStageX(View<?, ?> view) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double max = bounds.getWidth();
        StageSize stageSize = getStageSizeForView(view);
        return stageSize.x * max;
    }

    public double getStageY(View<?, ?> view) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double max = bounds.getHeight();
        StageSize stageSize = getStageSizeForView(view);
        return stageSize.y * max;
    }

    public void resetOptions() {
        options = new Options();
        serializeOptions();
    }

    public void setLanguage(Locale value) {
        boolean changed = !options.getLanguage().equals(value.getLanguage());
        if (changed) {
            options.setLanguage(value.getLanguage());
            ResourceUtil.setLocale(value);
            serializeOptions();
        }
    }

    public void setStageSize(View<?, ?> view) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double maxH = bounds.getHeight();
        double maxW = bounds.getWidth();
        double h = view.getStage().getHeight() / maxH;
        double w = view.getStage().getWidth() / maxW;
        double x = view.getStage().getX() / maxW;
        double y = view.getStage().getY() / maxH;
        Class<?> type = view.getClass();
        StageSize stageSize = new StageSize(type, w, h, x, y);
        boolean changed;
        if (options.getStageSizes().contains(stageSize)) {
            changed = getStageSizeForView(view).compareTo(stageSize) != 0;
            options.getStageSizes().remove(stageSize);
        } else {
            changed = true;
        }
        if (changed) {
            options.getStageSizes().add(stageSize);
            serializeOptions();
        }
    }

    private StageSize getStageSizeForView(View<?, ?> view) {
        Set<StageSize> sizes = options.getStageSizes();
        for (StageSize s : sizes) {
            if (s.type.equals(view.getClass())) {
                return s;
            }
        }
        return new StageSize(view.getClass());
    }

    private void serializeOptions() {
        Preferences preferences = Preferences.userRoot().node(ResourceUtil.getConfigProperty("preferences-node"));
        try {
            preferences.put(OPTIONS_NODE, MAPPER.writeValueAsString(options));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}