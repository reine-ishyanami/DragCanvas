package com.reine.dragcanvas.controller;

import com.reine.dragcanvas.component.DrawableShape;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DragController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
        DrawableShape.fillShapeMap();
    }

    @FXML
    void canvasDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
    }

    @FXML
    void canvasDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        String content = (String) dragboard.getContent(DrawableShape.shapeFormat);
        DrawableShape shapeType = DrawableShape.of(content);
        AnchorPane source = (AnchorPane) event.getSource();
        Node node = shapeType.drawShape(event.getX(), event.getY());
        source.getChildren().add(node);
    }

}
