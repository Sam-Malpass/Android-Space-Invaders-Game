package uk.me.malpass.android_space_invaders_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.games.Player;

/**
 * Created by sam on 08/04/2018.
 */

public class SpaceInvadersView  extends SurfaceView implements Runnable  {
    public Context context;
    public Thread gameThread = null;
    public SurfaceHolder holder;
    public volatile boolean playing;
    public boolean paused = true;
    public Canvas canvas;
    public Paint paint;
    public long fps;
    public long timeThisFrame;
    public int screenX;
    public int screenY;
    public Player player;
    public Bullet bullet;
    public Invader[] invaders = new Invader[60];
    public int numInvaders;
    public boolean uhOrOh = true;
    public int animationInterval;
    public long lastAnimTime;
    public Bullet[] invaderBullets = new Bullet[200];
    public int nextBullet;
    public int maxInvaderBullets = 10;
    public Brick[] blocks = new Brick[400];
    public int numBlocks;
    public int score;
    public int playerLives = 3;
    public int remainingInvaders;
    public boolean started = false;

    public SpaceInvadersView(Context context, int x, int y)  {
        super(context);
        this.context = context;
        holder = getHolder();
        paint = new Paint();
        screenX = x;
        screenY = y;
    }
}
