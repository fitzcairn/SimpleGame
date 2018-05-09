package com.stevezero.apps.simplegame.app.controls.events;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Translation layer for different types of events.
 */
public interface AppletEvent {
  public boolean hasKeyEvent();

  public boolean hasMouseEvent();
  
  public KeyEvent getKeyEvent();
  
  public MouseEvent getMouseEvent();
}
