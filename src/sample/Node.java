package sample;

public class Node {
    
    private int height;//reference to the amount of the edges bellow this edge
    private int id;//unique id to speccific node
    private Node parent;//a reference to a union using disjointSet implementation

    public Node(int height, int id, Node parent) {
        this.height = height;
        this.id = id;
        this.parent = parent;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
