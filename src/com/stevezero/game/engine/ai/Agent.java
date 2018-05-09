package com.stevezero.game.engine.ai;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.ai.processor.Processor;

/**
 * Interface for visited AI elements.
 * 
 * Any object that requires AI behaviors applied must implement this interface.
 */
public interface Agent {
  void accept(Processor behavior, Engine engine);
  
  boolean isAiReady(Engine engine);
}
