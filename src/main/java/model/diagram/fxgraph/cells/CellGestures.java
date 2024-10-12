package model.diagram.fxgraph.cells;

import java.util.Arrays;
import java.util.List;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.diagram.fxgraph.graph.Graph;

public class CellGestures {

    private static final double HANDLE_RADIUS = 4d;

    static DragNodeSupplier NORTH =
            (graph, region, wrappedEvent) -> {
                final DoubleProperty xProperty = region.layoutXProperty();
                final DoubleProperty yProperty = region.layoutYProperty();
                final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
                final DoubleBinding halfWidthProperty = widthProperty.divide(2);

                final Rectangle resizeHandleN =
                        new Rectangle(HANDLE_RADIUS, HANDLE_RADIUS, Color.BLACK);
                resizeHandleN.setOpacity(0.5);
                resizeHandleN
                        .xProperty()
                        .bind(xProperty.add(halfWidthProperty).subtract(HANDLE_RADIUS / 2));
                resizeHandleN.yProperty().bind(yProperty.subtract(HANDLE_RADIUS / 2));

                setUpDragging(resizeHandleN, wrappedEvent, Cursor.N_RESIZE);

                resizeHandleN.setOnMouseDragged(
                        event -> {
                            if (wrappedEvent.value != null
                                    && wrappedEvent.value.getButton()
                                            == graph.getNodeGestures().getDragButton()) {
                                dragNorth(event, wrappedEvent, region);
                                wrappedEvent.value = event;
                            }
                        });
                return resizeHandleN;
            };

    static DragNodeSupplier NORTH_EAST =
            (graph, region, wrappedEvent) -> {
                final DoubleProperty xProperty = region.layoutXProperty();
                final DoubleProperty yProperty = region.layoutYProperty();
                final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();

                final Rectangle resizeHandleNE =
                        new Rectangle(HANDLE_RADIUS, HANDLE_RADIUS, Color.BLACK);
                resizeHandleNE.setOpacity(0.5);
                resizeHandleNE
                        .xProperty()
                        .bind(xProperty.add(widthProperty).subtract(HANDLE_RADIUS / 2));
                resizeHandleNE.yProperty().bind(yProperty.subtract(HANDLE_RADIUS / 2));

                setUpDragging(resizeHandleNE, wrappedEvent, Cursor.NE_RESIZE);

                resizeHandleNE.setOnMouseDragged(
                        event -> {
                            if (wrappedEvent.value != null
                                    && wrappedEvent.value.getButton()
                                            == graph.getNodeGestures().getDragButton()) {
                                dragNorth(event, wrappedEvent, region);
                                dragEast(event, wrappedEvent, region);
                                wrappedEvent.value = event;
                            }
                        });
                return resizeHandleNE;
            };

    static DragNodeSupplier EAST =
            (graph, region, wrappedEvent) -> {
                final DoubleProperty xProperty = region.layoutXProperty();
                final DoubleProperty yProperty = region.layoutYProperty();
                final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
                final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();
                final DoubleBinding halfHeightProperty = heightProperty.divide(2);

                final Rectangle resizeHandleE =
                        new Rectangle(HANDLE_RADIUS, HANDLE_RADIUS, Color.BLACK);
                resizeHandleE.setOpacity(0.5);
                resizeHandleE
                        .xProperty()
                        .bind(xProperty.add(widthProperty).subtract(HANDLE_RADIUS / 2));
                resizeHandleE
                        .yProperty()
                        .bind(yProperty.add(halfHeightProperty).subtract(HANDLE_RADIUS / 2));

                setUpDragging(resizeHandleE, wrappedEvent, Cursor.E_RESIZE);

                resizeHandleE.setOnMouseDragged(
                        event -> {
                            if (wrappedEvent.value != null
                                    && wrappedEvent.value.getButton()
                                            == graph.getNodeGestures().getDragButton()) {
                                dragEast(event, wrappedEvent, region);
                                wrappedEvent.value = event;
                            }
                        });
                return resizeHandleE;
            };

    static DragNodeSupplier SOUTH_EAST =
            (graph, region, wrappedEvent) -> {
                final DoubleProperty xProperty = region.layoutXProperty();
                final DoubleProperty yProperty = region.layoutYProperty();
                final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
                final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();

                final Rectangle resizeHandleSE =
                        new Rectangle(HANDLE_RADIUS, HANDLE_RADIUS, Color.BLACK);
                resizeHandleSE.setOpacity(0.5);
                resizeHandleSE
                        .xProperty()
                        .bind(xProperty.add(widthProperty).subtract(HANDLE_RADIUS / 2));
                resizeHandleSE
                        .yProperty()
                        .bind(yProperty.add(heightProperty).subtract(HANDLE_RADIUS / 2));

                setUpDragging(resizeHandleSE, wrappedEvent, Cursor.SE_RESIZE);

                resizeHandleSE.setOnMouseDragged(
                        event -> {
                            if (wrappedEvent.value != null
                                    && wrappedEvent.value.getButton()
                                            == graph.getNodeGestures().getDragButton()) {
                                dragSouth(event, wrappedEvent, region);
                                dragEast(event, wrappedEvent, region);
                                wrappedEvent.value = event;
                            }
                        });
                return resizeHandleSE;
            };

