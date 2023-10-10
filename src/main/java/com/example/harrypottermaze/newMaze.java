package com.example.harrypottermaze;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Random;


public class newMaze extends Application {

    private static final int ROWS = 12;
    private static final int COLUMNS = 23;
    private static final int CELL_SIZE = 40;

    private static final int MINUTES = 1;
    private static final int SECONDS = 0;


    private int playerRow;
    private int playerCol;

    private double startX;
    private double startY;
    private Text timerText;

    private boolean isGamePaused = false;

    private Timeline timeline;
    private int remainingMinutes;
    private int remainingSeconds;


    Image lifeHeart = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/heart.png"));
    Image wand = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/wand.png"));
    Image potion = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/potion.png"));
    Image timeTurner = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/timeTurner.png"));
    Image ribbonItems = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/ribbonItems.png"));
    Image ribbonLife = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/ribbonLife.png"));
    Image help = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/help.png"));
    Image start = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/Start.png"));
    Image finish = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/Finish.png"));
    Image voldemort = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/targetVol.png"));
    Image you = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/targetYou.png"));
    Image steps = new Image (new File("/Users/stella/Desktop/steps.gif").toURI().toString());

    Random rand = new Random();

    int numWand = 0;

    private int[][] maze;
    private GridPane mazeGrid;
    private GridPane legendItems;
    private GridPane life;

    private Rectangle player;


    int numPotionsCollected = 0;
    int numWandCollected = 0;
    int numTimeCollected = 0;
    int numHearts = 3;



    //TODO:  the game menu,change the background and the characters, add voldemort and the fight, add the door
    @Override
    public void start(Stage primaryStage) {

        initializeMaze();
        playerRow = 1;
        playerCol = 1;

        mazeGrid = new GridPane();
        legendItems = new GridPane(); //TODO: perchè non riesco a vedere gli oggetti collezionati dopo che ho messo la targa
        life = new GridPane();
        drawMaze();
        player = drawPlayer();
        createLifeLegend();

        // Background of the game
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/harrypottermaze/sfondo.png"));

        //BackgroundSize backgroundSize = new BackgroundSize(3000, 2000, false, false, true, false);
        BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImageObject);

        ImageView helpImageView = new ImageView(help);
        helpImageView.setFitHeight(50);
        helpImageView.setFitWidth(50);
        helpImageView.setTranslateY(450);

        ImageView ribbonItemsView = new ImageView(ribbonItems);
        ribbonItemsView.setFitHeight(100);
        ribbonItemsView.setFitWidth(100);
        ribbonItemsView.setTranslateY(-100);


        // Creation of the timer
        remainingMinutes = MINUTES;
        remainingSeconds = SECONDS;
        timerText = new Text(getFormattedTime());
        timerText.setFont(Font.font("Zapfino", 24));
        //timerText.setStyle("-fx-background-image: url('/Users/stella/Desktop/ribbon.png');"); //TODO: perchè non vedo l'immagine dietro
        timerText.setFill(Color.BLACK);

        // Counting of time
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (remainingSeconds > 0) {
                        remainingSeconds--;
                    } else {
                        if (remainingMinutes > 0) {
                            remainingMinutes--;
                            remainingSeconds = 59;
                        } else {
                            timeline.stop();
                            gameOverWindow();
                        }
                    }
                    timerText.setText(getFormattedTime());
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        BorderPane timePane = new BorderPane();
        timePane.setTop(timerText);
        timePane.setAlignment(timerText, Pos.CENTER);
        VBox grid = new VBox();

        grid.getChildren().addAll(mazeGrid, legendItems);
        grid.getChildren().add(0,helpImageView);
        grid.getChildren().add(0, ribbonItemsView);
        grid.getChildren().add(0, life);
        grid.getChildren().add(0, timePane);

        grid.setBackground(background);
        Scene scene = new Scene(grid, COLUMNS * CELL_SIZE, ROWS * CELL_SIZE);
        primaryStage.setTitle("Harry Potter's Maze");

        //Controls the player movement event and control the pause menu
        scene.setOnKeyPressed(event -> {
            movePlayer(event.getCode());

            if (event.getCode() == KeyCode.SPACE){
                showPauseMenu();
            }

        });

        // We set the height of the stage
        primaryStage.setHeight(3000);
        primaryStage.setWidth(3000);

        mazeGrid.setAlignment(Pos.CENTER);
        mazeGrid.setTranslateY(-200);
        legendItems.setAlignment(Pos.CENTER_LEFT);
        life.setAlignment(Pos.CENTER_LEFT);
        ribbonItemsView.setTranslateY(280);
        ribbonItemsView.setTranslateX(20);

