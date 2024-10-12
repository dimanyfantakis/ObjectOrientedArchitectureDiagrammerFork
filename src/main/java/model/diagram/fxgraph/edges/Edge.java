package model.diagram.fxgraph.edges;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import model.diagram.fxgraph.graph.Graph;
import model.diagram.fxgraph.graph.ICell;
import model.graph.ArcType;
import model.diagram.fxgraph.util.DiagramStyler;

public class Edge extends AbstractEdge {
    private final StringProperty textProperty = new SimpleStringProperty();

    private final ArcType arcType;

    public Edge(ICell source, ICell target) {
        this(source, target, true);
    }

    public Edge(ICell source, ICell target, boolean isDirected) {
        this(source, target, isDirected, ArcType.ASSOCIATION);
    }

    public Edge(ICell source, ICell target, boolean isDirected, ArcType arcType) {
        super(source, target, isDirected);
        this.arcType = arcType;
    }

    @Override
    public EdgeGraphic getGraphic(Graph graph) {
        return new EdgeGraphic(graph, this, textProperty);
    }

    public StringProperty getTextProperty() {
        return textProperty;
    }

    public ArcType getArcType() {
        return arcType;
    }

    public static class EdgeGraphic extends AbstractEdgeGraphic {

        public EdgeGraphic(Graph graph, Edge edge, StringProperty textProperty) {
            Line line = new Line();
            
            // Set default styling for plain lines
            DiagramStyler.applyPlainEdgeStyle(line);

            final DoubleBinding sourceX = edge.getSource().getXAnchor(graph);
            final DoubleBinding sourceY = edge.getSource().getYAnchor(graph);
            final DoubleBinding targetX = edge.getTarget().getXAnchor(graph);
            final DoubleBinding targetY = edge.getTarget().getYAnchor(graph);

            if (edge.isDirected()) {
                Region target = graph.getGraphic(edge.getTarget());
                setupArrowIntersect(target, sourceX, sourceY, targetX, targetY);
                addStyle(edge.getArcType());
                group.getChildren().add(arrow);
            } else {
                line.startXProperty().bind(sourceX);
                line.startYProperty().bind(sourceY);

                line.endXProperty().bind(targetX);
                line.endYProperty().bind(targetY);
                group.getChildren().add(line);
            }

            // final DoubleProperty textWidth = new SimpleDoubleProperty();
            // final DoubleProperty textHeight = new SimpleDoubleProperty();
            // text.textProperty().bind(textProperty);
            // text.getStyleClass().add("edge-text");
            // text.xProperty().bind(sourceX.add(targetX).divide(2).subtract(textWidth.divide(2)));
            // text.yProperty().bind(sourceY.add(targetY).divide(2).subtract(textHeight.divide(2)));

            // final Runnable recalculateWidth =
            //         () -> {
            //             textWidth.set(text.getLayoutBounds().getWidth());
            //             textHeight.set(text.getLayoutBounds().getHeight());
            //         };
            // text.parentProperty().addListener((obs, oldVal, newVal) -> recalculateWidth.run());
            // text.textProperty().addListener((obs, oldVal, newVal) -> recalculateWidth.run());
            // group.getChildren().add(text);
            getChildren().add(group);
        }
    }
}
