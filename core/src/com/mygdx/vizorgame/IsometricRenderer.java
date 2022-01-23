package com.mygdx.vizorgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game_objects.*;
import com.mygdx.utils.GameManager;
import com.mygdx.woodcuter_controller.AIWoodcutterController;
import com.mygdx.woodcuter_controller.PlayerController;
import com.mygdx.woodcuter_controller.TreeKeeper;

import java.util.Comparator;


public class IsometricRenderer {

    private World world;
    private Box2DDebugRenderer b2dr;
    private Array<IUpdatable> gameObjects;
    private TiledMapObject tiledMapObject;
    private PlayerController playerController;

    public IsometricRenderer(World world) {
        this.world = world;

        b2dr = new Box2DDebugRenderer();

        tiledMapObject = new TiledMapObject(world);

        generateGameObjects();

        playerController = new PlayerController(world);
        GameManager.getInstance().playerController = playerController;

        AIWoodcutterController ai = new AIWoodcutterController(world);
        AIWoodcutterController ai2 = new AIWoodcutterController(world);

        gameObjects.add(ai);
        gameObjects.add(ai2);
        gameObjects.add(playerController);

    }


    private void generateGameObjects() {
        gameObjects = new Array<>();

        for (int i = 0; i < MathUtils.random(8, 17); i++) {
            if (MathUtils.random(0f, 1f) < 0.7f) gameObjects.add(new TowerBuilding(world));
            else gameObjects.add(new CryptBuilding(world));
        }

        for (int i = 0; i < MathUtils.random(30, 70); i++) {
            float rand = MathUtils.random(0, 1);
            if (rand < 0.25f) gameObjects.add(new TropicPalmTree(world));
            else if (rand >= 0.25f && rand < 0.65f) gameObjects.add(new PalmTree(world));
            else gameObjects.add(new OakTree(world));

            TreeKeeper.getInstance().addTree((Tree) gameObjects.get(gameObjects.size-1));
        }
    }

    private void sortIUpdatable() {
        gameObjects.sort(new SortByPosY());
    }


    public void batchDraw(OrthographicCamera camera, SpriteBatch batch) {
        for (IUpdatable updatable : gameObjects) {
            updatable.render(batch, camera);
        }
    }

    public void update(OrthographicCamera camera) {
        world.step(1 / 60f, 6, 2);
        tiledMapObject.update(camera);

        for (IUpdatable updatable : gameObjects) {
            updatable.update(camera);
        }

    }


    public void lateUpdate(OrthographicCamera camera) {
      //  b2dr.render(world, camera.combined);

        for (IUpdatable updatable : gameObjects) {
            updatable.lateUpdate(camera);
        }

        sortIUpdatable();
    }


    public void dispose() {
        b2dr.dispose();
        world.dispose();

        for (IUpdatable updatable : gameObjects) {
            updatable.dispose();
        }

        tiledMapObject.dispose();
    }


}

class SortByPosY implements Comparator<IUpdatable> {

    public int compare(IUpdatable a,IUpdatable b)
    {
        return (int)b.getPosition().y - (int)a.getPosition().y;
    }
}




