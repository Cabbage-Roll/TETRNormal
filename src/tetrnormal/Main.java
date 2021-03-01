package tetrnormal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import javazoom.jl.player.advanced.AdvancedPlayer;
import tetrcore.Constants;
import tetrcore.LoadConfig;

public class Main {
    
    public static Table table = new Table();
    
    public static void main(String[] args) throws IOException, InterruptedException, LoginException {
        System.setProperty("sun.java2d.opengl", "true");

        try {
            LoadConfig.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Constants.iKnowWhatIAmDoing) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        File f = new File("resources/songs");

                        int numberOfSongs = f.listFiles().length;
                        if (numberOfSongs > 0) {

                            String[] pathNames;

                            System.out.println(numberOfSongs + " song(s) found");

                            pathNames = new String[numberOfSongs];
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
                }
            }.start();
        }

        table.initGame();
        Window window = new Window(table);
        // Keyboard controls
        window.getFrame().addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                ///ovo ne valja
                switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    table.movePieceRelative(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    table.movePieceRelative(+1, 0);
                    break;
                case KeyEvent.VK_DOWN:
                    table.movePieceRelative(0, +1);
                    break;
                case KeyEvent.VK_SPACE:
                    table.hardDropPiece();
                    break;
                case KeyEvent.VK_Z:
                case KeyEvent.VK_Y:
                    table.rotatePiece(-1);
                    break;
                case KeyEvent.VK_X:
                    table.rotatePiece(+1);
                    break;
                case KeyEvent.VK_UP:
                    // 180
                    table.rotatePiece(+2);
                    break;
                case KeyEvent.VK_C:
                    table.holdPiece();
                    break;
                case KeyEvent.VK_SHIFT:
                    table.startZone();
                    break;
                }
                table.magicString = KeyEvent.getKeyText(e.getKeyCode());
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
