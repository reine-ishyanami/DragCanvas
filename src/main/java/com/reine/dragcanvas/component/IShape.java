package com.reine.dragcanvas.component;

import com.reine.dragcanvas.NodeContainer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
        shape.setOnMouseClicked(this::onMouseRightButtonDoubleClicked);
        // 拖拽组件
        shape.setOnMousePressed(this::onMousePressed);
        shape.setOnMouseDragged(this::onMouseDragged);
        shape.setOnMouseReleased(this::onMouseReleased);
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

    /**
     * 右键双击删除
     */
    default void onMouseRightButtonDoubleClicked(MouseEvent event) {
        Shape shape = (Shape) event.getSource();
        if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.SECONDARY)) {
            AnchorPane canvas = (AnchorPane) shape.getParent();
            canvas.getChildren().remove(shape);
        }

    }

    AtomicReference<Double> sceneOfX = new AtomicReference<>(0.0);
    AtomicReference<Double> sceneOfY = new AtomicReference<>(0.0);

    /**
     * 鼠标按下
     */
    default void onMousePressed(MouseEvent event) {
        sceneOfX.set(event.getSceneX());
        sceneOfY.set(event.getSceneY());
    }

    /**
     * 鼠标拖动组件
     */
    default void onMouseDragged(MouseEvent event) {
        Shape shape = (Shape) event.getSource();
        shape.setLayoutX(event.getSceneX() - sceneOfX.get());
        shape.setLayoutY(event.getSceneY() - sceneOfY.get());
    }

    /**
     * 松开鼠标按键（即完成鼠标拖拽动作）
     */
    default void onMouseReleased(MouseEvent event) {
        Shape shape = (Shape) event.getSource();
        sceneOfX.set(0.0);
        sceneOfY.set(0.0);
        shape.setTranslateX(shape.getLayoutX() + shape.getTranslateX());
        shape.setTranslateY(shape.getLayoutY() + shape.getTranslateY());
        shape.setLayoutX(0.0);
        shape.setLayoutY(0.0);
    }
}
