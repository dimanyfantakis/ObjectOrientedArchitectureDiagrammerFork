package model.diagram.fxgraph.graph;

public interface IEdge extends IGraphNode {

    ICell getSource();

    ICell getTarget();

    boolean isDirected();
}
