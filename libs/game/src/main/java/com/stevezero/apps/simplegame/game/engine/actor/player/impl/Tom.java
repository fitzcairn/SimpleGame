package com.stevezero.apps.simplegame.game.engine.actor.player.impl;

import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.controls.events.ControlEvent;
import com.stevezero.game.controls.events.ControlHandler;
import com.stevezero.game.controls.events.EventType;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Role;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.consumable.Consumable;
import com.stevezero.game.engine.actor.enemy.Enemy;
import com.stevezero.game.engine.actor.player.Player;
import com.stevezero.game.engine.actor.projectile.Projectile;
import com.stevezero.game.engine.graphics.motion.MotionGraph;
import com.stevezero.game.engine.physics.Direction;
import com.stevezero.game.geometry.Vector2;
import com.stevezero.game.util.Util;
import com.stevezero.apps.simplegame.game.engine.actor.projectile.impl.Burrito;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.PlayerDamage;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.PlayerDuck;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.PlayerRest;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.PlayerShoot;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.PlayerWalk;

/**
 * The playable Tom class.
 */
public final class Tom extends Player {
  // Damage immunity timer, currently 2 sec.
  private static final int DAMAGE_IMMUNITY_MILLIS = 2000;
  
  // Damage constants.
  private static final int PLAYER_STOMP_DAMAGE = 10;

  // Health constants.
  private static final int PLAYER_START_HEALTH = 6;
  private static final int PLAYER_MAX_HEALTH = 10;
  
  // Velocity constants.
  private static final int JUMP_VY = -35;
  private static final int MOVE_VX = 5;
  
  // Unit mass
  private final static float MASS = 50f;
  
  // Sprite states for this actor.
  private int stateStandRight;
  private int stateStandLeft;
  private int stateMoveRight;
  private int stateMoveLeft;
  private int stateJumpRight;
  private int stateJumpLeft;
  private int stateShootRight;
  private int stateShootLeft;
  private int stateDuckRight;
  private int stateDuckLeft;
  private int stateDamageRight;
  private int stateDamageLeft;
  
  // Common state transitions.
  private MotionGraph shootMoveLeft;
  private MotionGraph shootMoveRight;
  private MotionGraph shootStandLeft;
  private MotionGraph shootStandRight;
  private MotionGraph damageStandRight;
  private MotionGraph damageStandLeft;
  
  // Sounds
  private int jumpSoundId;
  private int throwSoundId;
  private int consumeSoundId;
  private int hitSoundId;
  
  // Actions the player can execute.  Note that a player can be jumping and shooting at once.
  private boolean isWalking = false;
  private boolean isJumping = false;
  private boolean isDucking = false;
  private boolean isCreatingProjectile = false;

  // Movement smoothers
  private boolean leftKeyDown = false;
  private boolean rightKeyDown = false;

  private long lastDamageTime = -1;
  private int powerBonus = 0;
  private Engine engine;

  public Tom() {
    super(MASS, DAMAGE_IMMUNITY_MILLIS);
    
    // Set health.
    this.setMaxHealth(PLAYER_MAX_HEALTH);
    this.setHealth(PLAYER_START_HEALTH);
  }

  //
  // Controls ------------------------>
  //
  
