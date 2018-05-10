package com.stevezero.apps.simplegame.android.assets.drawable.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.stevezero.game.assets.drawable.GameDrawable;


/**
 * Represents a portion of a drawable resource.  Sliced on-demand.
 */
public final class AppPartialDrawable extends AppDrawable {
  private static final Rect SRC = new Rect();
  private static final Rect DEST = new Rect();

  private final int x;
  private final int y;

  public AppPartialDrawable(int x, int y, int width, int height, Bitmap originalImage) {
    super(originalImage, width, height);
    this.x = x;
    this.y = y;
  }

  /**
   * Renders a part of the bitmap already decoded in memory.
   */
  @Override
  public GameDrawable renderToCanvas(Canvas canvas, Paint paint, int x, int y) {
    SRC.set(this.x, this.y, this.x + this.width, this.y + this.height);
    DEST.set(x, y, x + this.width, y + this.height);
    canvas.drawBitmap(this.image, SRC, DEST, paint);
    return this;
  }
}
