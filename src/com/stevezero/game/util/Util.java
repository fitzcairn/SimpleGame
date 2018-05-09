package com.stevezero.game.util;


/**
 * Simple static utilities.
 */
public final class Util {
  
  /**
   * @return the concatenation of a and b.
   */
  public static long concatenateDigits(int a, int b) {
    return ((long)a << 32) | ((long)b & 0xFFFFFFFL);
  }
    
  /**
   * @return the absolute value of the input, capped by limit.
   */
  public static float absCap(float value, float limit) {
    return value < 0 ? Math.max(-limit, value): Math.min(limit, value);
  }

  /**
   * @return the zero-safe inverse of value.
   */
  public static float invert(float value) {
    return value > 0 ? 1/value : 0;
  }
  
  private Util() {
    throw new UnsupportedOperationException();
  }
}