  @Override
  public void setControls(ControlHandler controls) {
    controls.registerEvent(EventType.GAME_DUCK_START, new ControlEvent() {
      @Override
      public void onEvent() {
        duck();
      }
    });
    controls.registerEvent(EventType.GAME_DUCK_STOP, new ControlEvent() {
      @Override
      public void onEvent() {
        stopDuck();
      }
    });
    controls.registerEvent(EventType.GAME_JUMP_START, new ControlEvent() {
      @Override
      public void onEvent() {
        jump();
      }
    });
    controls.registerEvent(EventType.GAME_MOVE_LEFT_START, new ControlEvent() {
      @Override
      public void onEvent() {
        moveLeft();
      }
    });
    controls.registerEvent(EventType.GAME_MOVE_LEFT_STOP, new ControlEvent() {
      @Override
      public void onEvent() {
        stopMoveLeft();
      }
    });  
    controls.registerEvent(EventType.GAME_MOVE_RIGHT_START, new ControlEvent() {
      @Override
      public void onEvent() {
        moveRight();
      }
    });
    controls.registerEvent(EventType.GAME_MOVE_RIGHT_STOP, new ControlEvent() {
      @Override
      public void onEvent() {
        stopMoveRight();
      }
    });
    controls.registerEvent(EventType.GAME_SHOOT_START, new ControlEvent() {
      @Override
      public void onEvent() {
        shoot();
      }
    });
  }
  
  
  //
  // Handlers for Player Events ------>
  //
  
  /**
   * Override to initialize Tom.  Allows for lazy initialization.
   * This callback is guaranteed to occur before the first update tick is processed, or
   * Player.update will throw an exception.
   */
  @Override
  protected void onInitPlayer(Engine engine) {
    this.engine = engine;
    
    // Load the sprite states.
    Loader loader = engine.getLoader();
    stateStandRight = sprite.addAnimation(new PlayerRest(loader, Direction.RIGHT));
    stateStandLeft = sprite.addAnimation(new PlayerRest(loader, Direction.LEFT));
    stateMoveRight = sprite.addAnimation(new PlayerWalk(loader, Direction.RIGHT));
    stateMoveLeft = sprite.addAnimation(new PlayerWalk(loader, Direction.LEFT));
    stateJumpRight = sprite.addAnimation(new PlayerRest(loader, Direction.RIGHT));
    stateJumpLeft = sprite.addAnimation(new PlayerRest(loader, Direction.LEFT));
    stateDuckRight = sprite.addAnimation(new PlayerDuck(loader, Direction.RIGHT));      
    stateDuckLeft = sprite.addAnimation(new PlayerDuck(loader, Direction.LEFT));
    stateShootRight = sprite.addAnimation(new PlayerShoot(loader, Direction.RIGHT));      
    stateShootLeft = sprite.addAnimation(new PlayerShoot(loader, Direction.LEFT));      
    stateDamageRight = sprite.addAnimation(new PlayerDamage(loader, Direction.RIGHT));      
    stateDamageLeft = sprite.addAnimation(new PlayerDamage(loader, Direction.LEFT));      
    
    // Create motion graphs.
    shootMoveLeft = MotionGraph.newBuilder()
        .addNext(stateShootLeft)
        .addNext(stateMoveLeft)
        .build();
    shootMoveRight = MotionGraph.newBuilder()
        .addNext(stateShootRight)
        .addNext(stateMoveRight)
        .build();
    shootStandLeft = MotionGraph.newBuilder()
        .addNext(stateShootLeft)
        .addNext(stateStandLeft)
        .build();
    shootStandRight = MotionGraph.newBuilder()
        .addNext(stateShootRight)
        .addNext(stateStandRight)
        .build();
    damageStandRight = MotionGraph.newBuilder()
        .addNext(stateDamageLeft)
        .addNext(stateStandLeft)
        .build();
    damageStandLeft = MotionGraph.newBuilder()
        .addNext(stateDamageRight)
        .addNext(stateStandRight)
        .build();
    
    // Load the sounds.
    jumpSoundId = engine.addSound("jump.wav");
    throwSoundId = engine.addSound("burrito.wav");
    consumeSoundId = engine.addSound("consume.wav");
    hitSoundId = engine.addSound("hit.wav");

    // Starting sprite state.
    setFacingDirection(Direction.LEFT);
    sprite.setAnimationTo(stateStandLeft);
  }

  /**
   * Override to reset Tom when Player.resetPlayer is called.
   */
  @Override
  protected void onResetPlayer(Engine engine) {
    this.setHealth(PLAYER_START_HEALTH);
    lastDamageTime = -1;
    powerBonus = 0;
  }

