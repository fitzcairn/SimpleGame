package com.stevezero.game.engine.actor.projectile;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Role;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.engine.physics.Direction;
import com.stevezero.game.geometry.Vector2;

/**
 * Encapsulates common information/methods for enemy actors.
 */
abstract public class Projectile extends Actor { 
  // Sprite states
  protected static int TRAVEL_STATE = 0;
  protected static int IMPACT_STATE = 1;
  
  // Universal projectile mass
  private final static float MASS = 10;

  // Collision sound.
  protected int collisionSoundId = -1;

  // The entity that generated this projectile.
  private final Actor orginator;
  
  private final Engine engine;

  // Damage
  private int power;

  
  //
  // Constructors -------------------->
  //  
  public Projectile(Engine engine, int x, int y, Vector2 direction, float speed,
      int power, Actor orginator) {
    super(MASS, x, y, Layers.FOREGROUND, Role.PROJECTILE);
    this.velocity.set(direction);
    this.velocity.scale(speed);
    this.orginator = orginator;
    this.engine = engine;
    this.power = power;
  }

  
  //
  // Simulatable Overrides ----------->
  //
  
  @Override
  public void update(Engine engine) {
    this.x += velocity.getX();
    this.y += velocity.getY();
    
    // If projectile is off-screen, delete projectile.
    if (!engine.getCamera().canSee(getBox())) {
      removeScreenElement();
    }
  }
  
  /**
   * Don't calculate collisions for friendly fire.
   */
  @Override
  public boolean isCollidableWith(Actor other) {
    // Already collided?
    if (!isCollidable()) {
      return false;
    }
    
    // If this is not from the player, and other is not the player, and the other is not
    // indestructible, ignore.
    if (!this.orginator.isPlayer() && !other.isPlayer() && other.isDestroyable()) {
      return false;
    }
    
    // Don't allow shots to collide with each other.
    if (other.getRole() == Role.PROJECTILE) {
      return false;
    }
    
    // Don't allow the player to shoot himself.
    if (orginator.isPlayer() && other.isPlayer()) {
      return false;
    }
    
    return true;
  }
  
  @Override
  public void onCollision(Direction direction, Actor other) {
    // Inform the projectile of the impact.
    switch(direction) {
      case UP:
        handleImpact(Direction.DOWN, other);
        break;
      case DOWN:
        handleImpact(Direction.UP, other);
        break;
      case LEFT:
        handleImpact(Direction.RIGHT, other);
        break;
      case RIGHT:
        handleImpact(Direction.LEFT, other);
        break;
      default:
        break;
    }

    // Update other with health.
    other.applyHealth(-power);
  }
  
  
  //
  // Get/Setters ---------------------> 
  //

  /**
   * @return the entity that originated this Projectile.
   */
  public Actor getOriginator() {
    return this.orginator;
  }

  /**
   * @return the power value for this Projectile.
   */
  public int getPower() {
    return power;
  }

  /**
   * @param power the new power value for this Projectile.
   * @return a reference to this Projectile.
   */
  public Projectile setPower(int power) {
    this.power = power;
    return this;
  }
  
  /**
   * @param power the amount of power to add to this Projectile.
   * @return a reference to this Projectile.
   */
  public Projectile addPower(int power) {
    this.power += power;
    return this;
  }
  
  
  //
  // Helper Methods ------------------>
  //
  
  /**
   * This projectile impacted "other"--handle.
   */
  private void handleImpact(Direction otherSide, Actor other) {
    // Ok, boom. Now switch this to an animation based on the site it hit.   
    sprite.setAnimationTo(IMPACT_STATE);

    // No longer can collide with anything.
    disableCollisions();
    
    // No longer moves.
    zeroVelocity();
    
    // Play sound, if any.
    if (collisionSoundId > 0) {
      engine.playSound(collisionSoundId);
    }
  }
}
