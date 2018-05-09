package com.stevezero.game.assets.loader;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.sound.Sound;

import java.util.List;

/**
 * Loading class responsible for loading assets.
 * 
 * Rather than wrapping ID between systems (app uses strings, Android uses ints), leaving
 * unneeded methods as UnsupportedOperationException().  Horrible, I know.
 */
abstract public class Loader {
  abstract public GameDrawable getMutableDrawable(int width, int height);

  public abstract GameDrawable getDrawable(String id);

  public abstract Sound getSound(String id);

  // Optional; used only for debugging.
  public List<String> getFileLines(String file) {
    throw new UnsupportedOperationException();
  }
}
