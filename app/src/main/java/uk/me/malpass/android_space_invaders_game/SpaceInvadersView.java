package uk.me.malpass.android_space_invaders_game;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.Random;

/**
 * Created by sam on 08/04/2018.
 */

public class SpaceInvadersView  extends SurfaceView implements Runnable  {
    MainMenu menu;
    private Context context;
    private Thread gameThread = null;
    private SurfaceHolder holder;
    private volatile boolean playing;
    private boolean paused = true;
    private Canvas canvas;
    private Paint paint;
    private long fps;
    private long timeThisFrame;
    private int screenX;
    private int screenY;
    private uk.me.malpass.android_space_invaders_game.Player player;
    private Bullet bullet;
    private Invader[] invaders = new Invader[60];
    private int numInvaders;
    private boolean uhOrOh = true;
    private int animationInterval;
    private long lastAnimTime;
    private Bullet[] invaderBullets = new Bullet[200];
    private int nextBullet;
    private int maxInvaderBullets = 10;
    private Brick[] blocks = new Brick[400];
    private int numBlocks;
    private int score;
    private int playerLives = 3;
    private int remainingInvaders;
    public boolean started = false;

    private boolean sEnabled = true;
    private SoundPool soundPool;
    private int playerExplodeID = -1;
    private int invaderExplodeID = -1;
    private int shootID = -1;
    private int damageShelterID = -1;
    private boolean tEnabled = true;
    private boolean pEnabled = true;

    public SpaceInvadersView(Context context, int x, int y, MainMenu menu)  {
        super(context);
        this.context = context;
        holder = getHolder();
        paint = new Paint();
        screenX = x;
        screenY = y;
        this.menu = menu;
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);

        try{

            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            descriptor = assetManager.openFd("shoot.ogg");
            shootID = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("invaderexplode.ogg");
            invaderExplodeID = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("damageshelter.ogg");
            damageShelterID = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("playerexplode.ogg");
            playerExplodeID = soundPool.load(descriptor, 0);
        }catch(IOException e){
            Log.e("error", "failed to load sound files");
        }
    }
    public void prepareLevel() {
        started = true;
        animationInterval = 1000;
        sEnabled = menu.sound;
        tEnabled = menu.tough;
        pEnabled = menu.power;
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
                        if(tEnabled) {
                            invaders[numInvaders] = new ToughInvader(context, row, column, screenX, screenY);
                        }
                        else {
                            invaders[numInvaders] = new BasicInvader(context, row, column, screenX, screenY);
                        }
                        break;
                    case 3:
                        if(pEnabled) {
                            invaders[numInvaders] = new PowerInvader(context, row, column, screenX, screenY);
                        }
                        else {
                            invaders[numInvaders] = new BasicInvader(context, row, column, screenX, screenY);
                        }
                        break;
                    default:
                        invaders[numInvaders] = new BasicInvader(context, row, column, screenX, screenY);
                        break;
                }
                if(menu.lTog) {
                    if(menu.level[column][row] == 0) {
                        invaders[numInvaders].setInvisible();
                        continue;
                    }
                    numInvaders++;
                    continue;
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
        if(menu.lTog) {
            menu.lTog = false;
        }
    }
    @Override
    public void run()   {
        while(playing) {
            long startFrameTime = System.currentTimeMillis();
            if (!paused) {
                if((startFrameTime - lastAnimTime) > animationInterval) {
                    lastAnimTime = System.currentTimeMillis();
                    uhOrOh = !uhOrOh;
                }
                update();
            }
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
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
                        if(sEnabled) {
                            soundPool.play(shootID, 1, 1, 0, 0, 1);
                        }
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
                    if(score > menu.hiScore) {
                        menu.hiScore = score;
                        menu.handleScore();
                    }
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
            if(invaderBullets[i].getStatus()) {
                if(RectF.intersects(player.getRect(), invaderBullets[i].getRect())) {
                    invaderBullets[i].setInactive();
                    playerLives = playerLives - invaderBullets[i].damage;
                    if(playerLives <= 0) {
                        if(sEnabled) {
                            soundPool.play(playerExplodeID, 1, 1, 0, 0, 1);
                        }
                        if(score > menu.hiScore) {
                            menu.hiScore = score;
                            menu.handleScore();
                        }
                        paused = true;
                        playerLives = 3;
                        score = 0;
                        prepareLevel();
                    }
                }
                for(int j = 0; j < numBlocks; j++) {
                    if(blocks[j].getVisibility()) {
                        if(RectF.intersects(invaderBullets[i].getRect(), blocks[j].getRect())) {
                            invaderBullets[i].setInactive();
                            blocks[j].setInvisible();
                            if(sEnabled) {
                                soundPool.play(damageShelterID, 1, 1, 0, 0, 1);
                            }
                        }
                    }
                }
            }
        }
        if(bullet.getStatus()) {
            for(int i = 0; i < numInvaders; i++){
                if(invaders[i].getVisible()) {
                    if(RectF.intersects(bullet.getRect(), invaders[i].getRect())) {
                        if(invaders[i].destroyed(bullet.damage)) {
                            if(sEnabled) {
                                soundPool.play(invaderExplodeID, 1, 1, 0, 0, 1);
                            }
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
                        if(sEnabled) {
                            soundPool.play(shootID, 1, 1, 0, 0, 1);
                        }
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
