package com.stevezero.apps.simplegame.android.system.impl;

import android.graphics.Point;
import android.view.Display;

import com.stevezero.game.system.SystemManager;

public class AppSystemManager implements SystemManager {
  // Size of the screen, in pixels.
  private final Display display;
  private Point size = new Point();

  public AppSystemManager(Display display) {
    this.display = display;
  }

  @Override
  public void quit() {
    System.exit(0);
  }

  @Override
  public int getViewWidth() {
    display.getSize(size);
    return size.x;
  }

  @Override
  public int getViewHeight() {
    display.getSize(size);
    return size.y;
  }

  @Override
  public long getCurrentTimeMillis() {
    return System.currentTimeMillis();
  }

}
