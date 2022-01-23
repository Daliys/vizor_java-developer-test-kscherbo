package com.mygdx.game_objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.utils.GameManager;

public class OakTree extends Tree{

    public final Vector2[] oakRayCastingArea = { new Vector2(114,5), new Vector2(114,19), new Vector2(139,19), new Vector2(139,5)};

    public OakTree(World world) {
        super(world);

        sprite = GameManager.getInstance().filesLoader.trees.oakTexture;
        stumpSprite = GameManager.getInstance().filesLoader.trees.oakStumpTexture;
        physicsBodies = GameManager.getInstance().filesLoader.trees.oakPhysicsBodies;
        physicsBodyName = GameManager.getInstance().filesLoader.trees.oakPhysicBodyName;
        verticls = oakRayCastingArea;
        playerOffsetForCutting = new Vector2( + 155,  + 18);

        Initialization();
    }
}
