package com.stevezero.apps.simplegame.game.engine.ai.impl;

import com.stevezero.apps.simplegame.game.engine.ai.processor.impl.RTSEnemyProcessors;
import com.stevezero.game.engine.ai.Ai;
import com.stevezero.apps.simplegame.game.engine.ai.processor.impl.RTSEnemyProcessors;

/**
 * AI for enemies in GMS Crusader.  To be provided by the Manifest.
 */
public final class RTSAi extends Ai {
  
  public RTSAi() {
    super(new RTSEnemyProcessors());
  } 
}
