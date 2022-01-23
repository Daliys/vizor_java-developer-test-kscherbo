package com.mygdx.game_objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.utils.Constants;
import com.mygdx.vizorgame.IUpdatable;

public abstract class GameObject implements IUpdatable {

    protected Body body;
    protected World world;
    protected Vector3 position;
    protected  String physicsBodyName;
    protected  Vector2[] verticls;
    private Array<Fixture> list;


    public GameObject(final World world){
        this.world = world;
    }

    public void createInRandomPosition() {
        Vector3 randPos = new Vector3();
        boolean isCreated = false;
        while (!isCreated) {
            randPos = new Vector3(MathUtils.random(.34f) * 10000, MathUtils.random(.27f) * 10000, 0);
            isCreated = true;

            for(Vector2 vector : verticls){
                if(!CheckIsRayCasting(new Vector3(randPos.x + vector.x, randPos.y + vector.y, 0))){
                    isCreated = false;
                    break;
                }
            }
        }

        position = randPos;
        createBodies();
    }

    protected void createBodies(){
        createBody();
    };


    protected boolean CheckIsRayCasting(Vector3 pos) {
        position = pos;
        list = new Array<>();

        world.QueryAABB(queryCallback, position.x - 0.1f, position.y - 0.1f, position.x + 0.1f, position.y + 0.1f);

        boolean isMap = false;
        for (Fixture f : list) {
            if(!f.testPoint(position.x, position.y)) continue;
            if (f.getFilterData().categoryBits == Constants.BIT_GAME_OBJECT) return false;
            if (f.getFilterData().categoryBits == Constants.BIT_MAP) isMap = true;
        }
        list.clear();
        return isMap;
    }


    private QueryCallback queryCallback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            list.add(fixture);
            return true;
        }
    };



    private void createBody() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = (BodyDef.BodyType.StaticBody);
        bodyDef.position.set(position.x, position.y);


        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.set(verticls);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = .95f;
        fixtureDef.density = 1f;
        fixtureDef.filter.categoryBits = Constants.BIT_GAME_OBJECT;
        fixtureDef.filter.maskBits = Constants.BIT_PLAYER | Constants.BIT_MAP | Constants.BIT_GAME_OBJECT;
        fixtureDef.filter.groupIndex = 0;

        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);
    }


    private static ShapeRenderer debugRenderer = new ShapeRenderer();

    public static void DrawDebugLine(Vector2 start, Vector2 end, int lineWidth, Color color, Matrix4 projectionMatrix) {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.line(start, end);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(position.x, position.y);
    }

}
