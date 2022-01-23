package com.mygdx.game_objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.utils.GameManager;

public class TowerBuilding extends Building{

    public final Vector2[] towerRayCastingArea = { new Vector2(71,3), new Vector2(7,36), new Vector2(64,78), new Vector2(136,36) };

        public TowerBuilding(World world) {
        super(world);
       // physicsBodies = GameManager.getInstance().filesLoader.buildings.towerPhysicsBodies;
      //  physicsBodyName = GameManager.getInstance().filesLoader.buildings.towerPhysicBodyName;
        textureRegion =  GameManager.getInstance().filesLoader.buildings.towerTextureRegion;
        animationDuration = GameManager.getInstance().filesLoader.buildings.towerCountTextureInAnimation;
        verticls = towerRayCastingArea;

        Initialization();
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(position.x, position.y +50);
    }
}
