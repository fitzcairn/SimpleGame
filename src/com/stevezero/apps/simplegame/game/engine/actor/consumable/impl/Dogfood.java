package com.stevezero.apps.simplegame.game.engine.actor.consumable.impl;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.consumable.Consumable;
import com.stevezero.game.statistics.Stat;
import com.stevezero.apps.simplegame.game.engine.actor.words.impl.FloatingText;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.DogfoodConsumed;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.DogfoodRest;

/**
 * Consumable, but the goal of the game.
 */
public final class Dogfood extends Consumable {
  // Floating text offset.
  private final static int FLOATING_TEXT_OFFSET = -100;

  private final int consumedState;
  private final int soundId;
    
  public Dogfood(Engine engine, int x, int y) {
    super(engine, x, y);

    sprite.addAnimation(new DogfoodRest(engine.getLoader()));
    this.consumedState = sprite.addAnimation(new DogfoodConsumed(engine.getLoader()));

    // HACK: glue this to the floor.
    this.setMass(100000);
    
    soundId = engine.addSound("win.wav");
  }

  @Override
  public void onConsumed() {
    sprite.setAnimationTo(consumedState);
    
    // Create and add a new text simulatable explaining whaddup to the player.
    engine.addSimulatable(
        new FloatingText(engine, this.getX(),
            this.getY() + FLOATING_TEXT_OFFSET, getPlayerMessaging()));
  }
  
  @Override
  public Type getConsumableType() {
    return Type.GOAL;
  }
 
  // Woot!  Once we're done playing the animation, game over!
  @Override
  public void removeScreenElement() {
    engine.playSound(soundId);
    engine.levelComplete();
  }
  
  private String getPlayerMessaging() {
   return "DOGFOOD ACHIEVED! You WIN!";
  }

  @Override
  public int getBonusAmount() {
    return 0;
  }

  @Override
  public int getStat() {
    return Stat.DOGFOOD;
  }
}
