package com.github.valentin.fedoskin.fb2me.desktop;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

import javafx.stage.Stage;

import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellPresenter;
import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellView;

public class ApplicationContext {

    public final DialogController dialogController;

    public final EventsController eventsController = new EventsController();

    public final NavigationController navigationController;

    public final OptionsController optionsController;

    public final Preferences preferences = Preferences.userRoot().node("com.github.valentin.fedoskin.fb2me");

    public final Stage stage;

    public final ViewController viewController;

    public ApplicationContext(Stage stage) {
        this.stage = stage;
        optionsController = new OptionsController(this);
        ResourceUtil.setLocale(optionsController.getLanguage());
        viewController = new ViewController(this);
        dialogController = new DialogController(this);
        navigationController = new NavigationController(this);
        viewController.getView(ShellView.class).setPresenter(new ShellPresenter(this));
        preferences.addPreferenceChangeListener(new PreferenceChangeListener() {

            @Override
            public void preferenceChange(PreferenceChangeEvent evt) {
                try {
                    evt.getNode().exportSubtree(System.out);
                } catch (IOException | BackingStoreException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public <T extends View<?, ?>> T getView(Class<T> type) {
        return viewController.getView(type);
    }
}