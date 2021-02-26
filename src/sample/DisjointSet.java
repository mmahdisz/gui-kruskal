package sample;

import java.util.ArrayList;
import java.util.List;

public class DisjointSet {

    private int nodeCount;
    private int setCount;
    private List<Node> rootNodes;

    DisjointSet(List<Vertex> vertexList) {
        this.rootNodes = new ArrayList<>(vertexList.size());
        makeSets(vertexList);
    }

    public int find(Node node){
        
        Node current=node;
        
        while(current.getParent() != null ){
            current = current.getParent() ;
        }
        
        Node root=current;
        return root.getId();
    }
    
    public void union(Node node1,Node node2){
        
        int index1=find(node1);
        int index2=find(node2);
        
        if(index1==index2){
            return;
        }
        
        Node root1=this.rootNodes.get(index1);
        Node root2=this.rootNodes.get(index2);
        
        if(root1.getHeight() < root2.getHeight()){
            root1.setParent(root2);
        }else if(root1.getHeight() > root2.getHeight()){
            root2.setParent(root1);
        }else{
            root2.setParent(root1);
            root1.setHeight(root1.getHeight()+1);
        }
        
        this.setCount--;
        
    }

    private void makeSets(List<Vertex> vertexList) {
        for(Vertex vertex : vertexList){
            makeSet(vertex);
        }
    }

    private void makeSet(Vertex vertex) {
        Node node=new Node(0,this.rootNodes.size(),null);
        vertex.setNode(node);
        nodeCount++;
        setCount++;
        this.rootNodes.add(node);
    }
    
}
