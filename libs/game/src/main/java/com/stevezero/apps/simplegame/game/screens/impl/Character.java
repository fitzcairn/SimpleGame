package com.stevezero.apps.simplegame.game.screens.impl;

import java.util.LinkedList;
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
import com.stevezero.game.controls.onscreen.MenuButton;
import com.stevezero.game.engine.Camera;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.engine.graphics.backgrounds.StaticBackground;
import com.stevezero.game.engine.graphics.backgrounds.TiledScrollingBackground;
import com.stevezero.game.engine.graphics.text.Text;
import com.stevezero.game.manifest.Manifest;
import com.stevezero.game.screens.Screen;

/**
 * Menu screen for the game.
 */
public class Character extends Screen {
  // Menu items.  Needs to match contents of menuItems.
  private static final int MENU_ID_THRILLSON = 0;
  private static final int MENU_ID_SANTORO = 1;

  private StaticBackground playerCard;
  private int cardY;
  private Text title;
  private MenuButton startButton;
  private final LinkedList<Interactable> screenControls;

  // Create the game engine for a screen of size width/height.
  public Character(Game game, Manifest manifest) {
    super(game, manifest);
    screenControls = new LinkedList<Interactable>();
  }

  @Override
  public void onActivate() {
    // Menu spacing.
    int spacing = 20;
    Camera camera = game.getCamera();
    Loader loader = game.getLoader();
    
    // Add background.
    renderQueue.add(new TiledScrollingBackground(loader, "bg_tile.png", camera));

    // Create the title.
    title = new Text(fonts.getFont(SimpleGameFonts.TITLE_FONT_ID), "Select Your Character",
        camera.getHeight() / 8, 0);
    cardY = title.getY() + title.getHeight() + 2 * spacing;
    
    // Create menu items.
    options = Options.newBuilder(0, cardY,
        camera.getWidth() / 2, spacing, fonts.getFont(SimpleGameFonts.MENU_FONT_ID),
        fonts.getFont(SimpleGameFonts.MENU_SELECTED_FONT_ID))
          .addItem("Thrillson", EventType.NONE, EventType.CHAR_THRILLSON,
              fonts.getFont(SimpleGameFonts.HUD_FONT_ID))
          .addItem("Hair Santoro", EventType.NONE, EventType.CHAR_SANTORO,
              fonts.getFont(SimpleGameFonts.HUD_FONT_ID))
          .build();
    
    // Add title.
    renderQueue.add(title.centerX(camera.getWidth()));

    // Add player card, not shown for now.
    playerCard = new StaticBackground(
        loader, "thrillson_card.png", (camera.getWidth()) / 2 - spacing,
        title.getY() + title.getHeight(), Layers.FOREGROUND);
    playerCard.setVisible(false);
    renderQueue.add(playerCard);

    // Add menu options.
    options.addRenderables(renderQueue);
    
    // Start!
    startButton = new MenuButton(0, camera.getHeight() - 120, camera.getWidth() / 2, "Start!",
        fonts.getFont(SimpleGameFonts.MENU_FONT_ID),
        fonts.getFont(SimpleGameFonts.MENU_SELECTED_FONT_ID), EventType.NONE,
        EventType.START);
    startButton.setVisible(false);
    renderQueue.add(startButton);
    
    screenControls.addAll(options.getInteractables());
    screenControls.add(startButton);
  }

  @Override
  public void onDeactivate() {
    // Free options for garbage collection.
    options = null;
    playerCard = null;
    title = null;
    startButton = null;
    screenControls.clear();
  }

  /**
   * Return on-screen controls for event testing.
   */
  @Override
  public List<Interactable> getInteractables() {
    return screenControls;
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
        updateCharacter();
      }
    });
    controls.registerEvent(EventType.MENU_DOWN, new ControlEvent() {
      @Override
      public void onEvent() {
        options.selectNext();
        updateCharacter();
      }
    });
    controls.registerEvent(EventType.MENU_SELECT, new ControlEvent() {
      @Override
      public void onEvent() {
        switch(options.getSelectedId()) {
          case(MENU_ID_SANTORO):
            // Coming soon. :(
            break;
          case(MENU_ID_THRILLSON):
            manifest.setScreen(game, SimpleGame.GAMEPLAY);
            break;
          default:
            break;
        }
      }
    });
    controls.registerEvent(EventType.MENU_OPEN, new ControlEvent() {
      @Override
      public void onEvent() {
        manifest.setScreen(game, SimpleGame.MENU);
      }
    });
    
    // Touch/mouse controls -->
    controls.registerEvent(EventType.CHAR_THRILLSON, new ControlEvent() {
      @Override
      public void onEvent() {
        options.setSelectedId(MENU_ID_THRILLSON);
        updateCharacter();
      }
    });
    controls.registerEvent(EventType.CHAR_SANTORO, new ControlEvent() {
      @Override
      public void onEvent() {
        options.setSelectedId(MENU_ID_SANTORO);
        updateCharacter();
      }
    });
    controls.registerEvent(EventType.BACK, new ControlEvent() {
      @Override
      public void onEvent() {
        manifest.setScreen(game, SimpleGame.MENU);
      }
    });
    controls.registerEvent(EventType.START, new ControlEvent() {
      @Override
      public void onEvent() {
        if (options.getSelectedId() == MENU_ID_THRILLSON) {
          manifest.setScreen(game, SimpleGame.GAMEPLAY);
        }
      }
    });
  }
  
  // Update character card to selected player.
  private void updateCharacter() {
    switch(options.getSelectedId()) {
      case(MENU_ID_SANTORO):
        playerCard.changeImageTo("santoro_card.png").setVisible(true);
        startButton.setVisible(false);
        break;
      case(MENU_ID_THRILLSON):
        playerCard.changeImageTo("thrillson_card.png").setVisible(true);
        startButton.setVisible(true);
        break;
      default:
        break;
    }
  }
}
