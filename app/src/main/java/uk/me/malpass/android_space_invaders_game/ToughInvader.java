package uk.me.malpass.android_space_invaders_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

/**
 * Created by sam on 08/04/2018.
 */

public class ToughInvader extends Invader {
    public ToughInvader(Context context, int row, int column, int screenX, int screenY) {
        rect = new RectF();
        length = screenX / 20;
        height = screenY / 20;
        visible = true;
        int padding = screenX / 25;
        x = column * (length + padding);
        y = row * (length + padding / 4);
        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tough1);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tough2);
        bitmap1 = Bitmap.createScaledBitmap(bitmap1, (int) (length), (int) (height), false);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, (int) (length), (int) (height), false);
        speed = 40;
        lives = 2;
    }
}
