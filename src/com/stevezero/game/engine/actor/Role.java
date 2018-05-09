package com.stevezero.game.engine.actor;

/**
 * Specific Actor roles.
 * 
 * If adding a new type of Actor, please be sure to assign it a role and basic properties here.
 */
public enum Role {
  PLAYER (false),
  ENEMY (false),
  OBSTACLE (true),
  PROJECTILE (true),
  CONSUMABLE (false),
  TEXT (true);
  
  private final boolean isIndestructible;

  private Role(boolean isIndestructible) {
    this.isIndestructible = isIndestructible;
  }

  public boolean isIndestructible() {
    return isIndestructible;
  }
}
