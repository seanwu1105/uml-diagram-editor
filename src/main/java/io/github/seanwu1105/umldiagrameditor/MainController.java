package io.github.seanwu1105.umldiagrameditor;

import io.github.seanwu1105.umldiagrameditor.canvas.Canvas;
import io.github.seanwu1105.umldiagrameditor.canvas.graph.GraphicComponent;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.Mode;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.ModeFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
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
    @NotNull
    private final BooleanProperty isGroupDisabled = new SimpleBooleanProperty(true);
    @NotNull
    private final BooleanProperty isUngroupDisabled = new SimpleBooleanProperty(true);
    @FXML
    private VBox toolBar;
    @FXML
    private Canvas canvas;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        initializeMenuBar();
        initializeToolBar();
    }

    private void initializeMenuBar() {
        canvas.getSelectedComponents().addListener((ListChangeListener<GraphicComponent<? extends Shape>>) c -> {
            final var components = c.getList();
            isGroupDisabled.set(components.size() <= 0);
            isUngroupDisabled.set(components.size() <= 1);
        });
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

    @SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
    public boolean getIsGroupDisabled() {
        return isGroupDisabled.get();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public void setIsGroupDisabled(final boolean value) {
        isGroupDisabled.set(value);
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    @NotNull
    public BooleanProperty isGroupDisabledProperty() {
        return isGroupDisabled;
    }

    @SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
    public boolean getIsUngroupDisabled() {
        return isUngroupDisabled.get();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public void setIsUngroupDisabled(final boolean value) {
        isUngroupDisabled.set(value);
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    @NotNull
    public BooleanProperty isUngroupDisabledProperty() {
        return isUngroupDisabled;
    }

    @FXML
    public void group() {
        canvas.group();
    }

    @FXML
    public void ungroup() {
        canvas.ungroup();
    }
}