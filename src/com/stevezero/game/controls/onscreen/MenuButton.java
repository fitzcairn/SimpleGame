package com.stevezero.game.controls.onscreen;

import com.stevezero.game.assets.font.Font;
import com.stevezero.game.controls.events.EventType;
import com.stevezero.game.engine.graphics.text.Text;
import com.stevezero.game.screens.Screen;

/**
 * TODO: Combine Options.Builder.Option into this class.
 *
 */
public class MenuButton extends Button {
  private final Text item;
  private final Font normalFont;
  private final Font selectedFont;
  private final EventType startEvent;
  private final EventType stopEvent;
  
  private boolean visible = true;
  private int width;

  public MenuButton(int x, int y, int width, String text, Font normalFont, Font selectedFont) {
    super(x, y);
    this.normalFont = normalFont;
    this.selectedFont = selectedFont;
    this.width = width;
    this.startEvent = EventType.NONE;
    this.stopEvent = EventType.MENU_SELECT;

    // Create visual element.
    this.item = new Text(normalFont, text, x, y).centerX(width);

    // Update hitbox based on new positioning.
    updateHitbox();
  }
  
  public MenuButton(int x, int y, int width, String text, Font normalFont, Font selectedFont,
      EventType startEvent, EventType stopEvent) {
    super(x, y);
    this.normalFont = normalFont;
    this.selectedFont = selectedFont;
    this.width = width;
    this.startEvent = startEvent;
    this.stopEvent = stopEvent;

    // Create visual element.
    this.item = new Text(normalFont, text, x, y).centerX(width);

    // Update hitbox based on new positioning.
    updateHitbox();
  }
  
  private void updateHitbox() {
    this.setX(this.item.getX());
    this.setY(this.item.getY());
    this.width = this.item.getWidth();
  }

  public MenuButton setVisible(boolean visible) {
    this.visible = visible;
    return this;
  }
  
  public boolean isVisible() {
    return visible;
  }
  
  @Override
  public int getHeight() {
    return item.getHeight();
  }
  
  @Override
  public int getWidth() {
    return this.width;
  }
  
  @Override
  public EventType getStartEvent() {
    select();
    return startEvent;
  }

  @Override
  public EventType getStopEvent() {
    unSelect();
    return stopEvent;
  }

  @Override
  public void render(Screen screen) {
    if (visible) {
      item.render(screen);
    }
  }

  public Text getText() {
    return item;
  }
  
  public MenuButton select() {
    item.setFont(selectedFont);
    return this;
  }
  
  public MenuButton unSelect() {
    item.setFont(normalFont);
    return this;
  }
}
