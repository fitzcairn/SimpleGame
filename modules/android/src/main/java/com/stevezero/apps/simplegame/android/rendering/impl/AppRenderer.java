package com.stevezero.apps.simplegame.android.rendering.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.stevezero.apps.simplegame.android.assets.drawable.impl.AppDebugDrawable;
import com.stevezero.apps.simplegame.android.assets.drawable.impl.AppDrawable;
import com.stevezero.apps.simplegame.android.system.impl.AppSystemManager;
import com.stevezero.game.Values;
import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.engine.graphics.rendering.Rendered;
import com.stevezero.game.engine.graphics.rendering.Renderer;

import java.util.Collection;


/**
 * A class for rendering to an Android canvas.
 */
public class AppRenderer extends Renderer {

  private final SurfaceHolder surfaceHolder;
  private final AppSystemManager systemManager;

  // Set up scaling for painting.
  private static final Paint PAINT = new Paint();

  public AppRenderer(AppSystemManager systemManager, SurfaceHolder surfaceHolder) {
    this.systemManager = systemManager;
    this.surfaceHolder = surfaceHolder;
  }

  /**
   * TODO(stevemar): Handle wireframing.
   */
  @Override
  protected void drawToScreen(Collection<Rendered> elements) {
    Canvas canvas = null;
    try {
      canvas = surfaceHolder.lockCanvas(null);

      // This can apparently happen at times when the app switcher comes up.  Weird.
      if (canvas == null) {
        return;
      }

      synchronized (surfaceHolder) {
        // Set the canvas scaling to match the current screen size.
        scaleCanvas(canvas);

        // Composite all rendered items into the buffer.
        for (Rendered item: elements) {
          AppDrawable drawable = (AppDrawable)(item.getFrame());
          drawable.renderToCanvas(canvas, PAINT, item.getX(), item.getY());
        }
      }
    } finally {
      if (canvas != null) {
        surfaceHolder.unlockCanvasAndPost(canvas);
      }
    }
  }

  // Set the current scaling for the canvas to fill the screen.
  private void scaleCanvas(Canvas canvas) {
    float scaleX = (float)systemManager.getViewWidth() / (float)Values.VIEW_WIDTH;
    float scaleY = (float)systemManager.getViewHeight() / (float)Values.VIEW_HEIGHT;
    canvas.scale(scaleX, scaleY);
  }

  // Handle translating a rendered item into an Applet-ready Image.
  private Bitmap getImage(Rendered item) {
    GameDrawable frame = item.getFrame();

    // WARNING: horribly wasteful; don't expect amazing framerates in wireframe mode.
    if (Values.DEBUG_RENDER_WIREFRAMES) {
      frame = new AppDebugDrawable(frame.getWidth(), frame.getHeight(), item.isHighlighted());
    }

    try {
      return (Bitmap)frame.getImage();
    } catch (Exception e) {
      return null;
    }
  }
}
