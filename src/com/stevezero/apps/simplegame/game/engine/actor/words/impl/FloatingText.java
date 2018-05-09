package com.stevezero.apps.simplegame.game.engine.actor.words.impl;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.words.Words;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.TextFloat;

/**
 * Floating text!  Woo!
 */
public final class FloatingText extends Words {
  public static final float FLOAT_VELOCITY = -0.3f;
  
  public FloatingText(Engine engine, int x, int y, String text) {
    super(x, y, text);
    // Floats up into the either.
    this.setVY(FLOAT_VELOCITY);
    
    // Create the sprite, get ready for rendering.
    this.sprite.addAnimation(new TextFloat(engine, text));
  }
}
