package com.reine.dragcanvas;

import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author reine
 */
public class ComponentContainer {

    private static final Map<String, Node> NODE_MAP = new HashMap<>();

    public static void putNode(String key, Node node) {
        NODE_MAP.put(key, node);
    }

    public static Node getNode(String key) {
        return NODE_MAP.get(key);
    }

    private static Stage mainStage;

    public static void initMainStage(Stage stage) {
        if (mainStage == null) mainStage = stage;
        else throw new RuntimeException("mainStage has init");
    }

    public static Stage getMainStage() {
        return mainStage;
    }

}
