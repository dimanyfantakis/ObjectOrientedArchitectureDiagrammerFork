package model.diagram.fxgraph.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.diagram.fxgraph.cells.UMLCellFactory;
import model.diagram.fxgraph.cells.PackageCell;
import model.diagram.fxgraph.edges.Edge;
import model.diagram.fxgraph.graph.Graph;
import model.diagram.fxgraph.graph.ICell;
import model.diagram.fxgraph.graph.Model;
import model.diagram.fxgraph.layout.RandomLayout;
import model.graph.ArcType;

public class Example extends Application {

    public Graph foo() {
        Graph graph = new Graph();
        final Model model = graph.getModel();

        graph.beginUpdate();

        final ICell cellA = UMLCellFactory.createClass("ClassA", new String[]{"- attr1: String", "+ attr2: int"}, new String[]{"+ method1(): void", "- method2(int): String"});
        final ICell cellB = UMLCellFactory.createInterface("Comparable", new String[]{}, new String[]{"+ compareTo(Object): int"});
        final ICell cellC = UMLCellFactory.createEnum("Color", new String[]{"RED", "GREEN", "BLUE"}, new String[]{"+ getRGB(): int"});
        final ICell cellD = UMLCellFactory.createClass("ClassD", new String[]{"+ attr4: List"}, new String[]{});
        final ICell cellE = UMLCellFactory.createClass("ClassE", new String[]{}, new String[]{});
        final ICell cellF = UMLCellFactory.createClass("ClassF", new String[]{}, new String[]{});
        final ICell cellG = new PackageCell("com.example");

        model.addCell(cellA);
        model.addCell(cellB);
        model.addCell(cellC);
        model.addCell(cellD);
        model.addCell(cellE);
        model.addCell(cellF);
        model.addCell(cellG);

        final Edge edgeAB = new Edge(cellA, cellB, true, ArcType.IMPLEMENTATION);
        edgeAB.getTextProperty().set("implements");
        model.addEdge(edgeAB);

        final Edge edgeAC = new Edge(cellA, cellC, true, ArcType.ASSOCIATION);
        edgeAC.getTextProperty().set("uses");
        model.addEdge(edgeAC);

        final Edge edgeAD = new Edge(cellA, cellD, true, ArcType.EXTENSION);
        edgeAD.getTextProperty().set("extends");
        model.addEdge(edgeAD);

        final Edge edgeBE = new Edge(cellB, cellE, true, ArcType.AGGREGATION);
        edgeBE.getTextProperty().set("has-a");
        model.addEdge(edgeBE);

        final Edge edgeDF = new Edge(cellD, cellF, true, ArcType.DEPENDENCY);
        edgeDF.getTextProperty().set("depends");
        model.addEdge(edgeDF);

        final Edge edgeCG = new Edge(cellC, cellG, true, ArcType.ASSOCIATION);
        edgeCG.getTextProperty().set("in package");
        model.addEdge(edgeCG);

        graph.endUpdate();
        graph.layout(new RandomLayout());

        return graph;
    }

    @Override
    public void start(Stage stage) {
        Graph graph = foo();

        // Configure interaction buttons and behavior
        graph.getViewportGestures().setPanButton(MouseButton.SECONDARY);
        graph.getNodeGestures().setDragButton(MouseButton.PRIMARY);

        // Zoom controls
        Button zoomIn = new Button("Zoom In");
        zoomIn.setOnAction(e -> graph.getCanvas().setScale(graph.getScale() * 1.2));

        Button zoomOut = new Button("Zoom Out");
        zoomOut.setOnAction(e -> graph.getCanvas().setScale(graph.getScale() / 1.2));

        Button resetZoom = new Button("Reset Zoom");
        resetZoom.setOnAction(e -> graph.getCanvas().setScale(1.0));

        HBox controls = new HBox(10, zoomIn, zoomOut, resetZoom);
        controls.setStyle("-fx-padding: 10; -fx-background-color: lightgray;");

        // Display the graph
        BorderPane root = new BorderPane();
        root.setTop(controls);
        root.setCenter(graph.getCanvas());
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String... args) {
        launch();
    }
}
