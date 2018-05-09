package com.stevezero.game.engine.ai.processor;

/**
 * Base interfaces for AI processors (visitors) for each Agent for which AI behaviors must be
 * processed (i.e. visited).
 * 
 * Implementing games should define at least one extending interface interface containing the
 * visitor methods to be called by visiting objects, where the type of the visited object determines
 * which visitor method is called.
 * 
 * Example for Enemy objects:
 *   public void processAi(Enemy agent, Engine engine);
 *   
 * The class implementing processAI(Enemy agent, Engine engine) would then define what behaviors
 * to apply to the agent given the current engine context.
 */
public interface Processor {
  // Intentionally left empty; extend this interface with game-specific interfaces as per above.
}
