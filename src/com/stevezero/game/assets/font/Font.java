package com.stevezero.game.assets.font;

import com.stevezero.game.assets.Asset;
import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;

/**
 * Render text for drawing on the screen.
 * 
 * Default kerning is 0; smaller numbers make text closer, larger farther.
 */
public abstract class Font extends Asset {
  protected final GameDrawable fontBitmap;
  protected final int charWidth;
  protected final int charHeight;
  protected final int startingAsciiValue;
  protected final int padX;
  protected final int padY;
  protected final int kern;
  protected final Loader loader;
  
  private String currentText;
  private GameDrawable currentDrawable;

  public Font(Loader loader, GameDrawable fontBitmap, int charWidth, int charHeight,
      int startingAsciiValue, int padX, int padY, int kern) {
    super(fontBitmap.getId());

    this.loader = loader;
    this.fontBitmap = fontBitmap;
    this.charWidth = charWidth;
    this.charHeight = charHeight;
    this.startingAsciiValue = startingAsciiValue;
    this.padX = padX;
    this.padY = padY;
    this.kern = kern;
  }
  
  /**
   * Get a Drawable back with font rendered in the selected style, suitable for
   * compositing onto the screen.
   */
  public GameDrawable getRenderedText(String text) {
    // Try not to do any work.
    if (currentDrawable == null || !text.equals(currentText)) {
      currentText = text;
      currentDrawable = renderString(currentText);
    }
    return currentDrawable;
  }
  
  /**
   * Subclasses must implement rendering.
   */
  public GameDrawable renderString(String text) {
    GameDrawable rendered = loader.getMutableDrawable(getActualWidth(text), charHeight);
    
    // Render each char into the composited font bitmap.
    int x = 0;
    for (char c : text.toCharArray()) {
      rendered.compose(getCharacter(c),
          x++ * (charWidth - kern - 2 * padX), 0);
    }

    return rendered;
  }

  /**
   * Get line height for this Font.
   */
  public int getCharHeight() {
    return charHeight;
  }
  
  /**
   * Get char width for this Font.
   */
  public int getCharWidth() {
    return charWidth;
  }
  
  // Helper to calculate bitmap width, given kerning.
  protected int getActualWidth(String text) {
    return text.length() * (charWidth - kern - 2 * padX + 1);
  }

  // Helper to select the character bitmap out of the drawable.
  protected GameDrawable getCharacter(char c) {
    int cellNumber = (int) c - startingAsciiValue;
    assert (cellNumber >= 0);

    int x = (cellNumber * charWidth) % fontBitmap.getWidth();
    int y = ((cellNumber * charWidth) / fontBitmap.getWidth()) * charHeight;
    return fontBitmap.getSelection(x + padX, y + padY, charWidth - 2 * padX, charHeight - 2 * padY);
  }
}
