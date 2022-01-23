package com.mygdx.woodcuter_controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_objects.Tree;
import com.mygdx.utils.GameManager;

public class PlayerController extends WoodCutterController {

    public PlayerController(World world) {
        super(world);
        position =  new Vector2(GameManager.getInstance().spawnPlayerPosition.x, GameManager.getInstance().spawnPlayerPosition.y);;
        Initialization();
    }

    public void setTargetPositionToMove(Vector2 target) {
        if (target == null) return;
        if (!switchState()) return;

        animationTimer = 0;
        targetPosition = new Vector2();
        targetPosition.x = target.x;
        targetPosition.y = target.y;
        currentAction = CurrentAction.moveToPosition;
        pathFinding.findPath(entity.getPosition(), targetPosition);
    }

    public void setTreeTargetToCut(Tree tree) {
        if (tree == null) return;
        if (!switchState()) return;

        treeHandler = TreeKeeper.getInstance().getTree(tree);
        if(treeHandler.woodCutterController != null){
            treeHandler = null;
            return;
        }

        targetPosition = new Vector2(tree.getPlayerOffsetForCutting().x, tree.getPlayerOffsetForCutting().y);
        pathFinding.findPath(entity.getPosition(), targetPosition);

        if (pathFinding.getNextNode() == null) {
            targetPosition = null;
            treeHandler = null;
            return;
        }

        treeHandler.woodCutterController = this;
        currentAction = CurrentAction.moveToTree;
        lastPosition = new Vector2(position.x, position.y);
    }

    private boolean switchState() {
        if (currentAction == CurrentAction.moveToTree) return true;
        if (currentAction == CurrentAction.cutTree) return false;
        if (currentAction == CurrentAction.returnWithWood) {
            entity.getAnimationState().isWithWood = false;
            targetPosition = null;
            treeHandler = null;
            return true;
        }
        return true;
    }

    public void changeHat() {
        entity.changeHat();
    }

    public void changeCloth() {
        entity.changeCloth();
    }


}
