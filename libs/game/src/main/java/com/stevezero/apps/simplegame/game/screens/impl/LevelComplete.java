package com.stevezero.apps.simplegame.game.screens.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.stevezero.apps.simplegame.game.assets.font.manager.impl.SimpleGameFonts;
import com.stevezero.apps.simplegame.game.manifest.impl.SimpleGame;
import com.stevezero.game.Game;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.controls.events.ControlEvent;
import com.stevezero.game.controls.events.EventType;
import com.stevezero.game.controls.onscreen.Interactable;
import com.stevezero.game.engine.Camera;
import com.stevezero.game.engine.graphics.backgrounds.TiledScrollingBackground;
import com.stevezero.game.engine.graphics.text.Text;
import com.stevezero.game.manifest.Manifest;
import com.stevezero.game.screens.Screen;

/**
 * Screen for level complete.
 */
public class LevelComplete extends Screen implements Interactable {
  private final List<Interactable> CONTROLS = Arrays.asList(new Interactable[] { this });
  private final List<Text> textList = new ArrayList<Text>();
  
  // Create the game engine for a screen of size width/height.
  public LevelComplete(Game game, Manifest manifest) {
    super(game, manifest);
  }

  @Override
  public void onActivate() {
    int spacing = 20;
    Camera camera = game.getCamera();
    Loader loader = game.getLoader();

    // Add background.
    renderQueue.add(new TiledScrollingBackground(loader, "bg_tile.png", camera));
    
    // Create the text.
    textList.add(new Text(fonts.getFont(SimpleGameFonts.TITLE_FONT_ID), "Congratulations!", 0, spacing)
        .centerX(camera.getWidth()));
    textList.add(new Text(fonts.getFont(SimpleGameFonts.HUD_FONT_ID),
        "You got GMS Core to Dogfood!", 0, getLastTextY() + spacing)
        .centerX(camera.getWidth()));
    textList.add(new Text(fonts.getFont(SimpleGameFonts.HUD_FONT_ID),
        "Your score: " + game.getStatistics().getScore(), 0, getLastTextY() + spacing)
        .centerX(camera.getWidth()));
    textList.add(new Text(fonts.getFont(SimpleGameFonts.MENU_SELECTED_FONT_ID),
        "Continue", 0, getLastTextY() + 2 * spacing)
        .centerX(camera.getWidth()));
    
    renderQueue.addAll(textList);
  }
  
  @Override
  public void onDeactivate() {
    // Free text for GC
    textList.clear();
  }
  
  /**
   * Register callbacks for all gameplay events.
   */
  public void registerControls() {
    game.getControlHandler().registerEvent(EventType.MENU_CLOSE, new ControlEvent() {
      @Override
      public void onEvent() {
        manifest.setScreen(game, SimpleGame.MENU);
      }
    });
    game.getControlHandler().registerEvent(EventType.MENU_SELECT, new ControlEvent() {
      @Override
      public void onEvent() {
        manifest.setScreen(game, SimpleGame.MENU);
      }
    });
  }
  
  // Helper function for font layout.
  private int getLastTextY() {
    if (textList.isEmpty()) {
      return 0;
    }
    Text lastText = textList.get(textList.size() - 1);
    return lastText.getY() + lastText.getHeight();
  }
  
  @Override
  public List<Interactable> getInteractables() {
    return CONTROLS;
  }
  
  @Override
  public EventType getStartEvent() {
    return EventType.NONE;
  }

  @Override
  public EventType getStopEvent() {
    return EventType.MENU_SELECT;
  }

  @Override
  public boolean wasActivated(int screenX, int screenY) {
    return true;
  }
}
