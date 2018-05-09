package com.stevezero.apps.simplegame.app;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import com.stevezero.apps.simplegame.app.controls.events.impl.AppletControlHandler;
import com.stevezero.apps.simplegame.app.system.impl.AppletSystemManager;
import com.stevezero.game.Game;

/**
 * Run the game as an Applet.
 */
public class AppGame extends Application { //} implements Runnable, KeyListener, MouseListener {
  private static final long serialVersionUID = 4356007424601405606L;
  
  // Applet primitives
  private Image screen;
  private Graphics graphicsBuffer;
  private URL baseURL;

  // The game instance.
  private Game game;
  
  // Controls
  private AppletControlHandler controls;
  
  @Override
  public void start(Stage stage) {
    // Create the system manager.
    AppletSystemManager systemManager = new AppletSystemManager();

    // Set up the screen and graphics buffer.
    Scene scene = new Scene(new Group(), systemManager.getViewWidth(), systemManager.getViewHeight());

    stage.setTitle("SimpleGame");
    stage.setScene(scene);
    stage.show();

//    screen = createImage(systemManager.getViewWidth(), systemManager.getViewHeight());
//    graphicsBuffer = screen.getGraphics();
//
//    // Set up the window.
//    setSize(systemManager.getViewWidth(), systemManager.getViewHeight());
//    setBackground(Color.BLACK);
//    setFocusable(true);
//    addKeyListener(this);
//    addMouseListener(this);
//    baseURL = getDocumentBase();
//
//    // Create the controls.
//    controls = new AppletControlHandler();
//
//    // Create the game instance.
//    game = new Game(
//        systemManager,
//        new AppletRenderer(this, graphicsBuffer),
//        new AppletLoader(baseURL),
//        controls,
//        new AppletSoundManager(),
//        new ServiceManager(new Achievements(), new Leaderboards()),
//        new SimpleGame()); // TODO: this needs to be pulled out into a specific game package.
  }
  
/*  @Override
  public void start() {
    Thread thread = new Thread(this);
    thread.start();
  }

  @Override
  public void stop() {
    super.stop();
  }

  @Override
  public void destroy() {
    super.destroy();
  }

  // Handle double-buffering by rendering to the back buffer, then drawing the buffer on
  // the screen.
  @Override
  public void update(Graphics g) {
    g.drawImage(screen,  0,  0,  this);    
  }

  // Main game loop.
  @Override
  public void run() {
    while (true) {
      // Start a game loop iteration.
      game.start();
      
      // Tick the engine forward.
      game.update();

      // Draw a frame using the renderer, which will render it to a backbuffer.
      game.render();
      
      // Do any cleanup.
      game.finalize();
    }
  }

  @Override
  public void keyPressed(final KeyEvent e) {
    controls.onEventStart(new AppletEvent() {
      @Override
      public boolean hasKeyEvent() {
        return true;
      }

      @Override
      public boolean hasMouseEvent() {
        return false;
      }

      @Override
      public KeyEvent getKeyEvent() {
        return e;
      }

      @Override
      public MouseEvent getMouseEvent() {
        return null;
      }
      
    }, game);
  }

  @Override
  public void keyReleased(final KeyEvent e) {
    controls.onEventStop(new AppletEvent() {
      @Override
      public boolean hasKeyEvent() {
        return true;
      }

      @Override
      public boolean hasMouseEvent() {
        return false;
      }

      @Override
      public KeyEvent getKeyEvent() {
        return e;
      }

      @Override
      public MouseEvent getMouseEvent() {
        return null;
      }
      
    }, game);
  }

  @Override
  public void mousePressed(final MouseEvent e) {
    controls.onEventStart(new AppletEvent() {
      @Override
      public boolean hasKeyEvent() {
        return false;
      }

      @Override
      public boolean hasMouseEvent() {
        return true;
      }

      @Override
      public KeyEvent getKeyEvent() {
        return null;
      }

      @Override
      public MouseEvent getMouseEvent() {
        return e;
      }
    }, game);
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    controls.onEventStop(new AppletEvent() {
      @Override
      public boolean hasKeyEvent() {
        return false;
      }

      @Override
      public boolean hasMouseEvent() {
        return true;
      }

      @Override
      public KeyEvent getKeyEvent() {
        return null;
      }

      @Override
      public MouseEvent getMouseEvent() {
        return e;
      }
    }, game);
  }

  // Ignored event handlers -->
  
  @Override
  public void keyTyped(KeyEvent arg0) {
    // Intentionally ignored.
  }

  @Override
  public void mouseEntered(MouseEvent arg0) {
    // Intentionally ignored.
  }

  @Override
  public void mouseExited(MouseEvent arg0) {
    // Intentionally ignored.
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
    // Intentionally ignored.
  }*/
}
