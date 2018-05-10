package com.stevezero.apps.simplegame.game.assets.font.impl;

import com.stevezero.game.assets.font.Font;
import com.stevezero.game.assets.loader.Loader;

/**
 * Selected font.
 */
public class InfoSmallFont extends Font {
  private static final String FONT_MAP_ID = "font_info.png";
  private static final String FONT_ID = "DEFAULT";
  
  public InfoSmallFont(Loader loader) {
    super(loader, loader.getDrawable(FONT_MAP_ID), 16, 22, 32, 0, 0, 7);
  }

  @Override
  public String getId() {
    return FONT_ID;
  }
}

