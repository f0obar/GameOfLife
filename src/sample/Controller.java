package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller {
    private int scale = 100;

    private LifeCycler lifeCycler;

    @FXML
    private Canvas board;

    @FXML
    public Slider speedSlider;

    @FXML
    private Button startButton;

    @FXML
    private Slider scaleSlider;

    private PixelWriter pixelWriter;

    @FXML
    void start(javafx.event.ActionEvent event) {
        if(lifeCycler != null){
            lifeCycler.stop();
            lifeCycler = null;
        } else {
            pixelWriter = board.getGraphicsContext2D().getPixelWriter();
            boolean[][] pixels = new boolean[1000 / scale][1000 / scale];

            Image image = board.snapshot(null,null);
            PixelReader reader = image.getPixelReader();

            for(int x = 0; x<1000; x+= scale){
                for(int y = 0; y<1000; y+= scale)
                    pixels[x / scale][y / scale] = reader.getColor(x, y).equals(Color.BLACK);
            }
            updateBoard(pixels);
            lifeCycler = new LifeCycler(pixels, this);
            lifeCycler.start();
        }
    }


    public void updateBoard(boolean[][] pixels){
        if (scale == 1) {
            for (int x = 0; x < 1000; x ++)
                for (int y = 0; y < 1000; y ++)
                    pixelWriter.setColor(x, y, (pixels[x][y] ? Color.BLACK : Color.WHITE));
        } else {
            board.getGraphicsContext2D().setFill(Color.WHITE);
            board.getGraphicsContext2D().fillRect(0,0,1000,1000);
            board.getGraphicsContext2D().setFill(Color.BLACK);
            for (int x = 0; x < 1000; x += scale) {
                for (int y = 0; y < 1000; y += scale)
                    if(pixels[x/ scale][y/ scale])
                        board.getGraphicsContext2D().fillRect(x, y, scale, scale);
            }
        }
    }

    @FXML
    void mouseMoved(MouseEvent event) {
        if(lifeCycler == null) {
            if (scale == 1) {
                pixelWriter = board.getGraphicsContext2D().getPixelWriter();
                pixelWriter.setColor((int) event.getX(), (int) event.getY(), Color.BLACK);
            } else {
                board.getGraphicsContext2D().setFill(Color.BLACK);
                board.getGraphicsContext2D().fillRect(Math.floor(event.getX() / scale) * scale, Math.floor(event.getY() / scale) * scale, scale, scale);
            }
        }
    }

    @FXML
    void scaleChanged(MouseEvent event) {
        int newScale = (int)scaleSlider.getValue();

        while (1000 % newScale != 0)
            newScale --;
        if(lifeCycler != null) {
            lifeCycler.stop();
            lifeCycler = null;
        }

        scale = newScale;
        updateBoard(new boolean[1000 / scale][1000 / scale]);
    }

    @FXML
    void initialize(){
        board.getGraphicsContext2D().setFill(Color.WHITE);
        board.getGraphicsContext2D().fillRect(0,0,1000,1000);
    }
}

