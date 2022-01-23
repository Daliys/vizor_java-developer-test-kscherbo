package com.mygdx.game_objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.utils.GameManager;

public class PalmTree extends Tree{
    public final Vector2[] palmRayCastingArea = { new Vector2(114,2), new Vector2(114,19), new Vector2(131,19), new Vector2(131,2)};

    public PalmTree(World world) {
        super(world);

        sprite = GameManager.getInstance().filesLoader.trees.palmTexture;
        stumpSprite = GameManager.getInstance().filesLoader.trees.palmStumpTexture;
        physicsBodies = GameManager.getInstance().filesLoader.trees.palmPhysicsBodies;
        physicsBodyName = GameManager.getInstance().filesLoader.trees.palmPhysicBodyName;
        verticls = palmRayCastingArea;
        playerOffsetForCutting = new Vector2( + 155,  + 15);

        Initialization();

    }

}
