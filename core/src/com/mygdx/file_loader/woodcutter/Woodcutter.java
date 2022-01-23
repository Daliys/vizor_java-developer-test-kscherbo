package com.mygdx.file_loader.woodcutter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.utils.Utils;

public abstract class Woodcutter {

    protected int spriteWidth = 267;

    protected Texture stand;
    protected Texture walkDown;
    protected Texture walkUp;
    protected Texture walkWoodDown;
    protected Texture walkWoodUp;
    protected Texture woodcut;

    protected TextureRegion[] standTextureRegion;
    protected TextureRegion[] walkDownTextureRegion;
    protected TextureRegion[] walkUpTextureRegion;
    protected TextureRegion[] walkWoodDownTextureRegion;
    protected TextureRegion[] walkWoodUpTextureRegion;
    protected TextureRegion[] woodcutTextureRegion;


    protected void setUpTextureRegion(){
        standTextureRegion = Utils.textureRegionsSplit(stand,1,1);
        walkDownTextureRegion = Utils.textureRegionsSplit(walkDown, walkDown.getWidth()/spriteWidth, 1);
        walkUpTextureRegion = Utils.textureRegionsSplit(walkUp, walkUp.getWidth()/spriteWidth, 1);
        walkWoodDownTextureRegion = Utils.textureRegionsSplit(walkWoodDown, walkWoodDown.getWidth()/spriteWidth, 1);
        walkWoodUpTextureRegion = Utils.textureRegionsSplit(walkWoodUp, walkWoodUp.getWidth()/spriteWidth, 1);
        woodcutTextureRegion = Utils.textureRegionsSplit(woodcut, woodcut.getWidth()/spriteWidth, 1);
    }


    public void dispose(){
        stand.dispose();
        walkDown.dispose();
        walkUp.dispose();
        walkWoodDown.dispose();
        walkWoodUp.dispose();
        woodcut.dispose();
    }


    public Texture getStand() {
        return stand;
    }
    public Texture getWalkDown() {
        return walkDown;
    }
    public Texture getWalkUp() {
        return walkUp;
    }
    public Texture getWalkWoodDown() {
        return walkWoodDown;
    }
    public Texture getWalkWoodUp() {
        return walkWoodUp;
    }
    public Texture getWoodcut() {
        return woodcut;
    }

    public TextureRegion[] getStandTextureRegion() {
        return standTextureRegion;
    }
    public TextureRegion[] getWalkDownTextureRegion() {
        return walkDownTextureRegion;
    }
    public TextureRegion[] getWalkUpTextureRegion() {
        return walkUpTextureRegion;
    }
    public TextureRegion[] getWalkWoodDownTextureRegion() {
        return walkWoodDownTextureRegion;
    }
    public TextureRegion[] getWalkWoodUpTextureRegion() {
        return walkWoodUpTextureRegion;
    }
    public TextureRegion[] getWoodcutTextureRegion() {
        return woodcutTextureRegion;
    }



}
