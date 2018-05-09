package com.stevezero.apps.simplegame.game.engine.map.impl;

import static com.stevezero.game.engine.physics.Direction.DOWN;
import static com.stevezero.game.engine.physics.Direction.LEFT;
import static com.stevezero.game.engine.physics.Direction.NONE;
import static com.stevezero.game.engine.physics.Direction.RIGHT;
import static com.stevezero.game.engine.physics.Direction.UP;
import static com.stevezero.game.util.RandomGenerator.nextEnum;
import static com.stevezero.game.util.RandomGenerator.nextInt;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;

import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Cherry;
import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Date;
import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Dogfood;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Jarlsberg;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Kenafa;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Longhorn;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Manchego;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.*;
import com.stevezero.game.assets.loader.Loader;
import com.stevezero.game.engine.Engine;
import com.stevezero.game.engine.actor.Actor;
import com.stevezero.game.engine.actor.consumable.Consumable;
import com.stevezero.game.engine.actor.enemy.Enemy;
import com.stevezero.game.engine.graphics.backgrounds.TiledScrollingBackground;
import com.stevezero.game.engine.graphics.backgrounds.TiledStaticAreaBackground;
import com.stevezero.game.engine.graphics.rendering.Renderable;
import com.stevezero.game.engine.map.Map;
import com.stevezero.game.engine.physics.Direction;
import com.stevezero.game.geometry.Point2;
import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Banana;
import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Cherry;
import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Date;
import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Dogfood;
import com.stevezero.apps.simplegame.game.engine.actor.consumable.impl.Elderberry;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Jarlsberg;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Kenafa;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Longhorn;
import com.stevezero.apps.simplegame.game.engine.actor.enemy.impl.Manchego;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.CheeseBlock;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.WallBottom;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.WallBottomLeftCorner;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.WallBottomRightCorner;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.WallLeft;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.WallRight;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.WallTop;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.WallTopLeftCorner;
import com.stevezero.apps.simplegame.game.engine.actor.obstacle.impl.WallTopRightCorner;


/**
 * A Map loaded from a file.
 */
public final class GeneratedMap extends Map {
  
  private static final int TILE_SIZE = 50; // Needs to match atlas_border.png
  private static final int ROOM_SIZE = TILE_SIZE * 9;

  // Probabilities for room generation, 0-100.
  private static final int PROB_ENEMY = 100;
  private static final int PROB_CONSUMABLE = 100;

  private final Loader loader;
  private List<Room> rooms = new ArrayList<Room>();
  private Point2 start;
  private Point2 end;
  
  /**
   * Private Room class used in construction of the map.
   */
  private class Room {
    public final int x;
    public final int y;
    public boolean[] walls;
    
