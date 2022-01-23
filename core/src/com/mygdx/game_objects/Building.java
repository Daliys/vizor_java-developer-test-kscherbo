package com.mygdx.game_objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.mygdx.utils.Constants;


public abstract class Building extends GameObject {

    protected TextureRegion textureRegion[];
    protected float animationDuration;
    private TextureRegion current_frame;
    protected float state_time;

    private Animation animation;

    protected Body clickableBody;
    protected PhysicsShapeCache physicsBodies ;

    public Building(final World world) {
        super(world);
        position = new Vector3();
    }

    protected void Initialization(){
        state_time = MathUtils.random(0f, animationDuration);
        animation = new Animation(1, textureRegion);
        createInRandomPosition();
    }


    @Override
    protected void createBodies() {
        super.createBodies();
        createClickableBody();
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        current_frame = (TextureRegion) animation.getKeyFrame(state_time, true);
        batch.draw(current_frame, position.x, position.y);
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

    protected void createClickableBody(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = (BodyDef.BodyType.StaticBody);
        bodyDef.position.set(position.x, position.y);

        clickableBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.set(verticls);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = .95f;
        fixtureDef.density = 1f;
        fixtureDef.filter.categoryBits = Constants.BIT_CLICKABLE;
        fixtureDef.filter.maskBits = Constants.BIT_NULL;
        fixtureDef.filter.groupIndex = 0;

        clickableBody.createFixture(fixtureDef);
        shape.dispose();
        clickableBody.setUserData(this);

    }

    public void onClickAction(){
        if(state_time + 1 < animationDuration) state_time++;
    }

}
