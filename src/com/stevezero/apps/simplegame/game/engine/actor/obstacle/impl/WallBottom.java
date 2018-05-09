package com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl;

import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.engine.actor.obstacle.Obstacle;
import com.stevezero.game.engine.physics.Direction;
import com.stevezero.game.util.RandomGenerator;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.WallRest;

public final class WallBottom extends Obstacle {

  public WallBottom(Loader loader, int x, int y) {
    super(loader, x, y);
    
    sprite.setAnimationTo(sprite.addAnimation(
        new WallRest(loader, Direction.DOWN, RandomGenerator.nextBoolean())));
  }
}
