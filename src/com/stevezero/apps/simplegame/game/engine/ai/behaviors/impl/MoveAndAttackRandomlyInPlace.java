package com.stevezero.apps.simplegame.game.engine.ai.behaviors.impl;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.enemy.Enemy;
import com.stevezero.game.engine.ai.behaviors.Behavior;

public class MoveAndAttackRandomlyInPlace implements Behavior {

  @Override
  public void applyBehavior(Engine engine, Enemy enemy) {
    enemy.setVX((float) (Math.random() * 3) - 1);
    enemy.setVY((float) (Math.random() * 3) - 1);
    enemy.attack(engine);
  }

}
