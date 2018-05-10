package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;

/**
 * Animation state for a burrito exploding.  Has 3 animation frames.
 */
public final class BurritoExplode extends Animation {
  // All frames are 40x40.
  private static final int HEIGHT_PX = 40;
  private static final int WIDTH_PX = 40;
  private static final int FRAMES_PER_ANIMATION_FRAME = 10;

  public BurritoExplode(Loader loader) {
    super(AssetManager.getDrawable(loader, "atlas_burrito.png"), WIDTH_PX, HEIGHT_PX, 3, false);
    this.setSpeed(FRAMES_PER_ANIMATION_FRAME);   
  }
    
  @Override
  protected GameDrawable getFrame(int animationFrame) {
    // | -- state1  -- | -- state2  -- | -- state3 -- |
    return spriteSheet.getSelection(animationFrame * WIDTH_PX, 0, WIDTH_PX, HEIGHT_PX);
  }
}
