package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Algorithm {

    private List<Edge> mst;

    Algorithm() {
        mst = new ArrayList<>();
    }

    void spanningTree(List<Vertex> vertexList, List<Edge> edgeList) {

        DisjointSet joint = new DisjointSet(vertexList);

        Collections.sort(edgeList);

        for (Edge edge : edgeList) {

            Vertex v = edge.getOneVertex();
            Vertex u = edge.getAnotherVertex();

            if (joint.find(v.getNode()) != joint.find(u.getNode())) {

                this.mst.add(edge);
                joint.union(v.getNode(), u.getNode());
            }
        }
    }

    public List<Edge> getMst() {
        return mst;
    }

    void printMST() {
        for (Edge e : this.mst) {
            System.out.println("Edge : " + e.getOneVertex() + "-" + e.getAnotherVertex());
        }
    }


}
