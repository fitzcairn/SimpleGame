package com.stevezero.apps.simplegame.android.controls.impl;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.stevezero.game.Game;
import com.stevezero.game.Values;
import com.stevezero.game.controls.events.ControlHandler;
import com.stevezero.game.controls.events.EventType;
import com.stevezero.game.controls.onscreen.Interactable;
import com.stevezero.game.system.SystemManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Translates Android actions for the game.
 */
public final class AppControlHandler extends ControlHandler implements OnTouchListener {
  private final List<Interactable> onUpList = new ArrayList<Interactable>();
  private final SystemManager systemManager;
  private Game game;

  public AppControlHandler(SystemManager systemManager) {
    this.systemManager = systemManager;
  }

  public AppControlHandler setGame(Game game) {
    this.game = game;
    return this;
  }

  private int getGameViewX(float screenX) {
    float scaleX = (float) Values.VIEW_WIDTH / (float)systemManager.getViewWidth();
    return (int)(scaleX * screenX);
  }

  private int getGameViewY(float screenY) {
    float scaleY = (float) Values.VIEW_HEIGHT / (float)systemManager.getViewHeight();
    return (int)(scaleY * screenY);
  }

  @Override
  public boolean onTouch(View view, MotionEvent motionEvent) {
    final int pointerCount = MotionEventCompat.getPointerCount(motionEvent);
    final int action = MotionEventCompat.getActionMasked(motionEvent);

    // Make sure we handle ALL touches on the screen.
    for (int p = 0; p < pointerCount; p++) {
      int x = getGameViewX(motionEvent.getX(p));
      int y = getGameViewY(motionEvent.getY(p));
      //Log.i("CONTROL: " + p, MotionEventCompat.getActionMasked(motionEvent) + " at " + x + " " + y);

      // On a press, run through controls and figure out which was hit.
      if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
        for (Interactable control : game.getInteractables()) {
          if (control.wasActivated(x, y)) {
            handleEvent(control.getStartEvent());
            // Add the up event to the list for any ACTION_UP we recieve.
            onUpList.add(control);
          }
        }
      }
    }

    // For any ACTION_UP we receive, cycle through all controls that were activated by the down.
    // This eliminates weird behavior from accidental swipes.
    if ( MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_UP) {
      for (Interactable interactable : onUpList) {
        handleEvent(interactable.getStopEvent());
      }
      onUpList.clear();
    }

    return true;
  }
}
