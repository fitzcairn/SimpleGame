package com.stevezero.game.controls.onscreen;

import com.stevezero.game.controls.events.EventType;

/**
 * Interface representing an on-screen control.
 */
public interface Interactable {
  
  public boolean wasActivated(int screenX, int screenY);
  
  public EventType getStartEvent();

  public EventType getStopEvent();
}
 