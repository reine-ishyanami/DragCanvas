package com.reine.dragcanvas.component;

import javafx.scene.Node;
import javafx.scene.shape.Polygon;

/**
 * @author reine
 */
public class TriangleType extends Polygon implements IShape {

    public TriangleType() {
        this.getPoints().addAll(0.0, Math.sqrt(3) * 10, 20.0, Math.sqrt(3) * 10, 10.0, 0.0);
        this.setStyle(IShape.style);
        this.setOnDragDetected(this::onDragDetected);
    }

    @Override
    public Node drawShape(double x, double y) {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(this.getPoints());
        triangle.setStyle(this.getStyle());
        triangle.setTranslateX(x);
        triangle.setTranslateY(y);
        process(triangle);
        return triangle;
    }


}
