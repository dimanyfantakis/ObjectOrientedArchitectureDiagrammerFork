package model.diagram.fxgraph.edges;

import java.util.Objects;
import javafx.scene.layout.Region;
import model.diagram.fxgraph.graph.Graph;
import model.diagram.fxgraph.graph.ICell;
import model.diagram.fxgraph.graph.IEdge;

/**
 * Abstract base class for all edge implementations.
 * Provides common functionality and reduces code duplication.
 */
public abstract class AbstractEdge implements IEdge {
    protected final ICell source;
    protected final ICell target;
    protected final boolean isDirected;

    protected AbstractEdge(ICell source, ICell target, boolean isDirected) {
        this.source = Objects.requireNonNull(source);
        this.target = Objects.requireNonNull(target);
        this.isDirected = isDirected;

        linkCells();
    }

    /**
     * Links the source and target cells bidirectionally.
     */
    private void linkCells() {
        source.addCellParent(target);
        target.addCellChild(source);
    }

    @Override
    public ICell getSource() {
        return source;
    }

    @Override
    public ICell getTarget() {
        return target;
    }

    @Override
    public boolean isDirected() {
        return isDirected;
    }

    @Override
    public abstract Region getGraphic(Graph graph);
}
