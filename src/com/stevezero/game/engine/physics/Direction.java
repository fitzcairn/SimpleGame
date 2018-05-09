package com.stevezero.game.engine.physics;

import com.stevezero.game.geometry.Vector2;
import com.stevezero.game.geometry.impl.ImmutableVector2;

/**
 * Simple direction enum suitable for a 2d game.
 */
public enum Direction {  
  NONE(ImmutableVector2.of(0, 0), -1),
  LEFT(ImmutableVector2.of(-1, 0), 0),
  RIGHT(ImmutableVector2.of(1, 0), 1),
  UP(ImmutableVector2.of(0, -1), 2),
  DOWN(ImmutableVector2.of(0, 1), 3);
  
  private final Vector2 vec2;
  private final int ord;

  private Direction(Vector2 vec2, int ord) {
    this.vec2 = vec2;
    this.ord = ord;
  }
  
  /**
   * @return the ordinal.  Useful when selecting from atlas sheets, etc.
   */
  public int getOrdinal() {
    return this.ord;
  }
  
  /**
   * @return the vector corresponding to the direction.
   */
  public Vector2 getVector2() {
    return this.vec2;
  }
  
  /**
   * @return the closest cardinal direction to the input vector.
   */
  public static Direction getClosestTo(Vector2 vec2) {
    float x = Math.abs(vec2.getX());
    float y = Math.abs(vec2.getY());
    if (x  > y) {
      if (vec2.getX() < 0) return LEFT;
      return RIGHT;
    } else {
      if (vec2.getY() < 0) return UP;
      return DOWN;
    }
  }
}
