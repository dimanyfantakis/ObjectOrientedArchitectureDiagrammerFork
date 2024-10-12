package model.diagram.fxgraph.edges;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import model.diagram.fxgraph.graph.Graph;
import model.diagram.fxgraph.graph.ICell;
import model.diagram.fxgraph.graph.SequenceDiagram;
import model.diagram.fxgraph.util.DiagramStyler;

public class MessageEdge extends AbstractEdge implements SequenceDiagram.IMessageEdge {
    private final DoubleProperty yOffsetProperty = new SimpleDoubleProperty();

    private final String name;

    public MessageEdge(
            SequenceDiagram.IActorCell source, SequenceDiagram.IActorCell target, String name) {
        this(source, target, name, true);
    }

    public MessageEdge(ICell source, ICell target, String name, boolean isDirected) {
        super(source, target, isDirected);
        this.name = name;
    }

    @Override
    public DoubleProperty yOffsetProperty() {
        return yOffsetProperty;
    }

    @Override
    public Region getGraphic(Graph graph) {
        return new EdgeGraphic(graph, this);
    }

    private static class EdgeGraphic extends AbstractEdgeGraphic {

        public EdgeGraphic(Graph graph, MessageEdge edge) {
            final DoubleBinding sourceX = edge.getSource().getXAnchor(graph);
            final DoubleBinding sourceY =
                    edge.getSource().getYAnchor(graph).add(edge.yOffsetProperty);
            final DoubleBinding targetX = edge.getTarget().getXAnchor(graph);
            final DoubleBinding targetY =
                    edge.getTarget().getYAnchor(graph).add(edge.yOffsetProperty);

            arrow.startXProperty().bind(sourceX);
            arrow.startYProperty().bind(sourceY);

            arrow.endXProperty().bind(targetX);
            arrow.endYProperty().bind(targetY);

            // Apply programmatic styling for message arrows (open arrows)
            for (javafx.scene.Node child : arrow.getChildren()) {
                if (child instanceof javafx.scene.shape.Line l) {
                    DiagramStyler.applyMessageArrowStyle(l);
                }
            }

            group.getChildren().add(arrow);

            final DoubleProperty textWidth = new SimpleDoubleProperty();
            final DoubleProperty textHeight = new SimpleDoubleProperty();

            Text text = new Text(edge.name);
            text.getStyleClass().add("edge-text");
            text.xProperty().bind(sourceX.add(targetX).divide(2).subtract(textWidth.divide(2)));
            text.yProperty().bind(sourceY.add(targetY).divide(2).subtract(textHeight.divide(2)));
            final Runnable recalculateWidth =
                    () -> {
                        textWidth.set(text.getLayoutBounds().getWidth());
                        textHeight.set(text.getLayoutBounds().getHeight());
                    };
            text.parentProperty().addListener((obs, oldVal, newVal) -> recalculateWidth.run());
            text.textProperty().addListener((obs, oldVal, newVal) -> recalculateWidth.run());
            group.getChildren().add(text);
            getChildren().add(group);
            getStyleClass().add("message-edge");
        }
    }
}
