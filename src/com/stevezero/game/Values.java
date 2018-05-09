package com.stevezero.game;

/**
 * Tunable values for the game.
 */
public final class Values {
  public static final String TITLE = "I Love Meat";
  
  /**
   * Gravity, in pixels.
   */
  public static final float GRAVITY = 1.0f;
  
  
  /**
   * Platforms
   */
  public static final int APPLET = 1;
  public static final int ANDROID = 2;

  
  /**
   * Size of generated levels
   */
  public static final int LEVEL_WIDTH = 4000;
  public static final int LEVEL_HEIGHT = 2000;
  
  
  /**
   * Target FPS
   */
  public static final int TARGET_FPS = 30;
  

  // Debug flags -->
  
  /**
   * Whether or not to visualize the camera box.
   */
  public static final boolean DEBUG_RENDER_CAMERA_BOX = false;

  /**
   * Render wireframes
   */
  public static final boolean DEBUG_RENDER_WIREFRAMES = false;
  
  /**
   * Come get some!
   */
  public static final boolean DEBUG_GOD_MODE = false;
  
  /**
   * Native game view size.
   */
  public static final int VIEW_WIDTH = 800;
  public static final int VIEW_HEIGHT = 480;
}
