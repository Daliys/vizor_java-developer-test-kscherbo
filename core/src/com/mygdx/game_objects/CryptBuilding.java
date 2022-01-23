package com.mygdx.game_objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.utils.GameManager;

public class CryptBuilding extends Building{

    public final Vector2[] cryptRayCastingArea = { new Vector2(175,-13), new Vector2(276,45), new Vector2(112,126), new Vector2(24,62)};

    public CryptBuilding(World world) {
        super(world);
      //  physicsBodies = GameManager.getInstance().filesLoader.buildings.cryptPhysicsBodies;
      //  physicsBodyName = GameManager.getInstance().filesLoader.buildings.cryptPhysicBodyName;
        textureRegion =  GameManager.getInstance().filesLoader.buildings.cryptTextureRegion;
        animationDuration = GameManager.getInstance().filesLoader.buildings.cryptCountTextureInAnimation;
        verticls = cryptRayCastingArea;

        Initialization();
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(position.x, position.y +100);
    }
}
