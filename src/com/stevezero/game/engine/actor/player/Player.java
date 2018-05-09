package com.stevezero.game.engine.actor.player;

import com.stevezero.game.Values;
import com.stevezero.game.controls.events.ControlHandler;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Role;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.consumable.Consumable;
import com.stevezero.game.engine.actor.enemy.Enemy;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.geometry.Point2;


/**
 * The player class.
 */
public abstract class Player extends Actor {
  // Unit mass
  protected final float mass;

  // Damage immunity timer, i.e. how long after damage the player shall be immune.
  protected final int damageImmuneMillis;
  
  // Initialized?
  private boolean isInitialized = false;
  
  
  //
  // Constructors -------------------->
  //
  
  public Player(float mass, int damageImmuneMillis) {
    super(mass, 0, 0, Layers.FOREGROUND, Role.PLAYER);
    this.mass = mass;
    this.damageImmuneMillis = damageImmuneMillis;
    
    if (Values.DEBUG_GOD_MODE) {
      this.setDestroyableOverride(false);
    }
  }

  
  //
  // Player Event Handlers ----------->
  //
  
  /**
   * Override to update velocity before it is applied in onUpdate() to move the character.
   */
  protected abstract void onUpdateBeforeVelocityApplied(Engine engine);

 /**
  * Override to do something else on update besides move and check for death.  Example: shooting.
  */
  protected abstract void onPlayerUpdate(Engine engine);
  
  /**
   * Override to do something when consuming consumables.
   */
  protected abstract void onConsume(Consumable consumable);
  
  /**
   * Override to do something to this object on Player death.
   */
  protected abstract void onDeath();
  
  /**
   * Override to reset the player object.
   */
  protected abstract void onResetPlayer(Engine engine);
  
  /**
   * Override to initialize the player object.
   */
  protected abstract void onInitPlayer(Engine engine);
  
  
  //
  // Controls ------------------------>
  //
  
  /**
   * Override to set up controls for a player.
   * @param controlHandler the control handler for the game.
   */
  public void setControls(ControlHandler controlHandler) {
  }
  
  
  //
  // Simulation Overrides ------------>
  //
  
  @Override
  public void update(Engine engine) {
    assert(!Float.isNaN(velocity.getY()));
    assert(isInitialized);
    
    // Update positions with additions due to player input/position.
    onUpdateBeforeVelocityApplied(engine);

    // Apply velocity to modify position.
    applyVelocity();
    
    // Are we dead?
    if (isDead()) {
      engine.playerDied();
    }
    
    // Any other specific updates?
    onPlayerUpdate(engine);
  }
  
  
  //
  // Mutators ------------------------>
  //

  /**
   * Initialize the player.  Must be called before first update tick.
   */
  public void initPlayer(Engine engine) {
    onInitPlayer(engine);
    resetPlayer(engine);
    isInitialized = true;
  }
  
  /**
   * Reset the player starting position.
   */
  public void resetPlayer(Engine engine) {
    setStartPoint(engine.getCurrentMap().getPlayerStart());
    onResetPlayer(engine);
  }
    
  /**
   * Handle taking damage from enemies.
   */
  public void takeDamage(Enemy enemy) {
    if (isDestroyable()) applyHealth(-enemy.getPower());
  }
  
  /**
   * Handle doing damage to enemies from stomping.
   */
  public void doDamage(Enemy enemy, int damage) {
    enemy.applyHealth(-damage);
  }
  
  
  //
  // Helpers ------------------------->
  //
  
  private void setStartPoint(Point2 start) {
    x = start.getX();
    y = start.getY();
  }
}
