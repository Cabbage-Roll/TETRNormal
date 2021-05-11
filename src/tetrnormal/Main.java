package tetrnormal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javazoom.jl.player.advanced.AdvancedPlayer;
import tetrcore.LoadConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {

    private static final int KEYCOUNT = 9;
    private static final boolean[] onlyOnePress = {
        false,
        false,
        false,
        true,
        true,
        true,
        true,
        true,
        true
    };
    private static final int xSize = 500;
    private static final int ySize = 500;
    private static final KeyCode[] keys = {
        KeyCode.LEFT,
        KeyCode.RIGHT,
        KeyCode.DOWN,
        KeyCode.SPACE,
        KeyCode.Y,
        KeyCode.X,
        KeyCode.UP,
        KeyCode.C,
        KeyCode.SHIFT,
    };
    private static final Table table = new Table();
    private static final boolean[] keyAlreadyProcessed = new boolean[KEYCOUNT];
    private static final boolean[] keyIsDown = new boolean[KEYCOUNT];
    private static GraphicsContext gc;

    private static final int pixelSize = 10;
    private static final int xOff = 0;
    private static final int yOff = 0;

    public static void main(String[] args) {
        launch(args);
    }

    private static void doAction(int i) {
        switch (i) {
            case 0:
                table.extMovePieceLeft();
                break;
            case 1:
                table.extMovePieceRight();
                break;
            case 2:
                table.extDropPieceSoft();
                break;
            case 3:
                table.extDropPieceHard();
                break;
            case 4:
                table.extRotatePieceCCW();
                break;
            case 5:
                table.extRotatePieceCW();
                break;
            case 6:
                table.extRotatePiece180();
                break;
            case 7:
                table.extHoldPiece();
                break;
            case 8:
                table.extStartZone();
                break;
        }
    }

    @Override
    public void start(Stage stage) {
        BorderPane pane = new BorderPane();
        Canvas canvas = new Canvas(xSize, ySize);
        canvas.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            for (int i = 0; i < KEYCOUNT; i++) {
                if (
                    key.equals(keys[i])) {
                    keyIsDown[i] = true;
                    break;
                }
            }
        });
        canvas.setOnKeyReleased(e -> {
            KeyCode key = e.getCode();
            for (int i = 0; i < KEYCOUNT; i++) {
                if (key.equals(keys[i])) {
                    keyIsDown[i] = false;
                    keyAlreadyProcessed[i] = false;
                    break;
                }
            }
        });

        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        pane.setCenter(canvas);
        Scene scene = new Scene(pane, xSize, ySize);
        stage.setScene(scene);
        stage.setTitle("bruh");
        stage.show();

        try {
            LoadConfig.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (true) {
            new Thread(() -> {
                try {
                    File f = new File("resources/songs");

                    int numberOfSongs = f.listFiles().length;
                    if (numberOfSongs > 0) {

                        String[] pathNames;

                        System.out.println(numberOfSongs + " song(s) found");

                        pathNames = f.list();
                        do {
                            // PLAY: NONE,RANDOM,CHOSEN
                            // REPEAT: NONE,ONE,RANDOM
                            // NONE, RANDOM NONE, RANDOM ONE, RANDOM RANDOM, CHOSEN NONE, CHOSEN ONE
                            String song = pathNames[(int) (Math.random() * numberOfSongs)];
                            FileInputStream fis = new FileInputStream("resources/songs/" + song);
                            AdvancedPlayer playMP3 = new AdvancedPlayer(fis);
                            System.out.println("Playing: " + song);
                            playMP3.play();
                        } while (true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to play the file.");
                }
            }).start();
        }

        table.extStartGame();

        //mesto gde se magija desava

        new Thread(() -> {
            while (!table.getGameover()) {
                for (int i = 0; i < KEYCOUNT; i++) {
                    if (keyIsDown[i] && !(onlyOnePress[i] && keyAlreadyProcessed[i])) {
                        doAction(i);
                        keyAlreadyProcessed[i] = true;
                    }
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                draw();
            }
        }).start();
    }

    private void draw() {
        gc.clearRect(0, 0, xSize, ySize);
        drawStage(gc);
        drawCurrentPiece(gc);
        drawHold(gc);
        drawNext(gc);
        drawGarbageMeter(gc);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void drawCurrentPiece(GraphicsContext gc) {

    }

    private void drawGarbageMeter(GraphicsContext gc) {

    }

    private void drawHold(GraphicsContext gc) {

    }

    private void drawNext(GraphicsContext gc) {

    }

    private void drawStage(GraphicsContext gc) {
        int[][] stage = table.getStage();
        for(int i=20;i<40;i++){
            for(int j=0;j<10;j++){
                gc.setFill(table.intToColor(stage[i][j]));
                gc.fillRect(xOff + pixelSize * j, yOff + pixelSize * i, pixelSize, pixelSize);
            }
        }
    }
}
