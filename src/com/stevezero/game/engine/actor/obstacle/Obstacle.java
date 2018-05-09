package com.stevezero.game.engine.actor.obstacle;

import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Role;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.graphics.Layers;

/**
 * An obstacle.  Can have velocities, be deformable, interact with the player, etc.
 */
public abstract class Obstacle extends Actor {

  // Unit mass -- currently infinite.
  private final static float MASS = 0f;

  public Obstacle(Loader loader, int x, int y) {
    super(MASS, x, y, Layers.OBSTACLE, Role.OBSTACLE);
  }

  /**
   * Don't calculate obstacle/obstacle collisions.
   */
  @Override
  public boolean isCollidableWith(Actor other) {
    if (other.getRole() == Role.OBSTACLE) {
      return false;
    }
    return true;
  }

  @Override
  public void update(Engine engine) {
    // If Obstacle is dead, remove.
    if (isDead()) {
      removeScreenElement();
    }    
  }
}
