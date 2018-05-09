package com.stevezero.game.engine.actor.words;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.Role;
import com.stevezero.game.engine.graphics.Layers;

/**
 * Base class for floating text.
 */
public class Words extends Actor {
  private final String text;
  
  public Words(int x, int y, String text) {
    super(0, x, y, Layers.FOREGROUND, Role.TEXT);

    this.text = text;
    
    // Set health to be infinite--only can be consumed, not destroyed.
    this.setDestroyableOverride(true);
    
    // No collisions ever
    this.disableCollisions();   
  }

  public String getText() {
    return this.text;
  }
  
  @Override
  public void update(Engine engine) {
    applyVelocity();
  }
}
