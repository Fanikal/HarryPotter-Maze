package com.example.harrypottermaze;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class selectHouse extends Application {

    private ImageView selectedImageView = null;
    private boolean isHighlighted = false;
    private String selectedHouse = "Gryffindor";

    // ImageViews for the house images
    ImageView gryffindorImageView = createHouseImageView("/com/example/harrypottermaze/Gryffindor.jfif", "Gryffindor");
    ImageView slytherinImageView = createHouseImageView("/com/example/harrypottermaze/Slytherin.jfif", "Slytherin");
    ImageView hufflepuffImageView = createHouseImageView("/com/example/harrypottermaze/Hufflepuff.jfif", "Hufflepuff");


    public static void main(String[] args) {
        // Launch of the application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Definition of the stage
        primaryStage.setTitle("Harry Potter's Maze");

        // Definition of the root pane which will contain all the other elements
        FlowPane selectLocationRoot = new FlowPane(Orientation.VERTICAL);
        selectLocationRoot.setAlignment(Pos.CENTER);
        selectLocationRoot.setVgap(20);

        // We set the height of the stage
        primaryStage.setHeight(800);
        primaryStage.setWidth(700);

        // Creation of the scene that contains root as a root pane
        Scene scene = new Scene(selectLocationRoot);

        // Background of the game
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/sfondo.jpg"));
        BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImageObject);
        selectLocationRoot.setBackground(background);

        // VBox for the title
        VBox titlePane = new VBox();
        titlePane.setPadding(new Insets(10, 10, 10, 10));
        titlePane.setAlignment(Pos.CENTER);

        // Crate label for selecting the house
        try {
            Font customFont = Font.loadFont(new FileInputStream("C:\\Users\\Fani\\AppData\\Local\\Microsoft\\Windows\\Fonts\\ZapfinoExtraLT-One.otf"), 25);

            Label titleLabel = new Label("Select your house");
            titleLabel.setFont(Font.font("Zapfino", 24));
            titleLabel.setFont(customFont);
            titleLabel.setStyle(" -fx-font-weight: bold;");
            titleLabel.setAlignment(Pos.CENTER);

        } catch (FileNotFoundException e) {
            System.out.println("Font file not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error loading font: " + e.getMessage());
        }

        Label titleLabel = new Label("Select your house");
        titleLabel.setFont(Font.font("Zapfino", 24 ));
        titlePane.getChildren().add(titleLabel);

        // HBox to display the house images side by side
        HBox houseImagesBox = new HBox(20);
        houseImagesBox.setAlignment(Pos.CENTER);
        houseImagesBox.getChildren().addAll(gryffindorImageView, slytherinImageView, hufflepuffImageView);

        // VBox for the title
        VBox buttonPane = new VBox();
        buttonPane.setPadding(new Insets(10, 10, 10, 10));
        buttonPane.setAlignment(Pos.CENTER);

        // "Let's go" button
        Button letsGoButton = new Button("Let's go");
        letsGoButton.setFont(Font.font("Zapfino"));
        letsGoButton.setAlignment(Pos.CENTER);
        letsGoButton.getStyleClass().add("main-button");

        // Adding letsGoButton to the buttonPane
        buttonPane.getChildren().add(letsGoButton);

        // Handle button click event
        letsGoButton.setOnAction(e -> {
            newMaze nMaze = new newMaze(selectedHouse);
            try {
                // Open the game
                nMaze.start(new Stage());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            // Close the welcome screen
            primaryStage.close();
        });

        // Adding all elements to the root pane
        selectLocationRoot.getChildren().addAll(titlePane, houseImagesBox, buttonPane);

        // We display the scene we just created in the stage
        primaryStage.setScene(scene);

        // Center the VBox containing the button
        selectLocationRoot.setAlignment(Pos.CENTER);

        // We show the stage
        primaryStage.show();

        // Definition of CSS file
        try {
            scene.getStylesheets().add(Objects.requireNonNull(selectHouse.class.getResource("/com/example/harrypottermaze/mycss.css")).toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Failed to load CSS file: " + e.getMessage());
        }
    }

    // method that handles the creation of the image view of each house
    private ImageView createHouseImageView(String imageUrl, String house) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(Objects.requireNonNull(selectHouse.class.getResourceAsStream(imageUrl))));
        imageView.setOnMouseClicked(event -> {
            if (selectedImageView != null && selectedImageView != imageView) {
                // Remove the highlight effect from the previously selected ImageView
                removeHighlight(selectedImageView);
            }

            if (imageView == selectedImageView) {
                // Clicked on the same image again, toggle the highlight effect
                if (isHighlighted) {
                    removeHighlight(imageView);
                    isHighlighted = false;
                } else {
                    applyHighlight(imageView);
                    isHighlighted = true;
                }
            } else {
                // Clicked on a different image
                applyHighlight(imageView);
                isHighlighted = true;
            }

            // Update the selectedImageView reference
            selectedImageView = imageView;

            // Update the selected house (Gryffindor, Slytherin, Hufflepuff)
            selectedHouse = house;
        });
        return imageView;
    }

    // applyHighlight to the selected house
    private void applyHighlight(ImageView imageView) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setWidth(70);
        dropShadow.setHeight(70);
        imageView.setEffect(dropShadow);
    }

    // remove highlight, if another house is selected or if image is re-clicked
    private void removeHighlight(ImageView imageView) {
        imageView.setEffect(null);
    }
}
