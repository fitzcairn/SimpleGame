package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.engine.graphics.motion.animation.Animation;

public final class ConsumableConsumed extends Animation {
  public ConsumableConsumed(GameDrawable spriteSheet) {
    super(spriteSheet, spriteSheet.getWidth(), spriteSheet.getHeight(), 5, false);
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    return this.spriteSheet;
  }  
}
