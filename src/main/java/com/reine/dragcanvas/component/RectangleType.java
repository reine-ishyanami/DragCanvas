package com.reine.dragcanvas.component;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * @author reine
 */
public class RectangleType extends Rectangle implements DrawableShape {

    public RectangleType() {
        this.setWidth(20.0);
        this.setHeight(20.0);
        this.setStyle(DrawableShape.style);
        this.setOnDragDetected(this::onDragDetected);
    }

    @Override
    public Node drawShape(double x, double y) {
        Rectangle rectangle = new Rectangle(this.getWidth(), this.getHeight());
        rectangle.setStyle(this.getStyle());
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);
        process(rectangle);
        return rectangle;
    }


}
