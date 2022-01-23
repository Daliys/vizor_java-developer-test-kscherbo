package com.mygdx.file_loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.mygdx.utils.Utils;

public class BuildingFiles {

    public final int cryptCountTextureInAnimation = 5;
    public final int towerCountTextureInAnimation = 3;

    public final Texture cryptTexture;
    public final Texture towerTexture;

    public final PhysicsShapeCache cryptPhysicsBodies;
    public final PhysicsShapeCache towerPhysicsBodies;

    public final TextureRegion[] cryptTextureRegion;
    public final TextureRegion[] towerTextureRegion;

    public final String cryptPhysicBodyName = "crypt";
    public final String towerPhysicBodyName = "tower";

    public BuildingFiles(){
        cryptTexture = new Texture(Gdx.files.internal("building/crypt.png"));
        towerTexture = new Texture(Gdx.files.internal("building/towers.png"));

        cryptPhysicsBodies = new PhysicsShapeCache(Gdx.files.internal("building/crypt.xml"));
        towerPhysicsBodies = new PhysicsShapeCache(Gdx.files.internal("building/tower.xml"));

        cryptTextureRegion = Utils.textureRegionsSplit(cryptTexture, cryptCountTextureInAnimation, 1);
        towerTextureRegion = Utils.textureRegionsSplit(towerTexture, towerCountTextureInAnimation, 1);
    }


    public void dispose(){
        cryptTexture.dispose();
    }


}
