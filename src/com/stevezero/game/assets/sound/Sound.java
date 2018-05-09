package com.stevezero.game.assets.sound;

import com.stevezero.game.assets.Asset;

/**
 * Class for a game sound.
 */
public abstract class Sound extends Asset {
  public Sound(String id) {
    super(id, -1);
  }

  public Sound(String id, int resId) {
    super(id, resId);
  }
  
  /**
   * Play this sound in a loop.
   */
  abstract public void loop(int count);
  
  /**
   * Play this sound.
   */
  abstract public void play();
  
  /**
   * Stop playing this sound.
   */
  abstract public void stop();
}
