package model.diagram.fxgraph.graph;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class PannableCanvas extends Pane {

    private final DoubleProperty scaleProperty;

    public PannableCanvas() {
        this(new SimpleDoubleProperty(1.0));
    }

    public PannableCanvas(DoubleProperty scaleProperty) {
        this.scaleProperty = scaleProperty;
        scaleXProperty().bind(scaleProperty);
        scaleYProperty().bind(scaleProperty);

        // Add grid
        Group grid = new Group();
        for (int i = -100; i <= 100; i++) {
            Line vLine = new Line(i * 20, -2000, i * 20, 2000);
            vLine.setStroke(javafx.scene.paint.Color.LIGHTGRAY);
            vLine.setStrokeWidth(0.5);
            grid.getChildren().add(vLine);

            Line hLine = new Line(-2000, i * 20, 2000, i * 20);
            hLine.setStroke(javafx.scene.paint.Color.LIGHTGRAY);
            hLine.setStrokeWidth(0.5);
            grid.getChildren().add(hLine);
        }
        getChildren().add(0, grid); // Add behind other elements
    }

    public double getScale() {
        return scaleProperty.get();
    }

    public void setScale(double scale) {
        scaleProperty.set(scale);
    }

    public DoubleProperty scaleProperty() {
        return scaleProperty;
    }

    public void setPivot(double x, double y) {
        setTranslateX(getTranslateX() - x);
        setTranslateY(getTranslateY() - y);
    }

    /** Mouse drag context used for scene and nodes. */
    public static class DragContext {

        double mouseAnchorX;
        double mouseAnchorY;

        double translateAnchorX;
        double translateAnchorY;
    }
}
