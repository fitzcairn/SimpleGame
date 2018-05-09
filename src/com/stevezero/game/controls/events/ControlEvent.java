package com.stevezero.game.controls.events;


/**
 * Callback interface for player control events.
 * Instantiate and override to handle events.
 */
public interface ControlEvent {  
  /**
   * Handler for a control event.
   */
  public void onEvent();
}
