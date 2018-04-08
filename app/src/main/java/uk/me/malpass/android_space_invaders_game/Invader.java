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
    public void update(long fps) {
        if(moving == LEFT) {
            x = x - speed / fps;
        }
        if(moving == RIGHT) {
            x = x + speed / fps;
        }
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }
    public void dropDown() {
        if(moving == LEFT) {
            moving = RIGHT;
        }
        else {
            moving = LEFT;
        }
        y = y + height;
        speed = speed * 1.18f;
    }

    public boolean takeAim(float playerX, float playerLength) {
        int randomNumber = -1;
        if((playerX + playerLength > x && playerX + playerLength < x + length) || (playerX > x && playerX < x + length)) {
            randomNumber = generator.nextInt(150);
            if(randomNumber == 0) {
                return true;
            }
        }
        randomNumber = generator.nextInt(2000);
        if(randomNumber == 0) {
            return true;
        }
        return false;
    }

    public void setInvisible() {
        visible = false;
    }
    public boolean getVisible() {
        return visible;
    }
    public RectF getRect() {
        return rect;
    }
    public Bitmap getBitmap1() {
        return bitmap1;
    }
    public Bitmap getBitmap2() {
        return bitmap2;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getLength() {
        return length;
    }
}
