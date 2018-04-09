package uk.me.malpass.android_space_invaders_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
    public uk.me.malpass.android_space_invaders_game.Player player;
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
        player = new uk.me.malpass.android_space_invaders_game.Player(context, screenX, screenY);
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
    private void update() {
        boolean bumped = false;
        boolean lost = false;
        player.update(fps);
        for(int i = 0; i < numInvaders; i++) {
            if(invaders[i].getVisible()) {
                invaders[i].update(fps);
                if(invaders[i].takeAim(player.getX(), player.getLength())) {
                    if(invaders[i]instanceof PowerInvader) {
                        invaderBullets[nextBullet] = new PowerBullet(screenY);
                    }
                    else {
                        invaderBullets[nextBullet] = new BasicBullet(screenY);
                    }
                    if(invaderBullets[nextBullet].shoot(invaders[i].getX() + invaders[i].getLength() / 2, invaders[i].getY(), bullet.DOWN)) {
                        nextBullet++;
                        if(nextBullet == maxInvaderBullets) {
                            nextBullet = 0;
                        }
                    }
                }
            }
            if(invaders[i].getX() > screenX - invaders[i].getLength() || invaders[i].getX() < 0) {
                bumped = true;
            }
        }
        for(int i = 0; i < invaderBullets.length; i++) {
            if(invaderBullets[i].getStatus()) {
                invaderBullets[i].update(fps);
            }
        }
        if(bumped) {
            for(int i = 0; i < numInvaders; i++) {
                invaders[i].dropDown();
                if(invaders[i].getY() > screenY -screenY / 10) {
                    lost = true;
                    score = 0;
                    playerLives = 3;
                }
            }
        }
        if(lost){
            prepareLevel();
        }
        if(bullet.getStatus()) {
            bullet.update(fps);
        }
        if(bullet.getImpactPoint() < 0) {
            bullet.setInactive();
        }
        for(int i = 0; i < invaderBullets.length; i++) {
            if(invaderBullets[i].getImpactPoint() > screenY) {
                invaderBullets[i].setInactive();
            }
        }
        if(bullet.getStatus()) {
            for(int i = 0; i < numInvaders; i++){
                if(invaders[i].getVisible()) {
                    if(RectF.intersects(bullet.getRect(), invaders[i].getRect())) {
                        if(invaders[i].destroyed(bullet.damage)) {
                            invaders[i].setInvisible();
                            remainingInvaders--;
                        }
                        bullet.setInactive();
                        score = score + 10;

                        if(remainingInvaders == 0)
                        {
                            paused = true;
                            playerLives++;
                            prepareLevel();
                        }
                    }
                }
            }
            for(int i = 0; i < invaderBullets.length; i++) {
                if(invaderBullets[i].getStatus()) {
                    for(int j = 0; j < numBlocks; j++) {
                        if(blocks[j].getVisibility()) {
                            if(RectF.intersects(invaderBullets[i].getRect(), blocks[j].getRect())) {
                                invaderBullets[i].setInactive();
                                blocks[j].setInvisible();
                            }
                        }
                    }
                }
            }
            for(int i = 0; i < invaderBullets.length; i++) {
                if(invaderBullets[i].getStatus()) {
                    if(RectF.intersects(player.getRect(), invaderBullets[i].getRect())) {
                        invaderBullets[i].setInactive();
                        playerLives = playerLives - invaderBullets[i].damage;
                        if(playerLives <= 0) {
                            paused = true;
                            playerLives = 3;
                            score = 0;
                            prepareLevel();
                        }
                    }
                }
            }
        }
    }
    private void draw() {
        if(holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.argb(255,0,0,0));
            paint.setColor(Color.argb(255, 255,255,255));
            canvas.drawBitmap(player.getBitmap(), player.getX(), screenY - 50, paint);
            for(int i = 0; i < numInvaders; i++) {
                if(invaders[i].getVisible()) {
                    if (uhOrOh) {
                        canvas.drawBitmap(invaders[i].getBitmap1(), invaders[i].getX(), invaders[i].getY(), paint);
                    } else {
                        canvas.drawBitmap(invaders[i].getBitmap2(), invaders[i].getX(), invaders[i].getY(), paint);
                    }
                }
            }
            for(int i = 0; i < numBlocks; i++) {
                if(blocks[i].getVisibility()) {
                    canvas.drawRect(blocks[i].getRect(), paint);
                }
            }
            if(bullet.getStatus()) {
                canvas.drawRect(bullet.getRect(), paint);
            }
            for(int i = 0; i < invaderBullets.length; i++) {
                if(invaderBullets[i].getStatus()) {
                    canvas.drawRect(invaderBullets[i].getRect(), paint);
                }
            }
            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(40);
            canvas.drawText("Score: " + score + "   Lives: " + playerLives, 10, 50, paint);
            holder.unlockCanvasAndPost(canvas);
        }
    }
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch(InterruptedException e) {
            Log.e("Error", "joining thread");
        }
    }
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & motionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                paused = false;
                if(motionEvent.getY() > screenY - screenY / 8) {
                    if(motionEvent.getX() > screenX / 2) {
                        player.setMovementState(player.RIGHT);
                    }
                    else {
                        player.setMovementState(player.LEFT);
                    }
                }
                if(motionEvent.getY() < screenY - screenY / 8) {
                    if(bullet.shoot(player.getX() + player.getLength() / 2, screenY, bullet.UP)) {
                      /*POSSIBLE SOUND INSERT*/
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(motionEvent.getY() > screenY -screenY / 10) {
                    player.setMovementState(player.STOPPED);
                }
                break;
        }
        return true;
    }
}
