package com.stevezero.game.engine.actor.consumable;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.Role;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.engine.physics.Direction;

/**
 * A consumable object.  Can be collided with, pushed around, etc.
 */
public abstract class Consumable extends Actor {
  // Types of consumable.
  public enum Type {
    HEALTH, SHIELD, POWER, POINTS, GOAL;
  }

  // Unit mass -- very light.
  private final static float MASS = .0001f;
  
  // Consumed?
  private boolean isConsumed = false;
  
  // Engine reference
  protected final Engine engine;
  
  public Consumable(Engine engine, int x, int y) {
    super(MASS, x, y, Layers.FOREGROUND, Role.CONSUMABLE);
    this.engine = engine;
    
    // Set health to be infinite--only can be consumed, not destroyed.
    this.setDestroyableOverride(true);
  }

  
  //
  // To Override --------------------->
  //
  
  /**
   * What type of consumable this is.
   */
  abstract public Type getConsumableType();
  
  /**
   * Numerical bonus amount.
   */
  abstract public int getBonusAmount();
  
  /**
   * Stat to increment.
   */
  abstract public int getStat();
  
  
  //
  // New Events ---------------------->
  //
  
  /**
   * Override to react to consumption.
   */
  abstract public void onConsumed();
    
  
  //
  // Simulatable Overrides ----------->
  //
  
  @Override
  public void onCollision(Direction direction, Actor other) {
    // If the player grabbed us for the first time (i.e. we're not consumed), update!
    if (other.getRole() != Role.PLAYER || isConsumed) {
      return;
    }
    
    // Ok, we're consumed!
    isConsumed = true;
    onConsumed();
    
    // Update consumption stat.
    engine.getStatistics().increment(getStat());
  }
  
  @Override
  public boolean isCollidableWith(Actor other) {
    // Only care about the player and obstacles.
    if (other.getRole() == Role.PLAYER || other.getRole() == Role.OBSTACLE) {
      return true;
    }
    return false;
  }

  @Override
  public void update(Engine engine) {
    applyGravity();
    applyVelocity();
  }
}
