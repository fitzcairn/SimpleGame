package com.stevezero.game.util;


/**
 * A simple stand-in for Enum value.
 */
public abstract class FakeEnum {
  private final int atlasOffset;
  private final int ord;
  private final String name;

  protected FakeEnum(String name, int atlasOffset, int ord) {
    this.name = name;
    this.atlasOffset = atlasOffset;
    this.ord = ord;
  }

  public String getName() {
    return this.name;
  }
  
  public int getOrd() {
    return this.ord;
  }
  
  public int getAtlasOffset() {
    return this.atlasOffset;
  }
}
