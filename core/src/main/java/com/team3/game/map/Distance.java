package com.team3.game.map;

import com.badlogic.gdx.ai.pfa.Heuristic;

/**
 * Distance class to be used as a heuristic in Path Finding algorithms.
 */
public class Distance implements Heuristic<Node> {

  @Override
  public float estimate(Node node, Node endNode) {
    int startY = node.getIndex() / Map.mapTileWidth;
    int startX = node.getIndex() % Map.mapTileWidth;

    int endY = endNode.getIndex() / Map.mapTileWidth;
    int endX = endNode.getIndex() % Map.mapTileWidth;

    // Distance in manhattan form.
    return Math.abs(startX - endX) + Math.abs(startY - endY);
  }
    
}
