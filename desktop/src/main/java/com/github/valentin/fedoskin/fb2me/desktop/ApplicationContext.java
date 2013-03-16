package com.github.valentin.fedoskin.fb2me.desktop;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import com.github.valentin.fedoskin.fb2me.desktop.options.OptionsView;
import com.github.valentin.fedoskin.fb2me.desktop.options.OptionsViewImpl;
import com.github.valentin.fedoskin.fb2me.desktop.reader.ReaderView;
import com.github.valentin.fedoskin.fb2me.desktop.reader.ReaderViewImpl;
import com.github.valentin.fedoskin.fb2me.desktop.shelf.ShelfView;
import com.github.valentin.fedoskin.fb2me.desktop.shelf.ShelfViewImpl;
import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellPresenter;
import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellView;
import com.github.valentin.fedoskin.fb2me.desktop.shell.ShellViewImpl;

public class ApplicationContext {

    private static <T> T loadController(Class<T> type) {
        FXMLLoader loader = new FXMLLoader(ResourceUtil.getFxml(type), ResourceUtil.getLocalizationBundle(type));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader.getController();
    }

    public final DialogController dialogController = new DialogController(this);

    public final NavigationController navigationController = new NavigationController(this);

    public final OptionsView optionsView = loadController(OptionsViewImpl.class);

    public final ReaderView readerView = loadController(ReaderViewImpl.class);

    public final ShelfView shelfView = loadController(ShelfViewImpl.class);

    public final ShellView shellView = loadController(ShellViewImpl.class);

    public final Stage stage;

    public ApplicationContext(Stage stage) {
        this.stage = stage;
        shellView.setPresenter(new ShellPresenter(this));
    }
}