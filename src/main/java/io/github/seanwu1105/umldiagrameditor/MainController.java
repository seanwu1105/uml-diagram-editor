package io.github.seanwu1105.umldiagrameditor;

import io.github.seanwu1105.umldiagrameditor.canvas.Canvas;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.Mode;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.ModeFactory;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @NotNull
    private final Mode defaultMode = ModeFactory.getSelectMode();
    @NotNull
    private final Iterable<Mode> modes = List.of(
            defaultMode,
            ModeFactory.getAddClassObjectMode(),
            ModeFactory.getAddUseCaseObjectMode()
    );
    @NotNull
    private final ToggleGroup toolToggleGroup = new ToggleGroup();
    @FXML
    private VBox toolBar;
    @FXML
    private Canvas canvas;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        initializeToolBar();
    }

    private void initializeToolBar() {
        modes.forEach(mode -> {
            final var button = new RadioButton();
            button.setId(mode.getId().toString());
            button.setGraphic(new ImageView(mode.getIcon()));
            button.setToggleGroup(toolToggleGroup);
            button.setSelected(mode.equals(defaultMode));
            button.getStyleClass().remove("radio-button");
            button.getStyleClass().add("toggle-button");
            toolBar.getChildren().add(button);
        });
        toolToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            final var toggled = (Styleable) newValue;
            modes.forEach(mode -> {
                final var modeId = mode.getId();
                if (modeId.toString().equals(toggled.getId())) canvas.setMode(mode);
            });
        });
    }

    @FXML
    public void group() {
        canvas.group();
    }
}