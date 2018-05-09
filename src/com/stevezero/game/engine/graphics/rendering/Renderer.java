package com.stevezero.game.engine.graphics.rendering;

import java.util.Collection;

import com.stevezero.game.engine.graphics.rendering.services.RenderedManager;

/**
 * Rendering class responsible for writing graphical elements to a screen buffer.
 */
public abstract class Renderer {

  /**
   * Draw all rendered elements to the screen.
   */
  public void draw(Collection<Rendered> elements) {
    drawToScreen(elements);
    
    // Reset the managed pool of rendered objects.
    RenderedManager.reset();
  }
  
  /**
   * Override to implement platfor-specific rendering code.
   */
  abstract protected void drawToScreen(Collection<Rendered> elements);

}
