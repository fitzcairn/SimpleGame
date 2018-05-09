package com.stevezero.game.geometry.impl;

import com.stevezero.game.geometry.Point2;
import com.stevezero.game.geometry.Vector2;

/**
 * A 2D Vector.  Vector2s are assumed to start at the origin. 
 */
public final class MutableVector2 extends Vector2 {
  private static final float NOISE = 0.01f;

  public MutableVector2() {
    this.x = 0;
    this.y = 0;
  }

  /**
   * @return a mutable Vector2 of X and Y.
   */
  public static MutableVector2 of(int x, int y) {
    MutableVector2 vec = new MutableVector2();
    return vec.set(x, y);
  }
  
  /**
   * @return a mutable copy of another Vector2.
   */
  public static MutableVector2 of(Vector2 other) {
    MutableVector2 vec = new MutableVector2();
    return vec.set(other);
  }
  
  /**
   * @return a mutable vector from two Point2 objects.
   */
  public static MutableVector2 of(Point2 a, Point2 b) {
    MutableVector2 vec = new MutableVector2();
    return vec.set(a, b);
  }
  
  // Mutators

  public MutableVector2 setX(float x) {
    this.x = x;
    return this;
  }

  public MutableVector2 addX(float x) {
    this.x += x;
    return this;
  }

  public MutableVector2 setY(float y) {
    this.y = y;
    return this;
  }

  public MutableVector2 addY(float y) {
    this.y += y;
    return this;
  }

  /**
   * Set this vector to copy the other.
   */
  public MutableVector2 set(Vector2 other) {
    this.x = other.getX();
    this.y = other.getY();
    return this;
  }

  /**
   * Set this vector's X and Y.
   */
  public MutableVector2 set(float x, float y) {
    this.x = x;
    this.y = y;
    return this;
  }
  
  /**
   * Set this vector from two Point2s.
   */
  public MutableVector2 set(Point2 a, Point2 b) {
    this.x = a.getX() - b.getX();
    this.y = a.getY() - b.getY();
    return this;
  }

  /**
   * Zero out this vector.
   */
  public MutableVector2 zero() {
    this.x = 0;
    this.y = 0;
    return this;
  }

  /**
   * Place the vector in 0, 1.
   */
  public MutableVector2 abs() {
    this.x = Math.abs(this.x);
    this.y = Math.abs(this.y);
    return this;
  }
  
  /**
   * Normalize this Vector2 and return it.
   */
  public MutableVector2 normalize() {
    float magnitude = getMagnitude();
    this.x = this.x / magnitude;
    this.y = this.y / magnitude;
    return this;
  }
  
  /**
   * Zero the vector if the vector is fuzzily zero.
   */
  public Vector2 zeroNoise() {
    if (Math.abs(x) < NOISE) {
      x = 0;
    }
    if (Math.abs(y) < NOISE) {
      y = 0;
    }
    return this;
  }
  
  /**
   * Scale this Vector2.
   */
  public MutableVector2 scale(float scale) {
    this.x *= scale;
    this.y *= scale;
    return this;
  }
  
  /**
   * Replace this Vector2 with the vector representing this - other.
   */
  public MutableVector2 subtract(Vector2 other) {
    this.x = this.x - other.getX();
    this.y = this.y - other.getY();
    return this;
  }
  
  /**
   * Get new Vector2 representing this + other.
   */
  public MutableVector2 add(Vector2 other) {
    this.x += other.getX();
    this.y += other.getY();
    return this;
  }
}
