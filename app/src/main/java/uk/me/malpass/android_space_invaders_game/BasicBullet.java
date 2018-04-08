package uk.me.malpass.android_space_invaders_game;

import android.graphics.RectF;

/**
 * Created by sam on 08/04/2018.
 */

public class BasicBullet extends Bullet {
    public BasicBullet(int screenY) {
        height = screenY/20;
        active = false;
        rect = new RectF();
        damage = 1;
    }
}
