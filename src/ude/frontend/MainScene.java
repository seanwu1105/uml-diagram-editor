package ude.frontend;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ude.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainScene extends Scene {
    private double orgSceneX, orgSceneY;

    public Map<String, MenuItem> menuItems = new HashMap<>();
    public Map<String, Button> taskButtons = new LinkedHashMap<>();
    public Pane diagram = new Pane();

    private BorderPane layout = (BorderPane) getRoot();
    private MenuBar menuBar = new MenuBar();
    private VBox taskBar = new VBox();

    public MainScene() {
        super(new BorderPane());
        this.initLayout();
    }

    private void initLayout() {
        initMenuBar();
        initTaskBar();
        initDiagram();

        layout.setTop(menuBar);
        layout.setLeft(taskBar);
        layout.setCenter(diagram);
    }

    private void initMenuBar() {
        String[] names = {"group", "ungroup", "change object name"};
        for (String name : names) {
            menuItems.put(name, new MenuItem(Utils.capitalize(name)));
        }

        Menu[] menus = new Menu[2];
        menus[0] = new Menu("File");
        menus[1] = new Menu("Edit");
        menus[1].getItems().addAll(menuItems.get("group"), menuItems.get("ungroup"), menuItems.get("change object name"));
        menuBar.getMenus().addAll(menus);
    }

    private void initTaskBar() {
        String[] names = {"select", "associate", "generalize", "composite", "class", "useCase"};
        for (String name : names) {
            Button btn = new Button(name);
            taskButtons.put(name, btn);
            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }
        taskBar.setStyle("-fx-background-color: #E5E5E5;");
        taskBar.getChildren().addAll(taskButtons.values());
    }

    private void initDiagram() {
        diagram.setMinSize(500, 500);
        diagram.setStyle("-fx-background-color: #DDDDDD;");

        Rectangle clipper = new Rectangle();
        clipper.heightProperty().bind(diagram.heightProperty());
        clipper.widthProperty().bind(diagram.widthProperty());
        diagram.setClip(clipper);

        /* For testing */
        Circle c = new Circle(100, 100, 50);
        c.setOnMousePressed(t -> {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            Circle current = (Circle) (t.getSource());
            current.toFront();
        });
        c.setOnMouseDragged(t -> {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;

            Circle current = (Circle) (t.getSource());

            current.setCenterX(current.getCenterX() + offsetX);
            current.setCenterY(current.getCenterY() + offsetY);

            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
        });
        diagram.getChildren().add(c);
    }
}
