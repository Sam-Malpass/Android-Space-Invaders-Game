package uk.me.malpass.android_space_invaders_game;

import android.graphics.RectF;

/**
 * Created by sam on 08/04/2018.
 */

public abstract class Bullet {
    public float x;
    public float y;
    public RectF rect;
    public final int UP = 0;
    public final int DOWN = 1;
    public int heading = -1;
    public float speed = 350;
    public int width = 1;
    public int height;
    public boolean active;
    public int damage;
}
