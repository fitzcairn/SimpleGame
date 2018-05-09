package com.stevezero.game.geometry;

/**
 * Base class for all shapes that can intersect.
 */
public abstract class Shape {
  abstract public boolean intersects(Shape other);
  
  abstract public Vector2 getCollisionNormal(Shape other);
  
  abstract public Vector2 getPenetrationDepth(Shape other);
}
