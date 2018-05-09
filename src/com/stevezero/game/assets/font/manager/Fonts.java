package com.stevezero.game.assets.font.manager;

import com.stevezero.game.assets.font.Font;
import com.stevezero.game.assets.loader.Loader;

/**
 * Manage fonts for the game.
 */
public abstract class Fonts {
  protected final Loader loader;
  
  public Fonts(Loader loader) {
    this.loader = loader;
  }
  
  public abstract Font getFont(String fontId);

  public abstract Font getDefaultFont();
}
