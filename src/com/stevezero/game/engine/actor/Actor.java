package com.stevezero.game.engine.actor;

import com.stevezero.game.Values;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.ai.Agent;
import com.stevezero.game.engine.ai.processor.Processor;
import com.stevezero.game.engine.graphics.ScreenElement;
import com.stevezero.game.engine.physics.Direction;
import com.stevezero.game.geometry.Shape;
import com.stevezero.game.geometry.Vector2;
import com.stevezero.game.geometry.impl.MutableVector2;
import com.stevezero.game.util.Util;


/**
 * Abstract class defining the behavior of an actor on the screen (as a ScreenElement) that can have
 * physics simulation and AI behaviors (as an Agent) applied.
 */
public abstract class Actor extends ScreenElement implements Agent { 
  // Terminal velocity for all objects.
  protected static final int TERMINAL_VELOCITY = 35;
  
  // Default coefficient of restitution--essentially, non-elastic.
  // TODO: REMOVE
  private final static float RESTITUTION = 0.001f;
  
  // Mass
  private float mass;
  private float inverseMass;
  
  // Coefficient of restitution
  private final float restitution;
  
  // Role of simulatable in the engine.
  private final Role role;
  
  // Max health of this simulatable element.
  private int maxHealth;
  
  // Health of this simulatable; default is 1.
  private int health = 1;
  
  // Allow overrides of basic role mutability properties.
  private boolean isDestroyableOverridden = false;
  private boolean isDestroyable = false;

  // Whether or not this object can take part in collisions
  private boolean isCollidable = true;
  
  // Velocity, in the global coordinate space.
  protected final MutableVector2 velocity = new MutableVector2();

  // Direction this Simulatable is facing.
  protected Direction facingDirection = Direction.RIGHT;

  
  //
  // Constructors
  //
  
  public Actor(float mass, int x, int y, int z, Role role) {
    super(x, y, z);
    this.mass = mass;
    this.inverseMass = Util.invert(mass);
    this.restitution = RESTITUTION;
    this.role = role;
  }
    
  public Actor(float inverseMass, int x, int y, int z, float restitution, Role role) {
    super(x, y, z);
    this.inverseMass = inverseMass;
    this.mass = Util.invert(inverseMass);
    this.restitution = restitution;
    this.role = role;
  }
  
  
  //
  // AI Overrides -------------------->
  //
  
  /**
   * By default, provide AI simulation for on-screen actors only.
   * Override to provide more detailed AI application criteria.
   */
  @Override
  public boolean isAiReady(Engine engine) {
    return isVisible(engine);
  }

  /**
   * By default, no AI behavior is applied to all Actors.
   * Override to allow for AI behavior to be applied.
   */
  @Override
  public void accept(Processor behavior, Engine engine) {
  }
  
  
  //
  // Simulation Update --------------->
  //
  
  /**
   * Update the current state of the simulation for this object.
   * 
   * @param engine instance of the game engine.
   */
  abstract public void update(Engine engine);
  
  
  //
  // Role ---------------------------->
  //
  
  /**
   * Get the role of this Simulatable.
   */
  public Role getRole() {
    return role;
  }
  
  /**
   * Convenience method.
   */
  public boolean isPlayer() {
    return role == Role.PLAYER;
  }
  
  
  //
  // Health -------------------------->
  //
  
  /**
   * Get object health.
   */ 
  public int getHealth() {
    return this.health;
  }

  /**
   * Set object health directly without firing the HealthChange event.
   */ 
  public Actor setHealth(int health) {
    this.health = this.maxHealth > 0 ? Math.min(health, this.maxHealth) : health;
    return this;
  }
    
  /**
   * Modify object health by signed amount.
   */ 
  public Actor applyHealth(int health) {
    if (!this.isDestroyable()) return this;
    this.health += health;
    this.health = Math.max(
        this.maxHealth > 0 ? Math.min(this.health, this.maxHealth) : this.health, 0);
    if (isHealthMutable()) onHealthChange(health);
    if (isDead()) onDeath();
    return this;
  }
  
