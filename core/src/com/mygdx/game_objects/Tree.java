package com.mygdx.game_objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.mygdx.utils.Constants;

public abstract class Tree extends GameObject  {

    protected Texture sprite;
    protected Texture stumpSprite;

    protected boolean isCutOff = false;
    protected Vector2 playerOffsetForCutting;

    protected Body clickableBody;
    protected PhysicsShapeCache physicsBodies ;

    public Tree(final World world){
        super(world);
    }

    @Override
    protected void createBodies() {
        super.createBodies();
        createClickableBody();
    }

    protected void Initialization(){
        createInRandomPosition();
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (isCutOff) batch.draw(stumpSprite, position.x, position.y);
        else batch.draw(sprite, position.x, position.y);
    }

    @Override
    public void update(OrthographicCamera camera) {

    }

    @Override
    public void lateUpdate(OrthographicCamera camera) {

    }

    @Override
    public void dispose() {
    }

    public Vector2 getPlayerOffsetForCutting() {
        return new Vector2(position.x + playerOffsetForCutting.x, position.y + playerOffsetForCutting.y);
    }

    public void cutTree(){
        clickableBody.setActive(false);
        isCutOff = true;
    }

    protected void createClickableBody(){

        clickableBody = physicsBodies.createBody(physicsBodyName,world,1,1);
        clickableBody.setTransform(position.x, position.y, 0);
        clickableBody.setUserData(this);
        Array<Fixture> f = clickableBody.getFixtureList();

        Filter filter = new Filter();
        filter.categoryBits = Constants.BIT_CLICKABLE;
        filter.maskBits = Constants.BIT_NULL;
        filter.groupIndex = 0;

        for(Fixture fixture: f){
            fixture.setFilterData(filter);
        }
    }

    public boolean isCutOff() {
        return isCutOff;
    }
}
