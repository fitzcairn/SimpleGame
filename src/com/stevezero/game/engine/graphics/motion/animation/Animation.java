package com.stevezero.game.engine.graphics.motion.animation;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.geometry.Box2;

/**
 * An animation for a given sprite, which may consist of a static image or a series of images.
 */
public abstract class Animation {
  private int gameTicks = 0;
  private int animationFrame = 0;
  private boolean repeated;
  private int numAnimationFrames;
  private int speed = 1;

  protected final Box2 selectionBox = new Box2();
  protected GameDrawable spriteSheet;
  
  //
  // Constructors -------------------->
  //
  
  public Animation() {
    this.numAnimationFrames = 0;
    this.repeated = true;
  }
  
  public Animation(GameDrawable spriteSheet) {
    this.spriteSheet = spriteSheet;
    this.numAnimationFrames = 0;
    this.repeated = true;
    this.selectionBox.setWidth(spriteSheet.getWidth());
    this.selectionBox.setHeight(spriteSheet.getHeight());
  }
  
  // Use this constructor to set up a self-loop.
  protected Animation(GameDrawable spriteSheet, int width, int height, int numAnimationFrames,
      boolean repeated) {
    this.spriteSheet = spriteSheet;
    this.numAnimationFrames = numAnimationFrames;
    this.selectionBox.setWidth(width);
    this.selectionBox.setHeight(height);
    this.repeated = repeated;
  }
  
  
  //
  // Protected Helpers --------------->
  //
  
  /**
   * Subclasses must implement sprite frame selection for animation.
   */
  protected abstract GameDrawable getFrame(int animationFrame);
  
  protected Animation resetSelectionBox(int width, int height) {
    this.selectionBox.setWidth(width);
    this.selectionBox.setHeight(height);
    return this;
  }
  
  protected Animation resetSpriteSheet(GameDrawable spriteSheet) {
    this.spriteSheet = spriteSheet;
    return this;
  }
  
  protected Animation resetNumAnimationFrames(int numAnimationFrames) {
    this.repeated = false;
    this.numAnimationFrames = numAnimationFrames;
    return this;
  }
  
  
  //
  // API ----------------------------->
  //

  public int getWidth() {
    return selectionBox.getWidth();
  }
  
  public int getHeight() {
    return selectionBox.getHeight();
  }
    
  public float getSpeed() {
    return speed;
  }
  
  public boolean isRepeated() {
    return repeated;
  }

  public void reset() {
    gameTicks = 0;
    animationFrame = 0;
  }
  
  /**
   * Change the speed of this state.
   * Speed is defined as the number of game ticks before the next animation frame is rendered.
   */
  public Animation setSpeed(int speed) {
    this.speed = speed;
    return this;
  }

  /**
   * Get the next frame, or null if this state is no longer visible.
   */
  public GameDrawable getNextFrame() {
    // Time for next frame?
    gameTicks++;
    if (gameTicks > speed) {
      // Advance frame?
      if (animationFrame < numAnimationFrames) {
        animationFrame++;
      }
      gameTicks = 0;
    }

    // If we're out of frames, we either repeat or we're done.
    if (animationFrame >= numAnimationFrames) {
      if (!repeated) {
        return null;
      }
      animationFrame = 0;
    }

    return getFrame(animationFrame);
  }
}

