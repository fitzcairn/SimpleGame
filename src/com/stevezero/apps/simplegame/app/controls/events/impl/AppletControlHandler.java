package com.stevezero.apps.simplegame.app.controls.events.impl;

import java.awt.event.KeyEvent;

import com.stevezero.apps.simplegame.app.controls.events.AppletEvent;
import com.stevezero.game.Game;
import com.stevezero.game.controls.events.ControlHandler;
import com.stevezero.game.controls.events.EventType;
import com.stevezero.game.controls.onscreen.Interactable;

/**
 * Translates keyboard actions for when this game runs as an com.stevezero.apps.simplegame.app.
 */
public final class AppletControlHandler extends ControlHandler {

  public void onEventStart(AppletEvent event, Game game) {
    if (event.hasKeyEvent()) {
      // Transate from an Applet KeyEvent to a control event.
      switch (event.getKeyEvent().getKeyCode()) {
        case KeyEvent.VK_ESCAPE:
          handleEvent(EventType.MENU_OPEN);
          handleEvent(EventType.MENU_CLOSE);
          break;
        case KeyEvent.VK_UP:
          handleEvent(EventType.MENU_UP);
          break;
        case KeyEvent.VK_DOWN:
          handleEvent(EventType.GAME_DUCK_START);
          handleEvent(EventType.MENU_DOWN);
          break;
        case KeyEvent.VK_LEFT:
          handleEvent(EventType.GAME_MOVE_LEFT_START);
          break;  
        case KeyEvent.VK_RIGHT:
          handleEvent(EventType.GAME_MOVE_RIGHT_START);
          break;
        case KeyEvent.VK_SPACE:
          handleEvent(EventType.GAME_JUMP_START);
          break;
        case KeyEvent.VK_ENTER:
          handleEvent(EventType.GAME_SHOOT_START);
          handleEvent(EventType.MENU_SELECT);
          break;
        case KeyEvent.VK_M:
          handleEvent(EventType.DEBUG_MAP_GEN);
          break;
      }
    }
    if (event.hasMouseEvent()) {
      // Run through the screen controls and detect which one was hit.
      for (Interactable control : game.getInteractables()) {
        if (control.wasActivated(event.getMouseEvent().getX(), event.getMouseEvent().getY())) {
          handleEvent(control.getStartEvent());
        }
      }
    }
  }

  public void onEventStop(AppletEvent event, Game game) {
    if (event.hasKeyEvent()) {
      // Transate from an Applet KeyEvent to a control event.
      switch (event.getKeyEvent().getKeyCode()) {
        case KeyEvent.VK_DOWN:
          handleEvent(EventType.GAME_DUCK_STOP);          
          break;
        case KeyEvent.VK_LEFT:
          handleEvent(EventType.GAME_MOVE_LEFT_STOP);          
          break;
        case KeyEvent.VK_RIGHT:
          handleEvent(EventType.GAME_MOVE_RIGHT_STOP);          
          break;
        case KeyEvent.VK_SPACE:
          handleEvent(EventType.GAME_JUMP_STOP);          
          break;
        case KeyEvent.VK_ENTER:
          handleEvent(EventType.GAME_SHOOT_STOP);          
          break;
      }
    }
    if (event.hasMouseEvent()) {
      // Run through the screen controls and detect which one was hit.
      for (Interactable control : game.getInteractables()) {
        if (control.wasActivated(event.getMouseEvent().getX(), event.getMouseEvent().getY())) {
          handleEvent(control.getStopEvent());
        }
      }
    }
  }
}