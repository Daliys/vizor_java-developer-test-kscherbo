package com.mygdx.path_finding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.utils.Constants;
import com.mygdx.vizorgame.GameScreen;

public class AStarMapGenerator {

    private static int mapWidth;
    private static int mapHeight;
    private static float scaleMapToWorldScale = 10;
    private static World _world;



    public static void generateMap(AStarMap aStarMap, final World world){
        _world = world;
        mapWidth = aStarMap.getWidth();
        mapHeight = aStarMap.getHeight();

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){
                if(CheckIsRayCasting(new Vector3((x * scaleMapToWorldScale) + (scaleMapToWorldScale/2),(y * scaleMapToWorldScale) + (scaleMapToWorldScale/2),0))){
                    aStarMap.getNodeAt(x,y).isGameObject = false;
                }else{
                    aStarMap.getNodeAt(x,y).isGameObject = true;
                }
            }
        }

    }

    private static boolean CheckIsRayCasting(Vector3 position) {

        list = new Array<>();

        _world.QueryAABB(queryCallback, position.x - scaleMapToWorldScale/2 , position.y -  scaleMapToWorldScale/2,
                position.x +  scaleMapToWorldScale/2, position.y +  scaleMapToWorldScale/2);

        boolean b = false;
        for (Fixture f : list) {
            if(!f.testPoint(position.x, position.y)) continue;
            if (f.getFilterData().categoryBits == Constants.BIT_GAME_OBJECT) return false;
            if (f.getFilterData().categoryBits == Constants.BIT_MAP) b = true;
        }
        list.clear();
        return b;
    }

    static com.badlogic.gdx.utils.Array<Fixture> list;

    private static QueryCallback queryCallback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            list.add(fixture);
            return true;
        }
    };

    //render Map cells
    public static void update(OrthographicCamera camera, AStarMap aStarMap){
        for(int y = 0; y < aStarMap.getHeight(); y++){
            for(int x = 0; x < aStarMap.getWidth(); x++) {
                if(!aStarMap.getNodeAt(x,y).isGameObject){
                    GameScreen.DrawDebugRect(new Vector2(x * scaleMapToWorldScale+0,y * scaleMapToWorldScale +0)
                            , (int)scaleMapToWorldScale-1, (int)scaleMapToWorldScale-1, 1, Color.BLUE, camera.combined);
                }else {
                    GameScreen.DrawDebugRect(new Vector2(x * scaleMapToWorldScale+0,y * scaleMapToWorldScale +0)
                            , (int)scaleMapToWorldScale-1, (int)scaleMapToWorldScale-1, 1, Color.RED, camera.combined);
                }

            }
        }
    }

}
