package ude.diagram;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.EventTarget;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ude.MainScene;
import ude.diagram.connection.AssociationConnection;
import ude.diagram.object.UmlBaseObject;
import ude.diagram.object.UmlBasicObject;
import ude.diagram.object.UmlCompositeObject;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class Diagram extends Pane {
    public final ObservableSet<UmlBasicObject> selectedBasicObjects = FXCollections.observableSet(new HashSet<>());
    public final ObservableSet<UmlBaseObject> selectedGroupRoots = FXCollections.observableSet(new HashSet<>());
    public final Set<UmlBasicObject<? extends Shape>> basicObjects = new HashSet<>();
    public final Set<AssociationConnection> connections = new HashSet<>();
    public ObjectProperty<MainScene.Mode> currentMode = new SimpleObjectProperty<>();
    private SelectingArea selectingArea = null;
    private UmlBaseShape newShape;

    public Diagram() {
        setMinSize(500, 500);
        setStyle("-fx-background-color: #DDDDDD;");

        Rectangle clipper = new Rectangle();
        clipper.heightProperty().bind(heightProperty());
        clipper.widthProperty().bind(widthProperty());
        setClip(clipper);

        selectedBasicObjects.addListener((SetChangeListener<UmlBasicObject>) c -> updateSelectedGroupRoots());

        initMouseEventListeners();
    }

    public void deselectAll() {
        basicObjects.forEach(UmlBasicObject::deselect);
        connections.forEach(AssociationConnection::toFront);
    }

    public void groupSelectedBasicObjects() {
        UmlCompositeObject umlCompositeObject = new UmlCompositeObject();
        umlCompositeObject.compose(selectedGroupRoots);
        updateSelectedGroupRoots();
    }

    public void ungroupSelectedCompositeObject() {
        if (selectedGroupRoots.size() != 1 || selectedBasicObjects.size() == 1)
            throw new RuntimeException("Ungrouping could only be applied when exactly one composite object is selected.");
        else
            ((UmlCompositeObject) selectedGroupRoots.iterator().next()).decompose();
        updateSelectedGroupRoots();
    }

    public String getSelectedBasicObjectName() {
        if (selectedBasicObjects.size() != 1)
            throw new RuntimeException("Getting object's name could only be applied when exactly one basic object is selected.");
        else
            return selectedBasicObjects.iterator().next().name.getText();
    }

    public void setSelectedBasicObjectName(String newName) {
        if (selectedBasicObjects.size() != 1)
            throw new RuntimeException("Naming object could only be applied when exactly one basic object is selected.");
        else
            selectedBasicObjects.iterator().next().name.setText(newName);
    }

    private void initMouseEventListeners() {
        addEventFilter(MouseEvent.MOUSE_PRESSED, e -> newShape = currentMode.getValue().getNewShape(e.getX(), e.getY()));
        addEventFilter(MouseEvent.DRAG_DETECTED, e -> startFullDrag());
        initMouseEventListenerForSelection();
        initMouseEventListenerForUmlBaseShape();
        initMouseEventListenerForConnection();
    }

    private void initMouseEventListenerForSelection() {
        addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if (currentMode.getValue() == MainScene.Mode.SELECT) {
                deselectAll();
                if (e.getTarget() == this) { // for compose selection (create selecting area rectangle)
                    selectingArea = new SelectingArea(e.getX(), e.getY());
                    getChildren().add(selectingArea);
                }
            }
        });

        addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            if (currentMode.getValue() == MainScene.Mode.SELECT) {
                if (selectingArea != null) {
                    // resize selecting area and check which UML object could be selected
                    selectingArea.setCursorX(e.getX());
                    selectingArea.setCursorY(e.getY());
                } // else: let the MOUSE_DRAGGED event pass to UmlBasicObject
            } else    // only in SELECT mode can UmlShapes be dragged
                e.consume();
        });

        addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (currentMode.getValue() == MainScene.Mode.SELECT)
                connections.forEach(AssociationConnection::toFront);
        });

        addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            if (currentMode.getValue() == MainScene.Mode.SELECT && selectingArea != null) {
                basicObjects.forEach(object -> {
                    if (object.isInside(e.getX(), e.getY(), selectingArea.initX, selectingArea.initY))
                        object.select();
                });
                getChildren().remove(selectingArea);
                selectingArea = null;
            }
        });
    }

    private void initMouseEventListenerForUmlBaseShape() {
        addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if (currentMode.getValue() != MainScene.Mode.SELECT) {
                newShape.onCreate(e, this);
            }
        });
    }

    private void initMouseEventListenerForConnection() {
        addEventHandler(MouseDragEvent.MOUSE_DRAG_OVER, e -> {
            if (currentMode.getValue() != MainScene.Mode.SELECT && newShape instanceof AssociationConnection) {
                UmlBasicObject<? extends Shape> lineTarget = getUmlBasicObject(e.getTarget());
                if (lineTarget != null) {
                    AssociationConnection newConnection = (AssociationConnection) newShape;
                    List<ObservableValue<Number>> sourcePortPosition = lineTarget.getClosestPortPositionProperty(e.getX(), e.getY());
                    newConnection.endXProperty().bind(sourcePortPosition.get(0));
                    newConnection.endYProperty().bind(sourcePortPosition.get(1));
                }
            }
        });
    }

    public UmlBasicObject<? extends Shape> getUmlBasicObject(EventTarget eventTarget) {
        UmlBasicObject<? extends Shape> umlBasicObject;
        try {
            umlBasicObject = getUmlBasicObject((Shape) eventTarget);
        } catch (ClassCastException | NoSuchElementException exception) {
            return null;
        }
        return umlBasicObject;
    }

    private UmlBasicObject<? extends Shape> getUmlBasicObject(Shape shape) throws NoSuchElementException {
        for (UmlBasicObject<? extends Shape> umlBasicObject : basicObjects)
            if (umlBasicObject.getShape() == shape)
                return umlBasicObject;
        throw new NoSuchElementException("Cannot find the UmlShape in basicObjects set.");
    }

    private void updateSelectedGroupRoots() {
        selectedGroupRoots.clear();
        for (UmlBasicObject umlBasicObject : selectedBasicObjects)
            selectedGroupRoots.add(UmlCompositeObject.getRoot(umlBasicObject));
    }
}
