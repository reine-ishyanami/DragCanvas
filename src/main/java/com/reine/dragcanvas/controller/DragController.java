package com.reine.dragcanvas.controller;

import com.reine.dragcanvas.NodeContainer;
import com.reine.dragcanvas.component.IShape;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Accordion;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class DragController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Rectangle rectangle;

    @FXML
    private Circle circle;

    @FXML
    private Polygon triangle;

    @FXML
    private Accordion accordion;

    @FXML
    void initialize() {
        NodeContainer.NODE_MAP.put("accordion", accordion);
        rectangle.setWidth(20.0);
        rectangle.setHeight(20.0);
        rectangle.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1");
        circle.setRadius(10.0);
        circle.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1");
        triangle.getPoints().addAll(0.0, Math.sqrt(3) * 10, 20.0, Math.sqrt(3) * 10, 10.0, 0.0);
        triangle.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1");
    }

    @FXML
    void onMouseDragOfRectangle(MouseEvent event) {
        Node button = (Node) event.getSource();
        Dragboard dragboard = button.startDragAndDrop(TransferMode.ANY);
        WritableImage image = new WritableImage((int) rectangle.getWidth(), (int) rectangle.getHeight());
        rectangle.snapshot(new SnapshotParameters(), image);
        dragboard.setDragView(image);
        ClipboardContent content = new ClipboardContent();
        content.put(IShape.shapeFormat, "rectangle");
        dragboard.setContent(content);
    }

    @FXML
    void onMouseDragOfCircle(MouseEvent event) {
        Node button = (Node) event.getSource();
        Dragboard dragboard = button.startDragAndDrop(TransferMode.ANY);
        WritableImage image = new WritableImage((int) circle.getRadius() * 2, (int) circle.getRadius() * 2);
        circle.snapshot(new SnapshotParameters(), image);
        dragboard.setDragView(image);
        ClipboardContent content = new ClipboardContent();
        content.put(IShape.shapeFormat, "circle");
        dragboard.setContent(content);
    }

    @FXML
    void onMouseDragOfTriangle(MouseEvent event) {
        Node button = (Node) event.getSource();
        Dragboard dragboard = button.startDragAndDrop(TransferMode.ANY);
        WritableImage image = new WritableImage(20, 20);
        triangle.snapshot(new SnapshotParameters(), image);
        dragboard.setDragView(image);
        ClipboardContent content = new ClipboardContent();
        content.put(IShape.shapeFormat, "triangle");
        dragboard.setContent(content);
    }

    @FXML
    void canvasDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
    }

    @FXML
    void canvasDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        String content = (String) dragboard.getContent(IShape.shapeFormat);
        IShape shapeType = IShape.of(content);
        AnchorPane source = (AnchorPane) event.getSource();
        Node node = shapeType.drawShape(event.getX(), event.getY());
        source.getChildren().add(node);
    }

}
