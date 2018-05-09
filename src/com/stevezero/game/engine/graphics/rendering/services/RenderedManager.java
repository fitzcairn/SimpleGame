package com.stevezero.game.engine.graphics.rendering.services;

import java.util.ArrayList;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.engine.graphics.rendering.Rendered;
import com.stevezero.game.geometry.Point2;

/**
 * Manager for a static pool of Rendered objects that are recycled to prevent GC churn.
 */
public final class RenderedManager {
  private static final ArrayList<Rendered> RENDERED_CACHE = new ArrayList<Rendered>();
  private static int NEXT_ELEMENT = 0;

  /**
   * Reset the pool of Rendered for the next frame.
   */
  public static void reset() {
    NEXT_ELEMENT = 0;
  }
  
  public static Rendered get(GameDrawable image, int x, int y, int z) {
    return getNext(image, x, y, z);
  }
  
  public static Rendered get(GameDrawable image, Point2 point, int z) {
    return getNext(image, point.getX(), point.getY(), z);
  }

  private static Rendered getNext(GameDrawable image, int x, int y, int z) {
    Rendered rendered;
    if (NEXT_ELEMENT < RENDERED_CACHE.size()) {
      rendered = RENDERED_CACHE.get(NEXT_ELEMENT);
      if (rendered == null) {
        rendered = Rendered.create(image, x, y, z);
      } else {
        rendered.set(image, x, y, z);
      }
    } else {
      rendered = Rendered.create(image, x, y, z);
      RENDERED_CACHE.add(rendered);
    }
    NEXT_ELEMENT++;
    return rendered;
  }
}
