package com.stevezero.apps.simplegame.javafx.controls.events;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Translation layer for different types of events.
 */
public interface JavaFXEvent {
  public boolean hasKeyEvent();

  public boolean hasMouseEvent();
  
  public KeyEvent getKeyEvent();
  
  public MouseEvent getMouseEvent();
}
