package com.github.valentin.fedoskin.fb2me.desktop.reader;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import com.github.valentin.fedoskin.fb2me.desktop.AbstractView;
import com.github.valentin.fedoskin.fb2me.desktop.ApplicationContext;

public class ReaderViewImpl extends AbstractView<ReaderView.Presenter, AnchorPane> implements ReaderView {

    @FXML
    private Label label;

    public ReaderViewImpl(FXMLLoader loader) {
        super(loader);
    }

    @Override
    public void afterReload(ApplicationContext context, Map<String, Object> viewData) {
        super.afterReload(context, viewData);
        label.setText((String) viewData.get("L"));
    }

    @Override
    public Map<String, Object> beforeReload(ApplicationContext context) {
        Map<String, Object> viewData = super.beforeReload(context);
        viewData.put("L", label.getText());
        return viewData;
    }

    @Override
    public void refresh() {
        label.textProperty().set(getPresenter().getText());
        // WebView w = new WebView();
        // WebEngine e = w.getEngine();
        // StringBuilder sb = new StringBuilder();
        // e.setUserStyleSheetLocation(getClass().getResource("sumatra.css").toExternalForm());
        // // sb.append("<style>.red{color:red;}</style>");
        // for (int i = 0; i < 100; i++) {
        // sb.append("<div class='red'><b>aaa</b></div>");
        // sb.append("<p />");
        // sb.append("<i>aaa</i>");
        // sb.append("<p />");
        // }
        // e.load(getClass().getResource("aaa.fb2.html").toExternalForm());
        // // e.loadContent(sb.toString());
        // getRoot().getChildren().add(w);
    }
}