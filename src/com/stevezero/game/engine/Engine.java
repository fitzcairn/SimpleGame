package com.stevezero.game.engine;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.stevezero.game.Game;
import com.stevezero.game.Values;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.controls.events.ControlEvent;
import com.stevezero.game.controls.events.ControlHandler;
import com.stevezero.game.controls.events.EventType;
import com.stevezero.game.controls.onscreen.Button;
import com.stevezero.game.controls.onscreen.Interactable;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.player.Player;
import com.stevezero.game.engine.graphics.ScreenElement;
import com.stevezero.game.engine.graphics.rendering.Renderable;
import com.stevezero.game.engine.map.Map;
import com.stevezero.game.engine.physics.Collisions;
import com.stevezero.game.geometry.Point2;
import com.stevezero.game.manifest.Manifest;
import com.stevezero.game.screens.Screen;
import com.stevezero.game.statistics.Statistics;
import com.stevezero.game.system.SystemManager;


/**
 * Gameplay engine for the game.  This class manages the engine loop.
 */
public class Engine extends Screen {
  // The game manifest.
  private final Manifest manifest;
  
  // Whether or not there is a current level being played.
  private boolean mapInProgress;

  // Camera coords when control is taken away from the gameplay engine.
  private final Point2 lastCameraPosition = new Point2();

  // Simulation queue for the game world.
  private final List<Actor> simulationQueue;
  
  // On-screen controls.
  private final List<Interactable> screenControls;
  
  
  //
  // Constructors -------------------->
  //
  
  public Engine(Game game, Manifest manifest) {
    super(game, manifest);
    this.manifest = manifest;
    
    // Create the queue for simulation.
    simulationQueue = new LinkedList<Actor>();

    // Create on-screen controls list.
    screenControls = new LinkedList<Interactable>();
  }

  
  //
  // API ----------------------------->
  // 
  
  /**
   * Provide access to the loader to create new elements.
   */
  public Loader getLoader() {
    return game.getLoader();
  }
  
  /**
   * Provide access to statistics for elements to update them.
   */
  public Statistics getStatistics() {
    return game.getStatistics();
  }
  
  /**
   * Provide access to the system manager.
   */
  public SystemManager getSystemManager() {
    return game.getSystemManager();
  }
  
  /**
   * Provide access to the current map.
   */
  public Map getCurrentMap() {
    return manifest.getMap(this, simulationQueue, renderQueue);
  }
  
  /**
   * Provide access to the current player.
   */
  public Player getCurrentPlayer() {
    return manifest.getPlayer(this);
  }
  
  /**
   * Add a sound effect to gameplay.
   */
  public int addSound(String id) {
    return game.addSound(id);
  }
  
  /**
   * Play a sound.
   */
  public void playSound(int poolId) {
    game.playSound(poolId);
  }
   
  /**
   * Is there a level currently being played?
   */
  public boolean isPlaying() {
    return mapInProgress;
  }
  
  /**
   * Restart the game.
   */
  public void restart() {
    // No longer playing map.
    mapInProgress = false;
  }
  
  /**
   * Level complete!
   */
  public void levelComplete() {
    // No longer playing map.
    mapInProgress = false;
    
    // Swap to win screen.
    manifest.setScreenLevelComplete(game);
  }
  
  /**
   * Player died.
   */
  public void playerDied() {
    // No longer playing map.
    mapInProgress = false;
    
    // Swap to loss screen.
    manifest.setScreenLoss(game);
  }
  
  /**
   * Add a new actor to the simulation queue.
   */
  public void addSimulatable(Actor simulatable) {
    simulationQueue.add(simulatable);
  }
  
  //
  // Event Handlers ------------------>
  //
  
  @Override
  public void onActivate() {
    // Were we playing already?
    if (mapInProgress) {
      // Move camera back to where we were.
      game.getCamera().moveTo(lastCameraPosition);

      // TODO(stevemar): Any other global state to restore?
      
      return;
    }
    
    // Create level
    createLevel();
    
    // Create on-screen controls.
    createOnScreenControls();
    
    // Ok, we're playing.
    mapInProgress = true;
  }

