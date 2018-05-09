package com.stevezero.game.assets.services;

import java.util.HashMap;
import java.util.Map;

import com.stevezero.game.assets.Asset;
import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.sound.Sound;

/**
 * Asset manager for the com.stevezero.game.
 *
 * This class prevents memory explosion by ensuring that assets are only loaded once.
 */
public final class AssetManager {

  private static Map<String, Asset> ASSET_CACHE = new HashMap<String, Asset>();
  
  public static GameDrawable getDrawable(Loader loader, String id) {
    GameDrawable drawable = getFromCache(id);
    return drawable != null ? drawable : loadCache(id, loader.getDrawable(id));
  }
  
  public static Sound getSound(Loader loader, String id) {
    Sound sound = getFromCache(id);
    return sound != null ? sound : loadCache(id, loader.getSound(id));
  }

  @SuppressWarnings("unchecked")
  private static <E extends Asset> E getFromCache(String id) {
    if (ASSET_CACHE.containsKey(id)) {
      return (E) ASSET_CACHE.get(id);
    }
    return null;
  }

  private static <E extends Asset> E loadCache(String id, E asset) {
    if (ASSET_CACHE.containsKey(id)) {
      throw new RuntimeException("Bad load call: ID " + id + " collision.");
    }
    ASSET_CACHE.put(id, asset);
    return asset;    
  }
}