    static DragNodeSupplier SOUTH =
            (graph, region, wrappedEvent) -> {
                final DoubleProperty xProperty = region.layoutXProperty();
                final DoubleProperty yProperty = region.layoutYProperty();
                final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
                final DoubleBinding halfWidthProperty = widthProperty.divide(2);
                final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();

                final Rectangle resizeHandleS =
                        new Rectangle(HANDLE_RADIUS, HANDLE_RADIUS, Color.BLACK);
                resizeHandleS.setOpacity(0.5);
                resizeHandleS
                        .xProperty()
                        .bind(xProperty.add(halfWidthProperty).subtract(HANDLE_RADIUS / 2));
                resizeHandleS
                        .yProperty()
                        .bind(yProperty.add(heightProperty).subtract(HANDLE_RADIUS / 2));

                setUpDragging(resizeHandleS, wrappedEvent, Cursor.S_RESIZE);

                resizeHandleS.setOnMouseDragged(
                        event -> {
                            if (wrappedEvent.value != null
                                    && wrappedEvent.value.getButton()
                                            == graph.getNodeGestures().getDragButton()) {
                                dragSouth(event, wrappedEvent, region);
                                wrappedEvent.value = event;
                            }
                        });
                return resizeHandleS;
            };

    static DragNodeSupplier SOUTH_WEST =
            (graph, region, wrappedEvent) -> {
                final DoubleProperty xProperty = region.layoutXProperty();
                final DoubleProperty yProperty = region.layoutYProperty();
                final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();

                final Rectangle resizeHandleSW =
                        new Rectangle(HANDLE_RADIUS, HANDLE_RADIUS, Color.BLACK);
                resizeHandleSW.setOpacity(0.5);
                resizeHandleSW.xProperty().bind(xProperty.subtract(HANDLE_RADIUS / 2));
                resizeHandleSW
                        .yProperty()
                        .bind(yProperty.add(heightProperty).subtract(HANDLE_RADIUS / 2));

                setUpDragging(resizeHandleSW, wrappedEvent, Cursor.SW_RESIZE);

                resizeHandleSW.setOnMouseDragged(
                        event -> {
                            if (wrappedEvent.value != null
                                    && wrappedEvent.value.getButton()
                                            == graph.getNodeGestures().getDragButton()) {
                                dragSouth(event, wrappedEvent, region);
                                dragWest(event, wrappedEvent, region);
                                wrappedEvent.value = event;
                            }
                        });
                return resizeHandleSW;
            };

    static DragNodeSupplier WEST =
            (graph, region, wrappedEvent) -> {
                final DoubleProperty xProperty = region.layoutXProperty();
                final DoubleProperty yProperty = region.layoutYProperty();
                final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();
                final DoubleBinding halfHeightProperty = heightProperty.divide(2);

                final Rectangle resizeHandleW =
                        new Rectangle(HANDLE_RADIUS, HANDLE_RADIUS, Color.BLACK);
                resizeHandleW.setOpacity(0.5);
                resizeHandleW.xProperty().bind(xProperty.subtract(HANDLE_RADIUS / 2));
                resizeHandleW
                        .yProperty()
                        .bind(yProperty.add(halfHeightProperty).subtract(HANDLE_RADIUS / 2));

                setUpDragging(resizeHandleW, wrappedEvent, Cursor.W_RESIZE);

                resizeHandleW.setOnMouseDragged(
                        event -> {
                            if (wrappedEvent.value != null
                                    && wrappedEvent.value.getButton()
                                            == graph.getNodeGestures().getDragButton()) {
                                dragWest(event, wrappedEvent, region);
                                wrappedEvent.value = event;
                            }
                        });
                return resizeHandleW;
            };

