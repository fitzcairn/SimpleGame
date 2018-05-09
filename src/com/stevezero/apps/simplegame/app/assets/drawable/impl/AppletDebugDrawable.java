package com.stevezero.apps.simplegame.app.assets.drawable.impl;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.geometry.Box2;
import com.stevezero.game.util.IdGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Draw a rectangle on the screen.
 */
public final class AppletDebugDrawable extends GameDrawable {

  private static final Color BACKGROUND = new Color(255, 255, 255, 0);
  private static final Color NORMAL = new Color(0, 0, 0, 255);
  private static final Color HIGHLIGHT = new Color(255, 0, 0, 255);
  
  private final BufferedImage image;
  private final int width;
  private final int height;
  private Color color;
  
  public AppletDebugDrawable(Box2 box) {
    super(IdGenerator.nextString());
    this.width = box.getWidth();
    this.height = box.getHeight();
    this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    this.color = NORMAL;
  }
  
  public AppletDebugDrawable(Box2 box, boolean highlight) {
    super(IdGenerator.nextString());
    this.width = box.getWidth();
    this.height = box.getHeight();
    this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    this.color = highlight ? HIGHLIGHT : NORMAL;;
  }
  
  public AppletDebugDrawable(int width, int height) {
    super(IdGenerator.nextString());
    this.width = width;
    this.height = height;
    this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    this.color = NORMAL;
  }
  
  public AppletDebugDrawable(int width, int height, boolean highlight) {
    super(IdGenerator.nextString());
    this.width = width;
    this.height = height;
    this.image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    this.color = highlight ? HIGHLIGHT : NORMAL;
  }
  
  private void drawRect() {
    // Draw a rectangle on a transparent background.
    Graphics2D g = image.createGraphics();
    g.setBackground(BACKGROUND);
    g.clearRect(0, 0, width, height);
    g.setColor(color);
    g.draw(new Rectangle(0, 0, width-1, height-1));
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
