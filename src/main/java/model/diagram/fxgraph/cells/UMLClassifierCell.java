package model.diagram.fxgraph.cells;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.diagram.fxgraph.graph.Graph;

public class UMLClassifierCell extends AbstractCell {

    protected final String name;
    protected final String[] section1;
    protected final String[] section2;
    protected final String stereotypeLabel;
    protected final Color backgroundColor;

    public UMLClassifierCell(
            String name,
            String[] section1,
            String[] section2,
            String stereotypeLabel,
            Color backgroundColor) {
        this.name = name;
        this.section1 = section1;
        this.section2 = section2;
        this.stereotypeLabel = stereotypeLabel;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public Region getGraphic(Graph graph) {
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPrefWidth(150);
        vbox.setMaxWidth(150);
        vbox.setStyle("-fx-padding: 10; -fx-background-color: transparent; -fx-border-color: transparent;");

        // Stereotype indicator (if any)
        if (stereotypeLabel != null && !stereotypeLabel.isEmpty()) {
            Text indicator = new Text(stereotypeLabel);
            indicator.setFont(Font.font("Arial", 9));
            indicator.setFill(Color.GRAY);
            vbox.getChildren().add(indicator);
        }

        // Element name
        Text nameText = new Text(name);
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        nameText.setWrappingWidth(130);
        vbox.getChildren().add(nameText);

        // Separator line 1
        if (section1.length > 0) {
            Line line1 = new Line(0, 0, 130, 0);
            line1.setStroke(Color.BLACK);
            vbox.getChildren().add(line1);

            // Section 1 items
            for (String item : section1) {
                Text itemText = new Text(item);
                itemText.setFont(Font.font("Arial", 10));
                itemText.setWrappingWidth(130);
                vbox.getChildren().add(itemText);
            }
        }

        // Separator line 2
        if (section2.length > 0) {
            Line line2 = new Line(0, 0, 130, 0);
            line2.setStroke(Color.BLACK);
            vbox.getChildren().add(line2);

            // Section 2 items
            for (String item : section2) {
                Text itemText = new Text(item);
                itemText.setFont(Font.font("Arial", 10));
                itemText.setWrappingWidth(130);
                vbox.getChildren().add(itemText);
            }
        }

        final Pane pane = new Pane();
        Rectangle background = new Rectangle(150, 100);
        background.setFill(backgroundColor);
        background.setStroke(Color.BLACK);
        background.setArcWidth(10);
        background.setArcHeight(10);
        background.setEffect(new DropShadow(5, Color.GRAY));
        background.widthProperty().bind(pane.prefWidthProperty());
        background.heightProperty().bind(pane.prefHeightProperty());

        pane.getChildren().addAll(background, vbox);
        pane.prefWidthProperty().bind(vbox.widthProperty());
        pane.prefHeightProperty().bind(vbox.heightProperty());
        CellGestures.makeResizable(graph, pane);

        return pane;
    }
}
