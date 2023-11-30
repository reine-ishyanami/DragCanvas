package com.reine.dragcanvas.component;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * @author reine
 */
public class CircleType extends Circle implements DrawableShape {

    public CircleType() {
        this.setRadius(10.0);
        this.setStyle(DrawableShape.style);
        this.setOnDragDetected(this::onDragDetected);
    }

    @Override
    public Node drawShape(double x, double y) {
        Circle circle = new Circle(this.getRadius());
        circle.setStyle(this.getStyle());
        circle.setTranslateX(x + 10.0);
        circle.setTranslateY(y + 10.0);
        process(circle);
        return circle;
    }

}
