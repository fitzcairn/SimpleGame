package com.stevezero.apps.simplegame.app.assets.drawable.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.geometry.Box2;
import com.stevezero.game.util.IdGenerator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class AppletDrawable extends GameDrawable {
  private final int width;
  private final int height;
  private final BufferedImage image;

  public AppletDrawable(BufferedImage image, String id) {
    super(id);
    this.image = image;
    this.width = image.getWidth();
    this.height = image.getHeight();
  }
  
  public AppletDrawable(int width, int height) {
    super(IdGenerator.nextString());
    this.width = width;
    this.height = height;
    this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
    return new AppletDrawable(image.getSubimage(x, y, width, height), IdGenerator.nextString());
  }

  @Override
  public GameDrawable compose(GameDrawable other, int x, int y) {
    Graphics2D graphicsContext = image.createGraphics();
    graphicsContext.drawImage((BufferedImage)other.getImage(), x, y, null);
    graphicsContext.dispose();    
    return this;
  }

  @Override
  public GameDrawable setTransparency(int r, int g, int b) {
    int transparentRgb = (r << 16) | (g << 8) | b;
    for (int y = 0; y < image.getHeight(); ++y) {
      for (int x = 0; x < image.getWidth(); ++x) {
        int argb = image.getRGB(x, y);
        if ((argb & 0x00FFFFFF) == transparentRgb) {
          image.setRGB(x, y, 0);
        }
      }
    }
    return this;
  }
}
