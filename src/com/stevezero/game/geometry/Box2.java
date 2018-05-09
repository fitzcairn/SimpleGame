package com.stevezero.game.geometry;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.stevezero.game.geometry.impl.MutableVector2;

/**
 * Simple 2D box.  No spatial units are implied.
 */
public final class Box2 extends Shape { 
  private final Point2 ZERO_POINT = Point2.of(0, 0);
  
  protected int width;
  protected int height;
  protected Point2 point;
  
  public Box2(Point2 point, int width, int height) {
    this.point = point;
    this.width = width;
    this.height = height;
  }

  public Box2(int x, int y, int width, int height) {
    this.point = new Point2(x, y);
    this.width = width;
    this.height = height;
  }
  
  public Box2(int width, int height) {
    this.point = new Point2();
    this.width = width;
    this.height = height;
  }
  
  public Box2(Point2 point) {
    this.point = point;
    this.width = 0;
    this.height = 0;
  }

  public Box2() {
    this.point = new Point2();
    this.width = 0;
    this.height = 0;
  }

  public int getX() {
    return this.point.getX();
  }
  
  public int getY() {
    return this.point.getY();
  }

  public int getRight() {
    return getX() + width;
  }
  
  public int getBottom() {
    return getY() + height;
  }
  
  public Point2 getCenter() {
    return new Point2(getCenterX(), getCenterY());
  }
  
  public int getCenterX() {
    return this.point.getX() + width / 2;
  }
  
  public int getCenterY() {
    return this.point.getY() + height / 2;
  }
  
  public boolean intersects(Point2 point) {
    return (point.getX() >= this.point.getX() && point.getX() <= (this.point.getX() + width) &&
        point.getY() >= this.point.getY() && point.getY() <= (this.point.getY() + height));
  }
  
  @Override
  public boolean intersects(Shape other) {
    if (other instanceof Box2) {
      return intersects((Box2) other);
    }
    return false;
  }
  
  public boolean intersects(Box2 other) {
    return intersects(other, ZERO_POINT);
  }
  
  public boolean intersects(Box2 other, Point2 viewExpansion) {
    return
        (abs(getCenterX() - other.getCenterX()) * 2 <
            (width + other.getWidth() + viewExpansion.getX())) &&
        (abs(getCenterY() - other.getCenterY()) * 2 <
            (height + other.getHeight() + viewExpansion.getY()));
  }
  
  public Box2 getIntersection(Box2 other) {
    if (!intersects(other)) {
      return null;
    }
    
    // This box: x1,y1 (upper left), x2,y2 (lower right)
    int x1 = point.getX();
    int y1 = point.getY();
    int x2 = x1 + width;
    int y2 = y1 + height;
    
    // Other box: x3,y3 (upper left), x4,y4 (lower right)
    int x3 = other.getPoint().getX();
    int y3 = other.getPoint().getY();
    int x4 = x3 + other.getWidth();
    int y4 = y3 + other.getHeight();
    
    // Intersection: x5,y5 (upper left), x6,y6 (lower right)
    int x5 = max(x1, x3);
    int x6 = min(x2, x4);
    int y5 = max(y1, y3);
    int y6 = min(y2, y4);
    
    return new Box2(x5, y5, x6 - x5, y6 - y5);
  }    

  @Override
  public Vector2 getCollisionNormal(Shape other) {
    assert(intersects(other));
    // TODO(stevemar): eventually add other types of shapes.
    if (other instanceof Box2) {
      Box2 otherBox = (Box2) other;
      Box2 intersectionBox = getIntersection(otherBox);

      // Ensure the normal vector points towards other.
      int normX = getCenterX() > otherBox.getCenterX() ? -1 : 1;
      int normY = getCenterY() > otherBox.getCenterY() ? -1 : 1;

      // Choose the closest face.
      if (intersectionBox.getWidth() <= intersectionBox.getHeight()) {
        return MutableVector2.of(normX, 0);
      }
      return MutableVector2.of(0, normY);
    }
    return new MutableVector2();
  }
  
  @Override
  public Vector2 getPenetrationDepth(Shape other) {
    // TODO(stevemar): eventually add other types of shapes.
    if (other instanceof Box2) {
      Box2 otherBox = (Box2) other;
      Box2 intersectionBox = getIntersection(otherBox);
      return intersectionBox == null ? new MutableVector2() :
        MutableVector2.of(intersectionBox.getWidth(), intersectionBox.getHeight());
    }
    return new MutableVector2();
  }
  
  public int getWidth() {
    return width;
  }
  
  public Box2 setWidth(int width) {
    this.width = width;
    return this;
  }
  
  public int getHeight() {
    return height;
  }
  
  public Box2 setHeight(int height) {
    this.height = height;
    return this;
  }
  
  public Point2 getPoint() {
    return point;
  }
  
  public Box2 setPoint(Point2 point) {
    this.point.set(point);
    return this;
  }
  
  public Box2 set(int x, int y, int width, int height) {
    this.point.set(x, y);
    this.width = width;
    this.height = height;
    return this;
  }
}
