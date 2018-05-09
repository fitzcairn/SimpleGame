package com.stevezero.apps.simplegame.game.engine.hud.impl;

import java.util.ArrayList;
import java.util.List;

import com.stevezero.game.Game;
import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.assets.font.manager.Fonts;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.assets.services.AssetManager;
import com.stevezero.game.controls.events.EventType;
import com.stevezero.game.controls.onscreen.Button;
import com.stevezero.game.controls.onscreen.MenuButton;
import com.stevezero.game.engine.Camera;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.graphics.Layers;
import com.stevezero.game.engine.graphics.rendering.services.RenderedManager;
import com.stevezero.game.engine.graphics.text.Text;
import com.stevezero.game.engine.hud.Hud;
import com.stevezero.game.screens.Screen;
import com.stevezero.game.statistics.Statistics;
import com.stevezero.apps.simplegame.game.assets.font.manager.impl.SimpleGameFonts;
import com.stevezero.apps.simplegame.game.controls.onscreen.impl.ActionButton;

/**
 * Manages the HUD shown during gameplay.
 */
public class SimpleGameHud extends Hud {
  // TODO(stevemar): Localize!
  private static final String SCORE = "Score: ";

  // All images are 35x35
  private static final int ASSET_DIM = 35;
  private static final int HEALTH_PAD = 3;
  
  private final Text scoreText;
  private final Fonts fonts;
  private final GameDrawable healthImage;
  private final List<Button> buttons = new ArrayList<Button>();

  public SimpleGameHud(Engine engine) {
    super(engine);
    this.fonts = engine.getManifest().getFonts(engine.getLoader());
    this.healthImage = AssetManager.getDrawable(engine.getLoader(), "atlas_health.png");

    // TODO(stevemar): Compose the HUD using the screen resolution.
    this.scoreText = new Text(fonts.getFont(SimpleGameFonts.HUD_FONT_ID), SCORE, 0, 0);
  }

  private String formatScore(long score) {
    return SCORE + Long.toString(score);
  }

  @Override
  public void render(Screen screen) {
    Statistics stats = engine.getStatistics();
    int healthX = engine.getCamera().getWidth() -
        ((engine.getCurrentPlayer().getMaxHealth() + 1) / 2) * (ASSET_DIM + HEALTH_PAD);
    int healthY = (scoreText.getHeight() - ASSET_DIM) / 2;
    
    // TODO(stevemar): Update HUD with better info.
    screen.addToRenderedQueue(RenderedManager.get(
            scoreText.setText(formatScore(stats.getScore())).toDrawable(), x, y, Layers.HUD));

    // Build health image bar.  2 HP == one android.
    for (int i = 0; i < engine.getCurrentPlayer().getHealth() / 2; i++) {
      screen.addToRenderedQueue(RenderedManager.get
          (healthImage.getSelection(0, 0, ASSET_DIM, ASSET_DIM), healthX,
          healthY, Layers.HUD));
      healthX += (ASSET_DIM + HEALTH_PAD);
    }
    if (engine.getCurrentPlayer().getHealth() % 2 == 1) {
      // Add half droid.
      screen.addToRenderedQueue(RenderedManager.get(
          healthImage.getSelection(ASSET_DIM, 0, ASSET_DIM, ASSET_DIM),
          healthX, healthY, Layers.HUD));      
    }
  }

  @Override
  public List<Button> createOnScreenControls(Game game) {
    // Lazy init.
    if (!buttons.isEmpty()) return buttons;
    
    ActionButton button1;
    ActionButton button2;
    int pad = 20;
    int bottomPad = 140;
    Camera camera = game.getCamera();
    Loader loader = game.getLoader();
    
    // Left side: Left Arrow + Jump
    button1 = new ActionButton(pad, camera.getHeight() - bottomPad, ActionButton.TYPE_LEFT, loader);
    buttons.add(button1);
    
    button2 = new ActionButton(ActionButton.TYPE_JUMP, loader);
    button2.setPosition(pad + (button1.getWidth() - button2.getWidth()) / 2,
        button1.getY() - button2.getHeight() - pad);
    buttons.add(button2);
    
    // Right side: Right Arrow + Shoot
    button1 = new ActionButton(camera.getWidth() - pad - button1.getWidth(),
        camera.getHeight() - bottomPad, ActionButton.TYPE_RIGHT, loader);
    buttons.add(button1);
    
    // Burrito button.
    button2 = new ActionButton(ActionButton.TYPE_SHOOT, loader);
    button2.setPosition(button1.getX() + (button1.getWidth() - button2.getWidth()) / 2,
        button1.getY() - button2.getHeight() - pad);
    buttons.add(button2);
    
    // Top menu button.
    MenuButton button =
        new MenuButton(0, 0, camera.getWidth(), "Menu",
            fonts.getFont(SimpleGameFonts.MENU_FONT_ID),
            fonts.getFont(SimpleGameFonts.MENU_SELECTED_FONT_ID),
            EventType.NONE, EventType.MENU_OPEN);
    buttons.add(button);
    
    return buttons;
  }
}
