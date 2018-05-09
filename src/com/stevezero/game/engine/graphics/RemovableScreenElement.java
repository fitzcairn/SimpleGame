package com.stevezero.game.engine.graphics;

/**
 * Interface for an Element that is permanently removable from the game. 
 */
public interface RemovableScreenElement {
  
  /**
   * Mark this Element for deletion.
   */
  public void removeScreenElement();
}
