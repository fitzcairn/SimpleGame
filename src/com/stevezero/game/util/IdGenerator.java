package com.stevezero.game.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generate a sequence of Ids in a thread-safe way.
 */
public class IdGenerator {
  private static final AtomicInteger SEQUENCE = new AtomicInteger();

  /**
   * @return the next Id in the sequence, flipping over to 0 at 2^16 - 1.
   */
  public static int next() {
    return SEQUENCE.incrementAndGet();
  }
  
  public static String nextString() {
    return Integer.toString(next());
  }

  private IdGenerator() {
  }
}
