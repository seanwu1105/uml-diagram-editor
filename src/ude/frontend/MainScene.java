package ude.frontend;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import ude.Utils;
import ude.frontend.diagram.*;

import java.util.Arrays;
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
            RadioButton btn = new RadioButton(mode.toString());
            btn.getStyleClass().remove("radio-button");
            btn.getStyleClass().add("toggle-button");
            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            btn.setToggleGroup(toggleGroup);
            btn.setSelected(mode.equals(defaultMode));
            bar.getChildren().add(btn);
        }
        toggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> shapes.forEach(BaseShape::deselect));
        bar.setStyle("-fx-background-color: #E5E5E5;");
        return bar;
    }

    private Pane initDiagram() {
        // TODO: this function should be factorized into smaller pieces
        Pane diagram = new Pane();
        diagram.setMinSize(500, 500);
        diagram.setStyle("-fx-background-color: #DDDDDD;");

        Rectangle clipper = new Rectangle();
        clipper.heightProperty().bind(diagram.heightProperty());
        clipper.widthProperty().bind(diagram.widthProperty());
        diagram.setClip(clipper);

        initMouseEventListener(diagram);
        return diagram;
    }

    private void initMouseEventListener(Pane diagram) {
        diagram.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            Mode currentMode = getCurrentMode();
            if (currentMode == Mode.SELECT) {
                shapes.forEach(BaseShape::deselect);
            } else {
                e.consume();
                Paintable newItem;
                if (Arrays.asList(Mode.ASSOCIATE, Mode.GENERALIZE, Mode.COMPOSITE).contains(currentMode)) {
                    System.out.println("PRESSED");
                    // TODO: check which shape in shapes has e.getTarget() as .shape member
                    newItem = currentMode.getNewPaintable(e.getX(), e.getY());
                } else {
                    newItem = currentMode.getNewPaintable(e.getX(), e.getY());
                    shapes.add((BaseShape) newItem);
                }
                newItem.paint(diagram);
            }
        });

        diagram.addEventFilter(MouseEvent.DRAG_DETECTED, e -> {
            diagram.startFullDrag();
        });

        diagram.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            Mode currentMode = getCurrentMode();
            if (currentMode == Mode.SELECT) {
                // TODO: group selection
            } else {
                e.consume();
            }
        });

        diagram.addEventHandler(MouseDragEvent.MOUSE_DRAG_ENTERED_TARGET, e -> {
            System.out.println(e.getTarget());
            Mode currentMode = getCurrentMode();
            if (currentMode == Mode.SELECT) {
                // TODO: ???
            } else {
                e.consume();
                Paintable newLine;
            }
        });
    }

    enum Mode implements paintableCreator {
        SELECT {
            @Override
            public Paintable getNewPaintable(double x, double y) {
                throw new NullPointerException("The current mode cannot create new paintable object.");
            }
        }, ASSOCIATE {
            @Override
            public Paintable getNewPaintable(double x, double y) {
                return new AssociationLine(x, y);
            }
        }, GENERALIZE {
            @Override
            public Paintable getNewPaintable(double x, double y) {
                return new GeneralizationLine(x, y);
            }
        }, COMPOSITE {
            @Override
            public Paintable getNewPaintable(double x, double y) {
                return new CompositionLine(x, y);
            }
        }, CLASS {
            @Override
            public Paintable getNewPaintable(double x, double y) {
                return new ClassShape(x, y);
            }
        }, USE_CASE {
            @Override
            public Paintable getNewPaintable(double x, double y) {
                return new UseCaseShape(x, y);
            }
        }
    }

    private interface paintableCreator {
        Paintable getNewPaintable(double x, double y);
    }
}