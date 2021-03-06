package com.team3.game.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Stores information about the map and its graph representation.
 * This code allows for the instantiation of an abstract class. This is an issue.
 */
public abstract class Map {
  // Height and Width of Map in Tiles.
  public static int mapTileHeight;
  public static int mapTileWidth;

  // Height and Width of Map in Pixels.
  public static int mapPixelHeight;
  public static int mapPixelWidth;

  // Height and width of a Tile in Pixels.
  public static int tilePixelHeight;
  public static int tilePixelWidth;

  // Graph representing the Map.
  public static Graph graph;

  /**
   * Initialize a map.
   *
   * @param map Passes the map parameter
   */
  public static void create(TiledMap map) {
    // This is a hacky solution to stop it from being "created" more than once
    if (graph == null) {
      MapProperties properties = map.getProperties();

      Map.mapTileHeight = properties.get("height", Integer.class);
      Map.mapTileWidth = properties.get("width", Integer.class);
      Map.tilePixelHeight = properties.get("tileheight", Integer.class);
      Map.tilePixelWidth = properties.get("tilewidth", Integer.class);
      Map.mapPixelHeight = Map.tilePixelHeight * Map.mapTileHeight;
      Map.mapPixelWidth = Map.tilePixelWidth * Map.mapTileWidth;

      Map.graph = new Graph(map);
    }
  }
}
