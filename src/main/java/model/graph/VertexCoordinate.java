package model.graph;

public record VertexCoordinate(double x, double y) {
    public VertexCoordinate() {
        this(0, 0);
    }
}
