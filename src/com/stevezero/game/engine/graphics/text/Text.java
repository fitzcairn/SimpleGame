package com.stevezero.game.engine.graphics.text;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.font.Font;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.engine.graphics.rendering.Renderable;
import com.stevezero.game.engine.graphics.rendering.services.RenderedManager;
import com.stevezero.game.screens.Screen;

/**
 * A rendered string of text that can be passed into the renderer.
 */
public class Text implements Renderable {
  private Font font;
  private String text;
  private int x;
  private int y;
  
  public Text(Font font, String text, int x, int y) {
    this.font = font;
    this.text = text;
    this.x = x;
    this.y = y;
  }

  public Text(Font font, String text) {
    this.font = font;
    this.text = text;
    this.x = 0;
    this.y = 0;
  }

  public Font getFont() {
    return font;
  }

  public Text setFont(Font font) {
    this.font = font;
    return this;
  }

  public String getText() {
    return text;
  }

  public Text setText(String text) {
    this.text = text;
    return this;
  }

  public int getX() {
    return x;
  }

  public Text setX(int x) {
    this.x = x;
    return this;
  }

  public int getY() {
    return y;
  }

  public Text setY(int y) {
    this.y = y;
    return this;
  }

  public GameDrawable toDrawable() {
    return font.getRenderedText(text);
  }
  
  public int getWidth() {
    return toDrawable().getWidth();
  }
  
  public int getHeight() {
    return toDrawable().getHeight();
  }
  
  public Text centerX(int viewWidth) {
    return setX((viewWidth / 2) - (getWidth() / 2));
  }
  
  public Text centerY(int viewHeight) {
    return setY((viewHeight / 2) - (getHeight() / 2));
  }
  
  @Override
  public void render(Screen screen) {
    screen.addToRenderedQueue(RenderedManager.get(toDrawable(), x, y, Layers.FOREGROUND));
  }
}