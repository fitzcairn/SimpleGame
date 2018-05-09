package com.stevezero.game.engine.ai.behaviors;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.enemy.Enemy;

/**
 * Base interface for all AI behaviors.
 */
public interface Behavior {
  /**
   * Apply this behavior.
   */
  public void applyBehavior(Engine engine, Enemy enemy);
}
