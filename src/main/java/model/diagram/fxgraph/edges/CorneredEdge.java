package model.diagram.fxgraph.edges;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Orientation;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import model.diagram.fxgraph.graph.Graph;
import model.diagram.fxgraph.graph.ICell;
import model.graph.ArcType;

public class CorneredEdge extends AbstractEdge {
    private final StringProperty textProperty = new SimpleStringProperty();

    private final Orientation orientation;
    private final ArcType arcType;

    public CorneredEdge(ICell source, ICell target, Orientation orientation) {
        this(source, target, true, orientation, ArcType.ASSOCIATION);
    }

    public CorneredEdge(
            ICell source,
            ICell target,
            boolean isDirected,
            Orientation orientation,
            ArcType arcType) {
        super(source, target, isDirected);
        this.orientation = orientation;
        this.arcType = arcType;
    }

    @Override
    public EdgeGraphic getGraphic(Graph graph) {
        return new EdgeGraphic(graph, this, orientation, textProperty);
    }

    public StringProperty getTextProperty() {
        return textProperty;
    }

    public ArcType getArcType() {
        return arcType;
    }

    public static class EdgeGraphic extends AbstractEdgeGraphic {

        public EdgeGraphic(
                Graph graph,
                CorneredEdge edge,
                Orientation orientation,
                StringProperty textProperty) {
            super();

            final DoubleBinding sourceX = edge.getSource().getXAnchor(graph);
            final DoubleBinding sourceY = edge.getSource().getYAnchor(graph);
            final DoubleBinding targetX = edge.getTarget().getXAnchor(graph);
            final DoubleBinding targetY = edge.getTarget().getYAnchor(graph);

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

            if (orientation == Orientation.HORIZONTAL) {
                final Line lineA = new Line();
                lineA.startXProperty().bind(sourceX);
                lineA.startYProperty().bind(sourceY);
                lineA.endXProperty().bind(targetX);
                lineA.endYProperty().bind(sourceY);
                group.getChildren().add(lineA);

                if (edge.isDirected()) {
                    // Use simple center-to-center arrow positioning to avoid flickering
                    Region target = graph.getGraphic(edge.getTarget());
                    setupArrowIntersect(target, targetX, sourceY, targetX, targetY);
                    arrow.addStyle(edge.getArcType());
                    group.getChildren().add(arrow);
                } else {
                    final Line lineB = new Line();
                    lineB.startXProperty().bind(targetX);
                    lineB.startYProperty().bind(sourceY);
                    lineB.endXProperty().bind(targetX);
                    lineB.endYProperty().bind(targetY);
                    group.getChildren().add(lineB);
                }

                text.xProperty().bind(targetX.subtract(textWidth.divide(2)));
                text.yProperty().bind(sourceY.subtract(textHeight.divide(2)));
            } else {
                final Line lineA = new Line();
                lineA.startXProperty().bind(sourceX);
                lineA.startYProperty().bind(sourceY);
                lineA.endXProperty().bind(sourceX);
                lineA.endYProperty().bind(targetY);
                group.getChildren().add(lineA);

                if (edge.isDirected()) {
                    // Use simple center-to-center arrow positioning to avoid flickering
                    Region target = graph.getGraphic(edge.getTarget());
                    setupArrowIntersect(target, sourceX, targetY, targetX, targetY);
                    arrow.addStyle(edge.getArcType());
                    group.getChildren().add(arrow);
                } else {
                    final Line lineB = new Line();
                    lineB.startXProperty().bind(sourceX);
                    lineB.startYProperty().bind(targetY);
                    lineB.endXProperty().bind(targetX);
                    lineB.endYProperty().bind(targetY);
                    group.getChildren().add(lineB);
                }

                text.xProperty().bind(sourceX.subtract(textWidth.divide(2)));
                text.yProperty().bind(targetY.subtract(textHeight.divide(2)));
            }

            group.getChildren().add(text);
            getChildren().add(group);
        }
    }
}
