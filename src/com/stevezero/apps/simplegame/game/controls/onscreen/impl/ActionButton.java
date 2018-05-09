package com.stevezero.apps.simplegame.game.controls.onscreen.impl;

import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.controls.events.EventType;
import com.stevezero.game.controls.onscreen.Button;
import com.stevezero.game.engine.graphics.RemovableScreenElement;
import com.stevezero.game.engine.graphics.rendering.services.RenderedManager;
import com.stevezero.game.engine.graphics.sprite.Sprite;
import com.stevezero.game.screens.Screen;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.ActionButtonDown;
import com.stevezero.apps.simplegame.game.engine.graphics.motion.animation.impl.ActionButtonUp;

public class ActionButton extends Button implements RemovableScreenElement {
  public static final int TYPE_LEFT = 0;
  public static final int TYPE_RIGHT = 1;
  public static final int TYPE_JUMP = 2;
  public static final int TYPE_SHOOT = 3;
  
  public static final int UP = 0;
  public static final int DOWN = 1;

  private final int type;
  private final Sprite sprite;
  
  public ActionButton(int x, int y, int type, Loader loader) {
    super(x, y);
    this.type = type;

    // Init sprite.  We use a sprite to facilitate animations down the road.
    this.sprite = new Sprite(this);
    this.sprite.addAnimation(new ActionButtonUp(loader, this.type));
    this.sprite.addAnimation(new ActionButtonDown(loader, this.type));
  }
  
  public ActionButton( int type, Loader loader) {
    super(0, 0);
    this.type = type;

    // Init sprite.  We use a sprite to facilitate animations down the road.
    this.sprite = new Sprite(this);
    this.sprite.addAnimation(new ActionButtonUp(loader, this.type));
    this.sprite.addAnimation(new ActionButtonDown(loader, this.type));
  }
  
  public void setPosition(int x, int y) {
    setX(x);
    setY(y);
  }

  @Override
  public EventType getStartEvent() {
    sprite.setAnimationTo(DOWN);

    switch(type) {
      case(TYPE_LEFT):
        return EventType.GAME_MOVE_LEFT_START;
      case(TYPE_RIGHT):
        return EventType.GAME_MOVE_RIGHT_START;
      case(TYPE_JUMP):
        return EventType.GAME_JUMP_START;
      case(TYPE_SHOOT):
        return EventType.GAME_SHOOT_START;
      default:
        return EventType.NONE;
    }

  }

  @Override
  public EventType getStopEvent() {
    sprite.setAnimationTo(UP);

    switch(type) {
      case(TYPE_LEFT):
        return EventType.GAME_MOVE_LEFT_STOP;
      case(TYPE_RIGHT):
        return EventType.GAME_MOVE_RIGHT_STOP;
      case(TYPE_JUMP):
        return EventType.GAME_JUMP_STOP;
      case(TYPE_SHOOT):
        return EventType.GAME_SHOOT_STOP;
      default:
        return EventType.NONE;
    }
  }

  @Override
  public void removeScreenElement() {
    // Noop -- element is non-removable.
  }

  @Override
  public void render(Screen screen) {
    screen.addToRenderedQueue(RenderedManager.get(sprite.getNextFrame(), getX(), getY(), getZ()));
  }

  @Override
  public int getWidth() {
     return sprite.getWidth();
  }

  @Override
  public int getHeight() {
    return sprite.getHeight();
  }
}
