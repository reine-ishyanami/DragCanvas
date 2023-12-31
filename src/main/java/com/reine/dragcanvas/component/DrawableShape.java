package com.reine.dragcanvas.component;

import com.reine.dragcanvas.ComponentContainer;
import com.reine.dragcanvas.utils.ClassScanner;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;


/**
 * @author reine
 */
public interface DrawableShape {

    DataFormat shapeFormat = new DataFormat("data/shape");

    Node drawShape(double x, double y);

    String style = "-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1";

    /**
     * 设置鼠标事件
     */
    private void setMouseAction(Shape shape) {
        shape.setPickOnBounds(true);
        // 右键双击删除
        shape.setOnMouseClicked(this::onMouseClick);
        // 拖拽组件
        shape.setOnMousePressed(this::onMousePressed);
        shape.setOnMouseDragged(this::onMouseDragged);
        shape.setOnMouseReleased(this::onMouseReleased);
        // TODO 缩放
    }

    default void process(Shape shape) {
        setMouseAction(shape);
    }

    Map<String, DrawableShape> SHAPE_MAP = new HashMap<>();

    static void fillShapeMap() {
        try {
            String packName = DrawableShape.class.getPackage().getName();
            Set<Class<?>> classes = ClassScanner.getClasses(packName);
            for (Class<?> aClass : classes) {
                if (Arrays.asList(aClass.getInterfaces()).contains(DrawableShape.class)) {
                    SHAPE_MAP.put(aClass.getSimpleName(), (DrawableShape) aClass.getDeclaredConstructor().newInstance());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    static DrawableShape of(String shapeName) {
        return SHAPE_MAP.get(shapeName);
    }

    /**
     * 右键双击删除
     */
    default void onMouseClick(MouseEvent event) {
        Shape shape = (Shape) event.getSource();
        AnchorPane canvas = (AnchorPane) shape.getParent();
        ObservableList<Node> children = canvas.getChildren();
        // 将选中的节点移到最上层
        children.remove(shape);
        children.add(shape);
        // 右键双击删除
        if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.SECONDARY)) {
            children.remove(shape);
            return;
        }
        // 左键双击更改颜色
        if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
            ColorPicker colorPicker = new ColorPicker();
            colorPicker.setValue((Color) shape.getFill());
            Stage stage = new Stage();
            stage.setX(shape.localToScreen(shape.getBoundsInLocal()).getMaxX());
            stage.setY(shape.localToScreen(shape.getBoundsInLocal()).getMaxY());
            stage.setScene(new Scene(colorPicker));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initOwner(ComponentContainer.getMainStage());
            stage.show();
            colorPicker.valueProperty().addListener(((observable, oldValue, newValue) -> {
                shape.setFill(newValue);
                stage.close();
            }));
            return;
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

    /**
     * 拖拽开始
     */
    default void onDragDetected(MouseEvent event) {
        Node shape = (Node) event.getSource();
        Dragboard dragboard = shape.startDragAndDrop(TransferMode.ANY);
        double width = shape.getBoundsInLocal().getWidth();
        double height = shape.getBoundsInLocal().getHeight();
        WritableImage image = new WritableImage((int) width, (int) height);
        shape.snapshot(new SnapshotParameters(), image);
        dragboard.setDragView(image);
        ClipboardContent content = new ClipboardContent();
        content.put(DrawableShape.shapeFormat, this.getClass().getSimpleName());
        dragboard.setContent(content);
    }
}
