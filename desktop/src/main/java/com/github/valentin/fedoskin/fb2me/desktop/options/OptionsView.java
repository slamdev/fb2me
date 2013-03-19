package com.github.valentin.fedoskin.fb2me.desktop.options;

import java.util.Locale;

import javafx.scene.layout.AnchorPane;

import com.github.valentin.fedoskin.fb2me.desktop.View;
import com.github.valentin.fedoskin.fb2me.desktop.options.OptionsView.Presenter;

public interface OptionsView extends View<Presenter, AnchorPane> {

    interface Presenter {

        void changeLanguageOption(Locale value);

        void reloadViews();
    }
}