package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;

/**
 * Animation for the dogfood dish.
 */
public final class DogfoodRest extends Animation {
  // All frames are 100x 100h.
  private static final int HEIGHT_PX = 100;
  private static final int WIDTH_PX = 100;
  private static final int FRAMES_PER_ANIMATION_FRAME = 10;
  
  public DogfoodRest(Loader loader) {
    super(AssetManager.getDrawable(loader, "atlas_dogfood.png"), WIDTH_PX, HEIGHT_PX, 10, true);
    this.setSpeed(FRAMES_PER_ANIMATION_FRAME);
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    // 10 animation frames spread across 5 actual sprite frames.
    // Want 0 -> 4, then 4 -> 0.
    if (animationFrame > 4) {
      animationFrame = 9 - animationFrame;
    }
    return spriteSheet.getSelection(animationFrame * WIDTH_PX, 0, WIDTH_PX, HEIGHT_PX);
  }
}
