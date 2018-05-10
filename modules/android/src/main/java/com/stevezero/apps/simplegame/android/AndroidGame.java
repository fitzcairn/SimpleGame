package com.stevezero.apps.simplegame.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.stevezero.apps.simplegame.android.util.BaseGameActivity;

/**
 * Main game activity.
 */
public class AndroidGame extends BaseGameActivity {
  private View decorView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Hide both the navigation bar and the status bar.
    decorView = getWindow().getDecorView();
    int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_FULLSCREEN;
    decorView.setSystemUiVisibility(uiOptions);

    // Set the content to the game view.
    setContentView(R.layout.activity_android_game);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      decorView.setSystemUiVisibility(
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_FULLSCREEN
              | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
  }

  @Override
  public void onSignInFailed() {
    Log.e("Sign-in", "Failed!");
  }

  @Override
  public void onSignInSucceeded() {
    Log.i("Sign-in", "Succeeded!");
  }
}
