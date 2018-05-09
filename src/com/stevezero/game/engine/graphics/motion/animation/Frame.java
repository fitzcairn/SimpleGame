package com.stevezero.game.engine.graphics.motion.animation;

import com.stevezero.game.assets.drawable.GameDrawable;

/**
 * Convenience class for creating a simple one-frame animation.
 * Intended to be overridden with game-specific frames.
 */
public class Frame extends Animation {

  // A frame must come with a sprite sheet.
  public Frame(GameDrawable spriteSheet) {
    super(spriteSheet);
  }

  /**
   * Override for more specific selection criteria.
   */
  protected GameDrawable getFrame(int animationFrame) {
    return spriteSheet;
  }
}