  /**
   * Override to update velocity on Player movements.
   */
  @Override
  protected void onUpdateBeforeVelocityApplied(Engine engine) {
    // X Velocity -->
    if (isWalking && facingDirection == Direction.LEFT) {
      velocity.addX(-MOVE_VX);
    }
    if (isWalking && facingDirection == Direction.RIGHT) {
      velocity.addX(MOVE_VX);
    }

    // Add a velo cap to avoid instability.
    velocity.setX(Util.absCap(velocity.getX(), MOVE_VX));   

    // Y Velocity -->
    applyGravity();
    
    // Add a velo cap to avoid instability.
    velocity.setY(Util.absCap(velocity.getY(), TERMINAL_VELOCITY));
  }
  

  /**
   * Override to handle shooting.
   */
  @Override
  protected void onPlayerUpdate(Engine engine) {
    // If Tom shot something, handle.
    // This is done here rather than in shoot() to ensure that all accesses to the object queue
    // happen on the same thread, avoiding synchronization requirements (performance bottleneck).
    if (isCreatingProjectile) {
      Vector2 direction = getFacingDirection().getVector2();
      // The spawn location is the center of the emitter + the direction unit vector.
      int startX = (int)(
          this.getBox().getCenterX() + direction.getX() * this.getBox().getWidth() / 2);
      int startY = this.getBox().getY(); // HACK
      
      Projectile projectile = new Burrito(engine, startX, startY, direction, this);
      projectile.addPower(powerBonus);
      engine.addSimulatable(projectile);
      
      isCreatingProjectile = false;
    }
  }

  /**
   * Override to handle Tom consuming Consumables.
   */
  @Override
  protected void onConsume(Consumable consumable) {
    switch(consumable.getConsumableType()) {
      case SHIELD:
      case HEALTH:
        applyHealth(consumable.getBonusAmount());
        break;
      case POINTS:
        //this.engine.getStatistics().addScore(consumable.getBonusAmount());
        break;
      case POWER:
        this.powerBonus += consumable.getBonusAmount();
        break;
      default:
        break;
    }
    
    // Play sound!
    engine.playSound(consumeSoundId);
  }
   
  @Override
  public void onCollision(Direction direction, Actor other) {
    // Handle consumables.
    if (other.getRole() == Role.CONSUMABLE) {
      onConsume((Consumable)other);
      return;
    }
    
    // If we hit something, change state maybe.
    switch(direction) {
      case DOWN:
        if (isJumping && isWalking) {
          switch(getFacingDirection()) {
            case LEFT:
              sprite.setAnimationTo(stateMoveLeft);
            break;
            case RIGHT:
            default:
              sprite.setAnimationTo(stateMoveRight);
              break;          
          }
        }
        isJumping = false;
        // If its a cheese, deal damage.
        if (other.getRole() == Role.ENEMY) {
          doDamage((Enemy)other, PLAYER_STOMP_DAMAGE);
        }
        break;
      case UP:
      case LEFT:
      case RIGHT:
        // If its a cheese, take damage.
        if (other.getRole() == Role.ENEMY) {
          takeDamage((Enemy)other);
        }
        break;
      default:
        break;
    }
  }
  
  @Override
  protected void onDeath() {
    // TODO: Switch to death animation.
  }

  @Override
  protected void onHealthChange(int health) {
    if (health < 0) {
      // Taking damage.  Check against damage timer.
      long currTime = engine.getSystemManager().getCurrentTimeMillis();
      if (currTime - lastDamageTime < DAMAGE_IMMUNITY_MILLIS) {
        return;
      }
      lastDamageTime = currTime;

      // Depending on which way we're facing, execute a state change loop.
      if (facingDirection.getVector2().getX() < 0) {
        sprite.setMotionGraph(damageStandRight);
      } else {
        sprite.setMotionGraph(damageStandLeft);
      }
      
      // Clear movement.
      clearMovementStates();
      
      // Play hit sound.
      engine.playSound(hitSoundId);
    }
    setHealth(Math.max(getHealth() + health, 0));
  }
  
  
  //
  // Movement ------------------------>
  //
  
