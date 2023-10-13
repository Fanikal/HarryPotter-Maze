package com.example.harrypottermaze;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class FreeformDrawingGame extends Application {

    private Pane drawingPane;
    private Path currentPath;
    private boolean isDrawing = false;
    private Circle pointA, pointB, pointC;
    private int connectedPoints = 0;
    private newMaze newMaze;
    private Timeline voldemortTimer;

    public FreeformDrawingGame(newMaze newMaze) {
        this.newMaze = newMaze;
    }

    public FreeformDrawingGame() {
        //empty constructor
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connect Points Game");

        drawingPane = new Pane();

        // Create three points (A, B, and C) as circles on the canvas
        pointA = createPoint(100, 100);
        pointB = createPoint(400, 400);
        pointC = createPoint(700, 100);

        // Load the background image from the classpath
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/sfondo.jpg"));
        // Set the background image for the Pane
        BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        drawingPane.setBackground(new Background(backgroundImageObject));

        // Initialize event handlers for this drawingPane
        drawingPane.setOnMousePressed(this::handleMousePressed);
        drawingPane.setOnMouseDragged(this::handleMouseDragged);
        drawingPane.setOnMouseReleased(this::handleMouseReleased);

        Scene scene = new Scene(drawingPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startDrawingGame() {
        // Show the drawing game window
        Stage drawingStage = new Stage();
        drawingStage.setTitle("Connect Points Game");

        if (drawingPane == null) {
            drawingPane = new Pane();
            // Initialize event handlers for this drawingPane
            drawingPane.setOnMousePressed(this::handleMousePressed);
            drawingPane.setOnMouseDragged(this::handleMouseDragged);
            drawingPane.setOnMouseReleased(this::handleMouseReleased);

            // Create three points (A, B, and C) as circles on the canvas
            pointA = createPoint(100, 100);
            pointB = createPoint(400, 400);
            pointC = createPoint(700, 100);

            // Load the background image from the classpath
            Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/sfondo.jpg"));
            // Set the background image for the Pane
            BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
            drawingPane.setBackground(new Background(backgroundImageObject));
        }

        // Start the 10-second timer
        voldemortTimer = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            // Timer expiration - check if the user connected the points
            if (connectedPoints == 3) {
                System.out.println("Success in drawing, going to remove Voldemort");
                // User succeeded
                // Hide Voldemort from the newMaze
                newMaze.removeVoldemort();
            }
            drawingStage.close(); // Close the drawing game window
        }));
        voldemortTimer.setCycleCount(1);
        voldemortTimer.play();

        Scene scene = new Scene(drawingPane, 800, 600);
        drawingStage.setScene(scene);
        drawingStage.show();
    }

    private Circle createPoint(double x, double y) {
        Circle point = new Circle(x, y, 10);
        point.setFill(Color.RED);
        drawingPane.getChildren().add(point);
        return point;
    }

    private void handleMousePressed(MouseEvent event) {
        if (!isDrawing) {
            currentPath = new Path();
            currentPath.setStroke(Color.BLACK);
            currentPath.setStrokeWidth(2);
            currentPath.getElements().add(new MoveTo(event.getX(), event.getY()));
            drawingPane.getChildren().add(currentPath);
            isDrawing = true;
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        if (isDrawing) {
            currentPath.getElements().add(new LineTo(event.getX(), event.getY()));
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        if (isDrawing) {
            if (isCloseTo(pointA, currentPath)) {
                connectedPoints++;
                pointA.setFill(Color.GREEN);
            }
            if (isCloseTo(pointB, currentPath)) {
                connectedPoints++;
                pointB.setFill(Color.GREEN);
            }
            if (isCloseTo(pointC, currentPath)) {
                connectedPoints++;
                pointC.setFill(Color.GREEN);
            }

            // Check if the user has connected all points
            if (connectedPoints == 3) {
                System.out.println("Success in drawing");
                currentPath.setStroke(Color.GREEN);
            } else {
                currentPath.setStroke(Color.RED);
            }

            isDrawing = false;
        }
    }

    private boolean isCloseTo(Circle point, Path path) {
        for (PathElement pathElement : path.getElements()) {
            if (pathElement instanceof LineTo) {
                LineTo lineTo = (LineTo) pathElement;
                double distance = Math.sqrt(Math.pow(point.getCenterX() - lineTo.getX(), 2)
                        + Math.pow(point.getCenterY() - lineTo.getY(), 2));
                if (distance < 20) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
