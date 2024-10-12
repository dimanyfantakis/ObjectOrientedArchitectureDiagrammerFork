package model.diagram.fxgraph.cells;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.diagram.fxgraph.graph.Graph;

public class RectangleCell extends AbstractCell {

    private final Color strokeColor;
    private final Color fillColor;

    public RectangleCell() {
        this(Color.ORANGE, Color.ORANGE);
    }

    public RectangleCell(Color strokeColor, Color fillColor) {
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
    }

    @Override
    public Region getGraphic(Graph graph) {
        final Rectangle view = new Rectangle(50, 50);

        view.setStroke(strokeColor);
        view.setFill(fillColor);

        final Pane pane = new Pane(view);
        pane.setPrefSize(50, 50);
        view.widthProperty().bind(pane.prefWidthProperty());
        view.heightProperty().bind(pane.prefHeightProperty());
        CellGestures.makeResizable(graph, pane);

        return pane;
    }
}
