package com.stevezero.game.engine.graphics.rendering;

import com.stevezero.game.assets.drawable.GameDrawable;

/**
 * A rendered scene element that is at least partially visible to the camera.
 */
public final class Rendered implements Comparable<Rendered> {
  // All coordinates are in local (i.e. camera) space.
  private int x;
  private int y;
  private int z;
  private GameDrawable image;

  // For debugging
  private boolean highlight = false;
  
  public Rendered(GameDrawable image, int x, int y, int z) {
    set(image, x, y, z);
  }
  
  public Rendered set(GameDrawable image, int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.image = image;
    return this;
  }
  
  /**
   * Handle z-order sorting.
   */
  @Override
  public int compareTo(Rendered other) {
    // If items on different z-indices, return comparison.
    if (other.getZ() != z) {
      return other.getZ() > z ? 1 : -1;
    }
    return 0;
  }
  
  /**
   * Can return null if the sprite is out of frames.
   */
  public GameDrawable getFrame() {
    return image;
  }
  
  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getZ() {
    return z;
  }
  
  /**
   * Whether this Rendered should be highlighted.
   * This is only used for visual debugging.
   */
  public boolean isHighlighted() {
    return highlight;
  }
  
  /**
   * Turn on the highlight for this Rendered.
   * This is only used for visual debugging.
   */
  public Rendered highlight(boolean onOff) {
    highlight = onOff;
    return this;
  }
  
  public static Rendered create(GameDrawable image, int x, int y, int z) {
    return new Rendered(image, x, y, z);
  }
}
