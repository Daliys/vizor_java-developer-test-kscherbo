package com.mygdx.file_loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.codeandweb.physicseditor.PhysicsShapeCache;

public class TreesFiles {

    public final Texture oakTexture;
    public final Texture oakStumpTexture;

    public final Texture palmTexture;
    public final Texture palmStumpTexture;

    public final Texture tropicPalmTexture;
    public final Texture tropicPalmStumpTexture;

    public final PhysicsShapeCache oakPhysicsBodies;
    public final PhysicsShapeCache palmPhysicsBodies;
    public final PhysicsShapeCache tropicPalmPhysicsBodies;

    public final String oakPhysicBodyName = "oakF";
    public final String palmPhysicBodyName = "plamF";
    public final String tropicPalmPhysicBodyName = "tropicPalmF";



    public TreesFiles(){
        oakTexture = new Texture(Gdx.files.internal("trees/images/oakF.png"));
        oakStumpTexture = new Texture(Gdx.files.internal("trees/images/oakStumpF.png"));

        palmTexture = new Texture(Gdx.files.internal("trees/images/plamF.png"));
        palmStumpTexture = new Texture(Gdx.files.internal("trees/images/oakStumpF.png"));

        tropicPalmTexture = new Texture(Gdx.files.internal("trees/images/tropicPalmF.png"));
        tropicPalmStumpTexture = new Texture(Gdx.files.internal("trees/images/tropicPalmStumpF.png"));


        oakPhysicsBodies = new PhysicsShapeCache(Gdx.files.internal("trees/physics/oakF.xml"));
        palmPhysicsBodies = new PhysicsShapeCache(Gdx.files.internal("trees/physics/plamF.xml"));
        tropicPalmPhysicsBodies = new PhysicsShapeCache(Gdx.files.internal("trees/physics/tropicPalmF.xml"));


    }


    public void dispose(){
        oakTexture.dispose();
        oakStumpTexture.dispose();

        palmTexture.dispose();
        palmStumpTexture.dispose();

        tropicPalmTexture.dispose();
        tropicPalmStumpTexture.dispose();

        oakPhysicsBodies.dispose();
        palmPhysicsBodies.dispose();
        tropicPalmPhysicsBodies.dispose();
    }

}