        //mazeGrid.setTranslateY(80);
        legendItems.setTranslateX(20);
        legendItems.setTranslateY(-270);
        life.setTranslateX(20);
        life.setTranslateY(250);
        helpImageView.setTranslateX(1200);
        helpImageView.setTranslateY(350);
        helpWindow(helpImageView);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeMaze() {


        maze = new int[][]{
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,1,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,1,1,0,0,0,0,1,0,0,1,0,1,1,0,1,0,0,0,1,1},
                {1,1,1,1,1,0,0,0,0,1,0,0,3,0,1,0,0,1,0,0,0,0,1},
                {2,2,2,2,1,0,0,0,0,1,0,0,1,0,1,0,0,1,0,0,0,0,1},
                {2,2,2,2,1,1,1,1,0,1,1,0,1,1,1,0,0,1,0,0,0,0,1},
                {2,2,2,2,1,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0,0,0,1},
                {2,2,2,2,1,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0,0,0,1},
                {2,2,2,2,1,0,0,0,0,0,0,0,0,1,1,0,0,1,1,1,1,1,1},
                {2,2,2,2,1,0,0,0,0,0,0,0,0,1,1,0,0,1,2,2,2,2,2},
                {2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2},
        };
    }

    private void drawMaze() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);


                if (maze[row][col] == 1) {
                    cell.setFill(Color.BLACK); //Wall
                } else if (maze[row][col] == 0){
                    cell.setFill(Color.TRANSPARENT); //Empty
                    positionateItems(cell, row, col);
                }else if(maze[row][col] == 2)
                    cell.setFill(Color.TRANSPARENT); //Empty
                else if(maze[row][col] == 3){
                    cell.setFill(Color.SADDLEBROWN);
                    doorOpen(cell);
                }


                mazeGrid.add(cell, col, row);
            }

        }
    }

    public Rectangle drawPlayer() {
        Rectangle player = new Rectangle(CELL_SIZE, CELL_SIZE);
        player.setFill(Color.GREEN);
        //player.setFill((new ImagePattern(steps, 0, 0, 1, 1, true)));
        mazeGrid.add(player, playerCol, playerRow);
        checkCollision(player);

        return player;
    }

    //Manage the movement of the player with the WASD or arrows
    private void movePlayer(KeyCode keyCode) {
        int newRow = playerRow;
        int newCol = playerCol;

        switch (keyCode) {
            case UP:
            case W:
                newRow = playerRow - 1;
                break;
            case DOWN:
            case S:
                newRow = playerRow + 1;
                break;
            case LEFT:
            case A:
                newCol = playerCol - 1;
                break;
            case RIGHT:
            case D:
                newCol = playerCol + 1;
                break;
            default:
                break;
        }


        // Controls if the movement is valid
        if (isValidMove(newRow, newCol)) {
            mazeGrid.getChildren().remove(mazeGrid.getChildren().size() - 1); // Remove the player from the last position
            playerRow = newRow;
            playerCol = newCol;
            drawPlayer();

        }


    }

    private void positionateItems(Rectangle cell, int row, int col){
        int num = rand.nextInt(100);


        //randomly putting wand but after a certain position in the maze
        if (num >= 30 && num <= 100 && row >= 5 && col >= 8 && col < 12) {
            numWand++;
            if (numWand > 1)
                cell.setFill(Color.TRANSPARENT);
            else{
                cell.setFill((new ImagePattern(wand, 0, 0, 1, 1, true)));
                if(row == playerRow && col == playerCol)
                    cell.setFill(Color.TRANSPARENT);
            }

        }


        //randomly putting potions but after a certain position in the maze
        else if (num >= 1 && num < 3 && row > 0 && col > 12) {
            cell.setFill((new ImagePattern(potion, 0, 0, 1, 1, true)));

        }

        //randomly putting timeTurner but after a certain position in the maze
        else if (num < 30 && num >= 25 && row > 0 && col > 9 && col < 12) {
            cell.setFill((new ImagePattern(timeTurner, 0, 0, 1, 1, true)));

        }




    }

    //Create the legend of life hearts
    private void createLifeLegend() {
        VBox legend = new VBox();

        ImageView ribbonView = new ImageView(ribbonLife);
        ribbonView.setFitHeight(100);
        ribbonView.setFitWidth(100);
        ribbonView.setTranslateY(10);
        legend.getChildren().addAll(ribbonView);


        Pane itemPane = new Pane();


        VBox.setMargin(legend, new Insets(5, 0, 0, 0));

        for (int col = 0; col < numHearts; col++) {
            ImageView heartView = new ImageView(lifeHeart);
            heartView.setFitHeight(30);
            heartView.setFitWidth(30);
            heartView.setLayoutX(col * 35); // Item in horizontal
            itemPane.getChildren().add(heartView);
        }

        legend.getChildren().add(itemPane);

        life.getChildren().addAll(legend);
    }


    /*private void updateLegend(int numCollection){

        wandView.setFitHeight(30);
        wandView.setFitWidth(30);

        if(numWandCollected == 1){
            legendItems.add(wandView, 0, 2);
        }

        for (int col = 0; col < numPotionsCollected; col++) {
            legendItems.add(new ImageView(potion), col, 0);
        }

        // Aggiungi l'immagine dei Time Turner alla legenda
        for (int col = 0; col < numTimeCollected; col++) {
            legendItems.add(new ImageView(timeTurner), col, 1);
        }


    }*/

    //Check collision between the player and the items
    private void checkCollision(Rectangle player) {
        int playerRow = GridPane.getRowIndex(player);
        int playerCol = GridPane.getColumnIndex(player);

        Rectangle cell = getNodeByRowColumnIndex(playerRow, playerCol, mazeGrid);

        if (cell != null) {
            if (cell.getFill() instanceof ImagePattern) {
                ImagePattern pattern = (ImagePattern) cell.getFill();
                if (pattern.getImage().equals(potion)) {
                    cell.setFill(Color.TRANSPARENT);
                    numPotionsCollected++;
                    for (int col = 0; col < numPotionsCollected; col++) {
                        ImageView potionView = new ImageView(potion);
                        potionView.setFitHeight(30);
                        potionView.setFitWidth(30);
                        legendItems.add(potionView, col, 3);
                        makeDraggable(potionView);
                    }
                }
                else if(pattern.getImage().equals(timeTurner)){
                    cell.setFill(Color.TRANSPARENT);
                    numTimeCollected++;

                    for (int col = 0; col < numTimeCollected; col++) {
                        ImageView timeView = new ImageView(timeTurner);
                        timeView.setFitWidth(30);
                        timeView.setFitHeight(30);
                        legendItems.add(timeView, col, 2);
                        makeDraggable(timeView);
                    }

                }else if(pattern.getImage().equals(wand)) {
                    cell.setFill(Color.TRANSPARENT);
                    numWandCollected++;
                    if(numWandCollected == 1){
                        ImageView wandView = new ImageView(wand);
                        wandView.setFitHeight(30);
                        wandView.setFitWidth(30);
                        legendItems.add(wandView, 0, 1);
                        makeDraggable(wandView);
                    }

                }

            }
        }

    }

    //When you collect items in the legend you can drag and drop them on the player to use them
    private void makeDraggable(ImageView node){

        node.setOnMousePressed(e -> {
            startX = e.getSceneX() - node.getTranslateX();
            startY = e.getSceneY() - node.getTranslateY();
            node.toFront();
        });

        node.setOnMouseDragged(e -> {
            node.setTranslateX(e.getSceneX() - startX);
            node.setTranslateY(e.getSceneY() - startY);
        });

        node.setOnMouseReleased(e -> {

            node.setVisible(false);
            Bounds itemBounds = node.getBoundsInLocal();

            Bounds playerBounds = player.getBoundsInLocal();
            /*player.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                //Coordinates of the mouse
                double mouseX = event.getX();
                double mouseY = event.getY();

                /*if (itemBounds.intersects(playerBounds) && playerBounds.contains(e.getX(), e.getY()) && keyReleased == true){
                    mineButton.setVisible(false);
                    mineButtons.remove(mineButton);
                    keyReleased = false;
                    return;
                }

            });*/



        });

    }

    //During the checkCollision is used to control if there is an item inside the cell where there is the player
    private Rectangle getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Rectangle result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && colIndex != null && rowIndex == row && colIndex == column) {
                result = (Rectangle)node;
                break;
            }
        }

        return result;
    }


    //check the valid movement of the player
    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLUMNS && maze[row][col] == 0;
    }

    private String getFormattedTime() {
        return String.format("%02d:%02d", remainingMinutes, remainingSeconds);
    }

    private void helpWindow(ImageView image){
        image.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (!isGamePaused) {
                //Coordinates of the mouse
                double mouseX = event.getX();
                double mouseY = event.getY();

                Bounds imageBounds = image.getBoundsInLocal();

                if (imageBounds.contains(event.getX(), event.getY())) {
                    GridPane root = new GridPane();

                    root.setVgap(0);
                    root.setHgap(0);
                    root.setAlignment(Pos.CENTER);
                    Stage helpStage = new Stage();
                    Scene helpScene = new Scene(root, 500, 500);
                    helpStage.setTitle("HELP");
                    helpStage.setScene(helpScene);
                    helpStage.show();
                    timeline.stop(); //TODO: do the help menu with a play button and when you click on it the time start again
                }
            }
        });
    }

    //Managing the game over menu
    private void gameOverWindow(){

        GridPane root = new GridPane();

        root.setVgap(0);
        root.setHgap(0);
        root.setAlignment(Pos.CENTER);
        Stage gameOverStage = new Stage();
        Scene gameOverScene = new Scene(root, 500, 500);
        gameOverStage.setTitle("GAME OVER");
        gameOverStage.setScene(gameOverScene);
        gameOverStage.show();
        timeline.stop();

    }

    //Managing the pause menu
    private void showPauseMenu(){ //TODO:Add the buttons restart and continue

        if (!isGamePaused) {
            GridPane root = new GridPane();

            root.setVgap(0);
            root.setHgap(0);
            root.setAlignment(Pos.CENTER);
            Stage pauseStage = new Stage();
            Scene pauseScene = new Scene(root, 500, 500);
            pauseStage.setTitle("PAUSE");
            pauseStage.setScene(pauseScene);
            pauseStage.show();
            timeline.stop();
            isGamePaused = true;
        }
    }

    private void doorOpen(Rectangle cell){

        cell.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {


        });


    }



    public static void main(String[] args) {
        launch(args);
    }


}
