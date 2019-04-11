package ude.frontend;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ude.backend.CompositeObject;
import ude.backend.UmlObject;
import ude.frontend.diagram.Paintable;
import ude.frontend.diagram.Selectable;
import ude.frontend.diagram.SelectingArea;
import ude.frontend.diagram.ShapeGroup;
import ude.frontend.diagram.line.AssociationLine;
import ude.frontend.diagram.line.CompositionLine;
import ude.frontend.diagram.line.GeneralizationLine;
import ude.frontend.diagram.shape.ClassShape;
import ude.frontend.diagram.shape.UmlBaseShape;
import ude.frontend.diagram.shape.UseCaseShape;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class MainScene extends Scene {
    private final Mode defaultMode = Mode.SELECT;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final Set<UmlBaseShape> shapes = new HashSet<>();
    private final Set<AssociationLine> lines = new HashSet<>();
    private final ObservableSet<UmlBaseShape> selectedShapes = FXCollections.observableSet(new HashSet<>());
    private final ObservableSet<Selectable> selectedRoots = FXCollections.observableSet(new HashSet<>());
    private SelectingArea selectingArea = null;
    private Paintable newUmlItem;

    public MainScene() {
        super(new BorderPane());

        BorderPane layout = (BorderPane) getRoot();
        layout.setTop(initMenuBar());
        layout.setLeft(initTaskBar());
        layout.setCenter(initDiagram());

        selectedShapes.addListener((SetChangeListener<? super UmlBaseShape>) c -> updateSelectedRoots());

        selectedRoots.addListener((SetChangeListener<? super Selectable>) c -> System.out.println(c.getSet()));
    }

    private MenuBar initMenuBar() {
        MenuBar bar = new MenuBar();

        Menu[] menus = new Menu[2];
        menus[0] = new Menu("File");
        menus[1] = new Menu("Edit");
        MenuItem[] editMenuItems = new MenuItem[3];
        editMenuItems[0] = new MenuItem("Group");
        editMenuItems[1] = new MenuItem("Ungroup");
        editMenuItems[2] = new MenuItem("Change object name");
        menus[1].getItems().addAll(editMenuItems);
        bar.getMenus().addAll(menus);

        editMenuItems[0].disableProperty().bind(Bindings.createBooleanBinding(
                // enabled when more than 1 UML shapes are selected
                () -> selectedRoots.size() <= 1, selectedRoots
        ));
        editMenuItems[1].disableProperty().bind(Bindings.createBooleanBinding(
                // enabled when exactly 1 composite object is selected
                () -> selectedRoots.size() != 1 || selectedShapes.size() == 1, selectedRoots, selectedShapes
        ));
        editMenuItems[2].disableProperty().bind(Bindings.createBooleanBinding(
                // enabled when exactly 1 basic object is selected
                () -> selectedShapes.size() != 1, selectedShapes
        ));

        editMenuItems[0].setOnAction(e -> {
            ShapeGroup newGroup = new ShapeGroup();
            for (UmlBaseShape selectedShape : selectedShapes) {
                UmlObject root = CompositeObject.getRoot(selectedShape);
                if (root != newGroup) newGroup.add(root);
            }
            updateSelectedRoots();
        });
        editMenuItems[1].setOnAction(e -> {
            if (selectedRoots.size() != 1 || selectedShapes.size() == 1) {
                throw new RuntimeException("Ungrouping could only be applied when exactly one group is selected.");
            } else {
                // TODO: unGroup
            }
        });

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
                if (e.getTarget() == diagram) { // for group selection (create selecting area rectangle)
                    selectingArea = new SelectingArea(e.getX(), e.getY());
                    diagram.getChildren().add(selectingArea);
                }
            } else {
                e.consume();    // only in SELECT mode can UML shapes be selected (get MOUSE_PRESSED event)
                newUmlItem = currentMode.getNewPaintable(e.getX(), e.getY());
                if (newUmlItem instanceof AssociationLine) {
                    UmlBaseShape lineSource;
                    try {
                        lineSource = getUmlShape((Shape) e.getTarget());
                    } catch (ClassCastException | NoSuchElementException exception) {
                        return;
                    }
                    // else: create new UML connection
                    List<ObservableValue<Number>> sourcePortPosition = lineSource.getClosestPortPositionProperty(e.getX(), e.getY());
                    ((AssociationLine) newUmlItem).startXProperty().bind(sourcePortPosition.get(0));
                    ((AssociationLine) newUmlItem).startYProperty().bind(sourcePortPosition.get(1));
                    lines.add((AssociationLine) newUmlItem);
                    newUmlItem.paint(diagram);
                } else {    // create new UML shape
                    shapes.add((UmlBaseShape) newUmlItem);
                    newUmlItem.paint(diagram);
                }
            }
        });

        diagram.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (getCurrentMode() == Mode.SELECT)
                updateSelectedSet();
        });

        diagram.addEventFilter(MouseEvent.DRAG_DETECTED, e -> diagram.startFullDrag());

        diagram.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            if (getCurrentMode() == Mode.SELECT) {
                if (selectingArea != null) {
                    // resize selecting area and check which UML shape could be selected
                    selectingArea.setCursorX(e.getX());
                    selectingArea.setCursorY(e.getY());
                } // else: let the MOUSE_DRAGGED event pass to UmlBaseShape
            } else    // only in SELECT mode can UmlShapes be dragged
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

        diagram.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            if (getCurrentMode() == Mode.SELECT && selectingArea != null) {
                for (UmlBaseShape shape : shapes)
                    if (shape.isInside(e.getX(), e.getY(), selectingArea.initX, selectingArea.initY))
                        shape.select();
                updateSelectedSet();
                diagram.getChildren().remove(selectingArea);
                selectingArea = null;
            }
        });
    }

    private Mode getCurrentMode() {
        return Mode.valueOf(((ToggleButton) toggleGroup.getSelectedToggle()).getText());
    }

    private UmlBaseShape getUmlShape(Shape shape) throws NoSuchElementException {
        for (UmlBaseShape umlShape : shapes)
            if (umlShape.shape == shape)
                return umlShape;
        throw new NoSuchElementException("Cannot find the UmlShape in shapes set.");
    }

    private void deselectAll() {
        shapes.forEach(UmlBaseShape::deselect);
        lines.forEach(AssociationLine::toFront);
        updateSelectedSet();
    }

    private void updateSelectedSet() {
        for (UmlBaseShape shape : shapes)
            if (shape.isSelected()) selectedShapes.add(shape);
            else selectedShapes.remove(shape);
    }

    private void updateSelectedRoots() {
        selectedRoots.clear();
        Selectable root;
        for (UmlBaseShape umlBaseShape : selectedShapes) {
            root = (Selectable) CompositeObject.getRoot(umlBaseShape);
            if (umlBaseShape.isSelected()) selectedRoots.add(root);
            else selectedRoots.remove(root);
        }
    }

    enum Mode implements paintableCreator {
        SELECT {
            @Override
            public Paintable getNewPaintable(double x, double y) throws NullPointerException {
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