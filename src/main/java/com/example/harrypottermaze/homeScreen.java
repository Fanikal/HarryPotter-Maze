package com.example.harrypottermaze;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Objects;

public class homeScreen extends Application {

    public static void main(String[] args) {
        // Launch of the application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Definition of the stage
        primaryStage.setTitle("Harry Potter's Maze");

        // Definition of the root pane which will contain all the other elements
        FlowPane homeRoot = new FlowPane(Orientation.VERTICAL);
        homeRoot.setAlignment(Pos.CENTER);
        homeRoot.getStyleClass().add("homeRoot");

        // We set the height of the stage
        primaryStage.setHeight(750);
        primaryStage.setWidth(550);

        // Creation of the scene that contains root as a root pane
        Scene scene = new Scene(homeRoot);

        // VBox for the title
        VBox titlePane = new VBox();
        titlePane.setPadding(new Insets(30, 10, 10, 10));
        titlePane.setAlignment(Pos.CENTER);
        homeRoot.getChildren().add(titlePane);

        // Create a moving popup
        Label movingPopup = new Label("Say \"I solemnly swear that I'm up to no good\" to open the game");
        movingPopup.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Add the moving popup to the titlePane
        titlePane.getChildren().add(movingPopup);

        // Create a TranslateTransition to make the text scroll vertically
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(15), movingPopup);
        translateTransition.setFromY(-400);
        translateTransition.setToY(400);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();

        /* Create a button for "Let's go"
        Button letsstartButton = new Button("Let's go");

        // Add an action to the button to transition to selectCharacter with fade-in effect
        letsstartButton.setOnAction(e -> {

            });

         */

        // We display the scene we just created in the stage
        primaryStage.setScene(scene);

        // We show the stage
        primaryStage.show();

        // Definition of css file
        try {
            scene.getStylesheets().add(Objects.requireNonNull(homeScreen.class.getResource("/com/example/harrypottermaze/mycss.css")).toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Failed to load CSS file: " + e.getMessage());
        }
    }
}
