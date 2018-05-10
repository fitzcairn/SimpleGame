package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;

public final class CheeseBlobExplode extends Animation {
  public CheeseBlobExplode(Loader loader) {
    super(AssetManager.getDrawable(loader, "cheesesplat.png"), 32, 32, 1, false);
    this.setSpeed(5);   
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    return this.spriteSheet;
  }  
}