  private void shoot() {
    // Only allow a shot if the shooting animation is done.
    // This adds a bit of a drag on fire rate.
    if (sprite.getCurrentAnimationId() == stateShootLeft || sprite.getCurrentAnimationId() == stateShootRight) {
      return;
    }
    
    isCreatingProjectile = true;

    // If moving, execute motion graph to keep moving.
    switch(facingDirection) {
      case LEFT:
        if (isWalking) {
          sprite.setMotionGraph(shootMoveLeft);
        } else {
          sprite.setMotionGraph(shootStandLeft);
        }
      break;
      case RIGHT:
      default:
        if (isWalking) {
          sprite.setMotionGraph(shootMoveRight);
        } else {
          sprite.setMotionGraph(shootStandRight);
        }
        break;          
    }
    
    // Play the throw sound!
    engine.playSound(throwSoundId);
  }
    
  private void moveRight() {
    // No new movement allowed if taking damage.
    if (inDamageState()) {
      clearMovementStates();
      return;
    }

    rightKeyDown = true;
    facingDirection = Direction.RIGHT;
    
    // Don't allow movement if ducking
    if (isDucking) {
      return;
    }
    isWalking = true;
    sprite.setAnimationTo(stateMoveRight);    
  }

  private void moveLeft() {
    // No new movement allowed if taking damage.
    if (inDamageState()) {
      clearMovementStates();
      return;
    }

    leftKeyDown = true;
    facingDirection = Direction.LEFT;

    // Don't allow movement if ducking
    if (isDucking) {
      return;
    }
    isWalking = true;
    sprite.setAnimationTo(stateMoveLeft);
  }

  private void jump() {
    // No new movement allowed if taking damage.
    if (inDamageState()) {
      clearMovementStates();
      return;
    }

    // Only allow jump state if there is no current jump.
    if (!isJumping) {
      isJumping = true;
      setVY(JUMP_VY);
      engine.playSound(jumpSoundId);
      switch(facingDirection) {
        case LEFT:
          sprite.setAnimationTo(stateJumpLeft);
        break;
        case RIGHT:
        default:
          sprite.setAnimationTo(stateJumpRight);
          break;          
      }
    }
  }

  private void duck() {
    // No new movement allowed if taking damage.
    if (inDamageState()) {
      return;
    }

    if (isJumping) {
      return;
    }
    isDucking = true;
    isWalking = false;
    switch(facingDirection) {
      case LEFT:
        sprite.setAnimationTo(stateDuckLeft);
      break;
      case RIGHT:
      default:
        sprite.setAnimationTo(stateDuckRight);
        break;          
    }
    setVX(0);
  }

  private void stopDuck() {
    isDucking = false;
    switch(facingDirection) {
      case LEFT:
        sprite.setAnimationTo(stateStandLeft);
      break;
      case RIGHT:
      default:
        sprite.setAnimationTo(stateStandRight);
        break;          
    }
    setVX(0);
  }

  private void stopMoveLeft() {
    leftKeyDown = false;

    if (rightKeyDown) {
      moveRight();
      return;
    }
    
    isWalking = false;
    stopDuck();
  }

  private void stopMoveRight() {
    rightKeyDown = false;

    if (leftKeyDown) {
      moveLeft();
      return;
    }
    
    isWalking = false;
    stopDuck();
  }
  
  
  //
  // Helpers ------------------------->
  //
  
  // Clear movement states.
  private void clearMovementStates() {
    isWalking = false;
    isJumping = false;
    isDucking = false;
    leftKeyDown = false;
    rightKeyDown = false;
  }
  
  // Whether or not we're in a damage animation.
  private boolean inDamageState() {
    int state = sprite.getCurrentAnimationId();
    return (state == stateDamageRight || state == stateDamageLeft);
  }
}
