package com.stevezero.apps.simplegame.android.assets.sound.impl;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.stevezero.game.assets.sound.Sound;
import com.stevezero.game.assets.sound.SoundManager;

public class AppSoundManager implements SoundManager {
  private final Context context;
  private final SoundPool soundPool;

  public AppSoundManager(Context context) {
    this.soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    this.context = context;
  }
  
  @Override
  public int addSound(Sound sound, int priority) {
    AppSound appSound = (AppSound)sound;
    int poolId = soundPool.load(context, appSound.getResId(), priority);
    appSound.finalizeInPool(poolId, soundPool, priority);
    return poolId;
  }

  @Override
  public SoundManager playSound(int poolId) {
    soundPool.play(poolId, 1.0f, 1.0f, 0, 0, 1.0f);
    return this;
  }
}
