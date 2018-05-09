package com.stevezero.apps.simplegame.game.engine.actor.enemy.impl;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.enemy.Enemy;
import com.stevezero.game.engine.actor.projectile.Projectile;
import com.stevezero.game.engine.ai.processor.Processor;
import com.stevezero.game.geometry.Vector2;
import com.stevezero.game.statistics.Stat;
import com.stevezero.apps.simplegame.game.engine.actor.projectile.impl.CheeseBlob;
import com.stevezero.apps.simplegame.game.engine.ai.processor.impl.RTSEnemyProcessor;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.EnemyDeath;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.EnemyRest;

public final class Kenafa extends Enemy {
  private final static float MASS = 50f;
  private final static int POINTS = 5;
  private final static int POWER = 1;
  private final static int MAX_HEALTH = 10;
  
  // Movement states
  private final int stateMove;
  private final int stateDeath;
  
  private final int shootSoundId;
  private final int deathSoundId;

  public Kenafa(Engine engine, int x, int y) {
    super(engine, MASS, x, y);
    this.setHealth(MAX_HEALTH);
    
    // Load the sprite states.
    stateMove = sprite.addAnimation(new EnemyRest(engine.getLoader(), getAtlasOrdinal()));
    stateDeath = sprite.addAnimation(new EnemyDeath(engine.getLoader(), getAtlasOrdinal()));

    // Starting sprite state.
    sprite.setAnimationTo(stateMove);

    // For now, all enemies have the same death sound.
    deathSoundId = engine.addSound("cheesedeath.wav");
    shootSoundId = engine.addSound("cheeseshoot.wav");
  }
  
  @Override
  public int getAtlasOrdinal() {
    return 1;
  }

  @Override
  public boolean canShoot() {
    return true;
  }
  
  @Override
  protected Projectile createProjectile(Engine engine, Vector2 direction, int startX, int startY) {
    // Spawn the bullet.
    // TODO: Spawn from a pool!!!
    return new CheeseBlob(engine, startX, startY, direction, this);
  }
  
  @Override
  public int getPoints() {
    return POINTS;
  }
  
  @Override
  public int getPower() {
    return POWER;
  }

  @Override
  public int getStat() {
    return Stat.KENAFA;
  }

  @Override
  public void accept(Processor behavior, Engine engine) {
    // Cast is safe; there is only one type of processor in GMS Crusader.
    ((RTSEnemyProcessor) behavior).processAi(this, engine);
  }

  @Override
  protected void onAttack(Engine engine) {
    engine.playSound(shootSoundId);
  }

  @Override
  protected void onEnemyDeath(Engine engine) {
    sprite.setAnimationTo(stateDeath);
    engine.playSound(deathSoundId);
  }
}
