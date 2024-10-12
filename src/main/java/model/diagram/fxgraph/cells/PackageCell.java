package model.diagram.fxgraph.cells;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.diagram.fxgraph.graph.Graph;

public class PackageCell extends AbstractCell {

    private final String packageName;

    public PackageCell(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public Region getGraphic(Graph graph) {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPrefWidth(120);
        vbox.setMaxWidth(120);
        vbox.setStyle("-fx-padding: 15; -fx-background-color: transparent; -fx-border-color: transparent;");

        Text nameText = new Text(packageName);
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        nameText.setWrappingWidth(100);
        vbox.getChildren().add(nameText);

        final Pane pane = new Pane();
        Rectangle background = new Rectangle(120, 80);
        background.setFill(Color.web("#F0E68C"));
        background.setStroke(Color.BLACK);
        background.setStrokeWidth(2);
        background.setEffect(new DropShadow(5, Color.GRAY));
        
        pane.getChildren().addAll(background, vbox);
        pane.setPrefSize(120, 80);
        CellGestures.makeResizable(graph, pane);

        return pane;
    }
}
