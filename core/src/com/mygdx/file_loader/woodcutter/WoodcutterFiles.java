package com.mygdx.file_loader.woodcutter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


public class WoodcutterFiles extends Woodcutter{

    private final String folder = "woodcutter_animation/woodcutter/";

    public WoodcutterFiles(){
        stand = new Texture(Gdx.files.internal(folder + "anim_woodcutter_stand.png"));
        walkDown = new Texture(Gdx.files.internal(folder + "anim_woodcutter_walk_down.png"));
        walkUp = new Texture(Gdx.files.internal(folder + "anim_woodcutter_walk_up.png"));
        walkWoodDown = new Texture(Gdx.files.internal(folder +"anim_woodcutter_walkwood_down.png"));
        walkWoodUp = new Texture(Gdx.files.internal(folder + "anim_woodcutter_walkwood_up.png"));
        woodcut = new Texture(Gdx.files.internal(folder + "anim_woodcutter_woodcut.png"));

        setUpTextureRegion();
    }



}
