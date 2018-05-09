package com.stevezero.game.engine.graphics.backgrounds;

import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.ScreenElement;
import com.stevezero.game.engine.graphics.motion.animation.Frame;
import com.stevezero.game.engine.graphics.rendering.services.RenderedManager;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.screens.Screen;

/**
 * A scrolling background that is exactly the same size as the entire level.  This background
 * is NOT tiled, and moves with the camera.
 */
public class ScrollingBackground extends ScreenElement {

  public ScrollingBackground(Loader loader, String imageId) {
    super(0, 0, Layers.BACKGROUND);

    // Load the background sprite states.
    // Right now, only one for the background.
    sprite.addAnimation(new Frame(AssetManager.getDrawable(loader, imageId)));
  }

  @Override
  public void render(Screen screen) {
    // Move background so that the camera is viewing the required area.
    x = -screen.getCamera().getGlobalX();
    y = -screen.getCamera().getGlobalY();
    screen.addToRenderedQueue(RenderedManager.get(sprite.getNextFrame(), x, y, z));
  }
}
