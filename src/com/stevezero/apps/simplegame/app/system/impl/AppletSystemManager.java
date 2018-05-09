package com.stevezero.apps.simplegame.app.system.impl;

import com.stevezero.game.Values;
import com.stevezero.game.system.SystemManager;

public class AppletSystemManager implements SystemManager {
  @Override
  public void quit() {
    System.exit(0);
  }

  @Override
  public int getViewWidth() {
    return Values.VIEW_WIDTH;
  }

  @Override
  public int getViewHeight() {
    return Values.VIEW_HEIGHT;
  }

  @Override
  public long getCurrentTimeMillis() {
    return System.currentTimeMillis();
  }

}
