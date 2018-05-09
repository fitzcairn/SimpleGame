package com.stevezero.game.assets.drawable;

import com.stevezero.game.assets.Asset;
import com.stevezero.game.geometry.Box2;


/**
 * Class representing an image asset.
 */
public abstract class GameDrawable extends Asset {

  public GameDrawable(String id) {
    super(id);
  }
  
  /**
   * Get back a platform-specific renderable object, suitable for casting and compositing.
   *
   * TODO(stevemar): There is probably a cleaner way to do this, but this is the least crappy way I
   * could come up with at the moment.
   */
  public abstract Object getImage();
  
  /**
   * Get the Drawable's width.
   */
  public abstract int getWidth();
  
  /**
   * Get the Drawable's height.
   */
  public abstract int getHeight();
  
  /**
   * Get a Drawable representing a portion of this Drawable.
   */
  public abstract GameDrawable getSelection(int x, int y, int width, int height);
  
  /**
   * Get a Drawable representing a portion of this Drawable.
   */
  public abstract GameDrawable getSelection(Box2 box);
  
  /**
   * Compose an image on top of this Drawable, returning the completed Drawable.
   */
  public abstract GameDrawable compose(GameDrawable other, int x, int y);
  
  /**
   * Set r, g, b to be transparent in this Drawable.
   * Modifies the drawable in place.
   */
  public abstract GameDrawable setTransparency(int r, int g, int b);
}
