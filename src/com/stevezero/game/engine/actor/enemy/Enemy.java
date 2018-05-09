package com.stevezero.game.engine.actor.enemy;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.Role;
import com.stevezero.game.engine.actor.projectile.Projectile;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.geometry.Vector2;
import com.stevezero.game.statistics.Statistics;
import com.stevezero.game.util.Util;

/**
 * Encapsulates common information/methods for enemy actors.
 * 
 * TODO: clean up spriting!!!!
 */
abstract public class Enemy extends Actor {
  private static final int MAX_VX = 2;
  private static final int MAX_VY = 15;
  
  // Attack power.
  protected int power;
  
  // Default speed
  protected float speed = 0.1f;
  
  // Default attack speed is ~every second.
  protected int attackProb = 2;

  // How often to announce themselves.
  protected int moveSoundProb = 3;
  
  private final Statistics statistics;
  private final Engine engine;
  
  
  //
  // Constructors -------------------->
  //
  
  public Enemy(Engine engine, float mass, int x, int y) {
    super(mass, x, y, Layers.FOREGROUND, Role.ENEMY);
    this.statistics = engine.getStatistics();
    this.engine = engine;
  }

  
  //
  // To Override --------------------->
  //

  /**
   * Called when this enemy attacks.
   * Override to provide custom behavior (sounds, sprite changes, etc).
   */
  protected void onAttack(Engine engine) {
  }
  
  /**
   * Called when this enemy makes a velocity update.
   * Override to provide custom behavior (sounds, sprite changes, etc).
   */
  protected void onMove(Engine engine) {
  }

  /**
   * Called when this enemy is dead.
   * Override to provide custom behavior (sounds, sprite changes, etc).
   */
  protected void onEnemyDeath(Engine engine) {
  }
  
  /**
   * @return the ordinal for use when looking up sprites in the atlas.
   */
  abstract protected int getAtlasOrdinal();

  /**
   * @return the number of points this enemy is worth if destroyed.
   */
  abstract public int getPoints();
    
  /**
   * @return the stat ID to increment.
   */
  abstract public int getStat();
  
  /**
   * Must be override by subclasses to create a projectile.
   */
  protected Projectile createProjectile(Engine engine, Vector2 direction, int startX,
      int startY) {
    return null;
  }
  
  /**
   * Create a bullet for this enemy.
   */
  protected void createProjectile(Engine engine, Vector2 direction) {
    // The spawn location is the edge of the emitter * the direction unit vector.
    int startX = (int)(
        this.getBox().getCenterX() +
        (direction.getX() * (this.getBox().getWidth() / 2)));
    int startY = this.getBox().getY() + (this.getBox().getHeight() / 2); // HACK
    if (canShoot()) {
      Projectile projectile = createProjectile(engine, direction, startX, startY);
      if (projectile != null) {
        engine.addSimulatable(projectile);
      } else {
        // TODO: Throw a runtime exception, this should never happen.
      }
    }
  }

  //
  // Simulation Overrides ------------>
  //
    
  @Override
  public void update(Engine engine) {
    if (isDead()) {
      return;
    }
    
    if (!canFly()) {
      this.applyGravity();
    }

    // Add a velo cap to avoid instability.
    velocity.setY(Util.absCap(velocity.getY(), MAX_VY));
    velocity.setX(Util.absCap(velocity.getX(), MAX_VX));
    
    x += Math.round(velocity.getX());
    y += Math.round(velocity.getY());
  }
  
  /**
   * Set the death animation potentially on health change.
   */
  @Override
  protected void onDeath() {
    onEnemyDeath(engine);
    
    statistics.addScore(getPoints());
    statistics.increment(getStat());
    disableCollisions();
    this.zeroVelocity();   
  }

  
  //
  // Public API ----------------->
  //

  /**
   * Override to allow for shooting behavior.
   * @return whether this enemy can shoot or not.
   */
  public boolean canShoot() {
    return false;
  }
  
  /**
   * Override to allow for jump behavior.
   * @return whether this enemy can jump or not.
   */
  public boolean canJump() {
    return false;
  }
  
  /**
   * Override to allow for flying behavior.
   * @return whether this enemy can fly or not.
   */
  public boolean canFly() {
    return false;
  }

  /**
   * Move in direction vector.
   */
  public void applyVelocity(Vector2 velDelta) {
    velocity.addX(velDelta.getX() * speed);
    if (canFly()) {
      velocity.addX(velDelta.getY() * speed);
    }
    
    onMove(engine);
  }
  
  /**
   * Call this to attack something.
   * @param engine
   */
  public void attack(Engine engine) {
    if (canShoot()) {
      if (Math.random() * 100 < attackProb) {
        createProjectile(engine, getFacingDirection().getVector2());
        onAttack(engine);
      }
    }
  }
  
  public int getPower() {
    return power;
  }

  public void setPower(int power) {
    this.power = power;
  }
}
