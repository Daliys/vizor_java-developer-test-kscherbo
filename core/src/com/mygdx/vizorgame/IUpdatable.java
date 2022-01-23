package com.mygdx.vizorgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface IUpdatable {

    public void render(SpriteBatch batch, OrthographicCamera camera);
    public void update(OrthographicCamera camera);
    public void lateUpdate(OrthographicCamera camera);
    public void dispose();
    public Vector2 getPosition();

}
