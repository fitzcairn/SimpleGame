package com.stevezero.game.external;

/**
 * Intended to be overridden for platform specific Achievements.
 */
public class Achievements {
  private static final String NONE = "";

  // Achievements
  public static final int LETS_GO_BANANAS = 0;
  public static final int BURRITO_GOD = 1;
  public static final int THRILLSON_ANGRY__THRILLSON_SMASH_GMS_CORE = 2;
  public static final int THRILLSON__HOW_ABOUT_TWINKLETOES = 3;
  public static final int CHERRY_PIE = 4;
  public static final int ITS_NOT_A_TURD__HONEST = 5;
  public static final int GMS_CORE_AVAILABLE_FOR_DOGFOOD = 6;
  public static final int ELDERBERRY_SUPREME = 7;
  
  
  public void showAchievements() {
    // No-op; override to implement.
  }
  
  public void unlock(String id) {
    // No-op; override to implement.
    System.out.println("Unlocking " + id + "!");
  }

  public void increment(String id, int numSteps) {
    // No-op; override to implement.
  }
  
  public String getIdFor(int achievement) {
    return NONE;
  }

}
