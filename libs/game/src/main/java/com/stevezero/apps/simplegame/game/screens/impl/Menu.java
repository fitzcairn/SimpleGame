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
 * Menu screen for the game.
 */
public class Menu extends Screen {
  // Menu items for keyboard scroll.  Needs to match contents of menuItems.
  private static final int MENU_ID_START = 0;
  private static final int MENU_ID_OPTIONS = 1;
  private static final int MENU_ID_ACHIEVEMENTS = 2;
  private static final int MENU_ID_LEADERBOARD = 3;
  private static final int MENU_ID_QUIT = 4;

 private Text title;
  
  // Create the game engine for a screen of size width/height.
  public Menu(Game game, Manifest manifest) {
    super(game, manifest);

  }
  
  @Override
  public void onActivate() {
    // Menu spacing.
    int spacing = 20;
    Camera camera = game.getCamera();
    Loader loader = game.getLoader();
    
    // Create the title.
    title = new Text(fonts.getFont(SimpleGameFonts.TITLE_FONT_ID), "Main Menu", camera.getHeight() / 8, 0);
    
    // Create menu items.
    options = Options.newBuilder(0, title.getY() + title.getHeight() + 2 * spacing,
        camera.getWidth(), spacing, fonts.getFont(SimpleGameFonts.MENU_FONT_ID),
        fonts.getFont(SimpleGameFonts.MENU_SELECTED_FONT_ID))
          .addItem("New Game", EventType.NONE, EventType.START)
          .addItem("How to Play", EventType.NONE, EventType.MENU_HOW)
          .addItem("Achievements", EventType.NONE, EventType.MENU_ACHIEVEMENTS)
          .addItem("Leaderboard", EventType.NONE, EventType.MENU_LEADERBOARDS)
          .addItem("Exit", EventType.NONE, EventType.MENU_EXIT)
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
          case(MENU_ID_START):
            manifest.setScreen(game, SimpleGame.CHARACTER);
            break;
          case(MENU_ID_OPTIONS):
            manifest.setScreen(game, SimpleGame.HOW);
            break;
          case(MENU_ID_ACHIEVEMENTS):
            game.getServiceManager().getAchievementsService(); // TODO(stevemar): IMPLEMENT
            break;
          case(MENU_ID_LEADERBOARD):
            game.getServiceManager().getLeaderboardsService(); // TODO(stevemar): IMPLEMENT
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
        game.quit();
      }
    });
    
    // Mouse controls -->
    controls.registerEvent(EventType.START, new ControlEvent() {
      @Override
      public void onEvent() {
        manifest.setScreen(game, SimpleGame.CHARACTER);
      }
    });
    controls.registerEvent(EventType.MENU_HOW, new ControlEvent() {
      @Override
      public void onEvent() {
        manifest.setScreen(game, SimpleGame.HOW);
      }
    });
    controls.registerEvent(EventType.MENU_ACHIEVEMENTS, new ControlEvent() {
      @Override
      public void onEvent() {
        game.getServiceManager().getAchievementsService().showAchievements();
      }
    });
    controls.registerEvent(EventType.MENU_LEADERBOARDS, new ControlEvent() {
      @Override
      public void onEvent() {
        game.getServiceManager().getLeaderboardsService().showLeaderboards();
      }
    });
    controls.registerEvent(EventType.MENU_EXIT, new ControlEvent() {
      @Override
      public void onEvent() {
        game.quit();
      }
    });
    
  }
}
