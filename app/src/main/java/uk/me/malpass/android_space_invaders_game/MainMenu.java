package uk.me.malpass.android_space_invaders_game;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {
    SpaceInvadersView SpaceInvadersView;
    public static int hiScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SpaceInvadersView = new SpaceInvadersView(this, size.x, size.y);
        setContentView(R.layout.activity_main_menu);
        Button button1 = findViewById(R.id.play);
        Button button2 = findViewById(R.id.leaderboard);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setContentView(SpaceInvadersView);
                SpaceInvadersView.prepareLevel();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_highscore);
            }
        });
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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
}
