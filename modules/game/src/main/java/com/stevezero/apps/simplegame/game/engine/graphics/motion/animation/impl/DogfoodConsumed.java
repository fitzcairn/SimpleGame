package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;

/**
 * Animation for the dogfood dish being consumed (flickering)
 */
public final class DogfoodConsumed extends Animation {
  // All frames are 100x 100h.
  private static final int HEIGHT_PX = 100;
  private static final int WIDTH_PX = 100;
  private static final int FRAMES_PER_ANIMATION_FRAME = 5;
  
  public DogfoodConsumed(Loader loader) {
    super(AssetManager.getDrawable(loader, "atlas_dogfood.png"), WIDTH_PX, HEIGHT_PX, 10, false);
    this.setSpeed(FRAMES_PER_ANIMATION_FRAME);
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    return (animationFrame % 2 > 0 ?
        spriteSheet.getSelection(5 * WIDTH_PX, 0, WIDTH_PX, HEIGHT_PX) :
        spriteSheet.getSelection(0, 0, WIDTH_PX, HEIGHT_PX));
  }
}
