package uk.me.malpass.android_space_invaders_game;

import android.graphics.RectF;

/**
 * Created by sam on 08/04/2018.
 */

public class Brick {
    public RectF rect;
    public boolean visibile;
    public Brick(int row, int column, int shelterNumber, int screenX, int screenY) {
        int width = screenX / 90;
        int height = screenY / 40;
        visibile = true;
        int blockPadding = 0;
        int shelterPaddig = screenX / 9;
        int startHeight = screenY - (screenY / 8 * 2);
        rect = new RectF(column * width + blockPadding + (shelterPaddig * shelterNumber) + shelterPaddig + shelterPaddig * shelterNumber, row * height + blockPadding + startHeight, column * width + width - blockPadding + (shelterPaddig * shelterNumber) + shelterPaddig + shelterPaddig * shelterNumber, row * height + height - blockPadding + startHeight);
    }
}
