package com.stevezero.game.assets;

/**
 * Common abstract class implemented by all assets.
 * 
 * Note: this is an abstract class instead of an interface to facilitate asset caching.
 */
public abstract class Asset {
  public static final int NONE = -1;
  
  private final String id;
  private final int resId;

  public Asset(String id, int resId) {
    this.id = id;
    this.resId = resId;
  }
  
  public Asset(String id) {
    this.id = id;
    this.resId = NONE;
  }

  /**
   * @return a unique ID for this asset.
   */
  public String getId() {
    return id;
  }

  /**
   * @return a unique integer ID for this asset, i.e. Android resource ID.
   */
  public int getResId() {
    return resId;
  }
}
