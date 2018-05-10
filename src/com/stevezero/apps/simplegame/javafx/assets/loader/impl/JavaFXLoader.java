package com.stevezero.apps.simplegame.javafx.assets.loader.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.stevezero.apps.simplegame.javafx.assets.drawable.impl.JavaFXDrawable;
import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.sound.Sound;
import com.stevezero.apps.simplegame.javafx.assets.sound.impl.JavaFXSound;

public class JavaFXLoader extends Loader {

  @Override
  public GameDrawable getDrawable(String id) {
    return new JavaFXDrawable(loadImage(id), id);
  }
  
  private BufferedImage loadImage(String id) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(this.getClass().getResource("/drawable/" + id));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return image;
  }

  @Override
  public List<String> getFileLines(String id) {
    List<String> lines = new ArrayList<String>();
    try {
      InputStream inputStream = this.getClass().getResource("/data/" + id).openStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while((line = bufferedReader.readLine()) != null){
        lines.add(line);
      } 
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }

  @Override
  public GameDrawable getMutableDrawable(int width, int height) {
    return new JavaFXDrawable(width, height);
  }

  @Override
  public Sound getSound(String id) {
    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.getClass().getResource("/sound/" + id));
      Clip clip = AudioSystem.getClip();
      clip.open(audioStream);
      return new JavaFXSound(clip, id);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }
    return null;
  }
}
