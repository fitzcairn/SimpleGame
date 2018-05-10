package com.stevezero.apps.simplegame.android.assets.drawable.impl;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.geometry.Box2;

import java.util.ArrayList;
import java.util.List;

/**
 * Drawable that maintains a list of bitmaps to be composed at render time.
 * Does not create any extra bitmaps in memory for efficiency.
 */
public class AppComposableDrawable extends com.stevezero.apps.simplegame.android.assets.drawable.impl.AppDrawable {
  private final List<Layer> layers;

  private class Layer {
    public final GameDrawable drawable;
    public final int x;
    public final int y;

    public Layer(GameDrawable drawable, int x, int y) {
      this.drawable = drawable;
      this.x = x;
      this.y = y;
    }
  }

  public AppComposableDrawable(int width, int height) {
    super(null, width, height);
    layers = new ArrayList<Layer>();
  }

  /**
   * Compose by drawing all the Drawables, back-to-front.
   */
  public GameDrawable renderToCanvas(Canvas canvas, Paint paint, int x, int y) {
    com.stevezero.apps.simplegame.android.assets.drawable.impl.AppDrawable drawable;
    for(Layer layer : layers) {
      drawable =  (com.stevezero.apps.simplegame.android.assets.drawable.impl.AppDrawable)layer.drawable;
      drawable.renderToCanvas(canvas, paint, x + layer.x, y + layer.y);
    }
    return this;
  }

  @Override
  public GameDrawable getSelection(int x, int y, int width, int height) {
    throw new UnsupportedOperationException("Can't select from an AppComposableDrawable.");
  }

  @Override
  public GameDrawable getSelection(Box2 box) {
    throw new UnsupportedOperationException("Can't select from an AppComposableDrawable.");
  }

  @Override
  public GameDrawable compose(GameDrawable other, int x, int y) {
    layers.add(new Layer(other, x, y));
    return this;
  }

  @Override
  public GameDrawable setTransparency(int r, int g, int b) {
    // TODO(stevemar): Implement
    return this;
  }
}
