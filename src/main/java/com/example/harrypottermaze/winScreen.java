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

public class winScreen extends Application {

    private WinCallback winCallback;
    private WinExitCallback winExitCallback;
    private String msgToShow = "Mischief managed!";
    private String selectedHouse;
    private Stage gameStage;

    public winScreen(WinCallback winCallback, WinExitCallback winExitCallback, Stage gameStage, String selectedHouse) {
        this.gameStage = gameStage;
        this.selectedHouse = selectedHouse;
        this.winCallback = winCallback;
        this.winExitCallback = winExitCallback;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage winStage) {

        // title of the stage
        winStage.setTitle("Congrats");

        // BorderPane to hold the content
        BorderPane root = new BorderPane();

        // Background of the game
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/sfondo.jpg"));
        BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImageObject);
        root.setBackground(background);

        // Load the "ribbon 3" image from resources
        Image winImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/ribbon 2.png"));

        // ImageView to display the image with a specified width and height
        ImageView winImageView = new ImageView(winImage);
        winImageView.setFitWidth(800);
        winImageView.setFitHeight(400);
        winImageView.setPreserveRatio(true);
        root.setAlignment(winImageView, Pos.CENTER);

        // Text node for the message
        Text messageText = new Text(msgToShow);
        messageText.setFont(Font.font("Zampfino", 20));
        messageText.setTranslateY(-180);

        // VBOx for the messageBox
        VBox messageBox = new VBox();
        messageBox.setAlignment(Pos.CENTER);
        messageBox.getChildren().add(messageText);

        // Create an HBox for the buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(0, 0, 0, 0));

        // Create the "Restart" button
        Button restartButton = new Button("Start new game");
        restartButton.getStyleClass().add("main-button");

        // handle the events of restart button
        restartButton.setOnAction(event -> {
            System.out.println("Restarting game");
            gameStage.close(); // close the gameStage
            newMaze newMazeGame = new newMaze(selectedHouse); // Create a new instance of the game
            try {
                newMazeGame.start(new Stage()); // Start the new game
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            winStage.close();
        });

        // Create the "Exit" button
        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("main-button");

        // handle the event of exit button
        exitButton.setOnAction(event -> {
            winExitCallback.onExitClicked(true);
            newMaze newMazeGame = new newMaze(selectedHouse); // Create a new instance of the game
            winStage.close(); // Close the win screen
            gameStage.close(); // Close the game screen
            homeScreen hScreen = new homeScreen(); // Go back to the home page
            try {
                hScreen.start(new Stage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Add buttons to the button box
        buttonBox.getChildren().addAll(restartButton, exitButton);
        buttonBox.setTranslateY(-200);

        // Set the image at the top of the BorderPane
        root.setTop(winImageView);

        // Set the message box at the center of the BorderPane
        root.setCenter(messageBox);

        // Set the button box at the center of the BorderPane
        root.setBottom(buttonBox);

        // Create the scene with the BorderPane as the root node
        Scene gameOverScene = new Scene(root, 800, 600);

        // Set the scene for the game over stage
        winStage.setScene(gameOverScene);

        // Show the game over stage
        winStage.show();

        // Load the CSS file
        gameOverScene.getStylesheets().add
                (winScreen.class.getResource("mycss.css").toExternalForm());

    }

    public interface WinCallback {
        void onRestartClicked(boolean restartClicked);
    }

    public interface WinExitCallback {
        void onExitClicked(boolean exitClicked);
    }
}
