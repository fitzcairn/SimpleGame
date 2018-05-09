package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;
import com.stevezero.game.engine.physics.Direction;

/**
 * Animation state for a wall.  Has no animation.
 */
public final class WallRest extends Animation {
  private static final String FILE = "atlas_border_filled.png";

  private static final int HEIGHT_PX = 50;
  private static final int WIDTH_PX = 50;
  private static final int NONE = -1;

  private final int leftRight;
  private final int topBottom;
  private final int fill;
  private final boolean which;
  
  // Corner
  public WallRest(Loader loader, Direction leftRight, Direction topBottom, int fill) {
    super(AssetManager.getDrawable(loader, FILE), WIDTH_PX, HEIGHT_PX, 1, true);
    this.leftRight = leftRight.getOrdinal();
    this.topBottom = topBottom.getOrdinal();
    this.fill = fill;
    this.which = false;
  }

  // Top or bottom, which can be randomly selected from 2 tiles each.
  public WallRest(Loader loader, Direction topBottom, boolean which) {
    super(AssetManager.getDrawable(loader, FILE), 2 * WIDTH_PX, HEIGHT_PX, 1, true);
    this.fill = NONE;
    this.leftRight = NONE;
    this.topBottom = topBottom.getOrdinal();
    this.which = which;
  }

  // Left/Right
  public WallRest(Loader loader, Direction leftRight) {
    super(AssetManager.getDrawable(loader, FILE), WIDTH_PX, HEIGHT_PX, 1, true);
    this.fill = NONE;
    this.leftRight = leftRight.getOrdinal();
    this.topBottom = NONE;
    this.which = false;
  }
  
  @Override
  protected GameDrawable getFrame(int animationFrame) {
    // Top or bottom
    if (leftRight == NONE && topBottom != NONE) {
      if (topBottom == Direction.UP.getOrdinal()) {
        return spriteSheet.getSelection(which ? 0 : WIDTH_PX, HEIGHT_PX, WIDTH_PX, HEIGHT_PX);        
      } else {
        return spriteSheet.getSelection(which ? 0 : WIDTH_PX, 0, WIDTH_PX, HEIGHT_PX);
      }
    }
    
    // Vertical piece
    if (leftRight != NONE && topBottom == NONE) {
      if (leftRight == Direction.LEFT.getOrdinal()) {
        return spriteSheet.getSelection(0, 2 * HEIGHT_PX, WIDTH_PX, HEIGHT_PX);
      } else {
        return spriteSheet.getSelection(WIDTH_PX, 2 * HEIGHT_PX, WIDTH_PX, HEIGHT_PX);
      }
    }
    
    // Corner
    if (leftRight == Direction.LEFT.getOrdinal() && topBottom == Direction.UP.getOrdinal()) {
      return spriteSheet.getSelection(
          (fill == Direction.UP.getOrdinal() ? 3 : 2) * WIDTH_PX, 2 * HEIGHT_PX, WIDTH_PX, HEIGHT_PX);
    } else if (leftRight == Direction.LEFT.getOrdinal() && topBottom == Direction.DOWN.getOrdinal()) {
      return spriteSheet.getSelection(
          (fill == Direction.UP.getOrdinal() ? 2 : 3) * WIDTH_PX, 3 * HEIGHT_PX, WIDTH_PX, HEIGHT_PX);
    } else if (leftRight == Direction.RIGHT.getOrdinal() && topBottom == Direction.UP.getOrdinal()) {
      return spriteSheet.getSelection(
          (fill == Direction.UP.getOrdinal() ? 3 : 2) * WIDTH_PX, 0, WIDTH_PX, HEIGHT_PX);
    } else {
      return spriteSheet.getSelection(
          (fill == Direction.UP.getOrdinal() ? 2 : 3) * WIDTH_PX, HEIGHT_PX, WIDTH_PX, HEIGHT_PX);
    }
  }
}
