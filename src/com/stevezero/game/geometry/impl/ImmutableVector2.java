package com.stevezero.game.geometry.impl;

import com.stevezero.game.geometry.Point2;
import com.stevezero.game.geometry.Vector2;

/**
 * An immutable Vector2.
 */
public final class ImmutableVector2 extends Vector2 {

  private ImmutableVector2(float x, float y) {
    this.x = x;
    this.y = y;
  }
  
  /**
   * @return an immutable copy of another vector.
   */
  public static Vector2 of(Vector2 other) {
    return new ImmutableVector2(other.getX(), other.getY());
  }
  
  /**
   * @return an immutable vector from two Point2 objects.
   */
  public static Vector2 of(Point2 a, Point2 b) {
    return new ImmutableVector2(a.getX() - b.getX(), a.getY() - b.getY());
  }  

  /**
   * @return an immutable vector from X and Y.
   */
  public static Vector2 of(float x, float y) {
    return new ImmutableVector2(x, y);
  }  
}
