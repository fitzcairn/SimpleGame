package com.stevezero.game.assets.sound;


/**
 * A sound manager.  Manages sound clips across the game.
 */
public interface SoundManager {

  /**
   * Add a sound to the roster, returning an int ID.
   */
  public int addSound(Sound sound, int priority);

  /**
   * Play a sound by id.
   */
  public SoundManager playSound(int id);
}
