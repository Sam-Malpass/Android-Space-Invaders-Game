package uk.me.malpass.android_space_invaders_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.games.Player;

import java.util.Random;

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

    public void prepareLevel() {
        started = true;
        animationInterval = 1000;
        player = new Player(context, screenX, screenY);
        bullet = new BasicBullet(screenY);
        for(int i = 0; i < invaderBullets.length; i++) {
            invaderBullets[i] = new BasicBullet(screenY);
        }
        numInvaders = 0;
        for(int column = 0; column < 6; column++) {
            for(int row = 0; row < 5; row++) {
                Random choice = new Random();
                int option = choice.nextInt(4);
                switch(option) {
                    case 1:
                        invaders[numInvaders] = new BasicInvader(context, row, column, screenX, screenY);
                        break;
                    case 2:
                        invaders[numInvaders] = new ToughInvader(context, row, column, screenX, screenY);
                        break;
                    case 3:
                        invaders[numInvaders] = new PowerInvader(context, row, column, screenX, screenY);
                        break;
                    default:
                        invaders[numInvaders] = new BasicInvader(context, row, column, screenX, screenY);
                        break;
                }
                numInvaders++;
            }
        }
        remainingInvaders = numInvaders;
        numBlocks = 0;
        for(int i = 0; i < 4; i++) {
            for(int c = 0; c < 10; c++) {
                for(int r = 0; r < 5; r++) {
                    blocks[numBlocks] = new Brick(r, c, i, screenX, screenY);
                    numBlocks++;
                }
            }
        }
    }
    @Override
    public void run()   {
        while(playing) {
            long startFrameTime = System.currentTimeMillis();
            if (!paused) {
                update();
            }
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
            if(!paused) {
                if((startFrameTime - lastAnimTime) > animationInterval) {
                    lastAnimTime = System.currentTimeMillis();
                    uhOrOh = !uhOrOh;
                }
            }
        }
    }
}
