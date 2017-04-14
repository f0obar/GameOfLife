package sample;

import javafx.application.Platform;

/**
 * Created by dominic on 14.04.2017.
 */
public class LifeCycler extends Thread {
    private boolean[][] pixels;
    private Controller controller;
    private boolean borderless = true;

    public LifeCycler(boolean[][] pixels, Controller controller, boolean borderless){
        this.pixels = pixels;
        this.controller = controller;
        this.borderless = borderless;
    }


    @Override
    public void run(){
        while (true) {
            try {
                Thread.sleep((long) controller.speedSlider.getValue());
            } catch (InterruptedException e) {}

            gameOfLife();
            Platform.runLater(() -> controller.updateBoard(pixels));
        }
    }

    private void gameOfLife(){
        boolean[][] nextIteration = new boolean[pixels[0].length][pixels[0].length];

        //array dimensions are always proportional in this version.
        for(int x = 0; x< pixels[0].length;x++){
            for(int y = 0; y< pixels[0].length;y++){
                int neighbours = getNeighbours(x,y);

                if(pixels[x][y] && (neighbours == 2 || neighbours == 3))
                    nextIteration[x][y] = true;
                else if((!pixels[x][y]) &&  neighbours == 3)
                    nextIteration[x][y] = true;
            }
        }
        pixels = nextIteration;
    }

    private int getNeighbours(int x, int y){
        int neighbours = 0;
        if(borderless){
            for (int xOffset = x - 1; xOffset < x + 2; xOffset++) {
                for (int yOffset = y - 1; yOffset < y + 2; yOffset++) {
                    if (!(xOffset == x && yOffset == y)) {
                        int tmpX = xOffset;
                        int tmpY = yOffset;
                        if(tmpX == -1)
                            tmpX += pixels[0].length;
                        else if(tmpX == pixels[0].length)
                            tmpX = 0;

                        if(tmpY == -1)
                            tmpY += pixels[0].length;
                        else if(tmpY == pixels[0].length)
                            tmpY = 0;

                        if(pixels[tmpX][tmpY])
                            neighbours++;
                    }
                }
            }
        } else {
            for(int xOffset = x-1; xOffset<x+2; xOffset++){
                for(int yOffset = y-1; yOffset<y+2; yOffset++){
                    if(xOffset > -1 && xOffset < pixels[0].length && yOffset > -1 &&  yOffset < pixels[0].length) {
                        if (pixels[xOffset][yOffset] && !(xOffset == x && yOffset == y))
                            neighbours++;
                    }
                }
            }
        }
        return neighbours;
    }
}
