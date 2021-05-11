package tetrnormal;

import javax.swing.*;

public class Window extends JPanel {
/*
    private static final long serialVersionUID = 1L;
    private static final int PIXELSIZE = 12;
    private static final int GRIDSIZE = 0;
    private static final Point TOPLEFTCORNER = new Point(PIXELSIZE * 10, PIXELSIZE * 3);
    private final Table table;
    private final JFrame frame;

    public Window(Table table) throws InterruptedException {
        this.table = table;
        frame = new JFrame("TETR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(PIXELSIZE * 30 + 16, PIXELSIZE * 46 + 39);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setUndecorated(true);
        frame.setBackground(
            new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256), 0));
        frame.setAlwaysOnTop(true);
        frame.add(this);
        frame.setFocusable(false);
        frame.setAutoRequestFocus(false);
        frame.setResizable(false);
        frame.setVisible(true);

        new Thread() {
            @Override
            public void run() {
                while (!table.getGameover()) {
                    /*
                        Color color = new Color(
                                Math.abs((getFrame().getBackground().getRed() + (int) (Math.random() * 3) - 1)) % 256,
                                Math.abs((getFrame().getBackground().getGreen() + (int) (Math.random() * 3) - 1)) % 256,
                                Math.abs((getFrame().getBackground().getBlue() + (int) (Math.random() * 3) - 1)) % 256);
                        getFrame().setBackground(color);
                    frame.repaint();
                }
                JOptionPane.showMessageDialog(getFrame(), "gameover");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    frame.setVisible(!frame.isVisible());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    public JFrame getFrame() {
        return frame;
    }

    @Override
    public void paintComponent(Graphics g) {
        long timeStart = System.nanoTime();




        // garbage meter
        int total = 0;
        for (int num : table.getGarbageQueue()) {
            total += num;
        }

        g.setColor(Color.BLACK);
        g.fillRect(TOPLEFTCORNER.x - PIXELSIZE * 2, TOPLEFTCORNER.y + GameLogic.STAGESIZEY / 2 * PIXELSIZE,
                PIXELSIZE - GRIDSIZE, (PIXELSIZE - GRIDSIZE) * 20);

        for (int i = 0; i < total; i++) {
            g.setColor(Table.intToColor((i / (GameLogic.STAGESIZEY / 2)) % 7));
            g.fillRect(TOPLEFTCORNER.x - PIXELSIZE * 2,
                    TOPLEFTCORNER.y + (GameLogic.STAGESIZEY - 1 - i % 20) * PIXELSIZE, PIXELSIZE - GRIDSIZE,
                    PIXELSIZE - GRIDSIZE);
        }

        // Paint the well
        for (int i = GameLogic.STAGESIZEY - Table.PLAYABLEROWS; i < GameLogic.STAGESIZEY; i++) {
            for (int j = 0; j < GameLogic.STAGESIZEX; j++) {
                g.setColor(Table.intToColor(table.getStage()[i][j]));
                g.fillRect(TOPLEFTCORNER.x + PIXELSIZE * j, TOPLEFTCORNER.y + PIXELSIZE * i, PIXELSIZE - GRIDSIZE,
                        PIXELSIZE - GRIDSIZE);
            }
        }

        // Display the score
        g.setColor(Color.WHITE);
        g.drawString("points: " + table.getScore(), 19 * 6, PIXELSIZE - GRIDSIZE);

        g.setColor(Color.WHITE);
        g.drawString("alpha", 20 * 12, 20 * 2);




        // show controls

        g.setColor(Color.WHITE);
        g.drawString("Controls:", 50, 80);
        g.drawString("move left/right - left/right arrow\n", 50, 90);
        g.drawString("rotate counterclockwise: z/y\n", 50, 100);
        g.drawString("rotate clockwise: x\n", 50, 110);
        g.drawString("rotate 180: arrow up\n", 50, 120);
        g.drawString("hold: c\n", 50, 130);
        g.drawString("hard drop: space\n", 50, 140);
        g.drawString("soft drop: arrown down", 50, 150);
        g.drawString("zone: shift", 50, 160);
        g.drawString(table.getMagicString(), 120, 200);


        drawPiece(g);
        drawQueue(g);
        drawHold(g);


        long timeEnd = System.nanoTime();

        long timeElapsed = timeEnd - timeStart;
        g.setColor(Color.YELLOW);
        g.drawString(1000000000 / timeElapsed + " FPS", 10, 10);
        g.drawString("" + getFrame(), 10, 20);

        drawKeys(g);
    }

    private void drawHold(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(TOPLEFTCORNER.x - 7 * PIXELSIZE, TOPLEFTCORNER.y + GameLogic.STAGESIZEY / 2 * PIXELSIZE,
            PIXELSIZE * 4, PIXELSIZE * 4);
        if (table.getHeldPiece() != -1) {
            g.setColor(Table.intToColor(table.getHeldPiece()));
            for (Point point : table.getPiece(table.getHeldPiece(), 0)) {
                g.fillRect(TOPLEFTCORNER.x + (-7 + point.x) * PIXELSIZE,
                    TOPLEFTCORNER.y + (point.y + GameLogic.STAGESIZEY / 2) * PIXELSIZE, PIXELSIZE, PIXELSIZE);
            }
        }
    }

    private void drawKeys(Graphics g) {
        g.drawString("" + Main.keyIsDown[0], 250, 80);
        g.drawString("" + Main.keyIsDown[1], 250, 90);
        g.drawString("" + Main.keyIsDown[2], 250, 100);
        g.drawString("" + Main.keyIsDown[3], 250, 110);
        g.drawString("" + Main.keyIsDown[4], 250, 120);
        g.drawString("" + Main.keyIsDown[5], 250, 130);
        g.drawString("" + Main.keyIsDown[6], 250, 140);
        g.drawString("" + Main.keyIsDown[7], 250, 150);
        g.drawString("" + Main.keyIsDown[8], 250, 160);
    }

    // Draw the falling piece
    private void drawPiece(Graphics g) {
        g.setColor(Table.intToColor(table.getCurrentPieceInt()));
        for (Point point : table.getPiece(table.getCurrentPieceInt(), table.getCurrentPieceRotation())) {
            g.fillRect(TOPLEFTCORNER.x + (point.x + table.getCurrentPiecePosition().x) * PIXELSIZE,
                TOPLEFTCORNER.y + (point.y + table.getCurrentPiecePosition().y) * PIXELSIZE, PIXELSIZE - GRIDSIZE,
                PIXELSIZE - GRIDSIZE);
        }
    }

    private void drawQueue(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(TOPLEFTCORNER.x + (3 + GameLogic.STAGESIZEX) * PIXELSIZE,
            TOPLEFTCORNER.y + GameLogic.STAGESIZEY / 2 * PIXELSIZE, PIXELSIZE * 4, PIXELSIZE * 4 * 5);
        /// prints next blocks
        for (int i = 0; i < 5; i++) {
            int piece = table.getNextPieces().get(i);
            g.setColor(Table.intToColor(piece));
            for (Point point : table.getPiece(piece, 0)) {
                g.fillRect(TOPLEFTCORNER.x + (3 + point.x + GameLogic.STAGESIZEX) * PIXELSIZE,
                    TOPLEFTCORNER.y + (i * 4 + point.y + GameLogic.STAGESIZEY / 2) * PIXELSIZE,
                    PIXELSIZE - GRIDSIZE, PIXELSIZE - GRIDSIZE);
            }
        }
    }
*/
}
