package sample;

import javafx.scene.control.Label;
import javafx.scene.shape.Line;


public class Edge implements Comparable<Edge> {

    private Line line;
    private double weight;
    private Vertex oneVertex;
    private Vertex anotherVertex;
    private Label weightLabel;

    public Edge(double weight, Vertex oneVertex, Vertex anotherVertex) {
        this.weight = weight;
        this.oneVertex = oneVertex;
        this.anotherVertex = anotherVertex;
    }

    public Edge(Line line, double weight, Vertex oneVertex, Vertex anotherVertex) {
        this.line = line;
        this.weight = weight;
        this.oneVertex = oneVertex;
        this.anotherVertex = anotherVertex;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Vertex getOneVertex() {
        return oneVertex;
    }

    public void setOneVertex(Vertex oneVertex) {
        this.oneVertex = oneVertex;
    }

    public Vertex getAnotherVertex() {
        return anotherVertex;
    }

    public void setAnotherVertex(Vertex anotherVertex) {
        this.anotherVertex = anotherVertex;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "\nEdge{" +
                "weight= " + weight +
                ", FirstVertex= " + oneVertex +
                ", SecondVertex= " + anotherVertex +
                "}";
    }

    @Override
    public int compareTo(Edge o) {
        return Double.compare(this.weight, o.getWeight());
    }

    public Label getWeightLabel() {
        return weightLabel;
    }

    public void setWeightLabel(Label weightLabel) {
        this.weightLabel = weightLabel;
    }
}
