package com.stevezero.apps.simplegame.game.screens.impl;

import java.util.Arrays;
import java.util.List;

import com.stevezero.apps.simplegame.game.manifest.impl.SimpleGame;
import com.stevezero.game.Game;
import com.stevezero.game.controls.events.ControlEvent;
import com.stevezero.game.controls.events.EventType;
import com.stevezero.game.controls.onscreen.Interactable;
import com.stevezero.game.engine.graphics.backgrounds.StaticBackground;
import com.stevezero.game.manifest.Manifest;
import com.stevezero.game.screens.Screen;

/**
 * Starting screen for the game.  Tapping on screen continues.
 */
public class Start extends Screen implements Interactable {
  private final List<Interactable> CONTROLS = Arrays.asList(new Interactable[] { this });
  
  // Create the game engine for a screen of size width/height.
  public Start(Game game, Manifest manifest) {
    super(game, manifest);
  }

  @Override
  public void onActivate() {
    // Create all renderable elements.
    renderQueue.add(new StaticBackground(game.getLoader().getDrawable("title.png")));
  }

  /**
   * Register callbacks for all gameplay events.
   */
  public void registerControls() {
    game.getControlHandler().registerEvent(EventType.MENU_SELECT, new ControlEvent() {
      @Override
      public void onEvent() {
        // Start com.stevezero.game.
        manifest.setScreen(game, SimpleGame.MENU);
      }
    });
  }

  /**
   * Return on-screen controls for event testing.
   */
  @Override
  public List<Interactable> getInteractables() {
    return CONTROLS;
  }
  
  @Override
  public boolean wasActivated(int screenX, int screenY) {
    return true;
  }

  @Override
  public EventType getStartEvent() {
    return EventType.NONE;
  }

  @Override
  public EventType getStopEvent() {
    return EventType.MENU_SELECT;
  }
}
