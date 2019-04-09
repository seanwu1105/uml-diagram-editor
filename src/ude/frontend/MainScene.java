package ude.frontend;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ude.Utils;
import ude.frontend.diagram.*;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class MainScene extends Scene {
    private Mode defaultMode = Mode.SELECT;
    private ToggleGroup toggleGroup = new ToggleGroup();
    private Set<UmlBaseShape> shapes = new HashSet<>();
    private Set<AssociationLine> lines = new HashSet<>();
    private Paintable newUmlItem;

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
        toggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> deselectAll());
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
        // TODO: factorize this function into adding different event listeners for different study cases

        diagram.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            Mode currentMode = getCurrentMode();
            if (currentMode == Mode.SELECT) {
                deselectAll();
            } else {
                e.consume();
                newUmlItem = currentMode.getNewPaintable(e.getX(), e.getY());
                if (newUmlItem instanceof AssociationLine) {
                    UmlBaseShape lineSource;
                    try {
                        lineSource = getUmlShape((Shape) e.getTarget());
                    } catch (ClassCastException | NoSuchElementException exception) {
                        return;
                    }
                    // else
                    List<ObservableValue<Number>> sourcePortPosition = lineSource.getClosestPortPositionProperty(e.getX(), e.getY());
                    ((AssociationLine) newUmlItem).startXProperty().bind(sourcePortPosition.get(0));
                    ((AssociationLine) newUmlItem).startYProperty().bind(sourcePortPosition.get(1));
                    lines.add((AssociationLine) newUmlItem);
                    newUmlItem.paint(diagram);
                } else {
                    shapes.add((UmlBaseShape) newUmlItem);
                    newUmlItem.paint(diagram);
                }
            }
        });

        diagram.addEventFilter(MouseEvent.DRAG_DETECTED, e -> {
            diagram.startFullDrag();
        });

        diagram.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            if (getCurrentMode() == Mode.SELECT) {
                // TODO: group selection
            } else
                e.consume();
        });

        diagram.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (getCurrentMode() == Mode.SELECT)
                lines.forEach(AssociationLine::toFront);
        });

        diagram.addEventHandler(MouseDragEvent.MOUSE_DRAG_OVER, e -> {
            if (getCurrentMode() != Mode.SELECT && newUmlItem instanceof AssociationLine) {
                UmlBaseShape lineTarget;
                try {
                    lineTarget = getUmlShape((Shape) e.getTarget());
                } catch (ClassCastException | NoSuchElementException exception) {
                    return;
                }
                // else
                List<ObservableValue<Number>> sourcePortPosition = lineTarget.getClosestPortPositionProperty(e.getX(), e.getY());
                ((AssociationLine) newUmlItem).endXProperty().bind(sourcePortPosition.get(0));
                ((AssociationLine) newUmlItem).endYProperty().bind(sourcePortPosition.get(1));
            }
        });
    }

    private UmlBaseShape getUmlShape(Shape shape) {
        for (UmlBaseShape umlShape : shapes)
            if (umlShape.shape == shape)
                return umlShape;
        throw new NoSuchElementException("Cannot find the UmlShape in shapes set.");
    }

    private void deselectAll() {
        shapes.forEach(UmlBaseShape::deselect);
        lines.forEach(AssociationLine::toFront);
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