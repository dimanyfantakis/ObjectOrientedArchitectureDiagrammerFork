package model.diagram.fxgraph.graph;

import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import model.diagram.fxgraph.cells.AbstractCell;
import model.diagram.fxgraph.edges.Edge;

public class Model {
    private final ICell root;
    private ObservableList<ICell> allCells;
    private ObservableList<ICell> addedCells;
    private ObservableList<ICell> removedCells;
    private ObservableList<IEdge> allEdges;
    private ObservableList<IEdge> addedEdges;
    private ObservableList<IEdge> removedEdges;

    public Model() {
        root =
                new AbstractCell() {
                    @Override
                    public Region getGraphic(Graph graph) {
                        return null;
                    }
                };
        // clear model, create lists
        clear();
    }

    public void clear() {
        allCells = FXCollections.observableArrayList();
        addedCells = FXCollections.observableArrayList();
        removedCells = FXCollections.observableArrayList();

        allEdges = FXCollections.observableArrayList();
        addedEdges = FXCollections.observableArrayList();
        removedEdges = FXCollections.observableArrayList();
    }

    public void endUpdate() {
        // Every cell must have a parent, if it doesn't
        // then the graphParent is the parent.
        attachOrphansToGraphParent(getAddedCells());

        // remove reference to graphParent
        disconnectFromGraphParent(getRemovedCells());

        // merge added & removed cells with all cells
        merge();
    }

    public ObservableList<ICell> getAddedCells() {
        return addedCells;
    }

    public ObservableList<ICell> getRemovedCells() {
        return removedCells;
    }

    public ObservableList<ICell> getAllCells() {
        return allCells;
    }

    public ObservableList<IEdge> getAddedEdges() {
        return addedEdges;
    }

    public ObservableList<IEdge> getRemovedEdges() {
        return removedEdges;
    }

    public ObservableList<IEdge> getAllEdges() {
        return allEdges;
    }

    public void addCell(ICell cell) {
        addedCells.add(Objects.requireNonNull(cell));
    }

    public void removeCell(ICell cell) {
        removedCells.add(Objects.requireNonNull(cell));
    }

    public void addEdge(ICell sourceCell, ICell targetCell) {
        addEdge(new Edge(sourceCell, targetCell));
    }

    public void addEdge(IEdge edge) {
        addedEdges.add(Objects.requireNonNull(edge));
    }

    public void removeEdge(IEdge edge) {
        removedEdges.add(Objects.requireNonNull(edge));
    }

    /** Attach all cells which don't have a parent to graphParent */
    public void attachOrphansToGraphParent(List<ICell> cellList) {
        for (final ICell cell : cellList) {
            if (cell.getCellParents().isEmpty()) {
                root.addCellChild(cell);
            }
        }
    }

    /** Remove the graphParent reference if it is set */
    public void disconnectFromGraphParent(List<ICell> cellList) {
        for (final ICell cell : cellList) {
            root.removeCellChild(cell);
        }
    }

    public void merge() {
        // cells
        allCells.addAll(addedCells);
        allCells.removeAll(removedCells);

        addedCells.clear();
        removedCells.clear();

        // edges
        allEdges.addAll(addedEdges);
        allEdges.removeAll(removedEdges);

        addedEdges.clear();
        removedEdges.clear();
    }
}
