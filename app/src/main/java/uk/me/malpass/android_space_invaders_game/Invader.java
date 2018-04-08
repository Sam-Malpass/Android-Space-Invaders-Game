package uk.me.malpass.android_space_invaders_game;

import android.graphics.Bitmap;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created by sam on 08/04/2018.
 */

public abstract class Invader {
    public RectF rect;
    public Random generator = new Random();
    public Bitmap bitmap1;
    public Bitmap bitmap2;
    public float length;
    public float height;
    public float x;
    public float y;
    public float speed;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    public int moving = RIGHT;
    public boolean visible;
    public int lives;

    public boolean destroyed(int damage) {
        lives = lives - damage;
        if(lives <= 0) {
            return true;
        }
        return false;
    }
}
