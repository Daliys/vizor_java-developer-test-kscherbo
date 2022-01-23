package com.mygdx.woodcuter_controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.utils.Constants;

public class AIWoodcutterController extends WoodCutterController {

    public AIWoodcutterController(World world) {
        super(world);
        animationTimer = 100;

        Vector3 randPos = new Vector3();
        boolean hasFoundEmptyPosition = false;

        while (!hasFoundEmptyPosition) {
            randPos = new Vector3(MathUtils.random(.34f) * 10000, MathUtils.random(.27f) * 10000, 0);

            if (!CheckIsRayCasting(new Vector3(randPos.x, randPos.y, 0)))continue;
            hasFoundEmptyPosition = true;
        }

        position = new Vector2(randPos.x, randPos.y);

        Initialization();
    }

    @Override
    protected void findAnotherTarget() {
        if(currentAction != CurrentAction.waiting) return;
        if(treeHandler != null ){
            TreeKeeper.getInstance().removeTree(this);
            treeHandler = null;
        }
        if(MathUtils.random(0f, 1f) > 0.6){
            getRandomLocation();
        }else{
            getRandomTree();
        }
    }

    private void getRandomTree(){
        TreeKeeper.TreeHandler handler = TreeKeeper.getInstance().getRandomTree();
        if(handler.woodCutterController == null){
            pathFinding.findPath(position, handler.tree.getPlayerOffsetForCutting());
            if(pathFinding.getNextNode() != null){
                handler.woodCutterController = this;
                targetPosition = handler.tree.getPlayerOffsetForCutting();
                currentAction = CurrentAction.moveToTree;
                lastPosition = new Vector2(position.x, position.y);
                treeHandler = handler;
            }else{
                return;
            }
        }
    }

    private void getRandomLocation() {
        Vector3 randPos = new Vector3();
        boolean hasFoundEmptyPosition = false;

        while (!hasFoundEmptyPosition) {
            randPos = new Vector3(MathUtils.random(.34f) * 10000, MathUtils.random(.27f) * 10000, 0);

            if (!CheckIsRayCasting(new Vector3(randPos.x, randPos.y, 0)))continue;
            hasFoundEmptyPosition = true;
        }

        pathFinding.findPath(position, new Vector2(randPos.x, randPos.y));
        if(pathFinding.getNextNode() == null) getRandomLocation();
        else {
            targetPosition = new Vector2(randPos.x, randPos.y);
            currentAction = CurrentAction.moveToPosition;
        }
    }

    private Array<Fixture> list;

    protected boolean CheckIsRayCasting(Vector3 pos) {
        list = new Array<>();

        world.QueryAABB(queryCallback, pos.x - 0.1f, pos.y - 0.1f, pos.x + 0.1f, pos.y + 0.1f);

        boolean isMap = false;
        for (Fixture f : list) {
            if(!f.testPoint(pos.x, pos.y)) continue;
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


}
