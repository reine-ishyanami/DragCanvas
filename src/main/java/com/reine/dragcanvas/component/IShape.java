package com.reine.dragcanvas.component;

import com.reine.dragcanvas.NodeContainer;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


/**
 * @author reine
 */
public interface IShape {

    DataFormat shapeFormat = new DataFormat("data/shape");

    Node drawShape(double x, double y);

    /**
     * 设置组件样式
     */
    private void setStyle(Shape shape) {
        shape.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1");
    }

    /**
     * 设置鼠标事件
     */
    private void setMouseAction(Shape shape, String shapeName) {
        shape.setPickOnBounds(true);
        // 右键双击删除
        shape.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.SECONDARY)) {
                AnchorPane canvas = (AnchorPane) shape.getParent();
                canvas.getChildren().remove(shape);
            }
        });
        AtomicReference<Double> sceneOfX = new AtomicReference<>(0.0);
        AtomicReference<Double> sceneOfY = new AtomicReference<>(0.0);
        // 拖拽组件
        shape.setOnMousePressed(event -> {
            sceneOfX.set(event.getSceneX());
            sceneOfY.set(event.getSceneY());
        });
        shape.setOnMouseDragged(event -> {
            shape.setLayoutX(event.getSceneX() - sceneOfX.get());
            shape.setLayoutY(event.getSceneY() - sceneOfY.get());
        });
        shape.setOnMouseReleased(event -> {
            sceneOfX.set(0.0);
            sceneOfY.set(0.0);
            shape.setTranslateX(shape.getLayoutX()+shape.getTranslateX());
            shape.setTranslateY(shape.getLayoutY()+shape.getTranslateY());
            shape.setLayoutX(0.0);
            shape.setLayoutY(0.0);
        });

        // TODO 缩放
    }

    default void process(Shape shape, String shapeName) {
        setStyle(shape);
        setMouseAction(shape, shapeName);
    }

    Map<String, IShape> SHAPE_MAP = Map.of(
            "rectangle", new RectangleIShape(),
            "circle", new CircleType(),
            "triangle", new TriangleType()
    );

    static IShape of(String shapeName) {
        return SHAPE_MAP.get(shapeName);
    }
}
