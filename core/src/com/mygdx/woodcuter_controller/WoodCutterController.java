package com.mygdx.woodcuter_controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.path_finding.AStarMap;
import com.mygdx.path_finding.AStarMapGenerator;
import com.mygdx.path_finding.Node;
import com.mygdx.path_finding.PathFinding;
import com.mygdx.utils.GameManager;
import com.mygdx.vizorgame.GameScreen;
import com.mygdx.vizorgame.IUpdatable;
import com.mygdx.woodcutter.AnimationState;
import com.mygdx.woodcutter.WoodcutterEntity;

public class WoodCutterController implements IUpdatable {

    protected final float CUTTING_TREE_TIME = 3f;
    protected TreeKeeper.TreeHandler treeHandler = null;

    protected final World world;
    protected PathFinding pathFinding;
    protected Vector2 position = null;
    protected Vector2 targetPosition = null;
    protected Vector2 lastPosition = null;

    protected WoodcutterEntity entity;

    protected float timer;

    protected Animation waveCursorAnimation;
    protected float animationTimer;
    protected final Vector2 animationOffset = new Vector2(-34, -18);

    protected enum CurrentAction {
        moveToPosition, moveToTree, cutTree, returnWithWood, waiting
    }

    protected CurrentAction currentAction = CurrentAction.waiting;

    public WoodCutterController(final World world) {
        this.world = world;
        AStarMap aStarMap = new AStarMap(340, 270);
        AStarMapGenerator.generateMap(aStarMap, world);

        pathFinding = new PathFinding(aStarMap);


        waveCursorAnimation = new Animation(0.15f, GameManager.getInstance().filesLoader.map.waveCursorTextureRegion);
        animationTimer = 0;
    }

    protected void Initialization(){
        entity = new WoodcutterEntity(world, position);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {

        entity.render(batch, camera);
        if (targetPosition != null && animationTimer < waveCursorAnimation.getAnimationDuration()) {
            batch.draw((TextureRegion) waveCursorAnimation.getKeyFrame(animationTimer, true), targetPosition.x + animationOffset.x, targetPosition.y + animationOffset.y);
            animationTimer += Gdx.graphics.getDeltaTime();
        }
    }


    @Override
    public void update(OrthographicCamera camera) {
        position = (entity.getPosition());
     //   if (position != null && targetPosition != null) RenderPath(camera);
        entity.update(camera);
    }

    @Override
    public void lateUpdate(OrthographicCamera camera) {
        entity.lateUpdate(camera);

        moveToPos();
        if (currentAction == CurrentAction.cutTree) cuttingTreeTimer();
    }
    private void moveToPos() {
        if(targetPosition == null) {
            findAnotherTarget();
            return;
        }
        if (entity.isReachTarget) {

            if (pathFinding.getNextNode() == null) {
                if (currentAction == CurrentAction.moveToPosition) {
                    targetPosition = null;
                    currentAction = CurrentAction.waiting;
                } else if (currentAction == CurrentAction.moveToTree) {
                    currentAction = CurrentAction.cutTree;
                    entity.getAnimationState().state = AnimationState.State.WOODCUT;
                    targetPosition = null;
                } else if (currentAction == CurrentAction.returnWithWood) {
                    currentAction = CurrentAction.waiting;
                    entity.getAnimationState().isWithWood = false;
                    targetPosition = null;

                }
                return;
            }

            Node node = pathFinding.getNextNode();
            Vector2 pos = new Vector2(node.x * PathFinding.scaleMapToWorldScale + PathFinding.scaleMapToWorldScale/2,
                    node.y * PathFinding.scaleMapToWorldScale + PathFinding.scaleMapToWorldScale/2);

            Vector2 finishPos = new Vector2(pathFinding.getLastNode().x *PathFinding.scaleMapToWorldScale + PathFinding.scaleMapToWorldScale/2
                    , pathFinding.getLastNode().y * PathFinding.scaleMapToWorldScale + PathFinding.scaleMapToWorldScale/2);

            entity.MoveToPosition(pos, finishPos);
            pathFinding.nodeCounter++;

        }
    }

    protected void findAnotherTarget(){

    }

    private void cuttingTreeTimer() {
        timer += Gdx.graphics.getDeltaTime();
        if (timer > CUTTING_TREE_TIME) {
            timer = 0;
            currentAction = CurrentAction.returnWithWood;

            entity.getAnimationState().isWithWood = true;
            entity.getAnimationState().state = AnimationState.State.STAY;
            treeHandler.tree.cutTree();
            TreeKeeper.getInstance().removeTree(this);

            targetPosition = new Vector2(lastPosition.x, lastPosition.y);
            pathFinding.findPath(entity.getPosition(), targetPosition);
            lastPosition = null;
        }
    }

    @Override
    public void dispose() {
        entity.dispose();
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    private void RenderPath(OrthographicCamera camera) {

        GameScreen.DrawDebugRect(new Vector2(position.x, position.y), 6, 6, 2, Color.CYAN, camera.combined);
        GameScreen.DrawDebugRect(new Vector2(targetPosition.x, targetPosition.y), 6, 6, 2, Color.RED, camera.combined);

        Node temp;

        GraphPath<Connection<Node>> path = pathFinding.getFullConnectionPath();
        Vector2 lastPosition = new Vector2(0, 0);

        for (int i = 0; i < path.getCount(); i++) {
            if (i == 0) lastPosition = new Vector2(path.get(0).getToNode().x, path.get(0).getToNode().y);

            temp = path.get(i).getToNode();
            GameScreen.DrawDebugLine(new Vector2(lastPosition.x * 10 + 5, lastPosition.y * 10 + 5), new Vector2(temp.x * 10 + 5, temp.y * 10 + 5), 1, Color.GOLD, camera.combined);
            lastPosition = new Vector2(temp.x, temp.y);
        }
    }


}
