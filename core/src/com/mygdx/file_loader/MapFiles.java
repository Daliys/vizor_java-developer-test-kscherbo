package com.mygdx.file_loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.mygdx.utils.Utils;

public class MapFiles {

    public final Texture mainIslandAtlasTexture;
    public final PhysicsShapeCache mapTouchableAreaPhysicsShape;
    public final String nameMapTouchableAreaPhysicsShape = "mapArea1";
    public final FileHandle mainIslandXMLMap;

    public final Texture waveCursorTexture;
    public final TextureRegion[] waveCursorTextureRegion;
    public final int waveCursorTexturesInAnimation = 4;

    public MapFiles(){
        mainIslandAtlasTexture = new Texture(Gdx.files.internal("map/main_island_atlas.png"));
        mapTouchableAreaPhysicsShape = new PhysicsShapeCache(Gdx.files.internal("map/mapArea4.xml"));
        mainIslandXMLMap = (Gdx.files.internal("map/main_island_map_config.xml"));

        waveCursorTexture = new Texture(Gdx.files.internal("white_wave.png"));
        waveCursorTextureRegion = Utils.textureRegionsSplit(waveCursorTexture, waveCursorTexturesInAnimation,1);
    }

    public void dispose(){
        mainIslandAtlasTexture.dispose();
        mapTouchableAreaPhysicsShape.dispose();
    }
}
