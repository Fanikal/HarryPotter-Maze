package com.example.harrypottermaze;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;


public class newMaze extends Application {

    private static final int ROWS = 12;
    private static final int COLUMNS = 23;
    private static final int CELL_SIZE = 40;

    private int playerRow;
    private int playerCol;

    Image lifeHeart = new Image("file:////Users/stella/Desktop/heart.png");
    Image wand = new Image("file:////Users/stella/Desktop/wand.png");
    Image potion = new Image("file:////Users/stella/Desktop/potion.png");
    Image timeTurner = new Image("file:////Users/stella/Desktop/timeTurner.png");
    Image help = new Image("file:////Users/stella/Desktop/help.png");
    Image start = new Image("file:////Users/stella/Desktop/Start.png");
    Image finish = new Image("file:////Users/stella/Desktop/Finish.png");
    Image Voldemort = new Image("file:////Users/stella/Desktop/targetVol.png");
    Image You = new Image("file:////Users/stella/Desktop/targetYou.png");

    private Image[][] items;
    Random rand = new Random();

    int numWand = 0;

    private int[][] maze;
    private GridPane mazeGrid;


    //TODO: add the timer, the game menu, the collection of items, drag and drop them to use,change the background and the characters, add voldemort and the fight, add the door
    @Override
    public void start(Stage primaryStage) {

        initializeMaze();
        playerRow = 1;
        playerCol = 1;

        mazeGrid = new GridPane();
        drawMaze();
        drawPlayer();


        Scene scene = new Scene(mazeGrid, COLUMNS * CELL_SIZE, ROWS * CELL_SIZE);
        primaryStage.setTitle("Harry Potter's Maze");

        //Controls the player movement event
        scene.setOnKeyPressed(event -> {
            movePlayer(event.getCode());
        });

        // We set the height of the stage
        primaryStage.setHeight(700);
        primaryStage.setWidth(1200);
        mazeGrid.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeMaze() {


        maze = new int[][]{
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,1,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,1,1,0,0,0,0,1,0,0,1,0,1,1,0,1,0,0,0,1,1},
                {1,1,1,1,1,0,0,0,0,1,0,0,1,0,1,0,0,1,0,0,0,0,1},
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
                Pane cellPane = new Pane();
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);


                if (maze[row][col] == 1) {
                    cell.setFill(Color.BLACK); //Wall
                } else if (maze[row][col] == 0){
                    cell.setFill(Color.WHITE); //Empty
                    positionateItems(cell, row, col);
                }else if(maze[row][col] == 2)
                    cell.setFill(Color.WHITE); //Empty

                mazeGrid.add(cell, col, row);
            }

        }
    }

    private void drawPlayer() {
        Rectangle player = new Rectangle(CELL_SIZE, CELL_SIZE);
        player.setFill(Color.GREEN);
        mazeGrid.add(player, playerCol, playerRow);
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
                cell.setFill(Color.WHITE);
            else
                cell.setFill((new ImagePattern(wand, 0, 0, 1, 1, true)));
        }


        //randomly putting potions but after a certain position in the maze
        else if (num >= 1 && num < 3 && row > 0 && col > 12) {
            cell.setFill((new ImagePattern(potion, 0, 0, 1, 1, true)));

        }

        //randomly putting timeTurner but after a certain position in the maze
        else if (num < 50 && num >= 47 && row > 0 && col > 9 && col < 12) {
            cell.setFill((new ImagePattern(timeTurner, 0, 0, 1, 1, true)));
        }



    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLUMNS && maze[row][col] == 0;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
