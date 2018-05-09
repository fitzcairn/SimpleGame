package com.stevezero.apps.simplegame.app.assets.sound.impl;

import com.stevezero.game.assets.sound.Sound;
import com.stevezero.game.assets.sound.SoundManager;

import java.util.ArrayList;
import java.util.List;

public class AppletSoundManager implements SoundManager {

  protected final List<Sound> soundRoster;

  public AppletSoundManager() {
    soundRoster = new ArrayList<Sound>();
  }
  
  @Override
  public int addSound(Sound sound, int priority) {
    int index = soundRoster.size();
    soundRoster.add(sound);
    return index;
  }
  
  @Override
  public SoundManager playSound(int id) {
    assert(id < soundRoster.size());
    soundRoster.get(id).play();
    return this;
  }
}
