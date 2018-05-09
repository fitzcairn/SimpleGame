package com.stevezero.game.engine.graphics.text;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.font.Font;

/**
 * A string of text that wraps around an area.
 */
public final class WrappingText extends Text {
  private int width;
  private int height;
  
  public WrappingText(Font font, String text, int x, int y, int width, int height) {
    super(font, text, x, y);
    this.width = width;
    this.height = height;
  }

  @Override
  public GameDrawable toDrawable() {
    // TODO(stevemar): Implement!
    return getFont().getRenderedText(getText());
  }
  
  @Override
  public int getWidth() {
    return width;
  }
  
  @Override
  public int getHeight() {
    return height;
  }
}