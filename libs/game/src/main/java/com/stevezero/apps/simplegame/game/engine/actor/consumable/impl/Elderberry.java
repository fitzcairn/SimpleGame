package com.stevezero.apps.simplegame.game.engine.actor.consumable.impl;

import com.stevezero.apps.simplegame.game.engine.actor.words.impl.FloatingText;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.ConsumableConsumed;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.ConsumableRest;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.consumable.Consumable;
import com.stevezero.game.statistics.Stat;
import com.stevezero.apps.simplegame.game.engine.actor.words.impl.FloatingText;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.ConsumableConsumed;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.ConsumableRest;

public final class Elderberry extends Consumable {
  // Floating text offset.
  private final static int FLOATING_TEXT_OFFSET = -100;

  private final int consumedState;

  public Elderberry(Engine engine, int x, int y) {
    super(engine, x, y);
    
    sprite.addAnimation(new ConsumableRest(engine.getLoader(), ConsumableRest.ELDERBERRY));
    this.consumedState =
        sprite.addAnimation(
            new ConsumableConsumed(AssetManager.getDrawable(engine.getLoader(), "splat.png")));
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
    return Type.POINTS;
  }

  private String getPlayerMessaging() {
    return "Elderberry! +50 Points!";
  }

  @Override
  public int getBonusAmount() {
    return 50;
  }

  @Override
  public int getStat() {
    return Stat.ELDERBERRY;
  }
}
