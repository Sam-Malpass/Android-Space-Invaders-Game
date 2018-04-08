package uk.me.malpass.android_space_invaders_game;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

/**
 * Created by sam on 08/04/2018.
 */

public class SignIn extends Activity {
    private GoogleSignInClient mGoogleSignInClient;
    public static GoogleSignInAccount account;
    private static int RC_SIGN_IN = 100;

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
