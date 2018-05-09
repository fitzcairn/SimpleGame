package com.stevezero.game.engine.ai;

import java.util.List;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.ai.processor.Processor;

/**
 * Apply AI to game actors.  An instance of an implementing class must be provided by the game
 * manifest.
 * 
 * Note: AI is applied via the the acyclic visitor pattern (http://goo.gl/nvIwwZ) to allow for
 * custom behaviors to be added easily without blowing up object size.
 */
public abstract class Ai {
  // AI processors used for this game.
  private final Processor processor;
  
  public Ai(Processor processor) {
    this.processor = processor;
  }
  
  /**
   * Called to apply AI to all eligible actors.
   */
  public void applyAi(List<Agent> agents, Engine engine) {
    for (Agent agent : agents) {
      if (agent.isAiReady(engine)) {
        agent.accept(processor, engine);
      }
    }
  }
}
