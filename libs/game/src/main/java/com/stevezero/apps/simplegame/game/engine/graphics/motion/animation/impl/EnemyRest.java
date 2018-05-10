package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;

/**
 * Animation for an enemy.  Has no motion at the moment.
 */
public final class EnemyRest extends Animation {
  // All frames are 100x 100h.
  private static final int HEIGHT_PX = 80;
  private static final int WIDTH_PX = 80;

  private int type;
  
  public EnemyRest(Loader loader, int type) {
    super(AssetManager.getDrawable(loader, "atlas_cheese.png"), WIDTH_PX, HEIGHT_PX, 1, true);
    this.type = type;   
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    return spriteSheet.getSelection(type * WIDTH_PX, 0, WIDTH_PX, HEIGHT_PX);
  }
}
