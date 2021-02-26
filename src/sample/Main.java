package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    // global views
    private Rectangle rectangle;
    private Button confirmBtn;
    private Button linkBtn;
    private Button resetBtn;
    private Button saveResultBtn;
    private Circle circle;
    private Line line;

    // logical variables
    private int firstIndex = 0;
    private boolean isVerticesConfirmed = false;
    private int selectedVerticesCount = 0;
    private int lastVertex = 0;
    private boolean isVertexAlreadyCreated = false;

    private static ArrayList<Vertex> vertices = new ArrayList<>();
    private static ArrayList<Edge> edges = new ArrayList<>();
    private Algorithm kruskalAlgorithm = new Algorithm();

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        Scene scene = new Scene(root, 920, 910);
        scene.setFill(Color.rgb(250, 250, 250, 0.5));

        primaryStage.setTitle("Kruskal Algorithm");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        setScene(root);


        Alert tutorialAlert = new Alert(Alert.AlertType.INFORMATION,
                "Hi! :)" +
                        "\nTo find minimum spanning tree of a graph, place vertices in grey area.\n" +
                        "Good luck!",
                ButtonType.OK);
        tutorialAlert.setTitle("Tutorial");
        tutorialAlert.setHeaderText(null);
        tutorialAlert.showAndWait();


        scene.setOnMouseClicked(mouseEvent -> {
            if (!isVerticesConfirmed) {
                if (rectangle.contains(mouseEvent.getX() + 15, mouseEvent.getY() + 15)
                        && rectangle.contains(mouseEvent.getX() - 15, mouseEvent.getY() - 15)
                ) {
                    for (Vertex v : vertices) {
                        if (v.getCircle().contains(mouseEvent.getX(), mouseEvent.getY())) {
                            errorCreatingVertex();
                            isVertexAlreadyCreated = true;
                        }
                    }
                    if (!isVertexAlreadyCreated) {
                        addVertex(root, mouseEvent);
                    }
                    isVertexAlreadyCreated = false;
                }
            } else {
                for (int i = 0; i < vertices.size(); i++) {
                    if (vertices.get(i).getCircle().contains(mouseEvent.getX(), mouseEvent.getY())) {
                        if (vertices.get(i).getCircle().getFill() == Color.DARKGREY && selectedVerticesCount == 0) {
                            vertices.get(i).getCircle().setFill(Color.DARKRED);
                            selectedVerticesCount++;
                            lastVertex = i;
                        } else if (selectedVerticesCount == 1 && lastVertex != i) {
                            connectVertices(root, i);
                        } else if (lastVertex == i) {
                            selectedVerticesCount = 0;
                            vertices.get(lastVertex).getCircle().setFill(Color.DARKGREY);
                        }
                    }
                }
            }

        });

        confirmBtn.setOnAction(event -> {
            if (confirmBtn.getText().equals("Confirm Vertices")) {
                if (firstIndex > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "Choose two vertices to draw an Edge.\nThen Enter the weight.",
                            ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();

                    confirmBtn.setText("Execute Kruskal Algorithm");
                    isVerticesConfirmed = true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Place Vertices please");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            } else {
                System.out.println(vertices.toString());
                System.out.println(edges.toString());

                kruskalAlgorithm.spanningTree(vertices, edges);
                List<Edge> result = kruskalAlgorithm.getMst();

                for (Edge edge : result) {
                    System.out.println(edge);
                    for (int j = 0; j < edges.size(); j++) {
                        edge.getLine().setStyle("-fx-stroke: gold;");
                        edge.getLine().setStrokeWidth(4);
                    }
                }

                confirmBtn.setVisible(false);
            }
        });

        resetBtn.setOnMouseClicked(event -> {
            for (Vertex vertex : vertices) {
                root.getChildren().remove(vertex.getCircle());
            }
            for (Edge edge : edges) {
                root.getChildren().remove(edge.getLine());
                root.getChildren().remove(edge.getWeightLabel());
            }
            vertices.clear();
            edges.clear();
            firstIndex = 0;
            isVerticesConfirmed = false;
            selectedVerticesCount = 0;
            lastVertex = 0;
            isVertexAlreadyCreated = false;

            System.out.println("edges: " + edges.toString());
            System.out.println("vertices: " + vertices.toString());

            confirmBtn.setVisible(true);
            confirmBtn.setText("Confirm Vertices");

        });

        saveResultBtn.setOnMouseClicked(event -> {
            if (!confirmBtn.isVisible()) {
                String result = "Vertices: " + vertices.toString() + "\n\n" +
                        "Edges: " + edges.toString() + "\n\n" +
                        "MST: " + kruskalAlgorithm.getMst();

                FileChooser fileChooser = new FileChooser();

                //Set extension filter for text files
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    saveTextToFile(result, file);
                }

            } else {
                Alert alertErrorCreatingVertex = new Alert(Alert.AlertType.ERROR);
                alertErrorCreatingVertex.setTitle("Error Result");
                alertErrorCreatingVertex.setHeaderText("Result is not Ready");
                alertErrorCreatingVertex.setContentText("Please Let Kruskal Algorithm run to find result.");
                alertErrorCreatingVertex.showAndWait();
            }
        });

        primaryStage.show();
    }

    // provide text file to save result of running algorithm
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // create and show an alert to prevent place two vertex on each other
    private void errorCreatingVertex() {
        Alert alertErrorCreatingVertex = new Alert(Alert.AlertType.ERROR);
        alertErrorCreatingVertex.setTitle("Error Creating Vertex");
        alertErrorCreatingVertex.setHeaderText("There's a vertex in this place.");
        alertErrorCreatingVertex.setContentText("There's a vertex in this place." +
                " Please try somewhere else." +
                " having distanced vertices give you a better insight! :)");
        alertErrorCreatingVertex.showAndWait();
    }

    // set each layout in right place
    private void setScene(Group root) {
        rectangle = new Rectangle(100, 10, 720, 720);
        rectangle.setStroke(Color.DARKGREY);
        rectangle.setStrokeWidth(2);
        rectangle.setFill(Color.gray(0.95));
        rectangle.setX(100);
        rectangle.setY(50);
        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);
        root.getChildren().add(rectangle);

        confirmBtn = new Button("Confirm Vertices");
        confirmBtn.setLayoutX(100);
        confirmBtn.setLayoutY(rectangle.getHeight() + rectangle.getY() + 20);
        confirmBtn.setMaxWidth(rectangle.getWidth());
        confirmBtn.setMinWidth(rectangle.getWidth());
        confirmBtn.setStyle("-fx-font: 24 sans;");
        root.getChildren().add(confirmBtn);

        linkBtn = new Button("Kruskal algorithm wiki");
        linkBtn.setLayoutX(100);
        linkBtn.setLayoutY(confirmBtn.getLayoutY() + 70);
        linkBtn.setStyle("-fx-font: 16 sans;");
        root.getChildren().add(linkBtn);

        linkBtn.setOnMouseClicked(event -> {
            try {
                Desktop desktop = java.awt.Desktop.getDesktop();
                URI oURL = new URI("https://en.wikipedia.org/wiki/Kruskal%27s_algorithm");
                desktop.browse(oURL);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        resetBtn = new Button("Reset");
        resetBtn.setLayoutX(linkBtn.getLayoutX() + 200);
        resetBtn.setLayoutY(confirmBtn.getLayoutY() + 70);
        resetBtn.setStyle("-fx-font: 16 sans;");
        root.getChildren().add(resetBtn);

        saveResultBtn = new Button("Save Result");
        saveResultBtn.setLayoutX(linkBtn.getLayoutX() + 280);
        saveResultBtn.setLayoutY(confirmBtn.getLayoutY() + 70);
        saveResultBtn.setStyle("-fx-font: 16 sans;");
        root.getChildren().add(saveResultBtn);

    }

    // create and add a vertex to right place
    private void addVertex(Group root, MouseEvent mouseEvent) {
        circle = new Circle(mouseEvent.getX(), mouseEvent.getY(), 15);
        circle.setFill(Color.DARKGREY);
        Vertex vertex;
        vertex = new Vertex(this.circle, firstIndex + "");
        System.out.println("add: " + firstIndex);
        firstIndex++;
        vertices.add(vertex);
        root.getChildren().add(this.circle);
    }

    // place an edge between two vertex
    private void connectVertices(Group root, int i) {
        selectedVerticesCount = 0;
        vertices.get(lastVertex).getCircle().setFill(Color.DARKGREY);

        line = new Line(vertices.get(lastVertex).getCircle().getCenterX(),
                vertices.get(lastVertex).getCircle().getCenterY(),
                vertices.get(i).getCircle().getCenterX(),
                vertices.get(i).getCircle().getCenterY());

        line.setStroke(Color.DARKGREY);
        line.setStrokeWidth(3);
        int weight = 1;
        boolean isWeightConfirmed = false;


        while (!isWeightConfirmed) {
            try {
                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Weight");
                dialog.setHeaderText("Weight of edge");
                dialog.setContentText("Please enter weight:");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent() && result.get().matches("[0-9]+")) {
                    weight = Integer.parseInt(result.get());
                    isWeightConfirmed = true;
                    System.out.println(i + " to " + lastVertex + "  weight: " + weight);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }


        root.getChildren().add(line);

        Label weightLabel = new Label(weight + "");
        weightLabel.setLayoutX(Math.min(line.getEndX(), line.getStartX()) + Math.abs(line.getEndX() - line.getStartX()) / 2);
        weightLabel.setLayoutY(Math.min(line.getEndY(), line.getStartY()) + Math.abs(line.getEndY() - line.getStartY()) / 2);
        weightLabel.setStyle("-fx-font-size: 19;");
        root.getChildren().add(weightLabel);

        Edge newEdge = new Edge(line, weight, vertices.get(lastVertex), vertices.get(i));
        newEdge.setWeightLabel(weightLabel);

        edges.add(newEdge);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
