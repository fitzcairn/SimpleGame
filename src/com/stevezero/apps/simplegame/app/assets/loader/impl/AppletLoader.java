package com.stevezero.apps.simplegame.app.assets.loader.impl;

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

import com.stevezero.apps.simplegame.app.assets.drawable.impl.AppletDrawable;
import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.sound.Sound;
import com.stevezero.apps.simplegame.app.assets.sound.impl.AppletSound;

public class AppletLoader extends Loader {

  private final URL baseURL;
  
  public AppletLoader(URL baseURL) {
    this.baseURL = baseURL;
  }
  
  @Override
  public GameDrawable getDrawable(String id) {
    return new AppletDrawable(loadImage(baseURL, id), id);
  }
  
  private BufferedImage loadImage(URL baseURL, String id) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(new URL(baseURL, "../res/drawable/" + id));
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
      URL url = new URL(baseURL, "../res/data/" + id);
      InputStream inputStream = url.openStream();
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
    return new AppletDrawable(width, height);
  }

  @Override
  public Sound getSound(String id) {
    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(
          new URL(baseURL, "../res/sound/" + id));
      Clip clip = AudioSystem.getClip();
      clip.open(audioStream);
      return new AppletSound(clip, id);
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
