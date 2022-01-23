package com.mygdx.game_objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.utils.GameManager;

public class TropicPalmTree extends Tree{

    public final Vector2[] tropicPalmRayCastingArea = { new Vector2(154,8), new Vector2(154,30), new Vector2(202,30), new Vector2(202,8)};


    public TropicPalmTree(World world) {
        super(world);

        sprite = GameManager.getInstance().filesLoader.trees.tropicPalmTexture;
        stumpSprite = GameManager.getInstance().filesLoader.trees.tropicPalmStumpTexture;
        physicsBodies = GameManager.getInstance().filesLoader.trees.tropicPalmPhysicsBodies;
        physicsBodyName = GameManager.getInstance().filesLoader.trees.tropicPalmPhysicBodyName;
        verticls = tropicPalmRayCastingArea;
        playerOffsetForCutting = new Vector2( + 212,  + 22);

        Initialization();
    }
}
