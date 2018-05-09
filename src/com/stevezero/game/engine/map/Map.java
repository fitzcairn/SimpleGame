package com.stevezero.game.engine.map;

import java.util.LinkedList;
import java.util.List;

import com.stevezero.game.engine.Camera;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.consumable.Consumable;
import com.stevezero.game.engine.actor.enemy.Enemy;
import com.stevezero.game.engine.ai.Agent;
import com.stevezero.game.engine.graphics.rendering.Renderable;
import com.stevezero.game.geometry.Point2;


/**
 * Information about a Map.
 */
public abstract class Map {
  // Game camera
  protected final Camera camera;
  
  // Engine owning the map.
  protected final Engine engine;
  
  // Background renderables.
  protected final List<Renderable> renderQueue;
  
  // Actors in the map.
  protected final List<Actor> simulationQueue;
  
  // Enemies in the map.
  protected final List<Agent> agentQueue;

  // Size of the level, in global units.
  protected int width;
  protected int height;

  // Starting point for character, in global space.
  protected Point2 playerStart;

  public Map(Engine engine, List<Renderable> renderQueue, List<Actor> simulationQueue) {
    this.engine = engine;
    this.camera = engine.getCamera();
    this.renderQueue = renderQueue;
    this.simulationQueue = simulationQueue;
    this.agentQueue = new LinkedList<Agent>();
  }
  
  public List<Agent> getAgentsQueue() {
    return agentQueue;
  }
  
  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Point2 getPlayerStart() {
    return playerStart;
  }
  
  // Add an agent that will have AI applied.
  protected void addAgent(Agent agent) {
    agentQueue.add(agent);
  }
  
  // Add an enemy.
  protected void addEnemy(Enemy enemy) {
    simulationQueue.add(enemy);
  }

  // Add a consumable.
  protected void addConsumable(Consumable consumable) {
    simulationQueue.add(consumable);
  }
}
