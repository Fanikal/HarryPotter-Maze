package com.example.harrypottermaze;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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

        //root.setBackground(background);

        // Background of the game
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/sfondo.jpg"));
        BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImageObject);

        root.setBackground(background);

        // Load the "ribbon 3" image from resources
        Image pauseImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/ribbon.png"));

        // Create an ImageView to display the image with a specified width and height
        ImageView pauseImageView = new ImageView(pauseImage);
        pauseImageView.setFitWidth(800); // Set a larger width 800
        pauseImageView.setFitHeight(400); // Set a larger height 400
        pauseImageView.setPreserveRatio(true); // Preserve image aspect ratio
        pauseImageView.setTranslateY(-30);
        root.setAlignment(pauseImageView, Pos.CENTER);


        // Create a Text node for the message ("Time is up" or "Lives are up")
        Text messageText = new Text(msgToShow);
        messageText.setFont(Font.font("Zampfino", 20));
        messageText.setTranslateY(-130);

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
        buttonBox.setTranslateY(-80);

        // Set the image at the top of the BorderPane
        root.setTop(pauseImageView);

        // Set the message box at the center of the BorderPane
        root.setCenter(messageBox);

        // Set the button box at the center of the BorderPane
        root.setBottom(buttonBox);



        Scene pauseScene = new Scene(root, 500, 500);
        pauseStage.setScene(pauseScene);
        pauseStage.setTitle("PAUSE");
        pauseStage.show();
       // newMaze nm = new newMaze();

 //TODO: not working and don't understand why
        /*pauseScene.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.SPACE && nm.getSpacePressed() == true){
                pauseStage.hide();
                System.out.println("Space key pressed");
                nm.timeStart();
                nm.setSpacePressed(false);
                System.out.println(nm.getSpacePressed());
            }

        });*/

        // Load the CSS file
        pauseScene.getStylesheets().add
                (winScreen.class.getResource("mycss.css").toExternalForm());



    }

}
