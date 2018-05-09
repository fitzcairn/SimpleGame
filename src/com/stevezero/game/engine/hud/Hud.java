package com.stevezero.game.engine.hud;

import java.util.List;

import com.stevezero.game.Game;
import com.stevezero.game.controls.onscreen.Button;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.engine.graphics.ScreenElement;

/**
 * Manages the HUD shown during gameplay.
 * 
 * TODO: move more generic functionality into here for future games.
 */
public abstract class Hud extends ScreenElement {
  protected final Engine engine;

  public Hud(Engine engine) {
    super(0, 0, Layers.HUD);
    this.engine = engine;
  }
  
  public abstract List<Button> createOnScreenControls(Game game);
}
