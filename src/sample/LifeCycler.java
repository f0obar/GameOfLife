package sample;

import javafx.application.Platform;

/**
 * Created by dominic on 14.04.2017.
 */
public class LifeCycler extends Thread {
    private boolean[][] pixels;
    private Controller controller;

    public LifeCycler(boolean[][] pixels, Controller controller){
        this.pixels = pixels;
        this.controller = controller;
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
        boolean[][] nextIteration = pixels.clone();

        //array dimensions are always proportional in this version.
        for(int x = 0; x< pixels[0].length;x++){
            for(int y = 0; y< pixels[0].length;y++){
                int neighbours = 0;
                try{
                    if(pixels[x-1][y-1])
                        neighbours ++;
                } catch (IndexOutOfBoundsException e) {}
                try{
                    if(pixels[x-1][y])
                        neighbours ++;
                } catch (IndexOutOfBoundsException e) {}
                try{
                    if(pixels[x-1][y+1])
                        neighbours ++;
                } catch (IndexOutOfBoundsException e) {}
                try{
                    if(pixels[x][y-1])
                        neighbours ++;
                } catch (IndexOutOfBoundsException e) {}
                try{
                    if(pixels[x][y+1])
                        neighbours ++;
                } catch (IndexOutOfBoundsException e) {}
                try{
                    if(pixels[x+1][y-1])
                        neighbours ++;
                } catch (IndexOutOfBoundsException e) {}
                try{
                    if(pixels[x+1][y])
                        neighbours ++;
                } catch (IndexOutOfBoundsException e) {}
                try{
                    if(pixels[x+1][y+1])
                        neighbours ++;
                } catch (IndexOutOfBoundsException e) {}

                if(!pixels[x][y] && neighbours == 3)
                    nextIteration[x][y] = true;
                else if(pixels[x][y] && (neighbours == 2 || neighbours == 3))
                    nextIteration[x][y] = true;
                else
                    nextIteration[x][y] = false;
            }
        }
        pixels = nextIteration;
    }
}
