package com.stevezero.apps.simplegame.app.assets.sound.impl;

import com.stevezero.game.assets.sound.Sound;

import javax.sound.sampled.Clip;

public class AppletSound extends Sound {
  private Clip clip;
  
  public AppletSound(Clip clip, String id) {
    super(id);
    this.clip = clip;
  }
  
  @Override
  public void loop(int count) {
    clip.loop(count);
  }

  @Override
  public void play() {
    clip.stop();
    clip.setFramePosition(0);
    clip.start();
  }

  @Override
  public void stop() {
    clip.stop();
  }
}
