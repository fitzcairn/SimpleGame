package com.stevezero.apps.simplegame.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.stevezero.apps.simplegame.android.util.BaseGameActivity;
import com.stevezero.apps.simplegame.android.assets.loader.impl.AppLoader;
import com.stevezero.apps.simplegame.android.assets.sound.impl.AppSoundManager;
import com.stevezero.apps.simplegame.android.controls.impl.AppControlHandler;
import com.stevezero.apps.simplegame.android.rendering.impl.AppRenderer;
import com.stevezero.apps.simplegame.android.services.impl.AppAchievements;
import com.stevezero.apps.simplegame.android.services.impl.AppLeaderboards;
import com.stevezero.apps.simplegame.android.system.impl.AppSystemManager;
import com.stevezero.apps.simplegame.game.manifest.impl.SimpleGame;
import com.stevezero.game.Game;
import com.stevezero.game.external.services.ServiceManager;

/**
 * The Game view.
 */
public class GameView extends SurfaceView implements Callback {
  private final Context context;

  private SurfaceHolder surfaceHolder;
  private GameThread gameThread;


  public GameView(Context context) {
    super(context);
    this.context = context;
    setup();
  }

  public GameView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    setup();
  }

  public GameView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.context = context;
    setup();
  }

  private void setup() {
    surfaceHolder = getHolder();
    surfaceHolder.addCallback(this);
    setFocusable(true);

    // Get the default display.
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();

    // Set up game thread.
    gameThread = new GameThread(
        this, surfaceHolder, context, new Handler(), display, ((BaseGameActivity) context));
  }

  @Override
  public void surfaceCreated(SurfaceHolder surfaceHolder) {
    Canvas canvas = surfaceHolder.lockCanvas();
    canvas.drawColor(Color.BLACK);
    surfaceHolder.unlockCanvasAndPost(canvas);
    gameThread.start();
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    // Probably should do something here...
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    // Unregister listeners
    // TODO(stevemar): WRITE!

    boolean retry = true;
    while (retry) {
      try {
        gameThread.join();
        retry = false;
      } catch (InterruptedException e) {
      }
    }
  }

  /**
   * Game thread; run rendering loop off of main app thread so we can receive UI interrupts.
   */
  class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private Context context;
    private Handler handler;
    private final Game game;
    private final Display display;
    private final BaseGameActivity gameActivity;

    public GameThread(View gameView, SurfaceHolder surfaceHolder, Context context,
        Handler handler, Display display, BaseGameActivity gameActivity) {
      this.surfaceHolder = surfaceHolder;
      this.handler = handler;
      this.context = context;
      this.display = display;
      this.gameActivity = gameActivity;

      // Create a manager for system interaction.
      AppSystemManager systemManager = new AppSystemManager(display);

      // Register a handler for device interaction.
      AppControlHandler controlHandler = new AppControlHandler(systemManager);
      gameView.setOnTouchListener(controlHandler);

      // Create the game instance.
      this.game = new Game(
          systemManager,
          new AppRenderer(systemManager, surfaceHolder),
          new AppLoader(context.getResources(), context.getPackageName()),
          controlHandler,
          new AppSoundManager(context),
          new ServiceManager(new AppAchievements(gameActivity),
              new AppLeaderboards(gameActivity)),
          new SimpleGame());

      // Give the control handler a reference to the game.
      // This is required to map touch events to game widgets.
      controlHandler.setGame(this.game);
    }

    public void run() {
      while (true) {
        // Start a game loop iteration.
        game.start();

        // Tick the engine forward.  This also processes input.
        game.update();

        // Draw a frame using the renderer, which will render it to a backbuffer.
        game.render();

        // Do any cleanup.
        game.finalize();
      }
    }
  }
}
