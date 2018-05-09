package com.stevezero.game.engine;

import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.engine.graphics.rendering.Rendered;
import com.stevezero.game.engine.graphics.rendering.services.RenderedManager;
import com.stevezero.game.geometry.Box2;
import com.stevezero.game.geometry.Point2;
import com.stevezero.game.geometry.Vector2;
import com.stevezero.game.geometry.impl.MutableVector2;


/**
 * Defines the rendering viewport for the com.stevezero.game.
 * 
 * TODO(stevemar): Needs to handle scaling.
 */
public final class Camera { 
  
  // Camera box, positioned in the global space.
  private Box2 cameraBox;
  
  // Boundry for the player before we need to move the camera.
  // Specified local to the camera space.
  private Box2 playerBox;
  
  // Builder for a camera.
  public static class Builder {
    private int cameraX;
    private int cameraY;
    private int cameraWidth;
    private int cameraHeight;
    private int playerTopBoundry;
    private int playerBottomBoundry;
    private int playerLeftBoundry;
    private int playerRightBoundry;

    public Builder setCameraX(int cameraX) {
      this.cameraX = cameraX;
      return this;
    }

    public Builder setCameraY(int cameraY) {
      this.cameraY = cameraY;
      return this;
    }

    public Builder setCameraWidth(int cameraWidth) {
      this.cameraWidth = cameraWidth;
      return this;
    }

    public Builder setCameraHeight(int cameraHeight) {
      this.cameraHeight = cameraHeight;
      return this;
    }

    /**
     * Set the top boundry for the player, relative to the camera coordinate space,
     */
    public Builder setPlayerTopBoundry(int playerTopBoundry) {
      this.playerTopBoundry = playerTopBoundry;
      return this;
    }

    public Builder setPlayerBottomBoundry(int playerBottomBoundry) {
      this.playerBottomBoundry = playerBottomBoundry;
      return this;
    }

    public Builder setPlayerLeftBoundry(int playerLeftBoundry) {
      this.playerLeftBoundry = playerLeftBoundry;
      return this;
    }

    public Builder setPlayerRightBoundry(int playerRightBoundry) {
      this.playerRightBoundry = playerRightBoundry;
      return this;
    }

    public Camera build() {
      assert(cameraWidth > 0 && cameraHeight > 0);
      assert(playerRightBoundry > playerLeftBoundry && playerBottomBoundry > playerTopBoundry);

      Box2 cameraBox = new Box2(cameraX, cameraY, cameraWidth, cameraHeight);
      Box2 playerBox = new Box2(playerLeftBoundry, playerTopBoundry,
          playerRightBoundry - playerLeftBoundry, playerBottomBoundry - playerTopBoundry);
      
      return new Camera(cameraBox, playerBox);
    }
  }
 
  private Camera(Box2 cameraBox, Box2 playerBox) {
    this.cameraBox = cameraBox;
    this.playerBox = playerBox;
  }

  public static Builder newBuilder() {
    return new Builder();
  }
  
  /**
   * Translate a point in global space to local screen space for rendering.
   */
  public Point2 toCamera(int x, int y) {
    return new Point2(x - cameraBox.getPoint().getX(), y - cameraBox.getPoint().getY());
  }

  /**
   * Translate a point in global space to local screen space for rendering.
   * Updates point via reference.
   */
  public Point2 toCamera(Point2 point) {
    point.set(point.getX() - cameraBox.getPoint().getX(),
        point.getY() - cameraBox.getPoint().getY());
    return point;
  }
  
  /**
   * Translate a point in camera space to global space.
   */
  public Point2 toGlobal(Point2 cameraPoint) {
    return new Point2(cameraBox.getPoint().getX() + cameraPoint.getX(),
        cameraBox.getPoint().getY() + cameraPoint.getY());
  }
  
