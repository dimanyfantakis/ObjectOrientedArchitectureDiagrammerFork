package model.diagram.fxgraph.graph;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import model.diagram.fxgraph.layout.Layout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Graph {

    private static final Logger logger = LogManager.getLogger(Graph.class);

    public static final String STYLES_CSS = "styles/styles.css";

    private final AtomicBoolean isStyleSheetLoaded = new AtomicBoolean(false);
    private final Model model;
    private final PannableCanvas pannableCanvas;
    private final Map<IGraphNode, Region> graphics;
    private final NodeGestures nodeGestures;
    private final ViewportGestures viewportGestures;
    private final BooleanProperty useNodeGestures;

    public Graph() {
        this(new Model());
    }

    public Graph(Model model) {
        this.model = model;

        nodeGestures = new NodeGestures(this);
        useNodeGestures = new SimpleBooleanProperty(true);

        useNodeGestures.addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal) {
                        model.getAllCells()
                                .forEach(cell -> nodeGestures.makeDraggable(getGraphic(cell)));
                    } else {
                        model.getAllCells()
                                .forEach(cell -> nodeGestures.makeUndraggable(getGraphic(cell)));
                    }
                });

        pannableCanvas = new PannableCanvas();
        loadStylesheet(pannableCanvas);

        viewportGestures = new ViewportGestures(this);
        BooleanProperty useViewportGestures = new SimpleBooleanProperty(true);

        useViewportGestures.addListener(
                (obs, oldVal, newVal) -> {
                    final Parent parent = pannableCanvas.parentProperty().get();
                    if (parent == null) return;

                    if (newVal) {
                        parent.addEventHandler(
                                MouseEvent.MOUSE_PRESSED,
                                viewportGestures.getOnMousePressedEventHandler());
                        parent.addEventHandler(
                                MouseEvent.MOUSE_DRAGGED,
                                viewportGestures.getOnMouseDraggedEventHandler());
                        parent.addEventHandler(
                                MouseEvent.MOUSE_RELEASED,
                                viewportGestures.getOnMouseReleasedEventHandler());
                        parent.addEventHandler(
                                ScrollEvent.ANY, viewportGestures.getOnScrollEventHandler());
                    } else {
                        parent.removeEventHandler(
                                MouseEvent.MOUSE_PRESSED,
                                viewportGestures.getOnMousePressedEventHandler());
                        parent.removeEventHandler(
                                MouseEvent.MOUSE_DRAGGED,
                                viewportGestures.getOnMouseDraggedEventHandler());
                        parent.removeEventHandler(
                                MouseEvent.MOUSE_RELEASED,
                                viewportGestures.getOnMouseReleasedEventHandler());
                        parent.removeEventHandler(
                                ScrollEvent.ANY, viewportGestures.getOnScrollEventHandler());
                    }
                });

        pannableCanvas
                .parentProperty()
                .addListener(
                        (obs, oldVal, newVal) -> {
                            if (oldVal != null) {
                                oldVal.removeEventHandler(
                                        MouseEvent.MOUSE_PRESSED,
                                        viewportGestures.getOnMousePressedEventHandler());
                                oldVal.removeEventHandler(
                                        MouseEvent.MOUSE_DRAGGED,
                                        viewportGestures.getOnMouseDraggedEventHandler());
                                oldVal.removeEventHandler(
                                        MouseEvent.MOUSE_RELEASED,
                                        viewportGestures.getOnMouseReleasedEventHandler());
                                oldVal.removeEventHandler(
                                        ScrollEvent.ANY,
                                        viewportGestures.getOnScrollEventHandler());
                            }
                            if (newVal != null) {
                                newVal.addEventHandler(
                                        MouseEvent.MOUSE_PRESSED,
                                        viewportGestures.getOnMousePressedEventHandler());
                                newVal.addEventHandler(
                                        MouseEvent.MOUSE_DRAGGED,
                                        viewportGestures.getOnMouseDraggedEventHandler());
                                newVal.addEventHandler(
                                        MouseEvent.MOUSE_RELEASED,
                                        viewportGestures.getOnMouseReleasedEventHandler());
                                newVal.addEventHandler(
                                        ScrollEvent.ANY,
                                        viewportGestures.getOnScrollEventHandler());
                            }
                        });

        graphics = new HashMap<>();

        addEdges(getModel().getAllEdges());
        addCells(getModel().getAllCells());
    }

    public PannableCanvas getCanvas() {
        return pannableCanvas;
    }

    public Model getModel() {
        return model;
    }

    public void beginUpdate() {
        getCanvas().getChildren().clear();
    }

    public void endUpdate() {
        // add components to graph pane
        addEdges(model.getAddedEdges());
        addCells(model.getAddedCells());

        // remove components to graph pane
        removeEdges(model.getRemovedEdges());
        removeCells(model.getRemovedCells());

        // clean up the model
        getModel().endUpdate();
    }

    private void addEdges(List<IEdge> edges) {
        edges.forEach(
                edge -> {
                    try {
                        Region edgeGraphic = getGraphic(edge);
                        getCanvas().getChildren().add(edgeGraphic);
                        edge.onAddedToGraph(this, edgeGraphic);
                    } catch (Exception e) {
                        logger.error("Failed to add edge {}", edge);
                        throw new RuntimeException(e);
                    }
                });
    }

    private void removeEdges(List<IEdge> edges) {
        edges.forEach(
                edge -> {
                    try {
                        Region edgeGraphic = getGraphic(edge);
                        getCanvas().getChildren().remove(edgeGraphic);
                        edge.onRemovedFromGraph(this, edgeGraphic);
                    } catch (Exception e) {
                        logger.error("Failed to remove edge {}", edge);
                        throw new RuntimeException(e);
                    }
                });
    }

    private void addCells(List<ICell> cells) {
        cells.forEach(
                cell -> {
                    try {
                        Region cellGraphic = getGraphic(cell);
                        getCanvas().getChildren().add(cellGraphic);
                        if (useNodeGestures.get()) {
                            nodeGestures.makeDraggable(cellGraphic);
                        }
                        cell.onAddedToGraph(this, cellGraphic);
                    } catch (final Exception e) {
                        logger.error("Failed to add cell {}", cell);
                        throw new RuntimeException(e);
                    }
                });
    }

    private void removeCells(List<ICell> cells) {
        cells.forEach(
                cell -> {
                    try {
                        Region cellGraphic = getGraphic(cell);
                        getCanvas().getChildren().remove(cellGraphic);
                        cell.onRemovedFromGraph(this, cellGraphic);
                    } catch (final Exception e) {
                        logger.error("Failed to remove cell {}", cell);
                        throw new RuntimeException(e);
                    }
                });
    }

    public Region getGraphic(IGraphNode node) {
        graphics.putIfAbsent(node, node.getGraphic(this));
        return graphics.get(node);
    }

    private void loadStylesheet(Pane pane) {
        if (isStyleSheetLoaded.compareAndSet(false, true)) {
            URL resource = getClass().getClassLoader().getResource(STYLES_CSS);
            pane.getStylesheets().add(Objects.requireNonNull(resource).toExternalForm());
        }
    }

    public double getScale() {
        return getCanvas().getScale();
    }

    public void layout(Layout layout) {
        layout.execute(this);
    }

    public NodeGestures getNodeGestures() {
        return nodeGestures;
    }

    public ViewportGestures getViewportGestures() {
        return viewportGestures;
    }
}
