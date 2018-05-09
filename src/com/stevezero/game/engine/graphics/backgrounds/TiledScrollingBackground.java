package com.stevezero.game.engine.graphics.backgrounds;

import static java.lang.Math.abs;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.Camera;
import com.stevezero.game.engine.graphics.ScreenElement;
import com.stevezero.game.engine.graphics.motion.animation.Frame;
import com.stevezero.game.engine.graphics.rendering.services.RenderedManager;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.screens.Screen;

/**
 * A tiled, scrolling background that moves with the camera.
 * 
 * TODO(stevemar): Move to render-time composite to save memory!
 */
public class TiledScrollingBackground extends ScreenElement {
  private final GameDrawable tileImage;
  private final GameDrawable background;

  public TiledScrollingBackground(Loader loader, String tileImageId, Camera camera) {
    super(0, 0, Layers.BACKGROUND);
    // Load the tile image.
    this.tileImage = AssetManager.getDrawable(loader, tileImageId);
    
    // Determine how many tiles we need Y/X, and add extra splash for seamless wrapping.
    int numTilesY = camera.getHeight() / tileImage.getHeight() + 2;
    int numTilesX = camera.getWidth() / tileImage.getWidth() + 2;
    
    // Get a mutable Drawable large enough to handle the tiles.
    this.background = loader.getMutableDrawable(
        numTilesX * tileImage.getWidth(), numTilesY * tileImage.getHeight());

    // Compose the tiles onto the background.
    for (int row = 0; row < numTilesY; row++) {
      for (int col = 0; col < numTilesX; col++) {
        this.background.compose(tileImage, col * tileImage.getWidth(), row * tileImage.getHeight());
      }
    }
    
    // Set the sprite to render the composed Drawable.
    sprite.addAnimation(new Frame(this.background));
  }

  @Override
  public void render(Screen screen) {
    // Move background so that the camera is viewing the required area.
    // As one tile moves off the screen, shift the background so the next one is in view.
    x = -abs(screen.getCamera().getGlobalX()) % tileImage.getWidth();
    y = -abs(screen.getCamera().getGlobalY()) % tileImage.getHeight();
    screen.addToRenderedQueue(RenderedManager.get(sprite.getNextFrame(), x, y, z));
  }
}
