package com.stevezero.game.engine.graphics.motion;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Linear state graph for sprite animations.
 * 
 * Specifies a series of transitions from animation to animation.
 */
public class MotionGraph {
  private final List<Integer> animationIdList;
  private boolean repeat;
  private int nextAnimationIndex = 0;
  
  public static class Builder {
    private final List<Integer> animationIdList;
    private boolean repeat;
    
    private Builder() {
      animationIdList = new ArrayList<Integer>();
    }
    
    public Builder addNext(int stateId) {
      animationIdList.add(stateId);
      return this;
    }

    public Builder repeat() {
      repeat = true;
      return this;
    }
    
    public MotionGraph build() {
      return new MotionGraph(animationIdList, repeat);
    }
  }

  private MotionGraph(List<Integer> animationIdList, boolean repeat) {
    this.animationIdList = animationIdList;
    this.repeat = repeat;
  }
  
  public boolean hasNext() {
    return repeat || nextAnimationIndex < animationIdList.size();
  }
  
  public int next() {
    if (nextAnimationIndex >= animationIdList.size()) {
      if (repeat) {
        nextAnimationIndex = 0;
      }
      throw new NoSuchElementException(); 
    }
    int state = animationIdList.get(nextAnimationIndex);
    nextAnimationIndex++;
    return state;
  }
  
  public MotionGraph reset() {
    nextAnimationIndex = 0;
    return this;
  }
  
  public static Builder newBuilder() {
    return new Builder();
  }

}
