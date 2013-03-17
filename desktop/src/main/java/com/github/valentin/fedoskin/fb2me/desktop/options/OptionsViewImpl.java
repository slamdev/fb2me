package com.github.valentin.fedoskin.fb2me.desktop.options;

import java.util.Locale;
import java.util.prefs.Preferences;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import com.github.valentin.fedoskin.fb2me.desktop.AbstractView;
import com.github.valentin.fedoskin.fb2me.desktop.ResourceUtil;

public class OptionsViewImpl extends AbstractView<OptionsView.Presenter, AnchorPane> implements OptionsView {

    @FXML
    private ComboBox<Locale> languages;

    public OptionsViewImpl(FXMLLoader loader) {
        super(loader);
    }

    @FXML
    private void apply() {
    }

    @FXML
    private void cancel() {
        getStage().close();
    }

    @Override
    public String getTitle() {
        return getResources().getString("title");
    }

    @Override
    public void refresh() {
        getRoot().getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    cancel();
                } else if (event.getCode().equals(KeyCode.ENTER)) {
                    save();
                }
            }
        });
        languages.getItems().clear();
        languages.getItems().addAll(ResourceUtil.LOCALES);
        languages.setConverter(new StringConverter<Locale>() {

            @Override
            public Locale fromString(String string) {
                return null;
            }

            @Override
            public String toString(Locale object) {
                return object.getDisplayLanguage();
            }
        });
        languages.getSelectionModel().select(ResourceUtil.getLocale());
    }

    @FXML
    private void restoreDefaults() {
    }

    @FXML
    private void save() {
        Preferences p = Preferences.userRoot().node("com.github.valentin.fedoskin.fb2me");
        p.put("language", languages.getValue().getLanguage());
        ResourceUtil.setLocale(languages.getValue());
        getPresenter().reloadViews();
        getStage().close();
    }
}