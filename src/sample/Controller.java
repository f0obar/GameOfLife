package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Controller {
    private final int SCALE = 100;

    private LifeCycler lifeCycler;

    @FXML
    private Canvas board;

    @FXML
    private Slider speedSlider;

    @FXML
    private Button startButton;

    private PixelWriter pixelWriter;

    @FXML
    void start(javafx.event.ActionEvent event) {
        if(lifeCycler != null){
            lifeCycler.stop();
            lifeCycler = null;
        } else {
            pixelWriter = board.getGraphicsContext2D().getPixelWriter();
            boolean[][] pixels = new boolean[1000 / SCALE][1000 / SCALE];

            WritableImage image = new WritableImage(1000,1000);
            board.snapshot(null,image);

            for(int x = 0; x<1000; x+= SCALE){
                for(int y = 0; y<1000; y+= SCALE)
                    pixels[x/ SCALE][y/ SCALE] = image.getPixelReader().getColor(x,y) == Color.BLACK;
            }
            updateBoard(pixels);
        }
    }


    public void updateBoard(boolean[][] pixels){
        if (SCALE == 1) {
            for (int x = 0; x < 1000; x ++)
                for (int y = 0; y < 1000; y ++)
                    pixelWriter.setColor(x, y, (pixels[x][y] ? Color.BLACK : Color.WHITE));
        } else {
            board.getGraphicsContext2D().setFill(Color.WHITE);
            board.getGraphicsContext2D().fillRect(0,0,1000,1000);
            board.getGraphicsContext2D().setFill(Color.BLACK);
            for (int x = 0; x < 1000; x += SCALE) {
                for (int y = 0; y < 1000; y += SCALE)
                    if(pixels[x/ SCALE][y/ SCALE])
                        board.getGraphicsContext2D().fillRect(x, y, SCALE, SCALE);
            }
        }
    }
}

