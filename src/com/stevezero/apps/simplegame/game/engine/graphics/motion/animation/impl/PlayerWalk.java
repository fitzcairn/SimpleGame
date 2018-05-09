package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;
import com.stevezero.game.engine.physics.Direction;

/**
 * Animation state for a player walking.  Has 3 animation frames.
 */
public final class PlayerWalk extends Animation {
  // All frames are 110x 100h.
  private static final int HEIGHT_PX = 100;
  private static final int WIDTH_PX = 110;
  private static final int FRAMES_PER_ANIMATION_FRAME = 5;

  private Direction direction;
  
  public PlayerWalk(Loader loader, Direction direction) {
    super(AssetManager.getDrawable(loader, "atlas_thrillson.png"), WIDTH_PX, HEIGHT_PX, 3, true);
    this.direction = direction;   
    this.setSpeed(FRAMES_PER_ANIMATION_FRAME);   
  }

  private int getY() {
    return direction.getOrdinal() * HEIGHT_PX;
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    // | -- stand  -- | -- stride  -- | -- pull -- |
    return spriteSheet.getSelection(animationFrame * WIDTH_PX, getY(), WIDTH_PX, HEIGHT_PX);
  }
}
