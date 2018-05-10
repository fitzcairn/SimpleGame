package com.stevezero.apps.simplegame.game.engine.actor.projectile.impl;

import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.BurritoExplode;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.BurritoTravel;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.projectile.Projectile;
import com.stevezero.game.geometry.Vector2;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.BurritoExplode;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.BurritoTravel;

public final class Burrito extends Projectile {
  // Speed for this projectile.
  private static float SPEED = 8.0f;

  // Base power for this projectile.
  private static int POWER = 1;


  public Burrito(Engine engine, int x, int y, Vector2 direction, Actor originator) {
    super(engine, x, y, direction, SPEED, POWER, originator);

    // Add sprite states.
    sprite.addAnimation(new BurritoTravel(engine.getLoader()));
    sprite.addAnimation(new BurritoExplode(engine.getLoader()));
    
    // Sound for collision.
    collisionSoundId = engine.addSound("splat.wav");
  }
}
