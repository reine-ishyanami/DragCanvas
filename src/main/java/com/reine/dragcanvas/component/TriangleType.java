package com.reine.dragcanvas.component;

import javafx.scene.Node;
import javafx.scene.shape.Polygon;

/**
 * @author reine
 */
public class TriangleType implements IShape {
    @Override
    public Node drawShape(double x, double y) {
        Polygon triangle = new Polygon(
                0.0, Math.sqrt(3) * 10,
                20.0, Math.sqrt(3) * 10,
                10.0, 0.0
        );
        triangle.setTranslateX(x);
        triangle.setTranslateY(y);
        process(triangle, "triangle");
        return triangle;
    }


}
