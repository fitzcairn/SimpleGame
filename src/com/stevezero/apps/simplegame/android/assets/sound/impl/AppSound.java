package com.stevezero.apps.simplegame.android.assets.sound.impl;

import android.media.SoundPool;

import com.stevezero.game.assets.sound.Sound;

public class AppSound extends Sound {
  private SoundPool soundPool;
  private int poolId;
  private int priority;

  public AppSound(int resId, String id) {
    super(id, resId);
  }

  public void finalizeInPool(int poolId, SoundPool soundPool, int priority) {
    this.poolId = poolId;
    this.soundPool = soundPool;
    this.priority = priority;
  }

  @Override
  public void loop(int count) {
    assert(soundPool != null);
    soundPool.play(poolId, 1.0f, 1.0f, 0, -1, 1.0f);
  }

  @Override
  public void play() {
    assert(soundPool != null);
    stop();
    soundPool.play(poolId, 1.0f, 1.0f, 0, 0, 1.0f);
  }

  @Override
  public void stop() {
    assert(soundPool != null);
    soundPool.stop(poolId);
  }
}
