package com.stevezero.game.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Random sequence generator.
 * TODO(stevemar): Allow seed specification at runtime.
 * TODO(stevemar): Maybe move to SecureRandom.
 */
public class RandomGenerator {
  private static final Random RANDOM = new Random();
  private static final Map<Object, Object[]> ENUM_CACHE = new HashMap<Object, Object[]>();

  public static int next() {
    return RANDOM.nextInt();
  }
  
  public static int nextInt(int n) {
    return RANDOM.nextInt(n);
  }
  
  public static boolean nextBoolean() {
    return RANDOM.nextBoolean();
  }
  
  // Note caches enum constants to avoid GC churn.
  // Plays fast and loose with typing to be usable on all enums, but this should be safe due to
  // filter on T.
  @SuppressWarnings("unchecked")
  public static <T extends Enum<?>> T nextEnum(Class<T> clazz){
    T[] enumConstants;
    if (ENUM_CACHE.containsKey(clazz)) {
      enumConstants = (T[]) ENUM_CACHE.get(clazz);
    } else {
      enumConstants = clazz.getEnumConstants();
      ENUM_CACHE.put(clazz, enumConstants);
    }
    int x = RANDOM.nextInt(enumConstants.length);
    return enumConstants[x];
  }

  private RandomGenerator() {
  }
}
