package com.stevezero.apps.simplegame.javafx.rendering.impl;

import com.stevezero.apps.simplegame.javafx.assets.drawable.impl.JavaFXDebugDrawable;
import com.stevezero.game.Values;
import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.engine.graphics.rendering.Rendered;
import com.stevezero.game.engine.graphics.rendering.Renderer;

import java.util.Collection;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A class for rendering to a awt Graphics object, suitable for rendering engine output in an
 * javafx.
 * 
 * Does double-buffering.
 */
public class JavaFXRenderer extends Renderer {

  private final Canvas canvas;
  private final GraphicsContext graphicsContext;

  public JavaFXRenderer(Canvas canvas) {
    this.canvas = canvas;
    this.graphicsContext = canvas.getGraphicsContext2D();
  }
  
  @Override
  protected void drawToScreen(Collection<Rendered> elements) {
    // Clear the buffer.
    graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    // Composite all rendered items into the buffer.
    for (Rendered item: elements) {
      renderImage(item);
    }
  }
  
  // Handle translating a rendered item into an App-ready Image.
  private void renderImage(Rendered element) {
    GameDrawable drawableItem = element.getFrame();
    
    // WARNING: horribly wasteful; don't expect amazing framerates in wireframe mode.
    if (Values.DEBUG_RENDER_WIREFRAMES) {
      
      // Draw the wireframe box instead of the rendered drawable.
      doDrawImage(
          new JavaFXDebugDrawable(
              drawableItem.getWidth(), drawableItem.getHeight(), element.isHighlighted()),
          element.getX(), element.getY());
      
      // Also draw the center point as a small box.
      int x = element.getX() + (drawableItem.getWidth() / 2) - 1;
      int y = element.getY() + (drawableItem.getHeight() / 2) - 1;
      doDrawImage(new JavaFXDebugDrawable(2, 2, true), x, y);

    } else {
      doDrawImage(drawableItem, element.getX(), element.getY());
    }
  }
  
  private void doDrawImage(GameDrawable drawableItem, int x, int y) {
    try {
      Image image = (Image)drawableItem.getImage();
      graphicsContext.drawImage(image, x, y);
    } catch (Exception e) {
      // Something weird happened; bail on drawing this frame.
      return;
    }
  }
}
