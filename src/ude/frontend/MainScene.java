package ude.frontend;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainScene extends Scene {
    final private int CANVAS_LENGTH = 500;

    public HashMap<String, MenuItem> menuItems = new HashMap<>();
    public LinkedHashMap<String, Button> taskButtons = new LinkedHashMap<>();
    public Canvas canvas = new Canvas(CANVAS_LENGTH, CANVAS_LENGTH);

    private BorderPane layout = (BorderPane) getRoot();
    private MenuBar menuBar = new MenuBar();
    private VBox taskBar = new VBox();
    private Pane canvasHolder = new Pane(canvas);

    public MainScene() {
        super(new BorderPane());
        this.initLayout();
    }

    private void initLayout() {
        initMenuBar();
        initTaskBar();
        initCanvas();

        layout.setTop(menuBar);
        layout.setLeft(taskBar);
        layout.setCenter(canvasHolder);
    }

    private void initMenuBar() {
        Menu[] menus = new Menu[2];
        menus[0] = new Menu("File");
        menus[1] = new Menu("Edit");
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

    private void initCanvas() {
        canvasHolder.setStyle("-fx-background-color: #ECECEC;");
    }
}
