package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.apps.simplegame.game.controls.onscreen.impl.ActionButton;
import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.motion.animation.Animation;
import com.stevezero.game.geometry.Box2;
import com.stevezero.apps.simplegame.game.controls.onscreen.impl.ActionButton;

/**
 * Graphics for the action buttons.
 */
public final class ActionButtonDown extends Animation {
  private final int type;
  private static final Box2 BOX = new Box2();
  
  public ActionButtonDown(Loader loader, int type) {
    super(AssetManager.getDrawable(loader, "atlas_controls.png"),
        getBox(type).getWidth(), getBox(type).getHeight(), 1, true);
    this.type = type;
  }

  private static Box2 getBox(int type) {
    switch(type) {
      case(ActionButton.TYPE_LEFT):
        return BOX.set(130, 100, 130, 100);
      case(ActionButton.TYPE_RIGHT):
        return BOX.set(0, 100, 130, 100);
      case(ActionButton.TYPE_JUMP):
        return BOX.set(360, 0, 100, 130);
      case(ActionButton.TYPE_SHOOT):
      default:
        return BOX.set(460, 100, 110, 100);
    }
  }
  
  @Override
  protected GameDrawable getFrame(int animationFrame) {
    return spriteSheet.getSelection(getBox(type));
  }
}
