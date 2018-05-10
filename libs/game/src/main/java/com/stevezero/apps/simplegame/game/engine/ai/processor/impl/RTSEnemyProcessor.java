package com.stevezero.apps.simplegame.game.engine.ai.processor.impl;

import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Jarlsberg;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Kenafa;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Longhorn;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Manchego;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.ai.processor.Processor;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Jarlsberg;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Kenafa;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Longhorn;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Manchego;


public interface RTSEnemyProcessor extends Processor {
  public void processAi(Jarlsberg agent, Engine engine);
  public void processAi(Kenafa agent, Engine engine);
  public void processAi(Longhorn agent, Engine engine);
  public void processAi(Manchego agent, Engine engine);
}
