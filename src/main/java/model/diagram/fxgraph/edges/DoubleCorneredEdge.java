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

public class DoubleCorneredEdge extends AbstractEdge {
    private final StringProperty textProperty = new SimpleStringProperty();

    private final Orientation orientation;
    private final ArcType arcType;

    public DoubleCorneredEdge(ICell source, ICell target, Orientation orientation) {
        this(source, target, orientation, true, ArcType.ASSOCIATION);
    }

    public DoubleCorneredEdge(
            ICell source,
            ICell target,
            Orientation orientation,
            boolean isDirected,
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
                DoubleCorneredEdge edge,
                Orientation orientation,
                StringProperty textProperty) {
            DoubleBinding sourceX = edge.getSource().getXAnchor(graph);
            DoubleBinding sourceY = edge.getSource().getYAnchor(graph);
            DoubleBinding targetX = edge.getTarget().getXAnchor(graph);
            DoubleBinding targetY = edge.getTarget().getYAnchor(graph);

            DoubleBinding centerX = sourceX.add(targetX).divide(2);
            DoubleBinding centerY = sourceY.add(targetY).divide(2);

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

            Line lineA = new Line();
            Line lineC = new Line();
            Line lineB = new Line();
            if (orientation == Orientation.HORIZONTAL) {
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

                if (edge.isDirected()) {
                    Region target = graph.getGraphic(edge.getTarget());
                    setupArrowIntersect(target, centerX, targetY, targetX, targetY);
                    arrow.addStyle(edge.getArcType());
                    group.getChildren().add(arrow);
                } else {
                    lineC.startXProperty().bind(centerX);
                    lineC.startYProperty().bind(targetY);
                    lineC.endXProperty().bind(targetX);
                    lineC.endYProperty().bind(targetY);
                    group.getChildren().add(lineC);
                }

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

                if (edge.isDirected()) {
                    Region target = graph.getGraphic(edge.getTarget());
                    setupArrowIntersect(target, targetX, centerY, targetX, targetY);
                    arrow.addStyle(edge.getArcType());
                    group.getChildren().add(arrow);
                } else {
                    lineC.startXProperty().bind(targetX);
                    lineC.startYProperty().bind(centerY);
                    lineC.endXProperty().bind(targetX);
                    lineC.endYProperty().bind(targetY);
                    group.getChildren().add(lineC);
                }
            }
            text.xProperty().bind(centerX.subtract(textWidth.divide(2)));
            text.yProperty().bind(centerY.subtract(textHeight.divide(2)));

            group.getChildren().add(text);
            getChildren().add(group);
        }
    }
}
