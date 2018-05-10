package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;
import com.stevezero.game.engine.physics.Direction;

/**
 * Animation state for a player standing.  Has no animation.
 */
public final class PlayerRest extends Animation {
  // All frames are 110x 100h.
  private static final int HEIGHT_PX = 100;
  private static final int WIDTH_PX = 110;

  private Direction direction;
  
  public PlayerRest(Loader loader, Direction direction) {
    super(AssetManager.getDrawable(loader, "atlas_thrillson.png"), WIDTH_PX, HEIGHT_PX, 1, true);
    this.direction = direction;   
  }

  private int getY() {
    return direction.getOrdinal() * HEIGHT_PX;
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    return spriteSheet.getSelection(0, getY(), WIDTH_PX, HEIGHT_PX);
  }
}
