package com.stevezero.game.controls.events;

/**
 * Event types we care about.  Sorry for the enum, Android.
 */
public enum EventType {
  // Gameplay
  GAME_DUCK_START,
  GAME_DUCK_STOP,
  GAME_JUMP_START,
  GAME_JUMP_STOP,
  GAME_MOVE_LEFT_START,
  GAME_MOVE_LEFT_STOP,
  GAME_MOVE_RIGHT_START,
  GAME_MOVE_RIGHT_STOP,
  GAME_SHOOT_START,
  GAME_SHOOT_STOP,
  
  // Screen controls -- keyboard-driven
  MENU_OPEN,
  MENU_CLOSE,
  MENU_UP,
  MENU_DOWN,
  MENU_SELECT,
  
  // Screen controls -- touch driven
  START,
  CONTINUE,
  BACK,
  PAUSE,
  MENU_HOW,
  MENU_ACHIEVEMENTS,
  MENU_LEADERBOARDS,
  MENU_EXIT,
  CHAR_THRILLSON,
  CHAR_SANTORO,
  PAUSE_RESUME,
  PAUSE_NEW_GAME,
  PAUSE_OPTIONS,
  PAUSE_EXIT,
  
  // Noop
  NONE,
  
  // Debug
  DEBUG_MAP_GEN
}
