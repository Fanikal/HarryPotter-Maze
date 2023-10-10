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
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class homeScreen extends Application {

    private Stage primaryStage;
    private TranscriberDemo transcriberDemo;

    public static void main(String[] args) {
        // Launch of the application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Definition of the stage
        primaryStage.setTitle("Harry Potter's Maze");

        // Definition of the root pane which will contain all the other elements
        FlowPane homeRoot = new FlowPane(Orientation.VERTICAL);
        homeRoot.setAlignment(Pos.CENTER);
        homeRoot.getStyleClass().add("homeRoot");

            transcriberDemo = new TranscriberDemo(); // Initializing the TranscriberDemo

        // We set the height of the stage
        primaryStage.setHeight(800);
        primaryStage.setWidth(550);

        // Creation of the scene that contains root as a root pane
        Scene scene = new Scene(homeRoot);

        // Create a VBox to hold multiple Text nodes
        VBox textVBox = new VBox();
        textVBox.setPadding(new Insets(600, 10, 10, 10));
        textVBox.setAlignment(Pos.BOTTOM_CENTER); // Align at the bottom

        // loading the icon image
        Image recordIcon = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/record.png"));

        // creating an ImageView for the icon
        ImageView iconView = new ImageView(recordIcon);
        iconView.setFitHeight(50);
        iconView.setPreserveRatio(true);

        // Create Text nodes for each line of text
        Text line1 = new Text("Say");
        Text line2 = new Text("´I solemnly swear that I'm up to no good´");
        Text line3 = new Text("to open the game");

        // Apply styling to the Text nodes
        line1.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        line2.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        line3.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Apply glow effect to the Text nodes
        final Effect glow = new Glow(1.0);
        line1.setEffect(glow);
        line2.setEffect(glow);
        line3.setEffect(glow);

        // Set text fill color
        line1.setFill(Color.BLACK);
        line2.setFill(Color.BLACK);
        line3.setFill(Color.BLACK);

        // Add Text nodes to the VBox
        textVBox.getChildren().addAll(line1, line2, line3, iconView);

        // Add the VBox to the root pane
        homeRoot.getChildren().add(textVBox);

        iconView.setOnMouseClicked(event -> {
            boolean magicPhraseRecognized = TranscriberDemo.recognizeOpenMap();
            if (magicPhraseRecognized) {
                // Open the selectCharacter screen
                openSelectCharacterScreen();
            } else {
                // showing a message that the game cannot be opened
                System.out.println("Sorry, cannot open the game");
            }
        });



        /* Create a TranslateTransition to make the text scroll vertically
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(15), movingPopup);
        translateTransition.setFromY(-400);
        translateTransition.setToY(400);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();

         */

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

    private void openSelectCharacterScreen() {
        selectCharacter selChar = new selectCharacter();
        try {
            selChar.start(new Stage());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        // Close the welcome screen
        primaryStage.close();
    }
}


