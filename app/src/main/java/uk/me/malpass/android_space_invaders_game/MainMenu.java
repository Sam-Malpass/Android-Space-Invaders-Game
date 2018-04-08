package uk.me.malpass.android_space_invaders_game;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainMenu extends Activity {
    SignIn signIn;
    SpaceInvadersView SpaceInvadersView;
    public static GoogleSignInAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
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
