package com.stevezero.game.engine.graphics.rendering;

import com.stevezero.game.screens.Screen;

/**
 * Something that is renderable to the screen.
 */
public interface Renderable {
  /**
   * Render one or more Rendered objects to the screen.
   */
  public void render(Screen screen);
}
