package com.stevezero.apps.simplegame.game.engine.map.impl;

import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Cherry;
import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Date;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Kenafa;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Longhorn;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.CheeseBlock;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.graphics.backgrounds.ScrollingBackground;
import com.stevezero.game.engine.graphics.rendering.Renderable;
import com.stevezero.game.engine.map.Map;
import com.stevezero.game.geometry.Point2;
import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Banana;
import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Cherry;
import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Date;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Kenafa;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Longhorn;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.CheeseBlock;

import java.util.LinkedList;
import java.util.List;


/**
 * A Map loaded from a file.
 */
public final class LoadedMap extends Map {
  
  public LoadedMap(Engine engine, String dataFile, LinkedList<Renderable> renderQueue,
      LinkedList<Actor> simulationQueue) {
    super(engine, renderQueue, simulationQueue);
    
    // Load the map from the input file.
    loadMapFromFile(engine.getLoader(), dataFile);
  }
  
  private void loadMapFromFile(Loader loader, String dataFile) {
    // Load from file
    int row = 0;
    int col = 0;
    int x = 0;
    int y = 0;
    List<String> mapLines = loader.getFileLines(dataFile);
    
    // First lines are width, height, and background.
    this.width = Integer.parseInt(mapLines.get(row++));
    this.height = Integer.parseInt(mapLines.get(row++));

    // TODO(stevemar): Error detection.
    
    // Add background.
    renderQueue.add(new ScrollingBackground(loader, mapLines.get(row++)));
    
    // Go through each line char by char and add items.
    // Each character represents a 48x48 tile.
    while(row < mapLines.size()) {
      col = 0;
      for (char block : mapLines.get(row++).toCharArray()) {
        x = col * 48;
        y = (row - 4) * 48;
        switch(block) {
          case('o'):
            simulationQueue.add(new CheeseBlock(loader, x, y));
          break;
          case('l'):
            addEnemy(new Longhorn(engine, x, y));
          break;
          case('k'):
            addEnemy(new Kenafa(engine, x, y));
          break;
          case('h'):
            addConsumable(new Banana(engine, x, y));
          break;
          case('p'):
            addConsumable(new Cherry(engine, x, y));
          break;
          case('s'):
            addConsumable(new Date(engine, x, y));
          break;          
          case('x'):
            this.playerStart = new Point2(x, y);
          break;
          default:
          break;
        }
        col++;
      }
    }
  }
}
