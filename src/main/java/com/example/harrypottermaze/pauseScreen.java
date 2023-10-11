package com.example.harrypottermaze;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class pauseScreen extends Application {

    private String timeSpent;
    private String msgToShow = "The game is paused - press space to resume, or:";

    public pauseScreen() {
        // Default constructor with no arguments
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pauseStage) {

        pauseStage.setTitle("Game: paused");

        // Create a BorderPane to hold the content
        BorderPane root = new BorderPane();

        // Background of the game
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/sfondo.jpg"));
        BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImageObject);

        root.setBackground(background);

        // Load the "ribbon 3" image from resources
        Image pauseImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/ribbon.png"));

        // Create an ImageView to display the image with a specified width and height
        ImageView pauseImageView = new ImageView(pauseImage);
        pauseImageView.setFitWidth(800); // Set a larger width
        pauseImageView.setFitHeight(400); // Set a larger height
        pauseImageView.setPreserveRatio(true); // Preserve image aspect ratio
        root.setAlignment(pauseImageView, Pos.CENTER);



        // Create a Text node for the message ("Time is up" or "Lives are up")
        Text messageText = new Text(msgToShow);
        messageText.setFont(Font.font("Zampfino", 20));
        messageText.setTranslateY(-180);

        VBox messageBox = new VBox();
        messageBox.setAlignment(Pos.CENTER);
        messageBox.getChildren().add(messageText);


        // Create an HBox for the buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(0, 0, 0, 0));

        // Create the "Restart" button
        Button restartButton = new Button("Start new game");
        restartButton.setOnAction(event -> {
            // Go back to the screen with the maze
        });

        restartButton.getStyleClass().add("main-button");

        // Create the "Exit" button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            // Go back to the home page
            pauseStage.close(); // Close the win screen
            homeScreen hScreen = new homeScreen();
            try {
                hScreen.start(new Stage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        exitButton.getStyleClass().add("main-button");

        // Add buttons to the button box
        buttonBox.getChildren().addAll(restartButton, exitButton);
        buttonBox.setTranslateY(-200);

        // Set the image at the top of the BorderPane
        root.setTop(pauseImageView);

        // Set the message box at the center of the BorderPane
        root.setCenter(messageBox);

        // Set the button box at the center of the BorderPane
        root.setBottom(buttonBox);

        // Create the scene with the BorderPane as the root node
        Scene gameOverScene = new Scene(root, 800, 600); // Adjust width and height as needed

        // Set the scene for the game over stage
        pauseStage.setScene(gameOverScene);

        // Show the game over stage
        pauseStage.show();

        // Load the CSS file
        gameOverScene.getStylesheets().add
                (winScreen.class.getResource("mycss.css").toExternalForm());

    }
}
