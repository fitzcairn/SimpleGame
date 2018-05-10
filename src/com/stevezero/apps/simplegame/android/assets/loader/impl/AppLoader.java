package com.stevezero.apps.simplegame.android.assets.loader.impl;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.stevezero.apps.simplegame.android.assets.drawable.impl.AppComposableDrawable;
import com.stevezero.apps.simplegame.android.assets.drawable.impl.AppDrawable;
import com.stevezero.apps.simplegame.android.assets.sound.impl.AppSound;
import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.sound.Sound;

import java.util.HashMap;
import java.util.Map;

public class AppLoader extends Loader {

  private final Resources resources;
  private final String packageName;
  private final Map<String, Integer> resourceIdMap;
  private final BitmapFactory.Options options;

  public AppLoader(Resources resources, String packageName) {
    this.resources = resources;
    this.packageName = packageName;
    this.resourceIdMap = new HashMap<String, Integer>();
    this.options = new BitmapFactory.Options();

    // Set atlas decode options.
    this.options.inPurgeable = false;
    this.options.inMutable = false;
  }

  @Override
  public GameDrawable getMutableDrawable(int width, int height) {
    return new AppComposableDrawable(width, height);
  }

  @Override
  public GameDrawable getDrawable(String id) {
    return new AppDrawable(BitmapFactory.decodeResource(resources, getDrawableResourceId(id),
        options), id);
  }

  @Override
  public Sound getSound(String id) {
    return new AppSound(getRawResourceId(id), id);
  }

  // Translate a sound filename to a resource.
  private int getRawResourceId(String id) {
    return getResourceId(id, "raw");
  }

  // Translate an image filename to a resource.
  private int getDrawableResourceId(String id) {
    return getResourceId(id, "drawable");
  }

  // Translate a filename to a resource.
  private int getResourceId(String id, String type) {
    // Chop off the file extension.
    if (id.indexOf('.') > 0) {
      id = id.substring(0, id.lastIndexOf('.'));
    }

    // Seen this before?
    if (resourceIdMap.containsKey(id)) {
      return (Integer)resourceIdMap.get(id);
    }

    // Translate using system API.
    int resourceId = getIdFor(id, type);
    resourceIdMap.put(id, resourceId);
    return resourceId;
  }

  private int getIdFor(String id, String type) {
    return resources.getIdentifier(id , type, packageName);
  }
}
