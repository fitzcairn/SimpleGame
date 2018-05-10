package com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl;

import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.actor.obstacle.Obstacle;
import com.stevezero.game.engine.graphics.motion.animation.Frame;

public final class CheeseBlock extends Obstacle {

  public CheeseBlock(Loader loader, int x, int y) {
    super(loader, x, y);
    
    // Starting sprite state.
    sprite.addAnimation(new Frame(AssetManager.getDrawable(loader, "cheese.png")));
  }
}