  // Move the camera to keep the player within the playerBox.
  public void update(Engine engine) {
    Box2 player = engine.getCurrentPlayer().getBox();
    int vX = 0;
    int vY = 0;
    
    assert(player.getHeight() < cameraBox.getHeight() &&
        player.getWidth() < cameraBox.getWidth());

    // Translate the Player box to global space.
    Box2 globalPlayerBox = new Box2(
        toGlobal(playerBox.getPoint()), playerBox.getWidth(), playerBox.getHeight());
    
    // Calculate the intersection.
    Box2 intersection = player.getIntersection(globalPlayerBox);

    // If there's no intersection, we can't move the camera any further on one axis.
    // We may still need to move it on the other.
    if (intersection == null) {
      if (cameraBox.getRight() == engine.getCurrentMap().getWidth() || cameraBox.getX() == 0) {
        // Extend the camera box width and re-calculate intersection to catch Y-axis movement.
        globalPlayerBox.set(cameraBox.getRight() - cameraBox.getWidth(), globalPlayerBox.getY(),
            cameraBox.getWidth(), globalPlayerBox.getHeight());
        intersection = player.getIntersection(globalPlayerBox);
      }
      if (cameraBox.getBottom() == engine.getCurrentMap().getHeight() || cameraBox.getY() == 0) {
        // Extend the camera box height and re-calculate intersection to catch X-axis movement.
        //globalPlayerBox.setHeight(cameraBox.getHeight());
        globalPlayerBox.set(globalPlayerBox.getX(), cameraBox.getBottom() - cameraBox.getHeight(),
            globalPlayerBox.getWidth(), cameraBox.getHeight());
        intersection = player.getIntersection(globalPlayerBox);
      }
    }
    
    // Nothing to do here?  Return.
    if (intersection == null) {
      return;
    }
    
    // If the player is not fully enclosed in the player box, move camera (as much as we can).
    if (intersection.getWidth() < player.getWidth()) {
      if (player.getX() < globalPlayerBox.getX()) {
        // Move camera left.
        vX = -Math.min(player.getWidth() - intersection.getWidth(), cameraBox.getX());
      } else {
        // Move camera right.
        vX = Math.min(player.getWidth() - intersection.getWidth(),
            engine.getCurrentMap().getWidth() - cameraBox.getRight());
      }
    }
    if (intersection.getHeight() < player.getHeight()) {
      if (player.getY() < globalPlayerBox.getY()) {
        // Move camera up.
        vY = -Math.min(player.getHeight() - intersection.getHeight(), cameraBox.getY());
      } else {
        // Move camera down.
        vY = Math.min(player.getHeight() - intersection.getHeight(),
            engine.getCurrentMap().getHeight() - cameraBox.getBottom());
      }       
    }
    moveBy(vX, vY);
  }

  
  /**
   * Set the camera to a position around the player.
   */
  public Camera centerOnPlayerBox(Engine engine) {
    // Translate the Player box to global space.
    Box2 globalPlayerBox = new Box2(
        toGlobal(playerBox.getPoint()), playerBox.getWidth(), playerBox.getHeight());

    // Center the camera on the player box.
    MutableVector2 displacement =
        MutableVector2.of(
            engine.getCurrentPlayer().getBox().getCenter(), globalPlayerBox.getCenter());
    moveBy(displacement);

    // Don't allow camera to move off edge.
    displacement.zero();
    if (cameraBox.getX() < 0) {
      displacement.setX(-cameraBox.getX());
    } else if (cameraBox.getRight() > engine.getCurrentMap().getWidth()) {
      displacement.setX(engine.getCurrentMap().getWidth() - cameraBox.getRight());      
    }
    if (cameraBox.getY() < 0) {
      displacement.setY(-cameraBox.getY());
    } else if (cameraBox.getBottom() > engine.getCurrentMap().getHeight()) {
      displacement.setY(engine.getCurrentMap().getHeight() - cameraBox.getBottom());
    }
    moveBy(displacement);
    
    return this;
  }  
  
  /**
   * Just up and move the camera to a new point, then update it to be in a good position.
   */
  public Camera moveTo(Point2 point) {
    cameraBox.setPoint(point);
    return this;
  }
  
  public Camera moveBy(int vX, int vY) {
    cameraBox.getPoint().moveBy(vX, vY);
    return this;
  }
  
  public Camera moveBy(Vector2 displacement) {
    cameraBox.getPoint().moveBy(displacement);
    return this;
  }
  
  /**
   * Check to see if any part of a box is visible by the camera.
   * Works in global space.
   */
  public boolean canSee(Box2 box) {
    return cameraBox.intersects(box);
  }
  
  public boolean canSee(Point2 point) {
    return cameraBox.intersects(point);
  }
  
  
  /**
   * A fuzzy check to see if the box is visible by the camera, or has the potential to become
   * visible very soon.
   */
  public boolean canSeeFuzzy(Box2 box, Point2 viewExpansion) {
    return cameraBox.intersects(box, viewExpansion);
  }
  
  /**
   * Get the portion of a box that is in the camera's viewport.
   */
  public Box2 getVisible(Box2 box) {
    return cameraBox.getIntersection(box);
  }
  
  public int getWidth() {
    return cameraBox.getWidth();
  }
  
  public int getHeight() {
    return cameraBox.getHeight();
  }
  
  public int getGlobalX() {
    return cameraBox.getX();
  }

  public int getGlobalY() {
    return cameraBox.getY();
  }
  
  // For debug only.
  public Rendered renderDebugPlayerBox(Engine engine) {
    // TODO(stevemar): Replace this with a solution that doesn't require platform-specific
    // packages.
//    return new Rendered(
//        new AppDebugDrawable(playerBox), playerBox.getX(), playerBox.getY(), Layers.DEBUG);
    return RenderedManager.get(
        engine.getGame().getLoader().getDrawable("debug"), playerBox.getX(), playerBox.getY(),
        Layers.DEBUG);
  }
}
