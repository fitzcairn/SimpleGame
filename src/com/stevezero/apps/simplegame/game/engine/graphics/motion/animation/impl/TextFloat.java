package com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl;

import com.stevezero.apps.simplegame.game.assets.font.manager.impl.GMSCrusaderFonts;
import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.font.Font;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.graphics.motion.animation.Animation;
import com.stevezero.game.engine.graphics.text.Text;
import com.stevezero.apps.simplegame.game.assets.font.manager.impl.GMSCrusaderFonts;

/**
 * Simple text sprite.
 */
public final class TextFloat extends Animation {
  private final int FRAMES = 20;
  private final Text text;
  private final Font font;
  
  public TextFloat(Engine engine, String text) {
    this.font = engine.getManifest().getFonts(engine.getLoader())
        .getFont(GMSCrusaderFonts.INFO_FONT_ID);

    this.text = new Text(font, text, 0, 0);
    
    resetNumAnimationFrames(FRAMES);
  }

  @Override
  protected GameDrawable getFrame(int animationFrame) {
    return text.toDrawable();
  }
}
