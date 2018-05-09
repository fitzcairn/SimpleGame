package com.stevezero.apps.simplegame.game.assets.font.impl;

import com.stevezero.game.assets.font.Font;
import com.stevezero.game.assets.loader.Loader;

/**
 * Selected font.
 */
public class MenuFont extends Font {
  private static final String FONT_MAP_ID = "font_yellow.png";
  private static final String FONT_ID = "DEFAULT";
  
  public MenuFont(Loader loader) {
    super(loader, loader.getDrawable(FONT_MAP_ID), 32, 48, 32, 0, 0, 11);
  }

  @Override
  public String getId() {
    return FONT_ID;
  }
}

