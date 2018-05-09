package com.stevezero.game.external;

/**
 * Intended to be overridden for platform specific Leaderboards.
 */
public class Leaderboards {
  
  public void showLeaderboards() {
    // No-op; override to implement.
  }
  
  public void submitScoreTo(String id, long score) {
    // No-op; override to implement.
  }
  
  public void submitScore(long score) {
    submitScoreTo("NOOP", score);
  }
}
