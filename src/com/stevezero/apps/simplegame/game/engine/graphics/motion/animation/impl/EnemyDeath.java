package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;

/**
 * Animation state for an enemy death.
 */
public final class EnemyDeath extends Animation {
  // All frames are 100x 100h.
  private static final int HEIGHT_PX = 80;
  private static final int WIDTH_PX = 80;
  private static final int FRAMES_PER_ANIMATION_FRAME = 2;

  private int type;
  
  public EnemyDeath(Loader loader, int type) {
    super(AssetManager.getDrawable(loader, "atlas_cheese.png"), WIDTH_PX, HEIGHT_PX, 4, false);
    this.type = type;   
    this.setSpeed(FRAMES_PER_ANIMATION_FRAME);   
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    return spriteSheet.getSelection(
        type * WIDTH_PX, animationFrame * HEIGHT_PX, WIDTH_PX, HEIGHT_PX);
  }
}
