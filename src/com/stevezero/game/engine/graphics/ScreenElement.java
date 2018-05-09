package com.stevezero.game.engine.graphics;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.engine.graphics.rendering.Renderable;
import com.stevezero.game.engine.graphics.rendering.Rendered;
import com.stevezero.game.engine.graphics.rendering.services.RenderedManager;
import com.stevezero.game.engine.graphics.sprite.Sprite;
import com.stevezero.game.engine.physics.Collisions;
import com.stevezero.game.geometry.Box2;
import com.stevezero.game.geometry.Point2;
import com.stevezero.game.screens.Screen;
import com.stevezero.game.util.IdGenerator;

/**
 * An element in the game world that has a graphical representation.
 */
public abstract class ScreenElement implements RemovableScreenElement, Renderable { 
  // All coordinates are in global (i.e. game) space.
  protected int x;
  protected int y;
  protected int z;
    
  // Status for this element.
  private boolean deleted;
  
  // Point for this object.  Declared here to re-use memory.
  protected final Point2 position;
  
  // An ID for this object.
  private final int id;
  
  // Bounding box for this element.  Declared here to re-use memory.
  protected final Box2 boundingBox;
  
  // Sprite representing this Element.
  protected final Sprite sprite;
   
  // Turn on/off debug wireframe highlight mode.
  private boolean debugHighlight = false;
  
  public ScreenElement(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.deleted = false;
    this.position = new Point2(x, y);
    this.boundingBox = new Box2(this.position);
    this.sprite = new Sprite(this);
    
    // Generate an ID for this object.
    this.id = IdGenerator.next();
  }

  // Mark this element for deletion.
  @Override
  public void removeScreenElement() {
    deleted = true;
  }
    
  /**
   * Whether or not this element is visible on screen.
   */
  public boolean isVisible(Screen screen) {
    return !deleted && screen.getCamera().canSee(getBox());
  }

  /**
   * Whether or not this element is visible on the screen, or potentially could be soon.
   */
  public boolean isVisibleFuzzy(Screen screen) {
    return !isDeleted() && screen.getCamera().canSeeFuzzy(getBox(), Collisions.VIEW_PAD);
  }
    
  /**
   * If visible, translate into camera space and return.
   * @return null if not visible, Rendered in camera space otherwise.
   */
  @Override
  public void render(Screen screen) {
    Rendered frame = null;
    GameDrawable rendered = sprite.getNextFrame();
    if (rendered != null && !isDeleted() && isVisibleFuzzy(screen)) {
      frame = RenderedManager.get(rendered, screen.getCamera().toCamera(position), z);
      frame.highlight(debugHighlight);
    }
    unHighlight();
 
    if (frame != null) {
      screen.addToRenderedQueue(frame);
    }
  }
   
  /**
   * Helper method to make an array out of one or more Rendered.
   */
  protected Rendered[] toArray(Rendered... rendereds) {
    return rendereds;
  }

  /**
   * Whether or not this element is marked for deletion.
   */
  public boolean isDeleted() {
    return deleted;
  }

  /**
   * Get the bounding box for this element.
   */
  public Box2 getBox() {
    position.set(getX(), getY());
    // Update and return the bounding box for this element.
    boundingBox.setWidth(sprite.getWidth());
    boundingBox.setHeight(sprite.getHeight());
    boundingBox.setPoint(position);
    return boundingBox;
  }
  
  public Point2 getPosition() {
    position.set(getX(), getY());
    return position;
  }

  public void setPosition(Point2 position) {
    x = position.getX();
    y = position.getY();
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
  
  public int getId() {
    return id;
  }
  
  public int getWidth() {
    return sprite.getWidth();
  }
  
  public int getHeight() {
    return sprite.getHeight();
  }
  
  /**
   * Debug mode only.
   */
  public void highlight() {
    this.debugHighlight = true;
  }  

  /**
   * Debug mode only.
   */
  public void unHighlight() {
    this.debugHighlight = false;
  }  
}
