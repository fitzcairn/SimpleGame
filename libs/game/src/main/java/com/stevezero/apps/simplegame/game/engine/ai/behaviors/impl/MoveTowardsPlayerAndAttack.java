package com.stevezero.apps.simplegame.game.engine.ai.behaviors.impl;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.enemy.Enemy;
import com.stevezero.game.engine.actor.player.Player;
import com.stevezero.game.engine.ai.behaviors.Behavior;
import com.stevezero.game.engine.physics.Direction;
import com.stevezero.game.geometry.impl.MutableVector2;

public class MoveTowardsPlayerAndAttack implements Behavior {

  @Override
  public void applyBehavior(Engine engine, Enemy enemy) {
    Player player = engine.getCurrentPlayer();
    
    // Get the vector from enemy to player.
    MutableVector2 enemyToPlayer =
        MutableVector2.of(player.getBox().getCenter(), enemy.getBox().getCenter());
    
    enemy.setFacingDirection(Direction.getClosestTo(enemyToPlayer));

    if (!enemy.canFly()) {
      enemyToPlayer.setY(0);
    }
    
    // Move in that direction and attack.
    enemy.applyVelocity(enemyToPlayer.normalize());
    enemy.attack(engine);
  }

}
