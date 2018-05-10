package com.stevezero.apps.simplegame.game.screens.impl;

import java.util.List;

import com.stevezero.apps.simplegame.game.assets.font.manager.impl.SimpleGameFonts;
import com.stevezero.apps.simplegame.game.manifest.impl.SimpleGame;
import com.stevezero.game.Game;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.controls.events.ControlEvent;
import com.stevezero.game.controls.events.ControlHandler;
import com.stevezero.game.controls.events.EventType;
import com.stevezero.game.controls.menu.Options;
import com.stevezero.game.controls.onscreen.Interactable;
import com.stevezero.game.engine.Camera;
import com.stevezero.game.engine.graphics.backgrounds.TiledScrollingBackground;
import com.stevezero.game.engine.graphics.text.Text;
import com.stevezero.game.manifest.Manifest;
import com.stevezero.game.screens.Screen;

/**
 * Pause menu screen for the game.
 */
public class Pause extends Screen {
  // Menu items.  Needs to match contents of menuItems.
  private static final int MENU_ID_RESUME = 0;
  private static final int MENU_ID_NEW = 1;
  private static final int MENU_ID_OPTIONS = 2;
  private static final int MENU_ID_QUIT = 3;
  
  private Text title;
  
  // Create the game engine for a screen of size width/height.
  public Pause(Game game, Manifest manifest) {
    super(game, manifest);

  }
  
  @Override
  public void onActivate() {
    // Menu spacing.
    int spacing = 20;
    Camera camera = game.getCamera();
    Loader loader = game.getLoader();
    
    // Create the title.
    title = new Text(
        fonts.getFont(SimpleGameFonts.TITLE_FONT_ID), "Pause Menu", camera.getHeight() / 8, 0);
    
    // Create menu items.
    options = Options.newBuilder(0, title.getY() + title.getHeight() + 2 * spacing,
        camera.getWidth(), spacing, fonts.getFont(SimpleGameFonts.MENU_FONT_ID),
        fonts.getFont(SimpleGameFonts.MENU_SELECTED_FONT_ID))
          .addItem("Resume", EventType.NONE, EventType.CONTINUE)
          .addItem("New Game", EventType.NONE, EventType.START)
          .addItem("Options", EventType.NONE, EventType.PAUSE_OPTIONS)
          .addItem("Exit", EventType.NONE, EventType.PAUSE_EXIT)
          .build();
    
    // Add background.
    renderQueue.add(new TiledScrollingBackground(loader, "bg_tile.png", camera));
    
    // Add title.
    renderQueue.add(title.centerX(camera.getWidth()));

    // Add menu options.
    options.addRenderables(renderQueue);
  }
  
  @Override
  public void onDeactivate() {
    // Free options for garbage collection.
    options = null;
    title = null;
  }

  /**
   * Return on-screen controls for event testing.
   */
  @Override
  public List<Interactable> getInteractables() {
    return options.getInteractables();
  }
  
  /**
   * Register callbacks for all events.
   */
  public void registerControls() {
    ControlHandler controls = game.getControlHandler();
    
    // Keyboard controls -->
    controls.registerEvent(EventType.MENU_UP, new ControlEvent() {
      @Override
      public void onEvent() {
        options.selectPrevious();
      }
    });
    controls.registerEvent(EventType.MENU_DOWN, new ControlEvent() {
      @Override
      public void onEvent() {
        options.selectNext();
      }
    });
    controls.registerEvent(EventType.MENU_SELECT, new ControlEvent() {
      @Override
      public void onEvent() {
        switch(options.getSelectedId()) {
          case(MENU_ID_RESUME):
            manifest.setScreen(game, SimpleGame.GAMEPLAY);
            break;
          case(MENU_ID_NEW):
            game.restart();
            break;
          case(MENU_ID_OPTIONS):
            // TODO(stevemar): Options!
            break;
          case(MENU_ID_QUIT):
            game.quit();
            break;
          default:
            break;
        }
      }
    });
    controls.registerEvent(EventType.MENU_OPEN, new ControlEvent() {
      @Override
      public void onEvent() {
        manifest.setScreen(game, SimpleGame.GAMEPLAY);
      }
    });
    
    // Touch/mouse controls -->
    controls.registerEvent(EventType.CONTINUE, new ControlEvent() {
      @Override
      public void onEvent() {
        manifest.setScreen(game, SimpleGame.GAMEPLAY);
      }
    });
    controls.registerEvent(EventType.PAUSE_EXIT, new ControlEvent() {
      @Override
      public void onEvent() {
        game.quit();
      }
    });
    controls.registerEvent(EventType.PAUSE_OPTIONS, new ControlEvent() {
      @Override
      public void onEvent() {
       // TODO(stevemar): Options!
      }
    });
    controls.registerEvent(EventType.START, new ControlEvent() {
      @Override
      public void onEvent() {
        game.restart();
      }
    });
    
  }
}
