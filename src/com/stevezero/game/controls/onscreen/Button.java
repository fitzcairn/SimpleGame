package com.stevezero.game.controls.onscreen;

import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.engine.graphics.rendering.Renderable;

/**
 * Base class for all on-screen controls.
 */
abstract public class Button implements Interactable, Renderable {
  private static final int Z = Layers.MENU;

  private int x;
  private int y;
  
  public Button(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  protected int setX(int x) {
    this.x = x;
    return x;
  }
  
  protected int setY(int y) {
    this.y = y;
    return y;
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public int getZ() {
    return Z;
  }
  
  /**
   * Get control width.
   */
  abstract public int getWidth();

  /**
   * Get control height.
   */
  abstract public int getHeight();
  
  
  /**
   * Given a point on the screen the user interacted with, return whether or not this
   * button was clicked.
   */
  @Override
  public boolean wasActivated(int screenX, int screenY) {
    return (screenX >= getX() && screenX <= (getX() + getWidth())) &&
        (screenY >= getY() && screenY <= (getY() + getHeight()));
  }
}
