package com.mygdx.utils;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.file_loader.FilesLoader;
import com.mygdx.woodcuter_controller.PlayerController;

public class GameManager {

    private static GameManager instance;

    public final FilesLoader filesLoader;
    public final Vector2 spawnPlayerPosition = new Vector2(175,950);
    public PlayerController playerController;

    public GameManager(){

        filesLoader = new FilesLoader();

    }

    public void dispose(){
        filesLoader.dispose();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

}
