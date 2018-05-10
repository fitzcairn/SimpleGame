package com.stevezero.apps.simplegame.game.engine.actor.projectile.impl;

import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.CheeseBlobExplode;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.CheeseBlobTravel;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.projectile.Projectile;
import com.stevezero.game.engine.physics.Direction;
import com.stevezero.game.geometry.Vector2;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.CheeseBlobExplode;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.CheeseBlobTravel;

public final class CheeseBlob extends Projectile {
  // Speed for this projectile.
  private static float SPEED = 3.0f;
  
  // Base power for this projectile.
  private static int POWER = 1;

  public CheeseBlob(Engine engine, int x, int y, Vector2 direction, Actor originator) {
    super(engine, x, y, direction, SPEED, POWER, originator);

    // Add sprite states.
    sprite.addAnimation(new CheeseBlobTravel(engine.getLoader(),
        direction.getX() < 0 ? Direction.LEFT : Direction.RIGHT));
    sprite.addAnimation(new CheeseBlobExplode(engine.getLoader()));
  }
}
