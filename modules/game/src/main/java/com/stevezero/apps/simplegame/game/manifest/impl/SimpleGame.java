package com.stevezero.apps.simplegame.game.manifest.impl;

import java.util.List;

import com.stevezero.apps.simplegame.game.assets.font.manager.impl.SimpleGameFonts;
import com.stevezero.apps.simplegame.game.engine.actor.player.impl.Tom;
import com.stevezero.apps.simplegame.game.engine.ai.impl.SimpleGameAi;
import com.stevezero.apps.simplegame.game.engine.hud.impl.SimpleGameHud;
import com.stevezero.apps.simplegame.game.engine.map.impl.GeneratedMap;
import com.stevezero.game.Game;
import com.stevezero.game.Values;
import com.stevezero.game.assets.font.manager.Fonts;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.player.Player;
import com.stevezero.game.engine.ai.Ai;
import com.stevezero.game.engine.graphics.rendering.Renderable;
import com.stevezero.game.engine.hud.Hud;
import com.stevezero.game.engine.map.Map;
import com.stevezero.game.manifest.Manifest;
import com.stevezero.game.screens.Screen;
import com.stevezero.apps.simplegame.game.screens.impl.Character;
import com.stevezero.apps.simplegame.game.screens.impl.How;
import com.stevezero.apps.simplegame.game.screens.impl.LevelComplete;
import com.stevezero.apps.simplegame.game.screens.impl.Loss;
import com.stevezero.apps.simplegame.game.screens.impl.Menu;
import com.stevezero.apps.simplegame.game.screens.impl.Pause;
import com.stevezero.apps.simplegame.game.screens.impl.Start;
import com.stevezero.apps.simplegame.game.screens.impl.Test;


/**
 * Game Manifest for SimpleGame.
 */
public class SimpleGame extends Manifest {
  // Game interaction states.
  public static final int START = 0;
  public static final int GAMEPLAY = 1;
  public static final int LEVEL_COMPLETE = 2;
  public static final int MENU = 3;
  public static final int CHARACTER = 4;
  public static final int PAUSE = 5;
  public static final int LOSS = 6;
  public static final int HOW = 7;
  public static final int TEST = 8;
  
  // Screen controllers.
  private final Screen[] screens = new Screen[9];
  
  private int menuSoundId = Manifest.NONE;
  
  @Override
  public Screen getScreen(Game game, int screenId) {
    // Load sound if hasn't been done already.
    if (menuSoundId == Manifest.NONE) {
      menuSoundId = game.addSound("select.wav");
    }
    
    // Lazily init screen.
    switch(screenId) {
      case TEST:
        if (screens[TEST] == null)
          screens[TEST] = new Test(game, this);
        break;
      case START:
        if (screens[START] == null)
          screens[START] = new Start(game, this);
        break;
      case GAMEPLAY:
        if (screens[GAMEPLAY] == null)
          screens[GAMEPLAY] = game.getEngine();
        break;
      case LEVEL_COMPLETE:
        if (screens[LEVEL_COMPLETE] == null)
          screens[LEVEL_COMPLETE] = new LevelComplete(game, this);
        break;
      case MENU:
        if (screens[MENU] == null)
          screens[MENU] = new Menu(game, this);
        break;
      case CHARACTER:
        if (screens[CHARACTER] == null)
          screens[CHARACTER] = new Character(game, this);
        break;
      case PAUSE:
        if (screens[PAUSE] == null)
          screens[PAUSE] = new Pause(game, this);
        break;
      case LOSS:
        if (screens[LOSS] == null)
          screens[LOSS] = new Loss(game, this);
        break;
      case HOW:
        if (screens[HOW] == null)
          screens[HOW] = new How(game, this);
        break;
      case Manifest.NONE:
      default:
        return null;
    }
    return screens[screenId];
  }

  @Override
  public Screen setScreenStart(Game game) {
    return activateScreen(game, START);
  }

  @Override
  public Screen setScreenLevelComplete(Game game) {
    return activateScreen(game, LEVEL_COMPLETE);
  }

  @Override
  public Screen setScreenLoss(Game game) {
    return activateScreen(game, LOSS);
  }

  @Override
  public Screen setScreenPause(Game game) {
    return activateScreen(game, PAUSE);
  }
  
  @Override
  public Screen setScreen(Game game, int screenId) {
    // Deactivate current screen, if there is any.
    deactivateCurrentScreen(game);
    
    // Activate new screen.
    Screen currentScreen = activateScreen(game, screenId);
    
    // Play a sound
    game.playSound(menuSoundId);
    
    return currentScreen;
  }
  
  private Screen activateScreen(Game game, int screenId) {
    Screen screen = getScreen(game, screenId);
    if (screen != null) {
      deactivateCurrentScreen(game);
      
      screen.activate();
      screen.registerControls();
      setScreenId(screenId);
    }
    return screen;
  }
  
  private void deactivateCurrentScreen(Game game) {
    // Deactivate current screen, if there is any.
    Screen currentScreen = getCurrentScreen(game);
    if (currentScreen != null) {
      currentScreen.deactivate();
    }
  }
  
  
  @Override
  public Ai createAndInitAi(Engine engine) {
    Ai gameAi = new SimpleGameAi();
    return gameAi;
  }

  @Override
  protected Player createAndInitPlayer(Engine engine) {
    Player tom = new Tom();
    tom.initPlayer(engine);
    return tom;
  }

  @Override
  protected Map createAndInitMap(Engine engine, List<Actor> simulatableQueue,
      List<Renderable> renderableQueue) {

    // Load the map.
    //map = new LoadedMap(this, "level.dat", renderQueue, simulationQueue);
    Map map = new GeneratedMap(engine, Values.LEVEL_WIDTH, Values.LEVEL_HEIGHT, renderableQueue,
        simulatableQueue);
    return map;
  }

  @Override
  protected Hud createAndInitHud(Engine engine) {
    return new SimpleGameHud(engine);
  }

  @Override
  protected Fonts createAndInitFonts(Loader loader) {
    return new SimpleGameFonts(loader);
  }
}
