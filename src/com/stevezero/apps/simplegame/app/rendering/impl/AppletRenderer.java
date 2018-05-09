package com.stevezero.apps.simplegame.app.rendering.impl;

import com.stevezero.game.Values;
import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.engine.graphics.rendering.Rendered;
import com.stevezero.game.engine.graphics.rendering.Renderer;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Collection;

import com.stevezero.apps.simplegame.app.assets.drawable.impl.AppletDebugDrawable;

/**
 * A class for rendering to a awt Graphics object, suitable for rendering engine output in an
 * app.
 * 
 * Does double-buffering.
 */
public class AppletRenderer extends Renderer {

  private final Graphics drawBuffer;
  private final Applet applet;
  
  public AppletRenderer(Applet applet, Graphics buffer) {
    this.applet = applet;
    this.drawBuffer = buffer;
  }
  
  @Override
  protected void drawToScreen(Collection<Rendered> elements) {
    // Clear the buffer.
    drawBuffer.setColor(applet.getBackground());
    drawBuffer.fillRect(0, 0, applet.getWidth(), applet.getHeight());
    drawBuffer.setColor(applet.getForeground());
    
    // Composite all rendered items into the buffer.
    for (Rendered item: elements) {
      renderImage(item);
    }
    
    // Schedule a repaint on the main UI thread at some point.
    applet.repaint();
  }
  
  // Handle translating a rendered item into an Applet-ready Image.
  private void renderImage(Rendered element) {
    GameDrawable drawableItem = element.getFrame();
    
    // WARNING: horribly wasteful; don't expect amazing framerates in wireframe mode.
    if (Values.DEBUG_RENDER_WIREFRAMES) {
      
      // Draw the wireframe box instead of the rendered drawable.
      doDrawImage(
          new AppletDebugDrawable(
              drawableItem.getWidth(), drawableItem.getHeight(), element.isHighlighted()),
          element.getX(), element.getY());
      
      // Also draw the center point as a small box.
      int x = element.getX() + (drawableItem.getWidth() / 2) - 1;
      int y = element.getY() + (drawableItem.getHeight() / 2) - 1;
      doDrawImage(new AppletDebugDrawable(2, 2, true), x, y);

    } else {
      doDrawImage(drawableItem, element.getX(), element.getY());
    }
  }
  
  private void doDrawImage(GameDrawable drawableItem, int x, int y) {
    try {
      Image image = (Image)drawableItem.getImage();
      drawBuffer.drawImage(image, x, y, applet);
    } catch (Exception e) {
      // Something weird happened; bail on drawing this frame.
      return;
    }
  }
}
