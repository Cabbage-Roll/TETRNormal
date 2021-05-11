package tetrnormal;

import javafx.scene.paint.Color;
import tetrcore.GameLogic;

public class Table extends GameLogic {

    private static final int VISIBLEROWS = 30;

    private static final int BACKGROUNDROWS = 20;

    public static Color intToColor(int number) {
        switch (number) {
            case 0:
                return Color.RED;
            case 1:
                return Color.ORANGE;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.CYAN;
            case 5:
                return Color.BLUE;
            case 6:
                return Color.MAGENTA;
            case 7:
                return Color.BLACK;
            case 8:
                return Color.GRAY;
            case 16:
                return Color.WHITE;
        }
        return null;
    }

    @Override
    public void whatWhenGameover() {

    }

    @Override
    public void whatWhenLineClear(int i, int[] ints) {

    }

    @Override
    public void whatWhenPerfectClear() {

    }

    @Override
    public void whatWhenSendGarbage(int i) {

    }

    @Override
    public void whatWhenSpin() {

    }
}
