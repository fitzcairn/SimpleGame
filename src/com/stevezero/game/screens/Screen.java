package com.stevezero.game.screens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.stevezero.game.Game;
import com.stevezero.game.assets.font.manager.Fonts;
import com.stevezero.game.controls.menu.Options;
import com.stevezero.game.controls.onscreen.Interactable;
import com.stevezero.game.engine.Camera;
import com.stevezero.game.engine.graphics.rendering.Renderable;
import com.stevezero.game.engine.graphics.rendering.Rendered;
import com.stevezero.game.geometry.Point2;
import com.stevezero.game.manifest.Manifest;


/**
 * Base class for all screens.
 */
public abstract class Screen {
  protected static final Point2 CAMERA_ORIGIN = new Point2();
  
  // Global game state.
  protected final Game game;

  // Manifest describing the game.
  protected final Manifest manifest;
  
  // Shortcut to fonts object, used in most screens.
  protected final Fonts fonts;

  // Rendering queue for the game world.
  protected final LinkedList<Renderable> renderQueue;

  // The options for the screen.
  protected Options options;
  
  // Queue for rendering.
  protected final List<Rendered> queue = new ArrayList<Rendered>();
  
  // Create the game engine for a screen of size width/height.
  public Screen(Game game, Manifest manifest) {
    this.game = game;
    this.manifest = manifest;
    this.fonts = manifest.getFonts(game.getLoader());
    renderQueue = new LinkedList<Renderable>();
  }
  
  /**
   * Activate this controller.
   */
  public void activate() {
    // Set the camera starting position to 0, 0.
    game.getCamera().moveTo(CAMERA_ORIGIN);
    onActivate();
  }

  /**
   * Subclasses must implement handler.
   */
  protected abstract void onActivate();
  
  /**
   * Deactivate this controller in preparation for someone else grabbing the screen.
   */
  public void deactivate() {
    // Clear the renderqueue to free up memory.
    renderQueue.clear();
    
    // Clear controls so the new screen can register its own.
    game.getControlHandler().clear();
    
    onDeactivate();
  }
  
  /**
   * Provide access to the camera.
   */
  public Camera getCamera() {
    return game.getCamera();
  }
  
  /**
   * Optional deactivate handler.
   */
  public void onDeactivate() {
  }
  
  /**
   * Get objects ready to be painted, in z-ordering.
   * Render to output buffer via Renderer.
   */
  public void render() {
    queue.clear();

    // Render everything.
    for (Renderable r : renderQueue) {
      r.render(this);
    }
    
    // Sort on z-index and return scene for rendering.
    Collections.sort(queue);
    
    // Render out to buffer.
    game.getRenderer().draw(queue);
  }
  
  /**
   * Add a rendered result to Rendered queue for writing to screen.
   */
  public Screen addToRenderedQueue(Rendered toRender) {
    queue.add(toRender);
    return this;
  }
  
  /**
   * Get any on-screen controls
   */
  public List<Interactable> getInteractables() {
    return Collections.emptyList();
  }
  
  /**
   * If this screen has physics, override to run the simulation.
   */
  public void update() {
    return;
  }

  /**
   * Register callbacks for events on this screen.
   */
  public abstract void registerControls();
  
  public Options getOptions() {
    return options;
  }
  
  /**
   * Get access to the game instance.
   */
  public Game getGame() {
    return game;
  }

  /**
   * @return the game Manifest instance.
   */
  public Manifest getManifest() {
    return manifest;
  }
}
