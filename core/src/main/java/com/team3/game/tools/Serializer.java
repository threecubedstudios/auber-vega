package com.team3.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.team3.game.GameMain;
import com.team3.game.characters.ai.Enemy;
import com.team3.game.characters.ai.EnemyManager;
import com.team3.game.characters.ai.Npc;
import com.team3.game.characters.ai.NpcManager;
import com.team3.game.screen.Gameplay;
import com.team3.game.sprites.StationSystem;


/**
 * Utility class that handles JSON serialization.
 **/
public final class Serializer {
  /**
   * Dump the current state of the world to a JSON string.
   *
   * @param pretty Whether to return a spaced and indented string or not
   * @return A JSON string representing the game state
   **/
  public static String dumpStr(Gameplay gameplay, boolean pretty) {
    Json json = new Json();

    if (pretty) {
      return json.prettyPrint(gameplay);
    }
    return json.toJson(gameplay);
  }

  /**
   * Write a JSON string representing the current game state to a file.
   *
   * @param fileName The name of the file to save to (excluding its json extension)
   * @param pretty Whether the file should be spaced and indented
   **/
  public static void toFile(String fileName, boolean pretty, Gameplay gameplay) {
    FileHandle file = Gdx.files.local("saves/" + fileName + ".json");
    file.writeString(dumpStr(gameplay, pretty), false);
  }

  /**
   * Generate a gameplay object from a JSON save file.
   *
   * @param fileName The name of the save file (excluding its json extension)
   * @return A gameplay object representing the loaded game state
   **/
  public static Gameplay fromFile(String fileName, final GameMain main) {
    Json json = new Json();
    json.setSerializer(Gameplay.class, new Json.Serializer<Gameplay>() {
      @SuppressWarnings("rawtypes")
      @Override
      public void write(Json json, Gameplay object, Class knownType) {
        // This function can be empty as this code is only called when reading from JSON
      }

      @SuppressWarnings("rawtypes")
      @Override
      public Gameplay read(Json json, JsonValue jsonData, Class type) {
        Gameplay gameplay = new Gameplay(main, true);

        for (JsonValue systemData : jsonData.get("systems")) {
          StationSystem system = Gameplay.systems.stream()
              .filter(currentSystem -> systemData.getString("name")
                  .equals(currentSystem.getSystemName())).findFirst().get();
          system.hp = systemData.getFloat("hp");
        }

        for (JsonValue npcData : jsonData.getChild("npc_manager")) {
          JsonValue positionData = npcData.get("position");
          Npc npc = new Npc(gameplay.world, positionData.get("x").asFloat(), 
              positionData.get("y").asFloat());
          npc.destX = npcData.getFloat("dest_x");
          npc.destY = npcData.getFloat("dest_y");
          npc.moveToDest();
          NpcManager.npcs.add(npc);
        }

        for (JsonValue enemyData : jsonData.getChild("enemy_manager")) {
          JsonValue positionData = enemyData.get("position");
          Enemy enemy = new Enemy(gameplay.world, positionData.get("x").asFloat(), 
              positionData.get("y").asFloat());

          // Get the target system from the stored string
          StationSystem targetSystem = Gameplay.systems.stream()
              .filter(currentSystem -> enemyData.getString("target_system")
                  .equals(currentSystem.getSystemName())).findFirst().get();

          // Assign system target to enemy
          enemy.set_target_system(targetSystem);

          // Set the enemies "mode"
          enemy.mode = enemyData.getString("mode");

          EnemyManager.enemies.add(enemy);
        }

        return gameplay;
      }
    });

    FileHandle file = Gdx.files.local("saves/" + fileName + ".json");
    Gameplay gameplay = json.fromJson(Gameplay.class, file.readString());
    return gameplay;
  }
}
