package model.diagram.fxgraph.graph;

import javafx.scene.layout.Region;

public interface IGraphNode {

    Region getGraphic(Graph graph);

    default void onAddedToGraph(Graph graph, Region region) {}

    default void onRemovedFromGraph(Graph graph, Region region) {}
}
