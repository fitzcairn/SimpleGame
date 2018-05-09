package com.stevezero.game.system;

/**
 * Interface wrapping access to system calls.
 * 
 * Each supporting platform must implement this.
 */
public interface SystemManager {
  
  /**
   * Quit the game.
   */
  public void quit();

  /**
   * Get the viewport width.
   */
  public int getViewWidth();

  /**
   * Get the viewport height.
   */
  public int getViewHeight();
  
  /**
   * Get the current time, in millis.
   */
  public long getCurrentTimeMillis();
}
