package com.stevezero.apps.simplegame.game.assets.font.manager.impl;

import com.stevezero.game.assets.font.Font;
import com.stevezero.game.assets.font.manager.Fonts;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.apps.simplegame.game.assets.font.impl.HudFont;
import com.stevezero.apps.simplegame.game.assets.font.impl.InfoFont;
import com.stevezero.apps.simplegame.game.assets.font.impl.MenuFont;
import com.stevezero.apps.simplegame.game.assets.font.impl.MenuSelectedFont;
import com.stevezero.apps.simplegame.game.assets.font.impl.TitleFont;

/**
 * Class to manage fonts for the game.
 */
public final class SimpleGameFonts extends Fonts {
  public static final String TITLE_FONT_ID = "title_font";
  public static final String HUD_FONT_ID = "hud_font";
  public static final String MENU_FONT_ID = "menu_font";
  public static final String MENU_SELECTED_FONT_ID = "menu_selected_font";
  public static final String INFO_FONT_ID = "info_font";
  
  private TitleFont titleFont;
  private MenuFont menuFont;
  private MenuSelectedFont menuSelectedFont;
  private HudFont hudFont;
  private InfoFont infoFont;
  
  public SimpleGameFonts(Loader loader) {
    super(loader);
  }

  @Override
  public Font getFont(String fontId) {
    if (fontId == TITLE_FONT_ID) {
      if (titleFont == null) titleFont = new TitleFont(loader);
      return titleFont;
    }
    if (fontId == MENU_FONT_ID) {
      if (menuFont == null) menuFont = new MenuFont(loader);
      return menuFont;
    }
    if (fontId == MENU_SELECTED_FONT_ID) {
      if (menuSelectedFont == null) menuSelectedFont = new MenuSelectedFont(loader);
      return menuSelectedFont;
    }
    if (fontId == INFO_FONT_ID) {
      if (infoFont == null) infoFont = new InfoFont(loader);
      return infoFont;
    }
    return getHudFont();
  }

  @Override
  public Font getDefaultFont() {
    return getHudFont();
  }

  private Font getHudFont() {
    if (hudFont == null) hudFont = new HudFont(loader);
    return hudFont;
  }
}
