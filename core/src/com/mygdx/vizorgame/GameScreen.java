package com.mygdx.vizorgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen extends ScreenAdapter {

    public static final int WIDTH = 320 * 4;
    public static final int HEIGHT = 180 * 4;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private World world;

    private IsometricRenderer isometricRenderer;
    public Input input;


    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        camera.zoom = 1f;
        camera.position.y += 600;
        camera.update();

        world = new World(new Vector2(0,0),false);


        isometricRenderer = new IsometricRenderer(world);
        input = new Input(camera,world);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(122f / 255f, 170f / 255f, 201f / 255f, 1);
        //Gdx.gl.glClearColor(232 / 255f, 42 / 255f, 111 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(camera.combined);
        camera.update();

        isometricRenderer.update(camera);

        batch.begin();
        isometricRenderer.batchDraw(camera, batch);
        batch.end();

        isometricRenderer.lateUpdate(camera);


    }

    private void renderCells(){
        for (int i = 0; i < 28; i++) {
            DrawDebugLine(new Vector2(0, i * 100), new Vector2(3400, i * 100), 1, Color.BLACK, camera.combined);
        }
        for (int i = 0; i < 35; i++) {
            DrawDebugLine(new Vector2(i * 100, 0), new Vector2(i * 100, 2700), 1, Color.BLACK, camera.combined);
        }
    }


    private static ShapeRenderer debugRenderer = new ShapeRenderer();
    public static void DrawDebugLine(Vector2 start, Vector2 end, int lineWidth, Color color, Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.line(start, end);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

    public static void DrawDebugRect(Vector2 start, int width, int height, int lineWidth, Color color, Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.rect(start.x, start.y, width, height);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }


    @Override
    public void dispose() {
        super.dispose();
        isometricRenderer.dispose();
    }


}
