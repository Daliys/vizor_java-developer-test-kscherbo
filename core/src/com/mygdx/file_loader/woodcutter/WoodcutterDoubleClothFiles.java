package com.mygdx.file_loader.woodcutter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WoodcutterDoubleClothFiles extends Woodcutter{

    private final String folder = "woodcutter_animation/double_cloth/";

    public WoodcutterDoubleClothFiles() {
        stand = new Texture(Gdx.files.internal(folder + "anim_woodcutter_double_cloth_stand.png"));
        walkDown = new Texture(Gdx.files.internal(folder + "anim_woodcutter_double_cloth_walk_down.png"));
        walkUp = new Texture(Gdx.files.internal(folder + "anim_woodcutter_double_cloth_walk_up.png"));
        walkWoodDown = new Texture(Gdx.files.internal(folder + "anim_woodcutter_double_cloth_walkwood_down.png"));
        walkWoodUp = new Texture(Gdx.files.internal(folder + "anim_woodcutter_double_cloth_walkwood_up.png"));
        woodcut = new Texture(Gdx.files.internal(folder + "anim_woodcutter_double_cloth_woodcut.png"));

        setUpTextureRegion();
    }
}