  @Override
  public void onDeactivate() {
    Camera camera = game.getCamera();
    
    // If we're not playing then nothing to do here.
    if (!mapInProgress) {
      return;
    }
    
    // Save the camera coordinates.
    lastCameraPosition.set(camera.getGlobalX(), camera.getGlobalY());    
  }
  
  
  //
  // Simulate and Render ------------->
  //
  
  /**
   * Execute a tick of the simulation.
   */
  @Override
  public void update() {
    if (!mapInProgress) {
      releaseMemory();
      return;
    }
    
    Player player = manifest.getPlayer(this);

    // Check loss conditions.
    if (player.isDead()) {
      playerDied();
      return;
    }
    
    // Update the player.
    player.update(this);
    
    // Update the camera.
    game.getCamera().update(this);
    
    // Update enemy AI
    manifest.getAi(this).applyAi(getCurrentMap().getAgentsQueue(), this);
    
    // Apply physics; resolve all collision effects.
    Collisions.resolveCollisions(this, simulationQueue);
    
    // Update all other simulatable actors.
    Iterator<Actor> it = simulationQueue.iterator();
    while(it.hasNext()) {
      Actor actor = it.next();

      if (actor.isDeleted()) {
        it.remove();
      } else if (actor != player && actor.isVisible(this)) {
        actor.update(this);
      }
    }
  }  
  
  /**
   * Get objects ready to be painted, in z-ordering.
   * Render to output buffer via Renderer.
   */
  public void render() {
    queue.clear();

    // Render the player.
    // TODO: this should never be null, camera should move.
    manifest.getPlayer(this).render(this);
    
    // Go through all the actors.  If they are visible, add to render queue.
    for (ScreenElement r : simulationQueue) {
      r.render(this);
    }
    
    // Go through non-simulatable elements (like backgrounds).
    for (Renderable r : renderQueue) {
      r.render(this);
    }
    
    if (Values.DEBUG_RENDER_CAMERA_BOX) {
      queue.add(game.getCamera().renderDebugPlayerBox(this));
    }
    
    // Sort on z-index and return scene for rendering.
    Collections.sort(queue);
    
    // Render out to buffer.
    game.getRenderer().draw(queue);
  }
  
  
  @Override
  public List<Interactable> getInteractables() {
    return screenControls;
  }

  
  //
  // Controls ------------------------>
  //
  
  /**
   * Register callbacks for all gameplay events.
   */
  public void registerControls() {
    ControlHandler controls = game.getControlHandler();
    
    // Set up player controls.
    manifest.getPlayer(this).setControls(controls);
    
    // Set up game-wide controls.
    controls.registerEvent(EventType.MENU_OPEN, new ControlEvent() {
      @Override
      public void onEvent() {
        manifest.setScreenPause(game);
      }
    });
    controls.registerEvent(EventType.DEBUG_MAP_GEN, new ControlEvent() {
      @Override
      public void onEvent() {
        playerDied();
      }
    });
  }
  
  
  //
  // Helpers ------------------------->
  //
  
  // Create a level.
  private void createLevel() {
    releaseMemory();

    // Create the map.
    manifest.getMap(this, simulationQueue, renderQueue);

    // Create the HUD.
    renderQueue.add(manifest.getHud(this));

    // Create and get a player.
    Player player = manifest.getPlayer(this);
    
    // Add the player to the simulation queue.
    simulationQueue.add(player);
    
    // Set the player starting position.
    player.resetPlayer(this);
    
    // Move the camera to the starting position.
    game.getCamera().centerOnPlayerBox(this);
    
    // Reset stats.
    game.getStatistics().clear();
  }
  
  // Helper to clear expensive memory in this class.
  private void releaseMemory() {
    renderQueue.clear();
    simulationQueue.clear();
    screenControls.clear();
    manifest.releaseMap();
    manifest.releasePlayer();
  }
  
  // Create on-screen controls.
  private void createOnScreenControls() {
    List<Button> buttons = manifest.getHud(this).createOnScreenControls(game);
    for (Button button : buttons) {
      screenControls.add(button);
      renderQueue.add(button);
    }
  }
}
