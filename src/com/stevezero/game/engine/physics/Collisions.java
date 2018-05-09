package com.stevezero.game.engine.physics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.physics.Direction;
import com.stevezero.game.geometry.Point2;
import com.stevezero.game.geometry.Vector2;
import com.stevezero.game.geometry.impl.MutableVector2;

/**
 * Physics simulation for resolving collisions.
 */
public class Collisions {
  // How far to expand beyond the current view screen for physics simulation.
  public static final Point2 VIEW_PAD = Point2.of(100, 100);
  
  private static final List<Actor> VISIBLE_LIST = new ArrayList<Actor>();
  
  private static final float POSITION_CORRECTION = 0.9f;

  private static final float JITTER_THRESHOLD = 0.01f;
  
  /**
   * Detect and resolve all collisions in the simulation.
   * 
   * @param simulatables the queue of Simulatable objects.
   */
  public static void resolveCollisions(Engine engine, Collection<Actor> simulatables) {
    // Build out the list of visible elements.
    
    // TODO(stevemar): use some sort of spatial data structure here rather than n^2.
    
    // Note that this loop uses a fuzzy visibility check; otherwise, we create artifacts for the
    // player. For example, if the floor something is resting on is out of the frame, that something
    // can fall through since the floor is no longer simulated while the something is.
    for (Actor simulatable : simulatables) {
      if (simulatable.isVisibleFuzzy(engine)) {
        VISIBLE_LIST.add(simulatable);
      }
    }
    
    // Build out a list of detected collisions for all VISIBLE simulatable elements.
    int size = VISIBLE_LIST.size();
    for (int i = 0; i < size; i++) {
      for (int j = i + 1; j < size; j++) { // Test all pairs once.
        // If they are colliding...
        if (areColliding(VISIBLE_LIST.get(i), VISIBLE_LIST.get(j))) {
          // Resolve the collision.
          resolveCollision(VISIBLE_LIST.get(i), VISIBLE_LIST.get(j));
        }
      }
    }
    
    VISIBLE_LIST.clear();
  }
  
  private static void resolveCollision(Actor first, Actor second) {
    // Relative velocity of the collision
    MutableVector2 relVelocity =
        MutableVector2.of(second.getVelocity()).subtract(first.getVelocity());

    // Calculate collision normal.  This is a unit vector.
    Vector2 collisionNormal = first.getCollisionShape().getCollisionNormal(
        second.getCollisionShape());
    
    // Get the restitution to use.
    float restitution = Math.min(first.getRestitution(), second.getRestitution());
   
    // Calculate relative velocity in terms of the normal direction
    float velAlongNormal = relVelocity.getDot(collisionNormal);
   
    // Do not resolve if velocities are separating or too small to use.
    if(Float.isNaN(velAlongNormal) || velAlongNormal > 0) {
      return;
    }
   
    // Calculate impulse scalar
    float impulseScalar = -(1 + restitution) * velAlongNormal;
    impulseScalar /= (first.getInverseMass() + second.getInverseMass());
   
    // Apply impulse
    MutableVector2 impulse = MutableVector2.of(collisionNormal).scale(impulseScalar);
    first.setVelocity(
        ((MutableVector2) first.getVelocity()).subtract(
            MutableVector2.of(impulse).scale(first.getInverseMass())));
    second.setVelocity(
        ((MutableVector2) second.getVelocity()).add(
            MutableVector2.of(impulse).scale(second.getInverseMass())));
    
    // Notify both objects that a collision happened.
    notifyOnCollision(first, second, collisionNormal);
    
    // Deal with floating point issues if we actually made a change.
    if (velAlongNormal != 0) {
      ((MutableVector2) first.getVelocity()).zeroNoise();
      ((MutableVector2) second.getVelocity()).zeroNoise();
    } else {
      // Only correct for floating point if there has been movement along the collision normal.
      return;
    }

    // Correct position for floating point errors.
    float penetrationDepth = Math.abs(first.getCollisionShape().getPenetrationDepth(
        second.getCollisionShape()).getDot(collisionNormal));
    MutableVector2 correction = MutableVector2.of(collisionNormal).scale(
        POSITION_CORRECTION * (
            Math.max(penetrationDepth - JITTER_THRESHOLD, 0.0f) /
                (first.getInverseMass() + second.getInverseMass())));

    first.setPosition(
        first.getPosition().subtractVector(
            MutableVector2.of(correction).scale(first.getInverseMass())));
    second.setPosition(
        second.getPosition().addVector(
            MutableVector2.of(correction).scale(second.getInverseMass())));
  }
  
  private static void notifyOnCollision(Actor first, Actor second,
      Vector2 collisionNormal) {
    if (collisionNormal.getX() != 0) {
      if (collisionNormal.getX() < 0) {
        first.onCollision(Direction.LEFT, second);
        second.onCollision(Direction.RIGHT, first);
      } else {
        first.onCollision(Direction.RIGHT, second);
        second.onCollision(Direction.LEFT, first);
      }
    }
    if (collisionNormal.getY() != 0) {
      if (collisionNormal.getY() < 0) {
        first.onCollision(Direction.UP, second);
        second.onCollision(Direction.DOWN, first);
      } else {
        first.onCollision(Direction.DOWN, second);
        second.onCollision(Direction.UP, first);
      }
    }
  }

  private static boolean areColliding(Actor first, Actor second) {
    // First, give ourselves an out if there's no reason to calculate collisions,
    // i.e. friendly fire.
    if (!first.isCollidableWith(second) || !second.isCollidableWith(first)) {
      return false;
    }
    return first.getCollisionShape().intersects(second.getCollisionShape());
  }
}
