package com.mygdx.vizorgame;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.mygdx.utils.GameManager;
import com.mygdx.xmlreader.XMLMapReader;

public class TiledMapObject {

    private Body mapBody;

    private OrthogonalTiledMapRenderer renderer;
    private final World world;
    PhysicsShapeCache physicsBodiesMap ;

    public TiledMapObject(final World world){
        this.world = world;

        XMLMapReader xmlMapReader = new XMLMapReader();
        TiledMap map  = xmlMapReader.loadXMLTiledMap(GameManager.getInstance().filesLoader.map.mainIslandXMLMap);

        renderer = new OrthogonalTiledMapRenderer(map);
        physicsBodiesMap = GameManager.getInstance().filesLoader.map.mapTouchableAreaPhysicsShape;
        mapBody = physicsBodiesMap.createBody(GameManager.getInstance().filesLoader.map.nameMapTouchableAreaPhysicsShape,world,1f,1f);

        mapBody.setUserData(this);

    }

    public void update(OrthographicCamera camera){
        renderer.setView(camera);

        renderer.render();
    }

    public void dispose(){
        renderer.dispose();
        physicsBodiesMap.dispose();
    }

}
