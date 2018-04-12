package uk.me.malpass.android_space_invaders_game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainMenu extends Activity {
    public boolean sound = true;
    public boolean tough = true;
    public boolean power = true;
    public int hiScore = 0;
    private SpaceInvadersView SpaceInvadersView;
    private boolean hflag;
    private boolean sflag;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SpaceInvadersView = new SpaceInvadersView(this, size.x, size.y, this);
        setContentView(R.layout.activity_main_menu);
        prefs = this.getSharedPreferences("SCORING", Context.MODE_PRIVATE);
        setup();
    }

    private void setup() {
        Button button1 = findViewById(R.id.play);
        Button button2 = findViewById(R.id.leaderboard);
        Button button3 = findViewById(R.id.settings);
        hflag = false;
        sflag = false;
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setContentView(SpaceInvadersView);
                SpaceInvadersView.prepareLevel();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreViewer();

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewer();
            }
        });


    }

    private void settingsViewer() {
        sflag = true;
        setContentView(R.layout.activity_settings);
        final ToggleButton soundTog = findViewById(R.id.sound_on_off);
        final ToggleButton toughTog = findViewById(R.id.tough_on_off);
        final ToggleButton powerTog = findViewById(R.id.power_on_off);
        if(sound) {
            soundTog.setChecked(true);
        }
        else {
            soundTog.setChecked(false);
        }
        if(tough) {
            toughTog.setChecked(true);
        }
        else {
            toughTog.setChecked(false);
        }
        if(power) {
            powerTog.setChecked(true);
        }
        else {
            powerTog.setChecked(false);
        }
        soundTog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound) {
                    soundTog.setChecked(false);
                    sound = false;
                }
                else {
                    soundTog.setChecked(true);
                    sound = true;
                }
            }
        });
        toughTog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tough) {
                    toughTog.setChecked(false);
                    tough = false;
                }
                else {
                    toughTog.setChecked(true);
                    tough = true;
                }
            }
        });
        powerTog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(power) {
                    powerTog.setChecked(false);
                    power = false;
                }
                else {
                    powerTog.setChecked(true);
                    power = true;
                }
            }
        });
    }

    private void scoreViewer() {
        hflag = true;
        int score = prefs.getInt("score", 0);
        setContentView(R.layout.activity_highscore);
        TextView scoreView = findViewById(R.id.score);
        scoreView.setText(Integer.toString(score));
    }

    @Override
    public void onBackPressed() {
        if(hflag || sflag) {
            setContentView(R.layout.activity_main_menu);
            setup();
            hflag = false;
            sflag = false;
        }
        else {
            moveTaskToBack(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SpaceInvadersView.started == true) {
            setContentView(SpaceInvadersView);
        }
        SpaceInvadersView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SpaceInvadersView.pause();
    }

    public void handleScore() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("score", hiScore);
        editor.commit();
    }
}
