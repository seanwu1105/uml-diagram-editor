package io.github.seanwu1105.umldiagrameditor;

import io.github.seanwu1105.umldiagrameditor.diagram.mode.Mode;
import io.github.seanwu1105.umldiagrameditor.diagram.mode.ModeImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final Iterable<Mode> modes = List.of(new ModeImpl(), new ModeImpl(), new ModeImpl(), new ModeImpl());
    private final ToggleGroup toolToggleGroup = new ToggleGroup();
    @FXML
    private VBox toolBar;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        initializeToolBar();
    }

    private void initializeToolBar() {
        modes.forEach(mode -> {
            final var button = new RadioButton();
            button.setGraphic(new ImageView(mode.getIcon()));
            button.setToggleGroup(toolToggleGroup);
            button.getStyleClass().remove("radio-button");
            button.getStyleClass().add("toggle-button");
            toolBar.getChildren().add(button);
        });
    }
}