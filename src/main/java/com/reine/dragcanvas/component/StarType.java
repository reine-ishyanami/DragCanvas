package com.reine.dragcanvas.component;

import javafx.scene.Node;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * @author reine
 */
public class StarType extends Polygon implements DrawableShape {

    public StarType() {
        double R = 10.0;
        double[][] pointOuter = new double[5][2];
        double[][] pointInner = new double[5][2];
        for (int i = 0; i < 5; i++) {
            double pointX = R * cos(2 * PI * i / 5);
            double pointY = R * sin(2 * PI * i / 5);
            pointOuter[i] = new double[]{pointX, pointY};

            pointX = R / 2 * cos((2 * PI * i + PI) / 5);
            pointY = R / 2 * sin((2 * PI * i + PI) / 5);
            pointInner[i] = new double[]{pointX, pointY};
        }

        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(pointOuter[i][0]);
            list.add(pointOuter[i][1]);
            list.add(pointInner[i][0]);
            list.add(pointInner[i][1]);
        }

        this.getPoints().addAll(list);
        this.setStyle(DrawableShape.style);
        this.setOnDragDetected(this::onDragDetected);

    }

    @Override
    public Node drawShape(double x, double y) {
        Polygon star = new Polygon();
        star.getPoints().addAll(this.getPoints());
        star.setStyle(this.getStyle());
        star.setTranslateX(x);
        star.setTranslateY(y);
        process(star);
        return star;
    }

    /**
     * 角度转弧度
     *
     * @param angle 角度
     * @return 弧度
     */
    private double aTR(double angle) {
        return 180.0 / PI * angle;
    }

    /**
     * 弧度转角度
     *
     * @param radian 弧度
     * @return 角度
     */
    private double rTA(double radian) {
        return PI / 180.0 * radian;
    }
}
