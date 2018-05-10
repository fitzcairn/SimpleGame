package com.stevezero.apps.simplegame.game.assets.font.impl;

import com.stevezero.game.assets.font.Font;
import com.stevezero.game.assets.loader.Loader;

/**
 * Selected font.
 */
public class TitleFont extends Font {
  private static final String FONT_MAP_ID = "font_title.png";
  private static final String FONT_ID = "DEFAULT";
  
  public TitleFont(Loader loader) {
    super(loader, loader.getDrawable(FONT_MAP_ID), 48, 74, 32, 0, 0, 16);
  }

  @Override
  public String getId() {
    return FONT_ID;
  }
}

