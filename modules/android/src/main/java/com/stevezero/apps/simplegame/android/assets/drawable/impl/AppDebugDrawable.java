package com.stevezero.apps.simplegame.android.assets.drawable.impl;

import android.graphics.Bitmap;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.util.IdGenerator;
import com.stevezero.game.geometry.Box2;

/**
 * Draw a rectangle on the screen.
 * TODO(stevemar): Implement!
 */
public final class AppDebugDrawable extends GameDrawable {

  private final Bitmap image;
  private final int width;
  private final int height;

  public AppDebugDrawable(Box2 box) {
    super(IdGenerator.nextString());
    this.width = box.getWidth();
    this.height = box.getHeight();
    this.image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
  }
  
  public AppDebugDrawable(Box2 box, boolean highlight) {
    super(IdGenerator.nextString());
    this.width = box.getWidth();
    this.height = box.getHeight();
    this.image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
  }
  
  public AppDebugDrawable(int width, int height) {
    super(IdGenerator.nextString());
    this.width = width;
    this.height = height;
    this.image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
  }
  
  public AppDebugDrawable(int width, int height, boolean highlight) {
    super(IdGenerator.nextString());
    this.width = width;
    this.height = height;
    this.image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
  }
  
  private void drawRect() {
    // TODO(stevemar): Implement!
  }
  
  @Override
  public Object getImage() {
    drawRect();
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
    return this;
  }

  @Override
  public GameDrawable getSelection(Box2 box) {
    return this;
  }

  @Override
  public GameDrawable compose(GameDrawable other, int x, int y) {
    return this;
  }

  @Override
  public GameDrawable setTransparency(int r, int g, int b) {
    return this;
  }
}
