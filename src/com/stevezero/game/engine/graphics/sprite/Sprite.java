package com.stevezero.game.engine.graphics.sprite;

import java.util.ArrayList;
import java.util.List;

import com.stevezero.game.assets.drawable.GameDrawable;
import com.stevezero.game.engine.graphics.RemovableScreenElement;
import com.stevezero.game.engine.graphics.motion.MotionGraph;
import com.stevezero.game.engine.graphics.motion.animation.Animation;


/**
 * A 2d game sprite.
 */
public class Sprite {
  private List<Animation> animations;
  private MotionGraph motionGraph = null;
  private int currentAnimationId = 0;
  private final RemovableScreenElement owningElement;
  
  public Sprite(RemovableScreenElement owningElement) {
    this.owningElement = owningElement;
    this.animations = new ArrayList<Animation>();
  }
  
  /**
   * Add an animation to this sprite.
   * @return the animation ID for the state.
   */
  public int addAnimation(Animation state) {
    int stateId = animations.size();
    animations.add(state);
    return stateId;
  }

  /**
   * Set the sprite state.
   */
  public Sprite setAnimationTo(int animationId) {
    assert(animationId < animations.size() && animationId >= 0);    
    Animation state = animations.get(animationId);
    if (!state.isRepeated() || animationId != currentAnimationId) {
      state.reset();
    }
    currentAnimationId = animationId;
    return this;
  }
  
  /**
   * Execute a MotionGraph, a specified series of state transitions.
   */
  public void setMotionGraph(MotionGraph motionGraph) {
    this.motionGraph = motionGraph.reset();
    setAnimationTo(motionGraph.next());
  }

  /**
   * Get the next frame for the sprite.
   * Returns null if there are no more frames, and marks the owner for deletion.
   */
  public GameDrawable getNextFrame() {
    Animation animation = animations.get(currentAnimationId);
    GameDrawable frame = animation.getNextFrame();
    if (frame == null) {
      
      // Are we executing a statelist?
      if (motionGraph != null) {
        if (motionGraph.hasNext()) {
          setAnimationTo(motionGraph.next());
          return getNextFrame();
        }
        // Done with the state list.  Clear.
        motionGraph = null;
      }

      // No next frame.  This sprite is no longer renderable as-is.  Register for removal.
      owningElement.removeScreenElement();
    }

    return frame;
  }
  
  /**
   * Get the current width of the sprite.
   */
  public int getWidth() {
    return animations.get(currentAnimationId).getWidth();
  }

  /**
   * Get the current height of the sprite.
   */
  public int getHeight() {
    return animations.get(currentAnimationId).getHeight();
  }
  
  /**
   * Get the current Animation ID.
   */
  public int getCurrentAnimationId() {
    return currentAnimationId;
  }
}
