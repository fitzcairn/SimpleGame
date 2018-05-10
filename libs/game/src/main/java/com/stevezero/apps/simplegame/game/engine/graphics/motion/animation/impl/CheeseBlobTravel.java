package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;
import com.stevezero.game.engine.physics.Direction;

public final class CheeseBlobTravel extends Animation {
  // All frames are 40x40.
  private static final int HEIGHT_PX = 30;
  private static final int WIDTH_PX = 75;
  private static final int FRAMES_PER_ANIMATION_FRAME = 5;
  private final Direction direction;
  
  public CheeseBlobTravel(Loader loader, Direction direction) {
    super(AssetManager.getDrawable(loader, "atlas_cheeseblob.png"), WIDTH_PX, HEIGHT_PX, 3, true);
    this.setSpeed(FRAMES_PER_ANIMATION_FRAME);
    this.direction = direction;
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    return spriteSheet.getSelection(
        direction.getOrdinal() * WIDTH_PX, animationFrame * HEIGHT_PX, WIDTH_PX, HEIGHT_PX);
  }
}
