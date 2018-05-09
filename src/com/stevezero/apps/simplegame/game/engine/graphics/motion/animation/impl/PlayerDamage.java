package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;
import com.stevezero.game.engine.physics.Direction;

/**
 * Animation state for a player taking damage.  Has 8 animation frames that alternate between
 * 2 actual images.
 */
public final class PlayerDamage extends Animation {
  // All frames are 110x 100h.
  private static final int HEIGHT_PX = 100;
  private static final int WIDTH_PX = 110;
  private static final int FRAMES_PER_ANIMATION_FRAME = 15;

  private Direction direction;
  
  public PlayerDamage(Loader loader, Direction direction) {
    super(AssetManager.getDrawable(loader, "atlas_thrillson.png"), WIDTH_PX, HEIGHT_PX, 8, false);
    this.direction = direction;   
    this.setSpeed(FRAMES_PER_ANIMATION_FRAME);   
  }

  private int getY() {
    return direction.getOrdinal() * HEIGHT_PX;
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    // | -- stand  -- | -- stride  -- | -- pull -- |
    return (animationFrame % 2 > 0 ?
        spriteSheet.getSelection(WIDTH_PX, getY(), WIDTH_PX, HEIGHT_PX) :
        spriteSheet.getSelection(WIDTH_PX, 5 * HEIGHT_PX, WIDTH_PX, HEIGHT_PX));
  }
}
