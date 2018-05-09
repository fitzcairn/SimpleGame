package com.stevezero.game.manifest;

import java.util.List;

import com.stevezero.game.Game;
import com.stevezero.game.assets.font.manager.Fonts;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.player.Player;
import com.stevezero.game.engine.ai.Ai;
import com.stevezero.game.engine.graphics.rendering.Renderable;
import com.stevezero.game.engine.hud.Hud;
import com.stevezero.game.engine.map.Map;
import com.stevezero.game.screens.Screen;

/**
 * Describes a full game.  Subclasses must implement this class to set up and run their game.
 * 
 * Note: this is in effect a crude substitute for dependency injection.
 */
public abstract class Manifest {
  public static final int NONE = -1;

  protected Player player;
  protected Map map;
  protected Ai ai;
  protected Hud hud;
  protected Fonts fonts;
  
  // Current screen controller
  private int currentScreen = NONE;

  
  //
  // Screens ------------------------->
  //
  
  public abstract Screen setScreen(Game game, int screenId);

  public abstract Screen getScreen(Game game, int screenId);
  
  public Screen getCurrentScreen(Game game) {
    return getScreen(game, currentScreen);
  }

  public abstract Screen setScreenStart(Game game);
  
  public abstract Screen setScreenLevelComplete(Game game);

  public abstract Screen setScreenLoss(Game game);

  public abstract Screen setScreenPause(Game game);
  
  // Helper function
  protected void setScreenId(int screenId) {
    currentScreen = screenId;
  }
  
  
  //
  // Fonts --------------------------->
  //
  
  public Fonts getFonts(Loader loader) {
    // Lazily init AI, as this is a very heavy object.
    if (fonts == null) {
      fonts = createAndInitFonts(loader);
    }
    return fonts;
  }

  /**
   * Override this to init the Ai object
   */
  protected abstract Fonts createAndInitFonts(Loader loader);
  
  
  //
  // In-Game HUD --------------------->
  //
  
  public Hud getHud(Engine engine) {
    // Lazily init AI, as this is a very heavy object.
    if (hud == null) {
      hud = createAndInitHud(engine);
    }
    return hud;
  }

  /**
   * Override this to init the Ai object
   */
  protected abstract Hud createAndInitHud(Engine engine);
  
  
  //
  // AI ------------------------------>
  //
  
  /**
   * Provide an instantiated AI object to the engine.
   * @return an object that implements the Ai interface.
   */
  public Ai getAi(Engine engine) {
    // Lazily init AI, as this is a very heavy object.
    if (ai == null) {
      ai = createAndInitAi(engine);
    }
    return ai;
  }

  /**
   * Override this to init the Ai object
   */
  protected abstract Ai createAndInitAi(Engine engine);

  
  //
  // Player -------------------------->
  //
  
  /**
   * Get the player reference, creating one if there is not one.
   * @return a reference to the Player object for this game.
   */
  public Player getPlayer(Engine engine) {
    // Lazily init player, as this is a very heavy object.
    if (player == null) {
      player = createAndInitPlayer(engine);
    }
    return player;
  }
    
  /**
   * Release the reference to the Player object to allow for GC collection.
   */
  public void releasePlayer() {
    player = null;
  }
  
  /**
   * Override this to init the player object
   */
  protected abstract Player createAndInitPlayer(Engine engine);
  
  
  //
  // Map ----------------------------->
  //
  
  public Map getMap(Engine engine, List<Actor> simulatableQueue,
      List<Renderable> renderableQueue) {
    // Lazily init the Map, as this is a very heavy object.
    if (map == null) {
      map = createAndInitMap(engine, simulatableQueue, renderableQueue);
    }
    return map;
  }
    
  /**
   * Release the reference to the Map object to allow for GC collection.
   */
  public void releaseMap() {
    map = null;
  }
  
  /**
   * Override this to init the Map object
   */
  protected abstract Map createAndInitMap(Engine engine, List<Actor> simulatableQueue,
      List<Renderable> renderableQueue);
}
