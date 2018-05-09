package com.stevezero.game.util;

import java.util.ArrayList;
import java.util.List;

import com.stevezero.game.geometry.Vector2;
import com.stevezero.game.geometry.impl.ImmutableVector2;
import com.stevezero.game.util.FakeEnum;

/**
 * Set of Directions.
 */
public final class Direction extends FakeEnum {
  private static final List<Direction> values = new ArrayList<Direction>();
  private static int numValues = 0;
  
  private final Vector2 vec2;
  
  private Direction(String name, int atlasOffset, Vector2 vec2) {
    super(name, atlasOffset, numValues);
    this.vec2 = vec2;
    numValues++;
    values.add(this);
  }
  
  public Vector2 getVector2() {
    return vec2;
  }
  
  public static Direction getClosestTo(Vector2 vec2) {
    float x = Math.abs(vec2.getX());
    float y = Math.abs(vec2.getX());
    if (x  > y) {
      if (vec2.getX() < 0) return LEFT;
      return RIGHT;
    } else {
      if (vec2.getY() < 0) return UP;
      return DOWN;
    }
  }
  
  public static final Direction LEFT = new Direction("LEFT", 0, ImmutableVector2.of(-1, 0));
  public static final Direction RIGHT = new Direction("RIGHT", 1, ImmutableVector2.of(1, 0));
  public static final Direction UP = new Direction("UP", 2, ImmutableVector2.of(-1, 0));
  public static final Direction DOWN = new Direction("DOWN", 3, ImmutableVector2.of(1, 0));
}
 
