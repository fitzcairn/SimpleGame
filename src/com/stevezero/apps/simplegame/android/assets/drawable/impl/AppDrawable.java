package com.stevezero.apps.simplegame.android.assets.drawable.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.util.IdGenerator;
import com.stevezero.game.geometry.Box2;

import java.util.HashMap;
import java.util.Map;

/**
 * Android-specific GameDrawable implementation.
 */
public class AppDrawable extends GameDrawable {
  protected final int width;
  protected final int height;
  protected final Bitmap image;

  private Map<Long, AppPartialDrawable> slice_cache;

  public AppDrawable(Bitmap image, String id) {
    super(id);
    this.image = image;
    this.width = this.image.getWidth();
    this.height = this.image.getHeight();
  }

  public AppDrawable(Bitmap originalImage, int width, int height) {
    super(IdGenerator.nextString());
    this.width = width;
    this.height = height;
    this.image = originalImage;
  }

  /**
   * On Android, flip rendering around to have the Drawable render to the canvas rather than return
   * a bitmap.  This makes it easier to use bitmaps efficiently in a memory-constrained environment.
   */
  public GameDrawable renderToCanvas(Canvas canvas, Paint paint, int x, int y) {
    canvas.drawBitmap(image, x, y, paint);
    return this;
  }

  @Override
  public Object getImage() {
    return image;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public GameDrawable getSelection(int x, int y, int width, int height) {
    return doSelection(x, y, width, height);
  }

  @Override
  public GameDrawable getSelection(Box2 box) {
    return doSelection(box.getX(), box.getY(), box.getWidth(), box.getHeight());
  }
  
  private GameDrawable doSelection(int x, int y, int width, int height) {
    // This function is called a *lot* to slice up atlases.
    // To make it more efficient, we use a per-drawable cache to create objects once.
    if (slice_cache == null) {
      // Only create the cache if needed.
      slice_cache = new HashMap<Long, AppPartialDrawable>();
    }

    // Using a cache ensures that the number of AppPartialDrawable objects created is bounded.
    // Use a long as the key to avoid expensive string allocation.
    // Assume 12 bits is the max size for each of the arguments.
    // Key: | pad (16) | x (12) | y (12) | width (12) | height (12) |
    long key = x;
    key = key << 12;
    key |= y;
    key = key << 12;
    key |= width;
    key = key << 12;
    key |= height;
    Long keyObject = Long.valueOf(key);

    AppPartialDrawable partial;

    if (!slice_cache.containsKey(keyObject)) {
      partial = new AppPartialDrawable(x, y, width, height, image);
      slice_cache.put(keyObject, partial);
    } else {
      partial = slice_cache.get(keyObject);
    }

    return partial;
  }

  @Override
  public GameDrawable compose(GameDrawable other, int x, int y) {
    throw new UnsupportedOperationException("Can't compose.  Try using an AppComposableDrawable.");
  }

  @Override
  public GameDrawable setTransparency(int r, int g, int b) {
    // TODO(stevemar): Implement
    return this;
  }
}
