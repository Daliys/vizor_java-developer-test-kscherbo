package com.mygdx.vizorgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game_objects.Building;
import com.mygdx.game_objects.GameObject;
import com.mygdx.game_objects.Tree;
import com.mygdx.utils.Constants;
import com.mygdx.utils.GameManager;

public class Input extends InputAdapter {

    private final float zoomSpeed = 5f;
    private final float maxZoom = 2f;
    private final float minZoom = 0.5f;


    private OrthographicCamera camera;
    private World world;
    private Vector3 position = new Vector3();
    private boolean isDragged;
    private Array<Fixture> list;

    public Input(OrthographicCamera camera, World world){
        this.camera = camera;
        this.world = world;
        Gdx.input.setInputProcessor(this);
        isDragged = false;
        list = new Array<>();
    }


    private QueryCallback queryCallback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            list.add(fixture);
            return true;
        }
    };

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return super.touchDown(screenX, screenY, pointer, button);
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!isDragged) {
            position.set(screenX, screenY, 0);
            camera.unproject(position);
            world.QueryAABB(queryCallback, position.x - 0.1f, position.y - 0.1f, position.x + 0.1f, position.y + 0.1f);

            GameObject gm = null;
            boolean isMap = false;
            boolean isBuilding = false;

            for (Fixture f : list) {
                if (!f.testPoint(position.x, position.y)) continue;

                if (f.getFilterData().categoryBits == Constants.BIT_CLICKABLE) {
                    gm = (GameObject) f.getBody().getUserData();
                    break;
                }
                if (f.getFilterData().categoryBits == Constants.BIT_MAP) {
                    isMap = true;
                }
                if(f.getFilterData().categoryBits == Constants.BIT_GAME_OBJECT){
                    isBuilding = true;
                }
            }

            if (gm != null) {
                if(gm instanceof Tree) {
                    GameManager.getInstance().playerController.setTreeTargetToCut((Tree) gm);
                }else if(gm instanceof Building){
                    ((Building)gm).onClickAction();
                }
            } else if (isMap && !isBuilding) {
                GameManager.getInstance().playerController.setTargetPositionToMove( (new Vector2(position.x, position.y)));
            }

            list.clear();
        }


        isDragged = false;
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        isDragged = true;
        camera.translate(-Gdx.input.getDeltaX() * camera.zoom, Gdx.input.getDeltaY() * camera.zoom);
        if(camera.position.x < -100) camera.position.x = -100;
        if(camera.position.x > 3500) camera.position.x = 3500;
        if(camera.position.y < -100) camera.position.y = -100;
        if(camera.position.y > 2800) camera.position.y = 2800;
        camera.update();
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        camera.zoom += zoomSpeed* amountY * Gdx.graphics.getDeltaTime();
        if(camera.zoom >maxZoom) camera.zoom = maxZoom;
        if (camera.zoom < minZoom) camera.zoom = minZoom;

        return super.scrolled(amountX, amountY);
    }
}
