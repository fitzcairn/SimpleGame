package com.stevezero.game.geometry;

/**
 * Simple point in space.  No spacial units implied.
 */
public final class Point2 {
  protected int x;
  protected int y;

  public Point2(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Point2(Point2 other) {
    this.x = other.getX();
    this.y = other.getY();
  }
  
  public Point2() {
    this.x = 0;
    this.y = 0;    
  }
  
  public Point2 set(int x, int y) {
    this.x = x;
    this.y = y;
    return this;
  }
  
  public Point2 set(Point2 other) {
    this.x = other.getX();
    this.y = other.getY();
    return this;
  }
  
  public Point2 moveBy(int vX, int vY) {
    this.x += vX;
    this.y += vY;
    return this;
  }
  
  public Point2 moveBy(Vector2 displacement) {
    this.x += (int)displacement.getX();
    this.y += (int)displacement.getY();
    return this;
  }
  
  public Point2 addVector(Vector2 vector) {
    this.x += vector.getX();
    this.y += vector.getY();
    return this;
  }
  
  public Point2 subtractVector(Vector2 vector) {
    this.x -= vector.getX();
    this.y -= vector.getY();
    return this;
  }
  
  public int getX() {
    return x;
  }
  
  public Point2 setX(int x) {
    this.x = x;
    return this;
  }
  
  public int getY() {
    return y;
  }
  
  public Point2 setY(int y) {
    this.y = y;
    return this;
  }
  
  public boolean equals(Point2 other) {
    return x == other.getX() && y == other.getY();
  }

  public boolean equals(int x, int y) {
    return this.x == x && this.y == y;
  }
  
  // For more readable code.
  public static Point2 of(int x, int y) {
    return new Point2(x, y);
  }
} 
