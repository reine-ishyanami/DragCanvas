package com.reine.dragcanvas.component;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * @author reine
 */
public class RectangleIShape implements IShape {
    @Override
    public Node drawShape(double x, double y) {
        Rectangle rectangle = new Rectangle(20, 20);
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);
        process(rectangle, "rectangle");
        return rectangle;
    }


}
