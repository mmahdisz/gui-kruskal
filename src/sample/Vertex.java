package sample;

import javafx.scene.shape.Circle;

public class Vertex {

    private Circle circle;
    private String name;
    private Node node;

    public Vertex(String name) {
        this.name = name;
    }

    public Vertex(Circle circle, String name) {
        this.circle = circle;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return this.name ;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
