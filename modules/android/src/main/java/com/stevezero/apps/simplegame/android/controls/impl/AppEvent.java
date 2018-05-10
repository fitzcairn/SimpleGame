package com.stevezero.apps.simplegame.android.controls.impl;

import android.hardware.SensorEvent;
import android.view.MotionEvent;

/**
 * Holder class for types of app events.
 */
public class AppEvent {
  private final MotionEvent touchEvent;
  private final SensorEvent rotateEvent;

  public AppEvent(MotionEvent touchEvent) {
    this.touchEvent = touchEvent;
    this.rotateEvent = null;
  }

  public AppEvent(SensorEvent rotateEvent) {
    this.rotateEvent = rotateEvent;
    this.touchEvent = null;
  }

  public boolean hasTouchEvent() {
    return touchEvent != null;
  }

  public boolean hasRotateEvent() {
    return rotateEvent != null;
  }

  public SensorEvent getRotateEvent() {
    return rotateEvent;
  }

  public MotionEvent getTouchEvent() {
    return touchEvent;
  }
}
