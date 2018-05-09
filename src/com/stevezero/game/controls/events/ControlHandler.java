package com.stevezero.game.controls.events;

import java.util.Arrays;

/**
 * Class wrapping player control callbacks.
 */
public abstract class ControlHandler {
  private ControlEvent[] events = new ControlEvent[EventType.values().length];
    
  /**
   * Register a callback for an event.  Only one callback per event allowed.
   */
  public void registerEvent(EventType event, ControlEvent callback) {
    if (events[event.ordinal()] != null) {
      // Collision!  Throw a runtime exception.
      throw new IllegalStateException(
          "Attempt to register callback for an event that already has a callback assigned.");
    }
    events[event.ordinal()] = callback;
  }

  /**
   * Clear all event callbacks
   */
  public void clear() {
    Arrays.fill(events, null);
  }
  
  // Handle the event, once translated.
  protected void handleEvent(EventType event) {
    if (events[event.ordinal()] != null) {
      events[event.ordinal()].onEvent();
    }
  }
}