    static DragNodeSupplier NORTH_WEST =
            (graph, region, wrappedEvent) -> {
                final DoubleProperty xProperty = region.layoutXProperty();
                final DoubleProperty yProperty = region.layoutYProperty();

                final Rectangle resizeHandleNW =
                        new Rectangle(HANDLE_RADIUS, HANDLE_RADIUS, Color.BLACK);
                resizeHandleNW.setOpacity(0.5);
                resizeHandleNW.xProperty().bind(xProperty.subtract(HANDLE_RADIUS / 2));
                resizeHandleNW.yProperty().bind(yProperty.subtract(HANDLE_RADIUS / 2));

                setUpDragging(resizeHandleNW, wrappedEvent, Cursor.NW_RESIZE);

                resizeHandleNW.setOnMouseDragged(
                        event -> {
                            if (wrappedEvent.value != null
                                    && wrappedEvent.value.getButton()
                                            == graph.getNodeGestures().getDragButton()) {
                                dragNorth(event, wrappedEvent, region);
                                dragWest(event, wrappedEvent, region);
                                wrappedEvent.value = event;
                            }
                        });
                return resizeHandleNW;
            };

    public static void makeResizable(Graph graph, Region region) {
        makeResizable(
                graph,
                region,
                NORTH,
                NORTH_EAST,
                EAST,
                SOUTH_EAST,
                SOUTH,
                SOUTH_WEST,
                WEST,
                NORTH_WEST);
    }

    public static void makeResizable(
            Graph graph, Region region, DragNodeSupplier... nodeSuppliers) {
        final Wrapper<MouseEvent> wrappedEvent = new Wrapper<>();

        final List<Node> dragNodes =
                Arrays.stream(nodeSuppliers)
                        .map(supplier -> supplier.apply(graph, region, wrappedEvent))
                        .toList();

        region.parentProperty()
                .addListener(
                        (obs, oldParent, newParent) -> {
                            for (final Node c : dragNodes) {
                                final Pane currentParent = (Pane) c.getParent();
                                if (currentParent != null) {
                                    currentParent.getChildren().remove(c);
                                }
                                ((Pane) newParent).getChildren().add(c);
                            }
                        });
    }

    private static void dragNorth(
            MouseEvent event, Wrapper<MouseEvent> wrappedEvent, Region region) {
        final double deltaY = event.getSceneY() - wrappedEvent.value.getSceneY();
        final double newY = region.getLayoutY() + deltaY;
        if (newY >= CellGestures.HANDLE_RADIUS
                && newY <= region.getLayoutY() + region.getHeight() - CellGestures.HANDLE_RADIUS) {
            region.setLayoutY(newY);
            region.setPrefHeight(region.getPrefHeight() - deltaY);
        }
    }

    private static void dragEast(
            MouseEvent event, Wrapper<MouseEvent> wrappedEvent, Region region) {
        final double deltaX = event.getSceneX() - wrappedEvent.value.getSceneX();
        final double newMaxX = region.getLayoutX() + region.getWidth() + deltaX;
        if (newMaxX >= region.getLayoutX()
                && newMaxX
                        <= region.getParent().getBoundsInLocal().getWidth()
                                - CellGestures.HANDLE_RADIUS) {
            region.setPrefWidth(region.getPrefWidth() + deltaX);
        }
    }

    private static void dragSouth(
            MouseEvent event, Wrapper<MouseEvent> wrappedEvent, Region region) {
        final double deltaY = event.getSceneY() - wrappedEvent.value.getSceneY();
        final double newMaxY = region.getLayoutY() + region.getHeight() + deltaY;
        if (newMaxY >= region.getLayoutY()
                && newMaxY
                        <= region.getParent().getBoundsInLocal().getHeight()
                                - CellGestures.HANDLE_RADIUS) {
            region.setPrefHeight(region.getPrefHeight() + deltaY);
        }
    }

    private static void dragWest(
            MouseEvent event, Wrapper<MouseEvent> wrappedEvent, Region region) {
        final double deltaX = event.getSceneX() - wrappedEvent.value.getSceneX();
        final double newX = region.getLayoutX() + deltaX;
        if (newX != 0
                && newX
                        <= region.getParent().getBoundsInLocal().getWidth()
                                - CellGestures.HANDLE_RADIUS) {
            region.setLayoutX(newX);
            region.setPrefWidth(region.getPrefWidth() - deltaX);
        }
    }

    private static void setUpDragging(
            Node node, Wrapper<MouseEvent> wrappedEvent, Cursor hoverCursor) {
        node.setOnMouseEntered(event -> node.getParent().setCursor(hoverCursor));

        node.setOnMouseExited(event -> node.getParent().setCursor(Cursor.DEFAULT));

        node.setOnDragDetected(
                event -> {
                    node.getParent().setCursor(Cursor.CLOSED_HAND);
                    wrappedEvent.value = event;
                });

        node.setOnMouseReleased(
                event -> {
                    node.getParent().setCursor(Cursor.DEFAULT);
                    wrappedEvent.value = null;
                });
    }

    public interface DragNodeSupplier {
        Node apply(Graph graph, Region region, Wrapper<MouseEvent> wrappedEvent);
    }

    public static class Wrapper<T> {
        T value;
    }
}
