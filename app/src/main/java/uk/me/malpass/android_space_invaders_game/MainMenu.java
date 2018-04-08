package uk.me.malpass.android_space_invaders_game;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.games.Games;

public class MainMenu extends Activity {
    SignIn signIn;
    SpaceInvadersView SpaceInvadersView;
    public static GoogleSignInAccount account;
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
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setContentView(SpaceInvadersView);
                SpaceInvadersView.prepareLevel();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn.signIn();
                        break;

                }
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
