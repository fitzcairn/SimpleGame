package com.stevezero.game.geometry;

/**
 * A 2D Vector.  Vector2s are assumed to start at the origin. 
 */
public abstract class Vector2 {  
  protected float x;
  protected float y;
    
  // Read-only operations (Non-mutators)

  /**
   * @return the X component.
   */
  public float getX() {
    return x;
  }

  /**
   * @return the Y component.
   */
  public float getY() {
    return y;
  }
  
  /**
   * @return the magnitude of this vector.
   */
  public float getMagnitude() {
    return (float) Math.sqrt((x * x) + (y * y));
   }
  
  /**
   * @return the dot product of this Vector2 with other.
   */
  public float getDot(Vector2 other) {
    return x * other.getX() + y * other.getY();
  }
}
