package com.reine.dragcanvas.component;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * @author reine
 */
public class CircleType implements IShape {
    @Override
    public Node drawShape(double x, double y) {
        Circle circle = new Circle(10);
        circle.setTranslateX(x + 10.0);
        circle.setTranslateY(y + 10.0);
        process(circle, "circle");
        return circle;
    }

}
