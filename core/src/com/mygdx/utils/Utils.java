package com.mygdx.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public abstract class Utils {

    public static TextureRegion[] textureRegionsSplit(final Texture texture, int cols, int rows) {
        TextureRegion[] result =new TextureRegion[cols*rows];
        TextureRegion[][] temp = TextureRegion.split(texture, texture.getWidth() / cols, texture.getHeight() / rows);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++){
                result[y*cols + x] = temp[y][x];
            }
        }
        return result;
    }

}
