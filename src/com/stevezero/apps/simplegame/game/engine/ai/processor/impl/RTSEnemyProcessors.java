package com.stevezero.apps.simplegame.game.engine.ai.processor.impl;

import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Jarlsberg;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Kenafa;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Longhorn;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Manchego;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.ai.behaviors.Behavior;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Jarlsberg;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Kenafa;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Longhorn;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Manchego;
import com.stevezero.apps.simplegame.game.engine.ai.behaviors.impl.MoveTowardsPlayerAndAttack;


/**
 * Processors specific to SimpleGame.
 * 
 * For now, AI is stupid, stupid simple. :)
 */
public final class RTSEnemyProcessors implements RTSEnemyProcessor {
  // Behaviors used by GMS Crusader enemies.
  private final Behavior moveAndAttack;
  
  public RTSEnemyProcessors() {
    this.moveAndAttack = new MoveTowardsPlayerAndAttack();
  }

  @Override
  public void processAi(Jarlsberg agent, Engine engine) {
    moveAndAttack.applyBehavior(engine, agent);
  }

  @Override
  public void processAi(Kenafa agent, Engine engine) {
    moveAndAttack.applyBehavior(engine, agent);
  }

  @Override
  public void processAi(Longhorn agent, Engine engine) {
    moveAndAttack.applyBehavior(engine, agent);
  }

  @Override
  public void processAi(Manchego agent, Engine engine) {
    moveAndAttack.applyBehavior(engine, agent);
  }
}
