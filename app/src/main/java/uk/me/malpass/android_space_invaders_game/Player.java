package uk.me.malpass.android_space_invaders_game;

import android.graphics.Bitmap;
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
}
