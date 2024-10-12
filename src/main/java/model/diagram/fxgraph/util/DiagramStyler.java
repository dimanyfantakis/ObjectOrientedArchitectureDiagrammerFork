package model.diagram.fxgraph.util;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import model.graph.ArcType;

/**
 * Utility class for applying consistent styling to diagram elements programmatically.
 * Centralizes all styling logic to avoid code duplication.
 */
public class DiagramStyler {

    // Arrow styling constants
    private static final double ARROW_STROKE_WIDTH = 2.0;
    private static final Color ARROW_STROKE_COLOR = Color.BLACK;

    // Edge styling constants
    private static final double EDGE_STROKE_WIDTH = 2.0;
    private static final Color EDGE_STROKE_COLOR = Color.BLACK;

    // Text styling constants
    private static final Color TEXT_FILL_COLOR = Color.web("#424242");

    /**
     * Applies styling to arrow lines based on ArcType.
     * @param line The Line object to style
     * @param arcType The relationship type that determines arrow style
     */
    public static void applyArrowStyle(Line line, ArcType arcType) {
        line.setStrokeWidth(ARROW_STROKE_WIDTH);
        line.setStroke(ARROW_STROKE_COLOR);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);

        if (ArcType.isClosed(arcType)) {
            line.setFill(ARROW_STROKE_COLOR);
        } else {
            line.setFill(Color.TRANSPARENT);
        }
    }

    /**
     * Applies styling for plain edges (non-directed lines).
     * @param line The Line object to style
     */
    public static void applyPlainEdgeStyle(Line line) {
        line.setStrokeWidth(EDGE_STROKE_WIDTH);
        line.setStroke(EDGE_STROKE_COLOR);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);
        line.setFill(Color.TRANSPARENT);
    }

    /**
     * Applies styling for message/sequence diagram arrows (always open).
     * @param line The Line object to style
     */
    public static void applyMessageArrowStyle(Line line) {
        line.setStrokeWidth(ARROW_STROKE_WIDTH);
        line.setStroke(ARROW_STROKE_COLOR);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);
        line.setFill(Color.TRANSPARENT);
    }

    /**
     * Applies styling for loop arrows (always open).
     * @param line The Line object to style
     */
    public static void applyLoopArrowStyle(Line line) {
        applyMessageArrowStyle(line);
    }

    /**
     * Gets the standard text fill color for diagram elements.
     * @return The text color
     */
    public static Color getTextFillColor() {
        return TEXT_FILL_COLOR;
    }

    /**
     * Gets the standard arrow stroke color.
     * @return The arrow stroke color
     */
    public static Color getArrowStrokeColor() {
        return ARROW_STROKE_COLOR;
    }

    /**
     * Gets the standard edge stroke color.
     * @return The edge stroke color
     */
    public static Color getEdgeStrokeColor() {
        return EDGE_STROKE_COLOR;
    }
}
