package com.stevezero.game;

import java.util.List;

import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.assets.sound.SoundManager;
import com.stevezero.game.controls.events.ControlHandler;
import com.stevezero.game.controls.onscreen.Interactable;
import com.stevezero.game.engine.Camera;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.graphics.rendering.Renderer;
import com.stevezero.game.external.services.ServiceManager;
import com.stevezero.game.manifest.Manifest;
import com.stevezero.game.statistics.Statistics;
import com.stevezero.game.system.SystemManager;

/**
 * Main class controlling the game.
 * 
 * Example main game loop iteration:
 * 
 *     // Start a game loop iteration.
 *     game.start();
 *     
 *     // Tick the engine forward.
 *     game.update();
 *
 *     // Draw a frame using the renderer, which will render it to a backbuffer.
 *     game.render();
 *     
 *     // Do any cleanup.
 *     game.finalize();
 * 
 */
public final class Game { 
  // Elements
  private final ControlHandler controls;
  private final Statistics statistics;
  private final SystemManager systemManager;
  private final Engine engine;
  private final Renderer renderer;
  private final Loader loader;
  private final SoundManager soundManager;
  private final Camera camera;
  private final ServiceManager serviceManager;
  private final Manifest manifest;
  

  // Last update time.
  private long lastUpdateTime;
  
  public Game(SystemManager systemManager, Renderer renderer, Loader loader,
      ControlHandler controls, SoundManager sounds, ServiceManager serviceManager, Manifest game) {
    this.systemManager = systemManager;
    this.renderer = renderer;
    this.loader = loader;
    this.controls = controls;
    this.soundManager = sounds;
    this.serviceManager = serviceManager;
    this.manifest = game;
        
    // Create the stats object, and hand it a reference to the service manager for score saving.
    this.statistics = new Statistics(serviceManager);
    
    // Create a camera for the game.
    this.camera = Camera.newBuilder()
      .setCameraWidth(Values.VIEW_WIDTH)
      .setCameraHeight(Values.VIEW_HEIGHT)
      .setCameraX(0)
      .setCameraY(0)
      .setPlayerTopBoundry(100)
      .setPlayerBottomBoundry(444)
      .setPlayerRightBoundry(500)
      .setPlayerLeftBoundry(300)
      .build();
    
    // Instantiate the game engine.
    this.engine = new Engine(this, manifest);

    // Set the start screen.
    manifest.setScreenStart(this);
  }
  
  /**
   * Accessors for managers.
   */
  public SystemManager getSystemManager() {
    return this.systemManager;
  }
  
  public Camera getCamera() {
    return this.camera;
  }
  
  public Renderer getRenderer() {
    return this.renderer;
  }
  
  public Engine getEngine() {
    return this.engine;
  }
  
  public Loader getLoader() {
    return this.loader;
  }
  
  public ControlHandler getControlHandler() {
    return this.controls;
  }
  
  public SoundManager getSoundManager() {
    return this.soundManager;
  }
  
  public Statistics getStatistics() {
    return this.statistics;
  }
  
  public ServiceManager getServiceManager() {
    return this.serviceManager;
  }
  
  /**
   * Quit the game.
   */
  public void quit() {
    // TODO(stevemar): Any teardown?
    systemManager.quit();
  }
  
  /**
   * Restart the gameplay.
   */
  public void restart() {
    engine.restart();
    manifest.setScreenStart(this);
  }
  
  /**
   * Get the interactable elements on the screen.
   */
  public List<Interactable> getInteractables() {
    return manifest.getCurrentScreen(this).getInteractables();
  }
  
  /**
   * Start a game loop iteration.
   */
  public void start() {
    lastUpdateTime = systemManager.getCurrentTimeMillis();
  }  
  
  /**
   * Advance the game simulation.
   */
  public void update() {
    manifest.getCurrentScreen(this).update();
  }
  
  /**
   * Render the screen.
   */
  public void render() {
    manifest.getCurrentScreen(this).render();
  }

  /**
   * Finalize an iteration, doing any cleanup.
   */
  public void finalize() {
    sleepUntilNextFrame();
  }
  
  /**
   * Add a sound effect to gameplay.
   */
  public int addSound(String id) {
    return getSoundManager().addSound(AssetManager.getSound(getLoader(), id), 0);
  }
  
  /**
   * Play a sound.
   */
  public void playSound(int poolId) {
    getSoundManager().playSound(poolId);
  }
  
  // Try to ensure consistent FPS
  private void sleepUntilNextFrame() {
    long thisFrameMillis = systemManager.getCurrentTimeMillis() - lastUpdateTime;
    int minFrameMillis = 1000 / Values.TARGET_FPS;
    if (thisFrameMillis < minFrameMillis) {
      try {
        Thread.sleep(minFrameMillis - thisFrameMillis);
      } catch (InterruptedException e) {
        // Ignored on purpose.
      }
    }
  }
}
