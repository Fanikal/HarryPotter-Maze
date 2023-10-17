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
import javax.security.auth.callback.Callback;


import java.io.IOException;

public class gameOverScreen extends Application {

    private GameOverCallback gameOverCallback;
    private GameOverExitCallback gameOverExitCallback;
    private String msgToShow = "You didn't escape on time..!";
    private String selectedHouse;
    private Stage gameStage;
    private boolean restartButtonClicked = false;

    public gameOverScreen(GameOverCallback gameOverCallback, GameOverExitCallback gameOverExitCallback, boolean timeIsUp, Stage gameStage, String selectedHouse) {

        if (timeIsUp) {
            //message in case the user lost because of time over
            msgToShow = "You didn't escape on time..!";
        } else {
            //message in case the user lost because of Voldemort (0 lives)
            msgToShow = "The Marauder's map mischief failed..!";
        }

        this.gameStage = gameStage;
        this.selectedHouse = selectedHouse;
        this.gameOverCallback = gameOverCallback;
        this.gameOverExitCallback = gameOverExitCallback;
    }

    public gameOverScreen() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage gameOverStage) {

        // title of the stage
        gameOverStage.setTitle("Game Over");

        // BorderPane to hold the content
        BorderPane root = new BorderPane();

        // Background of the game
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/sfondo.jpg"));
        BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImageObject);
        root.setBackground(background);

        // Load the "ribbon 3" image from resources
        Image gameOverImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/ribbon 3.png"));

        // Create an ImageView to display the image with a specified width and height
        ImageView gameOverImageView = new ImageView(gameOverImage);
        gameOverImageView.setFitWidth(800);
        gameOverImageView.setFitHeight(400);
        gameOverImageView.setPreserveRatio(true);
        root.setAlignment(gameOverImageView, Pos.CENTER);

        // Text node for the message ("Time is up" or "Lives are up")
        Text messageText = new Text(msgToShow);
        messageText.setFont(Font.font("Zampfino", 20));
        messageText.setTranslateY(-180);

        // VBOx for the messageText
        VBox messageBox = new VBox();
        messageBox.setAlignment(Pos.CENTER);
        messageBox.getChildren().add(messageText);

        // HBox for the buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(0, 0, 0, 0));

        // create restart button
        Button restartButton = new Button("Start new game");

        // action when clicking restart button
        restartButton.setOnAction(event -> {
            System.out.println("Restarting game");
            gameOverCallback.onRestartClicked(true);
            restartButtonClicked = true;
            //close the gameStage
            gameStage.close();
            //re-initiliaze game
            newMaze newMazeGame = new newMaze(selectedHouse);
            try {
                newMazeGame.start(new Stage()); // Start the new game
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            gameOverStage.close(); // Close the game over screen
        });

        restartButton.getStyleClass().add("main-button");

        // Create the "Exit" button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            gameOverExitCallback.onExitClicked(true);
            newMaze newMazeGame = new newMaze(selectedHouse); //re-initiliaze game
            gameOverStage.close(); // Close the game over screen
            gameStage.close(); // close the gameStage
            homeScreen hScreen = new homeScreen();
            try {
                hScreen.start(new Stage()); // Go back to the home page
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        exitButton.getStyleClass().add("main-button");

        // Add buttons to the button box
        buttonBox.getChildren().addAll(restartButton, exitButton);
        buttonBox.setTranslateY(-200);

        // Set the image at the top of the BorderPane
        root.setTop(gameOverImageView);

        // Set the message box at the center of the BorderPane
        root.setCenter(messageBox);

        // Set the button box at the center of the BorderPane
        root.setBottom(buttonBox);

        // Create the scene with the BorderPane as the root node
        Scene gameOverScene = new Scene(root, 800, 600);

        // Set the scene for the game over stage
        gameOverStage.setScene(gameOverScene);

        // Show the game over stage
        gameOverStage.show();

        // Load the CSS file
        gameOverScene.getStylesheets().add
                (gameOverScreen.class.getResource("mycss.css").toExternalForm());

    }

    public interface GameOverCallback {
        void onRestartClicked(boolean restartClicked);
    }

    public interface GameOverExitCallback {
        void onExitClicked(boolean exitClicked);

    }


}
