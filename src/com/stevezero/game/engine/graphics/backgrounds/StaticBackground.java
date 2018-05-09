package com.stevezero.game.engine.graphics.backgrounds;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.engine.graphics.rendering.Renderable;
import com.stevezero.game.engine.graphics.rendering.services.RenderedManager;
import com.stevezero.game.screens.Screen;

/**
 * Background for static screens.
 */
public class StaticBackground implements Renderable {
  private final Loader loader;
  private final int x;
  private final int y;
  private final int z;

  private boolean visible = true;
  private GameDrawable background;
  
  public StaticBackground(Loader loader, String imageId, int x, int y, int z) {
    this.loader = loader;
    this.background = AssetManager.getDrawable(loader, imageId);
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public StaticBackground(GameDrawable background) {
    this.loader = null;
    this.background = background;
    this.x = 0;
    this.y = 0;
    this.z = Layers.BACKGROUND;
  }

  public StaticBackground(int x, int y, GameDrawable background) {
    this.loader = null;
    this.background = background;
    this.x = x;
    this.y = y;
    this.z = Layers.BACKGROUND;
  }
  
  public StaticBackground changeImageTo(String imageId) {
    assert(loader != null);
    this.background = AssetManager.getDrawable(loader, imageId);
    return this;
  }
  
  public boolean isVisible() {
    return this.visible;
  }
  
  public StaticBackground setVisible(boolean visible) {
    this.visible = visible;
    return this;
  }

  @Override
  public void render(Screen screen) {
    if (visible) {
      screen.addToRenderedQueue(RenderedManager.get(background, x, y, z));
    }
  }
}
