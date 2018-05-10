package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;

public final class ConsumableRest extends Animation {
  public static final int BANANA = 0;
  public static final int CHERRY = 1;
  public static final int DATE = 2;
  public static final int ELDERBERRY = 3;
  
  // All frames are 110x 100h.
  private static final int HEIGHT_PX = 50;
  private static final int WIDTH_PX = 40;

  private final int type;
  
  public ConsumableRest(Loader loader, int type) {
    super(AssetManager.getDrawable(loader, "atlas_consumable.png"), WIDTH_PX, HEIGHT_PX, 1, true);
    this.type = type;
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    return spriteSheet.getSelection(type * WIDTH_PX, 0, WIDTH_PX, HEIGHT_PX);
  }
}
