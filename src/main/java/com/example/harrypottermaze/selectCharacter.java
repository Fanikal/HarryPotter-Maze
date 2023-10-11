package com.example.harrypottermaze;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class selectCharacter extends Application {

    private List<Character> characters = new ArrayList<>();
    private int currentCharacterIndex = 0;
    private ImageView characterImageView = new ImageView();

    private double initialX;

    public static void main(String[] args) {
        // Launch of the application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Definition of the stage
        primaryStage.setTitle("Harry Potter's Maze");

        // Definition of the root pane which will contain all the other elements
        FlowPane selectCharRoot = new FlowPane(Orientation.VERTICAL);
        selectCharRoot.setAlignment(Pos.CENTER);
        selectCharRoot.setVgap(20);

        // We set the height of the stage
        primaryStage.setHeight(800);
        primaryStage.setWidth(700);

        // Background of the game
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/sfondo.jpg"));
        BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImageObject);

        selectCharRoot.setBackground(background);

        // Creation of the scene that contains root as a root pane
        Scene scene = new Scene(selectCharRoot);

        // Initialize characters
        characters.add(new Character("Harry Potter", "/com/example/harrypottermaze/harry-potter.png"));
        characters.add(new Character("Ron Weasley", "/com/example/harrypottermaze/ron-weasley.png"));
        characters.add(new Character("Hermione Granger", "/com/example/harrypottermaze/hermione-granger.png"));

        // Set up the initial character image
        displayCharacter(currentCharacterIndex);

        characterImageView.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            initialX = event.getSceneX();
            event.consume();
        });

        characterImageView.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            double deltaX = event.getSceneX() - initialX;
            if (deltaX > 50 && currentCharacterIndex > 0) {
                // Right swipe
                currentCharacterIndex--;
            } else if (deltaX < -50 && currentCharacterIndex < characters.size() - 1) {
                // Left swipe
                currentCharacterIndex++;
            }

            // update of the displayed character
            displayCharacter(currentCharacterIndex);
            event.consume();
        });

        // VBox for the title
        VBox titlePane = new VBox();
        titlePane.setPadding(new Insets(0, 0, 0, 0));
        titlePane.setAlignment(Pos.CENTER);

         // VBox for the title "Select character"
        Label titleLabel = new Label("Swipe to select character");
        titleLabel.setFont(Font.font("Zapfino", 22));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #302c2c;");
        titlePane.getChildren().add(titleLabel);

        // VBox that contains the label and button under the image
        VBox labelAndButtonBox = new VBox(10);
        labelAndButtonBox.setAlignment(Pos.CENTER);

        // button for "Let's go"
        Button letsstartButton = new Button("Let's go");
        letsstartButton.setFont(Font.font("Zapfino"));
        letsstartButton.getStyleClass().add("main-button");

        letsstartButton.setOnAction(event -> {
            selectHouse sHouse = new selectHouse();
            try {
                sHouse.start(new Stage());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            // Close the welcome screen
            primaryStage.close();
        });

        labelAndButtonBox.getChildren().addAll(letsstartButton);

        // VBox that contains the character image and the label/button VBox
        VBox characterBox = new VBox(0); // 20 pixels spacing
        characterBox.setAlignment(Pos.CENTER);
        characterBox.getChildren().addAll(characterImageView, labelAndButtonBox);

        selectCharRoot.getChildren().addAll(titlePane, characterBox);

        // We display the scene we just created in the stage
        primaryStage.setScene(scene);

        // Center the VBox containing the button
        selectCharRoot.setAlignment(Pos.CENTER);

        //We show the stage
        primaryStage.show();

        // Definition of CSS file
        try {
            scene.getStylesheets().add(Objects.requireNonNull(selectCharacter.class.getResource("/com/example/harrypottermaze/mycss.css")).toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Failed to load CSS file: " + e.getMessage());
        }
    }

    private void displayCharacter(int characterIndex) {
        if (characterIndex >= 0 && characterIndex < characters.size()) {
            Character character = characters.get(characterIndex);
            String imageUrl = character.getImageUrl();
            Image image = new Image(Objects.requireNonNull(selectCharacter.class.getResourceAsStream(imageUrl)));
            characterImageView.setFitHeight(500);
            characterImageView.setPreserveRatio(true);
            characterImageView.setImage(image);
        }
    }
}
