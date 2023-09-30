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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class selectLocation extends Application {

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

        // VBox for the title
        VBox titlePane = new VBox();
        titlePane.setPadding(new Insets(10, 10, 10, 10));
        titlePane.setAlignment(Pos.CENTER);

        // Title label
        Label titleLabel = new Label("Select your house");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        titlePane.getChildren().add(titleLabel);

        // ImageViews for the house images
        ImageView gryffindorImageView = createHouseImageView("/com/example/harrypottermaze/Gryffindor.jfif");
        ImageView slytherinImageView = createHouseImageView("/com/example/harrypottermaze/Slytherin.jfif");
        ImageView hufflepuffImageView = createHouseImageView("/com/example/harrypottermaze/Hufflepuff.jfif");

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
        letsGoButton.setStyle("-fx-font-size: 18px;");
        letsGoButton.setAlignment(Pos.CENTER);

        buttonPane.getChildren().add(letsGoButton);

        // Handle button click event
        letsGoButton.setOnAction(e -> {
            // to be done - to go to the next screen
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
            scene.getStylesheets().add(Objects.requireNonNull(selectLocation.class.getResource("/com/example/harrypottermaze/mycss.css")).toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Failed to load CSS file: " + e.getMessage());
        }
    }

    private ImageView createHouseImageView(String imageUrl) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(Objects.requireNonNull(selectLocation.class.getResourceAsStream(imageUrl))));
        imageView.setOnMouseClicked(event -> {
            // will do it later
        });
        return imageView;
    }
}
