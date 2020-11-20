package sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Systems extends InteractiveTileObject{

    public String sys_name;
    public float hp;
    /**
     * Creates a new instantiated System object.
     *
     * @param world  Physics world the teleporter should query
     * @param map    Tiled map onject will be placed in
     * @param bounds The bounds of where the object will interact with entities
     */
    public Systems(World world, TiledMap map, Rectangle bounds,String name) {
        super(world, map, bounds);
        sys_name = name;
        hp = 100;
        // use the fixture.userdata to store the system object.
        this.fixture.setUserData(this);
        // use the body.userdata to store the saboage status. used for contact listener
        this.fixture.getBody().setUserData("system_not_sabotaged");
        // check whether is a healing pod or not
        isHealing_pod(name);
        isDoors(name);

    }

    public void isHealing_pod(String name){
        // if system is healingPod, set the fixture to sensor
        if (name.equals("healingPod")){
            this.fixture.getBody().setUserData("healingPod_not_sabotaged");
            this.fixture.setSensor(true);
        }
    }

    public void isDoors(String name){
        // if system is healingPod, set the fixture to sensor
        if (name.equals("doors")){
            this.fixture.getBody().setUserData("doors_not_sabotaged");
            this.fixture.setSensor(true);
        }
    }

    public String getSystemName(){
        return sys_name;
    }

    /**
     *  sabotage status
     * @return
     */
    public String getSabotage_status(){
        return (String) this.body.getUserData();
    }

    public float[] getposition(){
        float[] position = new float[]{this.body.getPosition().x,this.body.getPosition().y};
        return position;
    }

    /**
     * set system to sabotaged
     */
    public void set_sabotaged(){
        body.setUserData("system_sabotaged");
    }

    /**
     * set system to not sabotaged
     */
    public void not_sabotaged(){
        body.setUserData("system_not_sabotaged");
    }

    /**
     * set system to sabotaging
     */
    public void set_sabotaging(){
        body.setUserData("system_sabotaging");
    }

    /**
     * check system is sabotaged or not
     * @return return true if system is sabotaged
     */
    public boolean is_sabotaged(){
        return body.getUserData().equals("system_sabotaged");
    }

    /**
     *
     * @return return true if is sabotaging
     */
    public boolean is_sabotaging(){
        return body.getUserData().equals("system_sabotaging");
    }

    /**
     *
     * @return true if system is not sabotaged and not sabotaging
     */
    public boolean is_not_sabotaged(){
        return body.getUserData().equals("system_not_sabotaged");
    }
}