    public Room(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
  
  public GeneratedMap(Engine engine, int width, int height, List<Renderable> renderQueue,
      List<Actor> simulationQueue) {
    super(engine, renderQueue, simulationQueue);
    this.loader = engine.getLoader();
    this.width = width;
    this.height = height;

    // Generate the map procedurally.
    generateMap(loader);
  }

  private void generateMap(Loader loader) {
    // Get a 2d array representing the graph of possible room paths.
    boolean[][] graph = createRoomGraph(width, height);
    
    // Get a random start and end room for the map.
    start = getRandomRoom(graph);
    end = getRandomRoom(graph);
    
    // Create a random path of rooms through the graph.
    createRoomPath(start.getX(), start.getY(), end, graph, getMinDistance(graph), rooms);
    
    // Go through the list in reverse and render each room.
    for (int i = rooms.size() - 1; i >= 0; i--) {
      renderRoom(
          graph,
          i + 1 == rooms.size() ? null : rooms.get(i + 1),
          rooms.get(i),
          i - 1 < 0 ? null : rooms.get(i -1));
    }

    // For all rooms that are NOT part of the map, fill.
    // At the same time, fill in the borders of the world.
    for (int i = -1; i <= graph.length; i++) {
      for (int j = -1; j <= graph[0].length; j++) {
        if (i < 0 || i == graph.length || j < 0 || j == graph[0].length || !graph[i][j]) {
          fillRoom(i, j);
        }
      }
    }
    
    // Add background.
    renderQueue.add(new TiledScrollingBackground(loader, "bg_tile.png", camera));
    
    // Put the player in the start room.
    this.playerStart = new Point2(start.getX() * ROOM_SIZE + TILE_SIZE + ROOM_SIZE / 4,
        start.getY() * ROOM_SIZE + TILE_SIZE);
  }
  
  private void fillRoom(int i, int j) {
    int xPad = 0;//TILE_SIZE / 2;
    int yPad = 0;//xPad + 8; // Arbitrarily tuned.
    renderQueue.add(
        new TiledStaticAreaBackground(
            loader, "fill.png", i * ROOM_SIZE - xPad, j * ROOM_SIZE - yPad, ROOM_SIZE, ROOM_SIZE));
  }
  
  // Render a room.
  private void renderRoom(boolean[][] graph, Room last, Room current, Room next) {
    // Derive the required walls for the current room.
    boolean[] walls = new boolean[4];
    adjacency(current, last, walls);
    adjacency(current, next, walls);
    current.walls = walls;

    buildRoomWalls(graph, last, current, next);

    // Enemy in this room?
    maybeAddEnemy(current);

    // Consumable?
    maybeAddConsumable(current);
    
    // Last room.
    if (next == null) {
      addGoal(current);
    } else {
      // Add platform[s] if necessary.
      addPlatforms(last, current);
    }
  }
  
  private void addGoal(Room current) {
    
    // Build a platform in the middle of the room.
    int platformWidth = 2 * TILE_SIZE;
    int platformY = current.y + (ROOM_SIZE / 2);
    int platformX = current.x + (ROOM_SIZE / 2) - platformWidth / 2;
    
    // Add the platform.
    for (int x = platformX; x < platformX + platformWidth; x += TILE_SIZE) {
      simulationQueue.add(new CheeseBlock(loader, x, platformY));
    }
    
    // Put the dogfood goal on the platform.
    addConsumable(new Dogfood(engine, platformX, platformY - 100));
  }
  
  private void maybeAddConsumable(Room current) {
    // If there's no floor in the room, don't add a consumable.
    if (!isWall(current.walls, Direction.DOWN)) {
      return;
    }
    
    if (nextInt(100) < PROB_CONSUMABLE) {
      int border = 2 * TILE_SIZE;
      int x = current.x + max(nextInt(ROOM_SIZE - border), border);
      int y = current.y + ROOM_SIZE - border;
      switch(nextEnum(Consumable.Type.class)) {
        case HEALTH:
          addConsumable(new Banana(engine, x, y));
        break;
        case POWER:
          addConsumable(new Cherry(engine, x, y));
        break;
        case SHIELD:
          addConsumable(new Date(engine, x, y));
        break;
        case POINTS:
          addConsumable(new Elderberry(engine, x, y));
        break;
        default:
        break;
      }
    }
  }
  
  private void maybeAddEnemy(Room current) {
    // If there's no floor in the room, don't add an enemy.
    if (!isWall(current.walls, DOWN)) {
      return;
    }

    if (nextInt(100) < PROB_ENEMY) {
      int x = current.x + ROOM_SIZE / 2;
      int y = current.y + ROOM_SIZE - 3 * TILE_SIZE;
      Enemy enemy;
      switch(nextInt(4)) {
        case 0:
          enemy = new Jarlsberg(engine, x, y);
        break;
        case 1:
          enemy = new Kenafa(engine, x, y);
        break;
        case 2:
          enemy = new Longhorn(engine, x, y);
        break;
        case 3:
        default:
          enemy = new Manchego(engine, x, y);
        break;
      }
      addEnemy(enemy);
      
      // Enemies in GMS Crusader get AI simulation.
      addAgent(enemy);
    }
  }

  // If we have two contiguous rooms, need to add at least one platform.
  private void addPlatforms(Room last, Room current) {
    if (last == null || !last.walls[UP.getOrdinal()]) {
      return;
    }
    addPlatform(last);
  }

  private void addPlatform(Room room) {
    // We want a platform somewhere around the middle.  Generate a y.
    int platformY = room.y - nextInt(ROOM_SIZE / 4);
    platformY -= (platformY % TILE_SIZE);
    int platformWidth = 2 * TILE_SIZE;

    // Pick a wall to join it to.
    int platformX = room.x + TILE_SIZE;
    if (room.walls[RIGHT.getOrdinal()]) {
      platformX += (ROOM_SIZE - platformWidth - 2 * TILE_SIZE);
    } 
    
    // Add the platform.
    for (int x = platformX; x < platformX + platformWidth; x += TILE_SIZE) {
      simulationQueue.add(new CheeseBlock(loader, x, platformY));
    }
  }

  private boolean isWall(boolean[] walls, Direction down) {
    if (down.getOrdinal() < walls.length) {
      return !walls[down.getOrdinal()];
    }
    return false;
  }

  private void buildRoomWalls(boolean[][] graph, Room last, Room current, Room next) {
    // Figure out which walls to build.  T == passage, F == wall.
    boolean[] walls = new boolean[4];
    adjacency(current, last, walls);
    adjacency(current, next, walls);
    current.walls = walls;
    
    // Build borders.
    if (isWall(walls, LEFT)) {
      addVerticalRoomWall(graph, walls, current, current.x, current.y, LEFT);
    }
    if (isWall(walls, UP)) {
      addHorizontalRoomWall(graph, walls, current, current.x, current.y, UP);
    }
    if (isWall(walls, RIGHT)) {
      addVerticalRoomWall(
          graph, walls, current, current.x + ROOM_SIZE - TILE_SIZE, current.y, RIGHT);
    }
    if (isWall(walls, DOWN)) {
      addHorizontalRoomWall(
          graph, walls, current, current.x, current.y + ROOM_SIZE - TILE_SIZE, DOWN);
    }
    
    // Build corners to join up borders around tricky cases.
    addExtraCorners(graph, walls, last, current, next);
  }
  
  private void addExtraCorners(boolean[][] graph, boolean[] walls, Room last, Room current,
      Room next) {
    // Example:
    //       -----     ----------
    //      |     |              |
    //      + <-  |    --- + <-   
    // |       X  |        |  X  
    //  ----------          ----- 
    if (!isWall(walls, UP)) {
      if (!isWall(walls, LEFT)) {
        addCorner(current.x, current.y, RIGHT, DOWN, UP);
      }
      if (!isWall(walls, RIGHT)) {
        addCorner(current.x + ROOM_SIZE - TILE_SIZE, current.y, LEFT, DOWN, UP);
      }
    }

    // Handle bottom caps.
    if (!isWall(walls, DOWN)) {
      if (!isWall(walls, RIGHT)) {
        addCorner(
            current.x + ROOM_SIZE - TILE_SIZE, current.y + ROOM_SIZE - TILE_SIZE, LEFT, UP, DOWN);
      }
      if (!isWall(walls, LEFT)) {
        addCorner(current.x, current.y + ROOM_SIZE - TILE_SIZE, RIGHT, UP, DOWN);
      }
    }
    
  }

  private void addCorner(int startX, int startY, Direction right, Direction down, Direction up) {
    if (right == RIGHT) {
      if (down == UP) {
        simulationQueue.add(new WallTopRightCorner(loader, startX, startY, up.getOrdinal()));
      } else {
        simulationQueue.add(new WallBottomRightCorner(loader, startX, startY, up.getOrdinal()));
      }
    } else {
      if (down == UP) {
        simulationQueue.add(new WallTopLeftCorner(loader, startX, startY, up.getOrdinal()));
      } else {
        simulationQueue.add(new WallBottomLeftCorner(loader, startX, startY, up.getOrdinal()));
      }  
    }
  }
  
  private void addWall(int startX, int startY, Direction direction) {
    switch(direction) {
      case LEFT:
        simulationQueue.add(new WallLeft(loader, startX, startY));
        break;
      case RIGHT:
        simulationQueue.add(new WallRight(loader, startX, startY));
        break;
      case UP:
        simulationQueue.add(new WallTop(loader, startX, startY));
        break;
      case DOWN:
        simulationQueue.add(new WallBottom(loader, startX, startY));
        break;
      default:
        break;
    }
  }
  
  private void addHorizontalRoomWall(boolean[][] graph, boolean[] walls, Room room, int startX,
      int startY, Direction up) {
    int endX = startX + ROOM_SIZE - TILE_SIZE;

    for (int x = startX + TILE_SIZE; x < endX; x += TILE_SIZE) {
      addWall(x, startY, up);
    }
    
    // Add basic corners.
    if (isWall(walls, LEFT)) {
      addCorner(startX, startY, LEFT, up, up);
    } else {
      addWall(startX, startY, up);
    }
    if (isWall(walls, RIGHT)) {
      addCorner(endX, startY, RIGHT, up, up);
    } else {
      addWall(endX, startY, up);
    }
  }

  private void addVerticalRoomWall(boolean[][] graph, boolean[] walls, Room room, int startX,
      int startY, Direction left) {
    int endY = startY + ROOM_SIZE - TILE_SIZE;
    
    for (int y = startY + TILE_SIZE; y < endY; y += TILE_SIZE) {
      addWall(startX, y, left);
    }

    // Add basic corners.
    if (isWall(walls, UP)) {
      addCorner(startX, startY, left, UP, UP);
    } else  {
      addWall(startX, startY, left);
    }
    if (isWall(walls, DOWN)) {
      addCorner(startX, endY, left, DOWN, DOWN);
    } else  {
      addWall(startX, endY, left);
    }
  }
  
  private void adjacency(Room current, Room next, boolean[] walls) {
    if (next != null) {
      int deltaX = current.x - next.x;
      int deltaY = current.y - next.y;
      if (deltaX == 0) {
        walls[deltaY > 0 ? UP.getOrdinal() : DOWN.getOrdinal()] = true;
      } else {
        walls[deltaX > 0 ? LEFT.getOrdinal() : RIGHT.getOrdinal()] = true;
      }
    }
  }
  
  // Add a room to the list.
  private boolean addRoom(int x, int y, List<Room> rooms) {
    // Convert x/y to global coordinates.
    rooms.add(new Room(x * ROOM_SIZE, y * ROOM_SIZE));
    return true;
  }
  
  // Recursively generate a list of rooms for this level.
  // TODO(stevemar): BUGS HERE-- last room not geting marked right on the graph.
  private boolean createRoomPath(int x, int y, Point2 end, boolean[][] graph,
      int minDistance, List<Room> rooms) {
    // Off the edge?
    if (x < 0 || x >= graph.length || y < 0 || y >= graph[0].length) {
      return false; 
    }
    // If we've reached the goal with a sufficiently twisted path, then we're done.
    if (end.equals(x, y)) {
      if (minDistance <= 0) {
        graph[x][y] = true;
        return addRoom(x, y, rooms);
      } else {
        // Avoid visiting the end until we're ready.
        return false;
      }
    }
    
    // If we've seen this room before, back-track.
    if(graph[x][y]) {
      return false;
    }
    // Give ourselves an early termination if we're going nuts.
    if (minDistance < -(2 * getMinDistance(graph))) {
      end.set(x, y);
      graph[x][y] = true;
      return addRoom(x, y, rooms);
    }
    
    // Maybe this room is part of the solution.
    graph[x][y] = true;

    // Go through possible directions in random order.
    Direction[] possibleDirections = getRandomizedDirections(x, y, graph);
    for (int i = 0; i < possibleDirections.length; i++) {
      if (possibleDirections[i] != NONE) {
        switch(possibleDirections[i]) {
          case LEFT:
            if (createRoomPath(x - 1, y, end, graph, minDistance - 1, rooms)) {
              return addRoom(x, y, rooms);
            }
            break;
          case UP:
            if (createRoomPath(x, y - 1, end, graph, minDistance - 1, rooms)) {
              return addRoom(x, y, rooms);
            }
            break;
          case RIGHT:
            if (createRoomPath(x + 1, y, end, graph, minDistance - 1, rooms)) {
              return addRoom(x, y, rooms);
            }
            break;
          case DOWN:
            if (createRoomPath(x, y + 1, end, graph, minDistance - 1, rooms)) {
              return addRoom(x, y, rooms);
            }
            break;
          default:
            break;
        }
      }
    }
    
    // There is no way this room is part of the solution.  Backtrack.
    graph[x][y] = false;
    return false;
  }
  
  // Fisher-Yates shuffle
  private void shuffleArray(Direction[] directions){
    for (int i = directions.length - 1; i > 0; i--) {
      int index = nextInt(i + 1);
      // Simple swap
      Direction element = directions[index];
      directions[index] = directions[i];
      directions[i] = element;
    }
  }
  
  // Return a randomized array of possible directions.
  private Direction[] getRandomizedDirections(int x, int y, boolean[][] graph) {
    int i = 0;
    Direction[] directions = { NONE, NONE, NONE, NONE };
    if (x > 0) directions[i++] = LEFT;
    if (x < graph.length - 1) directions[i++] = RIGHT;
    if (y > 0) directions[i++] = UP;
    if (y < graph[0].length - 1) directions[i++] = DOWN;
    shuffleArray(directions);
    return directions;
  }
  
  // The minimum distance for the path between the start and the goal.
  // Tune the value returned for path "twistiness."
  private int getMinDistance(boolean[][] graph) {
    return graph.length * graph[0].length - graph.length;
  }
  
  private Point2 getRandomRoom(boolean[][] graph) {
    int x = nextInt(graph.length);
    int y = nextInt(graph[x].length);
    return new Point2(x, y);
  }
  
  // Create an array of rooms for the level.
  private boolean[][] createRoomGraph(int width, int height) {
    return new boolean[width / ROOM_SIZE][height / ROOM_SIZE];
  }
  
  // DEBUG
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("START: " + start.getX() + " " + start.getY() + "\n");
    sb.append("END: " + end.getX() + " " + end.getY() + "\n");
    for (int i = rooms.size() - 1; i >= 0; i--) {
      sb.append("X: " + rooms.get(i).x / ROOM_SIZE + " Y: " + rooms.get(i).y / ROOM_SIZE +
          " WALLS: ");
      for (int j = 0; j < 4; j++) {
        sb.append(rooms.get(i).walls[j] + " ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
  
  
}
