package com.team3.game.tools;

import com.team3.game.screen.Gameplay;
import com.team3.game.sprites.Door;
import com.team3.game.sprites.Systems;
import java.util.ArrayList;
import java.util.Random;

/**
 * Controls the doors on the map.
 */
public abstract class DoorControl {
  private static int MaxDoorsToLock = 3;
  private static float MaxTime = 30;
  private static float time = MaxTime;
  private static boolean isSabotaged;
  private static ArrayList<Door> doors;

  /**
   * Updates all the doors in the map.

   * @param systems the list of systems
   * @param delta seconds since last update
   */
  public static void updateDoors(ArrayList<Systems> systems, float delta) {
    if (!isSabotaged) {
      if (isDoorsSabotaged(systems)) {
        if (time == MaxTime) {
          sabotage();
        } else {
          time = MaxTime;
        }                
      }
    } else if (time >= delta) {
      //System.out.println("Locked for: " + time + "s");
      time -= delta;            
    } else {
      unsabotage();
    }
  }

  private static boolean isDoorsSabotaged(ArrayList<Systems> systems) {
    for (Systems sys : systems) {
      if (sys.sysName.equals("doors")) {
        if (sys.is_sabotaged()) {
          isSabotaged = true;
          return true;
        }
        return  false;
      }
    }
    return false;
  }

  private static void sabotage() {
    doors = new ArrayList<Door>();
    for (int i = 0; i < MaxDoorsToLock; i++) {
      Random r = new Random();
      int index = r.nextInt(Gameplay.doors.size());
      index = 7;
      Door door = Gameplay.doors.get(index);
      if (!door.isLocked()) {
        door.lock();
        doors.add(door);
      }
    }
  }

  private static void unsabotage() {
    for (Door door : doors) {
      door.unlock();
    }
  }


}
