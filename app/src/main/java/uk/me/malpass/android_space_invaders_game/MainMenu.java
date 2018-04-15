package uk.me.malpass.android_space_invaders_game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainMenu extends Activity {
    public boolean sound = true;
    public boolean tough = true;
    public boolean power = true;
    public boolean lTog = false;
    public int[][] level = new int[6][5];
    public int hiScore = 0;
    private SpaceInvadersView SpaceInvadersView;
    private boolean hflag;
    private boolean sflag;
    private boolean lflag;
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
        Button button4 = findViewById(R.id.levelBuilder);
        hflag = false;
        sflag = false;
        lflag = false;
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(SpaceInvadersView.started) {
                    setContentView(SpaceInvadersView);
                    SpaceInvadersView.resume();
                }
                else {
                    setContentView(SpaceInvadersView);
                    SpaceInvadersView.prepareLevel();
                }
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
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelBuilder();
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

    private void levelBuilder() {
        lflag = true;
        final int[][] test = new int[6][5];
        setContentView(R.layout.activity_level_builder);
        final CheckBox checkBox0 = findViewById(R.id.checkBox1);
        checkBox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox0.isChecked()) {
                    checkBox0.setChecked(false);
                    test[0][0] = 0;
                }
                else {
                    checkBox0.setChecked(true);
                    test[0][0] = 1;
                }
            }
        });
        final CheckBox checkBox1 = findViewById(R.id.checkBox2);
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox1.isChecked()) {
                    checkBox1.setChecked(false);
                    test[1][0] = 0;
                }
                else {
                    checkBox1.setChecked(true);
                    test[1][0] = 1;
                }
            }
        });
        final CheckBox checkBox2 = findViewById(R.id.checkBox3);
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox2.isChecked()) {
                    checkBox2.setChecked(false);
                    test[2][0] = 0;
                }
                else {
                    checkBox2.setChecked(true);
                    test[2][0] = 1;
                }
            }
        });
        final CheckBox checkBox3 = findViewById(R.id.checkBox4);
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox3.isChecked()) {
                    checkBox3.setChecked(false);
                    test[3][0] = 0;
                }
                else {
                    checkBox3.setChecked(true);
                    test[3][0] = 1;
                }
            }
        });
        final CheckBox checkBox4 = findViewById(R.id.checkBox5);
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox4.isChecked()) {
                    checkBox4.setChecked(false);
                    test[4][0] = 0;
                }
                else {
                    checkBox4.setChecked(true);
                    test[4][0] = 1;
                }
            }
        });
        final CheckBox checkBox5 = findViewById(R.id.checkBox6);
        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox5.isChecked()) {
                    checkBox5.setChecked(false);
                    test[5][0] = 0;
                }
                else {
                    checkBox5.setChecked(true);
                    test[5][0] = 1;
                }
            }
        });
        final CheckBox checkBox6 = findViewById(R.id.checkBox7);
        checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox6.isChecked()) {
                    checkBox6.setChecked(false);
                    test[0][1] = 0;
                }
                else {
                    checkBox6.setChecked(true);
                    test[0][1] = 1;
                }
            }
        });
        final CheckBox checkBox7 = findViewById(R.id.checkBox8);
        checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox7.isChecked()) {
                    checkBox7.setChecked(false);
                    test[1][1] = 0;
                }
                else {
                    checkBox7.setChecked(true);
                    test[1][1] = 1;
                }
            }
        });
        final CheckBox checkBox8 = findViewById(R.id.checkBox9);
        checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox8.isChecked()) {
                    checkBox8.setChecked(false);
                    test[2][1] = 0;
                }
                else {
                    checkBox8.setChecked(true);
                    test[2][1] = 1;
                }
            }
        });
        final CheckBox checkBox9 = findViewById(R.id.checkBox10);
        checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox9.isChecked()) {
                    checkBox9.setChecked(false);
                    test[3][1] = 0;
                }
                else {
                    checkBox9.setChecked(true);
                    test[3][1] = 1;
                }
            }
        });
        final CheckBox checkBox10 = findViewById(R.id.checkBox11);
        checkBox10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox10.isChecked()) {
                    checkBox10.setChecked(false);
                    test[4][1] = 0;
                }
                else {
                    checkBox10.setChecked(true);
                    test[4][1] = 1;
                }
            }
        });
        final CheckBox checkBox11 = findViewById(R.id.checkBox12);
        checkBox11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox11.isChecked()) {
                    checkBox11.setChecked(false);
                    test[5][1] = 0;
                }
                else {
                    checkBox11.setChecked(true);
                    test[5][1] = 1;
                }
            }
        });
        final CheckBox checkBox12 = findViewById(R.id.checkBox13);
        checkBox12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox12.isChecked()) {
                    checkBox12.setChecked(false);
                    test[0][2] = 0;
                }
                else {
                    checkBox12.setChecked(true);
                    test[0][2] = 1;
                }
            }
        });
        final CheckBox checkBox13 = findViewById(R.id.checkBox14);
        checkBox13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox13.isChecked()) {
                    checkBox13.setChecked(false);
                    test[1][2] = 0;
                }
                else {
                    checkBox13.setChecked(true);
                    test[1][2] = 1;
                }
            }
        });
        final CheckBox checkBox14 = findViewById(R.id.checkBox15);
        checkBox14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox14.isChecked()) {
                    checkBox14.setChecked(false);
                    test[2][2] = 0;
                }
                else {
                    checkBox14.setChecked(true);
                    test[2][2] = 1;
                }
            }
        });
        final CheckBox checkBox15 = findViewById(R.id.checkBox16);
        checkBox15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox15.isChecked()) {
                    checkBox15.setChecked(false);
                    test[3][2] = 0;
                }
                else {
                    checkBox15.setChecked(true);
                    test[3][2] = 1;
                }
            }
        });
        final CheckBox checkBox16 = findViewById(R.id.checkBox17);
        checkBox16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox16.isChecked()) {
                    checkBox16.setChecked(false);
                    test[4][2] = 0;
                }
                else {
                    checkBox16.setChecked(true);
                    test[4][2] = 1;
                }
            }
        });
        final CheckBox checkBox17 = findViewById(R.id.checkBox18);
        checkBox17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox17.isChecked()) {
                    checkBox17.setChecked(false);
                    test[5][2] = 0;
                }
                else {
                    checkBox17.setChecked(true);
                    test[5][2] = 1;
                }
            }
        });
        final CheckBox checkBox18 = findViewById(R.id.checkBox19);
        checkBox18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox18.isChecked()) {
                    checkBox18.setChecked(false);
                    test[0][3] = 0;
                }
                else {
                    checkBox18.setChecked(true);
                    test[0][3] = 1;
                }
            }
        });
        final CheckBox checkBox19 = findViewById(R.id.checkBox20);
        checkBox19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox19.isChecked()) {
                    checkBox19.setChecked(false);
                    test[1][3] = 0;
                }
                else {
                    checkBox19.setChecked(true);
                    test[1][3] = 1;
                }
            }
        });
        final CheckBox checkBox20 = findViewById(R.id.checkBox21);
        checkBox20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox20.isChecked()) {
                    checkBox20.setChecked(false);
                    test[2][3] = 0;
                }
                else {
                    checkBox20.setChecked(true);
                    test[2][3] = 1;
                }
            }
        });
        final CheckBox checkBox21 = findViewById(R.id.checkBox22);
        checkBox21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox21.isChecked()) {
                    checkBox21.setChecked(false);
                    test[3][3] = 0;
                }
                else {
                    checkBox21.setChecked(true);
                    test[3][3] = 1;
                }
            }
        });
        final CheckBox checkBox22 = findViewById(R.id.checkBox23);
        checkBox22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox22.isChecked()) {
                    checkBox22.setChecked(false);
                    test[4][3] = 0;
                }
                else {
                    checkBox22.setChecked(true);
                    test[4][3] = 1;
                }
            }
        });
        final CheckBox checkBox23 = findViewById(R.id.checkBox24);
        checkBox23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox23.isChecked()) {
                    checkBox23.setChecked(false);
                    test[5][3] = 0;
                }
                else {
                    checkBox23.setChecked(true);
                    test[5][3] = 1;
                }
            }
        });
        final CheckBox checkBox24 = findViewById(R.id.checkBox25);
        checkBox24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox24.isChecked()) {
                    checkBox24.setChecked(false);
                    test[0][4] = 0;
                }
                else {
                    checkBox24.setChecked(true);
                    test[0][4] = 1;
                }
            }
        });
        final CheckBox checkBox25 = findViewById(R.id.checkBox26);
        checkBox25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox25.isChecked()) {
                    checkBox25.setChecked(false);
                    test[1][4] = 0;
                }
                else {
                    checkBox25.setChecked(true);
                    test[1][4] = 1;
                }
            }
        });
        final CheckBox checkBox26 = findViewById(R.id.checkBox27);
        checkBox26.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox26.isChecked()) {
                    checkBox26.setChecked(false);
                    test[2][4] = 0;
                }
                else {
                    checkBox26.setChecked(true);
                    test[2][4] = 1;
                }
            }
        });
        final CheckBox checkBox27 = findViewById(R.id.checkBox28);
        checkBox27.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox27.isChecked()) {
                    checkBox27.setChecked(false);
                    test[3][4] = 0;
                }
                else {
                    checkBox27.setChecked(true);
                    test[3][4] = 1;
                }
            }
        });
        final CheckBox checkBox28 = findViewById(R.id.checkBox29);
        checkBox28.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox28.isChecked()) {
                    checkBox28.setChecked(false);
                    test[4][4] = 0;
                }
                else {
                    checkBox28.setChecked(true);
                    test[4][4] = 1;
                }
            }
        });
        final CheckBox checkBox29 = findViewById(R.id.checkBox30);
        checkBox29.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!checkBox29.isChecked()) {
                    checkBox29.setChecked(false);
                    test[5][4] = 0;
                }
                else {
                    checkBox29.setChecked(true);
                    test[5][4] = 1;
                }
            }
        });
        final Button apply = findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lTog = true;
                level = test;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(hflag || sflag || lflag) {
            setContentView(R.layout.activity_main_menu);
            setup();
            hflag = false;
            sflag = false;
            lflag = false;
        }
        else if(SpaceInvadersView.started){
            SpaceInvadersView.pause();
            setup();
            setContentView(R.layout.activity_main_menu);
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