  /**
   * Get object max health.
   */
  public int getMaxHealth() {
    return maxHealth;
  }

  /**
   * Set object max health.
   */
  public Actor setMaxHealth(int maxHealth) {
    this.maxHealth = maxHealth;
    return this;
  }

  // Event Handlers
  
  /**
   * Called when health changes for this object.
   * Override this to react to health changes.
   */
  protected void onHealthChange(int health) {
    return;
  }

  /**
   * Called with the Simulatable dies.  Override with any custom logic.
   */
  protected void onDeath() {
    return;
  }
  
  // Property Checks
  
  /**
   * Override to add custom logic on when health should be modified.
   */
  protected boolean isHealthMutable() {
    return isDestroyable();
  }

  
  //
  // Mutability ---------------------->
  //
  
  /**
   * Override object mutability.
   * @param override is true if object is indestructible.
   */
  public Actor setDestroyableOverride(boolean override) {
    this.isDestroyableOverridden = true;
    this.isDestroyable = override;
    return this;
  }

  // Property Checks
  
  /**
   * Get object destroyability.
   */ 
  public boolean isDestroyable() {
    return this.isDestroyableOverridden ? this.isDestroyable : !role.isIndestructible();
  }
  
  /**
   * Check if object dead.
   */
  public boolean isDead() {
    return isDestroyable() && health <= 0;
  }

  
  //
  // Collisions ---------------------->
  //

  /**
   * Disable this simulatable from being considered in collisions.
   * @return 
   */
  public Actor disableCollisions() {
    this.isCollidable = false;
    return this;
  }
  
  /**
   * Get the Shape for collision physics.
   */
  public Shape getCollisionShape() {
    return this.getBox();
  }

  // Event Handlers
  
  /**
   * Called when a collision with another Simulatable has occurred.
   * Override to do something on collision.
   */
  public void onCollision(Direction direction, Actor other) {
    // For debugging.
    if (Values.DEBUG_RENDER_WIREFRAMES) {
      highlight();
      other.highlight();
    }    
  }
  
  // Property Checks
  
  /**
   * Whether or not this simulatable can collide with anything.
   */
  public boolean isCollidable() {
    return isCollidable;
  }

  /**
   * Whether or not a collision should be calculated for this Simulatable.
   * Override to offer an opinion on this.
   */
  public boolean isCollidableWith(Actor other) {
    return isCollidable;
  }
  

  //
  // Physics ------------------------->
  //
  
  /**
   * Apply gravity to this Simulatable.
   */
  public Actor applyGravity() {
    velocity.setY(velocity.getY() + Values.GRAVITY);
    return this;
  }
  
  /**
   * Apply any stored velocity to this Simulatable.
   */
  public Actor applyVelocity() {
    this.x += velocity.getX();
    this.y += velocity.getY();
    return this;
  }
  
  public float getInverseMass() {
    return inverseMass;
  }

  public Actor setInverseMass(float inverseMass) {
    this.mass = Util.invert(inverseMass);
    this.inverseMass = inverseMass;
    return this;
  }  
  
  public float getMass() {
    return this.mass;
  }
  
  public Actor setMass(float mass) {
    this.mass = mass;
    this.inverseMass = Util.invert(mass);
    return this;
  }  
  
  public float getRestitution() {
    return restitution;
  }
  
  public Vector2 getVelocity() {
    return velocity;
  }
  
  public Direction getFacingDirection() {
    return facingDirection;
  }
  
  public Actor setFacingDirection(Direction facing) {
    this.facingDirection = facing;
    return this;
  }
  
  public Actor setVX(float vX) {
    velocity.setX(vX);
    return this;
  }
  
  public Actor setVY(float vY) {
    velocity.setY(vY);
    return this;
  }
  
  public Actor zeroVelocity() {
    velocity.zero();
    return this;
  }
  
  public Actor setVelocity(Vector2 velocity) {
    this.velocity.setX(velocity.getX());
    this.velocity.setY(velocity.getY());
    return this;
  }
}