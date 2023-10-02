/*package com.example.harrypottermaze;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MazeDisplayer extends Canvas{

        GraphicsContext gc = getGraphicsContext2D();
        int cCol, cRow;
        private StringProperty wallFileName;

        public int getcCol() {
            return cCol;
        }

        public int getcRow() {
            return cRow;
        }

        public void setCharacterPosition(int row, int col) {
            cRow = row;
            cCol = col;
            redraw();
        }

        public MazeDisplayer() {
            wallFileName = new SimpleStringProperty();
            cCol = 0;
            cRow = 1;
        }

        public String getWallFileName() {
            return wallFileName.get();
        }

        public void setWallFileName(String wallFileName) {
            this.wallFileName.set(wallFileName);
        }

        int[][] mazeData;

        public void setMazeData(int[][] mazeData) {
            this.mazeData = mazeData;
            redraw();
        }

        public void redraw() {
            if (mazeData != null) {
                double W = getWidth();
                double H = getHeight();
                double w = W / mazeData[0].length;
                double h = H / mazeData.length;

                Image wall = null;
                try {
                    wall = new Image(new FileInputStream(wallFileName.get()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                gc.clearRect(0,0, W, H);

                for (int i=0; i<mazeData.length; i++) {
                    for (int j=0; j<mazeData[i].length; j++) {
                        if (mazeData[i][j] != 0) {
                            if (wall == null)
                                gc.fillRect(j*w, i*h, w, h);
                            else
                                gc.drawImage(wall, j*w, i*h, w, h);
                        }
                    }
                }

                gc.setFill(Color.BLACK);
                gc.fillOval(cCol*w, cRow*h, w, h);

            }
        }
    }
*/

