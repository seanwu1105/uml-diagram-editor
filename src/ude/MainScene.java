package ude;

import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import ude.diagram.Diagram;
import ude.diagram.connection.AssociationConnection;
import ude.diagram.connection.CompositionConnection;
import ude.diagram.connection.GeneralizationConnection;
import ude.diagram.object.UmlClassObject;
import ude.diagram.object.UmlUseCaseObject;

public class MainScene extends Scene {
    private final Mode defaultMode = Mode.SELECT;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final Diagram diagram = new Diagram();

    public MainScene() {
        super(new BorderPane());

        BorderPane layout = (BorderPane) getRoot();
        layout.setTop(initMenuBar());
        layout.setLeft(initTaskBar());
        layout.setCenter(initDiagram());
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
                // enabled when more than 1 UML basicObjects are selected
                () -> diagram.selectedGroupRoots.size() <= 1, diagram.selectedGroupRoots
        ));
        editMenuItems[1].disableProperty().bind(Bindings.createBooleanBinding(
                // enabled when exactly 1 composite object is selected
                () -> diagram.selectedGroupRoots.size() != 1 || diagram.selectedBasicObjects.size() == 1,
                diagram.selectedGroupRoots, diagram.selectedBasicObjects
        ));
        editMenuItems[2].disableProperty().bind(Bindings.createBooleanBinding(
                // enabled when exactly 1 basic object is selected
                () -> diagram.selectedBasicObjects.size() != 1, diagram.selectedBasicObjects
        ));

        editMenuItems[0].setOnAction(e -> diagram.groupSelectedBasicObjects());
        editMenuItems[1].setOnAction(e -> diagram.ungroupSelectedCompositeObject());
        editMenuItems[2].setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog(diagram.getSelectedBasicObjectName());
            dialog.setResizable(true);  // fix the weird bug: the first time pop-up, the dialog size is NaN.
            dialog.initOwner(getWindow());
            dialog.setTitle("Object Name");
            dialog.setHeaderText("Object Name");
            dialog.showAndWait().ifPresent(diagram::setSelectedBasicObjectName);
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
        toggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> diagram.deselectAll());
        bar.setStyle("-fx-background-color: #E5E5E5;");
        return bar;
    }

    private Pane initDiagram() {
        diagram.currentMode.bind(Bindings.createObjectBinding(
                () -> Mode.valueOf(((ToggleButton) toggleGroup.selectedToggleProperty().getValue()).getText()),
                toggleGroup.selectedToggleProperty()
        ));
        return diagram;
    }

    public enum Mode implements shapeFactory {
        SELECT {
            @Override
            public Shape getNewShape(double x, double y) {
                return null;
            }
        }, ASSOCIATE {
            @Override
            public Shape getNewShape(double x, double y) {
                return new AssociationConnection(x, y);
            }
        }, GENERALIZE {
            @Override
            public Shape getNewShape(double x, double y) {
                return new GeneralizationConnection(x, y);
            }
        }, COMPOSITE {
            @Override
            public Shape getNewShape(double x, double y) {
                return new CompositionConnection(x, y);
            }
        }, CLASS {
            @Override
            public Shape getNewShape(double x, double y) {
                return new UmlClassObject(x, y);
            }
        }, USE_CASE {
            @Override
            public Shape getNewShape(double x, double y) {
                return new UmlUseCaseObject(x, y);
            }
        }
    }

    private interface shapeFactory {
        Shape getNewShape(double x, double y);
    }
}