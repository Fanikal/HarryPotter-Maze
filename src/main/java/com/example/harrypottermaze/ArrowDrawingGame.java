package com.example.harrypottermaze;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class ArrowDrawingGame extends Application {

    private double startX, startY;
    private Circle currentCircle;
    private Pane drawingPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Freeform Circle Drawing Game");

        drawingPane = new Pane();
        drawingPane.setOnMousePressed(this::handleMousePressed);
        drawingPane.setOnMouseDragged(this::handleMouseDragged);
        drawingPane.setOnMouseReleased(this::handleMouseReleased);

        Scene scene = new Scene(drawingPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMousePressed(MouseEvent event) {
        startX = event.getX();
        startY = event.getY();
    }

    private void handleMouseDragged(MouseEvent event) {
        if (currentCircle != null) {
            drawingPane.getChildren().remove(currentCircle);
        }

        double currentX = event.getX();
        double currentY = event.getY();
        double radius = Math.sqrt(Math.pow(currentX - startX, 2) + Math.pow(currentY - startY, 2));

        currentCircle = new Circle(startX, startY, radius);
        currentCircle.setStroke(Color.BLACK);
        currentCircle.setFill(Color.TRANSPARENT);

        drawingPane.getChildren().add(currentCircle);
    }

    private void handleMouseReleased(MouseEvent event) {
        // Perform any action you want when the user releases the mouse
        // For example, check the final radius and perform an action if it meets your criteria
        if (currentCircle != null) {
            double finalRadius = currentCircle.getRadius();

            // You can define your criteria for recognizing the circular-like shape
            double minLength = 50; // Minimum radius for recognizing a circular-like shape
            if (finalRadius >= minLength) {
                performAction(); // Replace with your action (e.g., "killing" Voldemort)
            }

            drawingPane.getChildren().remove(currentCircle);
            currentCircle = null;
        }
    }

    private void performAction() {
        // Implement your action here, e.g., "killing" Voldemort
        System.out.println("Action performed: Voldemort killed!");
    }
}
