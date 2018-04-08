package uk.me.malpass.android_space_invaders_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

/**
 * Created by sam on 08/04/2018.
 */

public class Player {
    RectF rect;
    private Bitmap bitmap;
    private float length;
    private float height;
    private float x;
    private float y;
    private float speed;
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    private int moving = STOPPED;

    public Player(Context context, int screenX, int screenY) {
        rect = new RectF();
        length = screenX/10;
        height = screenY/10;
        x = screenX/2;
        y = screenY - 20;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.playership);
        Bitmap.createScaledBitmap(bitmap, (int) (length), (int) (height), false);
        speed = 350;
    }
}
