package com.stevezero.game.engine.graphics.backgrounds;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.ScreenElement;
import com.stevezero.game.engine.graphics.rendering.services.RenderedManager;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.geometry.Box2;
import com.stevezero.game.geometry.Point2;
import com.stevezero.game.screens.Screen;

/**
 * A tiled background that fills a specific area.
 * Experimental version that doesn't use a mutable drawable for performance.
 */
public class TiledStaticAreaBackground extends ScreenElement {
  private final Point2 drawAt;
  private final String tileImageId;
  private final Loader loader;
  
  public TiledStaticAreaBackground(Loader loader, String tileImageId, int x, int y, int width,
      int height) {
    super(x, y, Layers.FILL);
    this.loader = loader;
    this.tileImageId = tileImageId;
    this.boundingBox.setHeight(height);
    this.boundingBox.setWidth(width);
    this.drawAt = new Point2();
  }
  
  private void composeBackgroundImage(Screen screen) {
    // Load the tile image.
    GameDrawable tileImage = AssetManager.getDrawable(loader, tileImageId);
    int tileWidth = tileImage.getWidth();
    int tileHeight = tileImage.getHeight();
    
    // Determine how many tiles we need Y/X.
    int numTilesY = boundingBox.getHeight() / tileImage.getHeight();
    int numTilesX = boundingBox.getWidth() / tileImage.getWidth();

    // Compose the tiles onto the background.
    for (int row = 0; row < numTilesY; row++) {
      for (int col = 0; col < numTilesX; col++) {
        drawAt.set(x + (col * tileWidth), y + (row * tileHeight));
        screen.addToRenderedQueue(
            RenderedManager.get(tileImage, screen.getCamera().toCamera(drawAt), z));
      }
    }    
  }
  
  @Override
  public Box2 getBox() {
    position.set(getX(), getY());
    boundingBox.setPoint(position);
    return boundingBox;
  }

  @Override
  public void render(Screen screen) {
    if (!isVisibleFuzzy(screen)) {
      // This is expected to be the majority case.
      return;
    }
    composeBackgroundImage(screen);
  }
}
