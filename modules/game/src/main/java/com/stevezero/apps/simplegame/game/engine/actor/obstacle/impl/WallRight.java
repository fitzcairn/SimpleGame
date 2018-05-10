package com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl;

import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.engine.actor.obstacle.Obstacle;
import com.stevezero.game.engine.physics.Direction;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.WallRest;

public final class WallRight extends Obstacle {

  public WallRight(Loader loader, int x, int y) {
    super(loader, x, y);
    
    sprite.setAnimationTo(sprite.addAnimation(new WallRest(loader, Direction.RIGHT)));
  }
}
