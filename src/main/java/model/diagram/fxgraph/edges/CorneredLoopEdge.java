package model.diagram.fxgraph.edges;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.shape.Line;
import model.diagram.fxgraph.graph.Graph;
import model.diagram.fxgraph.graph.ICell;
import model.diagram.fxgraph.util.DiagramStyler;

public class CorneredLoopEdge extends AbstractEdge {
    private final DoubleProperty separationProperty = new SimpleDoubleProperty(15);
    private final DoubleProperty widthProperty = new SimpleDoubleProperty(0.5);
    private final StringProperty textProperty = new SimpleStringProperty();

    private final Position pos;

    public CorneredLoopEdge(ICell target, Position pos) {
        this(target, target, pos, true);
    }

    public CorneredLoopEdge(ICell source, ICell target, Position pos, boolean isDirected) {
        super(source, target, isDirected);
        this.pos = pos;
    }

    @Override
    public EdgeGraphic getGraphic(Graph graph) {
        return new EdgeGraphic(graph, this, pos, textProperty, separationProperty, widthProperty);
    }

    public enum Position {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT;

        boolean isVertical() {
            return this == LEFT || this == RIGHT;
        }

        boolean isHorizontal() {
            return this == TOP || this == BOTTOM;
        }
    }

    public static class EdgeGraphic extends AbstractEdgeGraphic {
        public EdgeGraphic(
                Graph graph,
                CorneredLoopEdge edge,
                Position position,
                StringProperty textProperty,
                DoubleProperty separation,
                DoubleProperty width) {
            DoubleBinding w = edge.getTarget().getWidth(graph).add(0.000001);
            DoubleBinding h = edge.getTarget().getHeight(graph).add(0.000001);
            DoubleBinding x = edge.getTarget().getXAnchor(graph);
            DoubleBinding y = edge.getTarget().getYAnchor(graph);

            DoubleBinding sourceX;
            DoubleBinding targetX;
            DoubleBinding sourceY;
            DoubleBinding targetY;
            DoubleBinding centerY;
            DoubleBinding centerX;
            if (position.isVertical()) {
                sourceY = y.subtract(h.divide(2.0).multiply(width));
                targetY = y.add(h.divide(2.0).multiply(width));

                if (position == Position.LEFT) {
                    sourceX = x.subtract(w.divide(2.0));
                    targetX = sourceX;
                    centerX = sourceX.subtract(w.divide(2.0)).subtract(separation);
                } else {
                    sourceX = x.add(w.divide(2.0));
                    targetX = sourceX;
                    centerX = sourceX.add(w.divide(2.0)).add(separation);
                }

                centerY = sourceY.add(targetY).divide(2);
            } else {
                sourceX = x.subtract(w.divide(2.0).multiply(width));
                targetX = x.add(w.divide(2.0).multiply(width));

                if (position == Position.TOP) {
                    sourceY = y.subtract(h.divide(2.0));
                    targetY = sourceY;
                    centerY = sourceY.subtract(h.divide(2.0)).subtract(separation);
                } else {
                    sourceY = y.add(h.divide(2.0));
                    targetY = sourceY;
                    centerY = sourceY.add(h.divide(2.0)).add(separation);
                }

                centerX = sourceX.add(targetX).divide(2);
            }

            text.textProperty().bind(textProperty);
            text.getStyleClass().add("edge-text");
            final DoubleProperty textWidth = new SimpleDoubleProperty();
            final DoubleProperty textHeight = new SimpleDoubleProperty();

            final Runnable recalculateWidth =
                    () -> {
                        textWidth.set(text.getLayoutBounds().getWidth());
                        textHeight.set(text.getLayoutBounds().getHeight());
                    };
            text.parentProperty().addListener((obs, oldVal, newVal) -> recalculateWidth.run());
            text.textProperty().addListener((obs, oldVal, newVal) -> recalculateWidth.run());

            if (position == Position.TOP) {
                text.yProperty().bind(centerY.subtract(textHeight.divide(2)));
            } else {
                text.yProperty().bind(centerY.add(textHeight.divide(2)));
            }
            text.xProperty().bind(centerX.subtract(textWidth.divide(2)));

            Line lineB = new Line();
            Line lineA = new Line();
            if (position.isVertical()) {
                lineA.startXProperty().bind(sourceX);
                lineA.startYProperty().bind(sourceY);
                lineA.endXProperty().bind(centerX);
                lineA.endYProperty().bind(sourceY);
                group.getChildren().add(lineA);

                lineB.startXProperty().bind(centerX);
                lineB.startYProperty().bind(sourceY);
                lineB.endXProperty().bind(centerX);
                lineB.endYProperty().bind(targetY);
                group.getChildren().add(lineB);

                setupArrow(centerX, targetY, targetX, targetY);
                // TODO: Add style to arrow
                // Add style
                // arrow.getStyleClass().add("arrow");
                // Apply programmatic styling for loop arrows (open arrows)
                for (javafx.scene.Node child : arrow.getChildren()) {
                    if (child instanceof javafx.scene.shape.Line l) {
                        DiagramStyler.applyLoopArrowStyle(l);
                    }
                }

                group.getChildren().add(arrow);
            } else {
                lineA.startXProperty().bind(sourceX);
                lineA.startYProperty().bind(sourceY);
                lineA.endXProperty().bind(sourceX);
                lineA.endYProperty().bind(centerY);
                group.getChildren().add(lineA);

                lineB.startXProperty().bind(sourceX);
                lineB.startYProperty().bind(centerY);
                lineB.endXProperty().bind(targetX);
                lineB.endYProperty().bind(centerY);
                group.getChildren().add(lineB);

                setupArrow(targetX, centerY, targetX, targetY);
                // TODO: Add style to arrow
                // Add style
                // arrow.getStyleClass().add("arrow");
                // Apply programmatic styling for loop arrows (open arrows)
                for (javafx.scene.Node child : arrow.getChildren()) {
                    if (child instanceof javafx.scene.shape.Line l) {
                        DiagramStyler.applyLoopArrowStyle(l);
                    }
                }

                group.getChildren().add(arrow);
            }

            group.getChildren().add(text);
            getChildren().add(group);
        }
    }
}
