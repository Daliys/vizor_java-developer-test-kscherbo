package com.mygdx.file_loader.woodcutter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WoodcutterHatFiles extends Woodcutter {

    private final String folder = "woodcutter_animation/hat/";

    public WoodcutterHatFiles(){
        stand = new Texture(Gdx.files.internal(folder + "anim_woodcutter_hat_stand.png"));
        walkDown = new Texture(Gdx.files.internal(folder + "anim_woodcutter_hat_walk_down.png"));
        walkUp = new Texture(Gdx.files.internal(folder + "anim_woodcutter_hat_walk_up.png"));
        walkWoodDown = new Texture(Gdx.files.internal(folder +"anim_woodcutter_hat_walkwood_down.png"));
        walkWoodUp = new Texture(Gdx.files.internal(folder + "anim_woodcutter_hat_walkwood_up.png"));
        woodcut = new Texture(Gdx.files.internal(folder + "anim_woodcutter_hat_woodcut.png"));

        setUpTextureRegion();
    }

}
