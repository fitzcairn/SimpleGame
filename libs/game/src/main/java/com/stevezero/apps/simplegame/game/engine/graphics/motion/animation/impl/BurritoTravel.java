package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;

/**
 * Animation state for a burrito traveling.  Has 8 animation frames in two rows of 4.
 */
public final class BurritoTravel extends Animation {
  // All frames are 40x40.
  private static final int HEIGHT_PX = 40;
  private static final int WIDTH_PX = 40;
  private static final int FRAMES_PER_ANIMATION_FRAME = 5;

  public BurritoTravel(Loader loader) {
    super(AssetManager.getDrawable(loader, "atlas_burrito.png"), WIDTH_PX, HEIGHT_PX, 8, true);
    this.setSpeed(FRAMES_PER_ANIMATION_FRAME);   
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    return spriteSheet.getSelection((animationFrame % 4) * WIDTH_PX,
        ((animationFrame / 4) + 1) * HEIGHT_PX,
        WIDTH_PX, HEIGHT_PX);
  }
}
