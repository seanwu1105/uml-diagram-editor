package ude.frontend;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import ude.Utils;
import ude.frontend.diagram.BaseShape;
import ude.frontend.diagram.ClassShape;
import ude.frontend.diagram.UseCaseShape;

import java.util.HashSet;
import java.util.Set;

public class MainScene extends Scene {
    private Mode defaultMode = Mode.SELECT;
    private ToggleGroup toggleGroup = new ToggleGroup();
    private Set<BaseShape> shapes = new HashSet<>();

    public MainScene() {
        super(new BorderPane());

        BorderPane layout = (BorderPane) getRoot();
        layout.setTop(initMenuBar());
        layout.setLeft(initTaskBar());
        layout.setCenter(initDiagram());
    }

    private Mode getCurrentMode() {
        return Mode.valueOf(((ToggleButton) toggleGroup.getSelectedToggle()).getText());
    }

    private MenuBar initMenuBar() {
        MenuBar bar = new MenuBar();
        String[] editNames = {"group", "ungroup", "change object name"};

        Menu[] menus = new Menu[2];
        menus[0] = new Menu("File");
        menus[1] = new Menu("Edit");
        for (String name : editNames) {
            menus[1].getItems().add(new MenuItem(Utils.capitalize(name)));
        }
        bar.getMenus().addAll(menus);
        return bar;
    }

    private Pane initTaskBar() {
        Pane bar = new VBox();
        for (Mode mode : Mode.values()) {
            ToggleButton btn = new ToggleButton(mode.toString());
            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            btn.setToggleGroup(toggleGroup);
            btn.setSelected(mode.equals(defaultMode));
            bar.getChildren().add(btn);
        }
        bar.setStyle("-fx-background-color: #E5E5E5;");

        return bar;
    }

    private Pane initDiagram() {
        Pane diagram = new Pane();
        diagram.setMinSize(500, 500);
        diagram.setStyle("-fx-background-color: #DDDDDD;");

        Rectangle clipper = new Rectangle();
        clipper.heightProperty().bind(diagram.heightProperty());
        clipper.widthProperty().bind(diagram.widthProperty());
        diagram.setClip(clipper);

        diagram.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            Mode currentMode = getCurrentMode();
            if (currentMode == Mode.SELECT) {
                shapes.forEach(BaseShape::deselect);
            } else {
                e.consume();
                BaseShape shape;
                if (currentMode == Mode.CLASS) {
                    shape = new ClassShape(e.getX(), e.getY());
                } else {
                    shape = new UseCaseShape(e.getX(), e.getY());
                }
                shapes.add(shape);
                shape.paint(diagram);
            }
        });
        diagram.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            Mode current = getCurrentMode();
            if (current != Mode.SELECT) {
                e.consume();
            }
        });
        return diagram;
    }

    enum Mode {SELECT, ASSOCIATE, GENERALIZE, COMPOSITE, CLASS, USE_CASE}
}