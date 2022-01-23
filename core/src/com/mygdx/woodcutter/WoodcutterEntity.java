package com.mygdx.woodcutter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.utils.GameManager;
import com.mygdx.vizorgame.IUpdatable;

public class WoodcutterEntity implements IUpdatable {

    private final World world;


    private Vector2 position;
    private Vector2 targetPosition;

    private AnimationState animationState;

    private final WoodcutterAnimator bodyAnimator;
    private final WoodcutterAnimator clothAnimator;
    private final WoodcutterAnimator doubleClothAnimator;
    private final WoodcutterAnimator hatAnimator;
    private final WoodcutterAnimator doubleHatAnimator;

    private float state_time_animation = 0;
    private final Vector2 offset = new Vector2(-137,-81);
    private int currentHat = 0;
    private int currentCloth = 0;
    public boolean isReachTarget = true;
    private float entitySpeed = 120;


    public WoodcutterEntity(final World world, Vector2 pos){
        this.world = world;
        animationState = new AnimationState();

        // position = new Vector2(GameManager.getInstance().spawnPlayerPosition.x,GameManager.getInstance().spawnPlayerPosition.y);
         position = new Vector2(pos.x, pos.y);

        bodyAnimator = new WoodcutterAnimator(animationState, true, GameManager.getInstance().filesLoader.woodcutter.woodcutter);
        clothAnimator = new WoodcutterAnimator(animationState, false, GameManager.getInstance().filesLoader.woodcutter.cloth);
        doubleClothAnimator = new WoodcutterAnimator(animationState, false, GameManager.getInstance().filesLoader.woodcutter.doubleCloth);
        hatAnimator = new WoodcutterAnimator(animationState, false, GameManager.getInstance().filesLoader.woodcutter.hat);
        doubleHatAnimator = new WoodcutterAnimator(animationState, false, GameManager.getInstance().filesLoader.woodcutter.doubleHat);
    }


    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        state_time_animation += Gdx.graphics.getDeltaTime();

        bodyAnimator.render(batch, position, offset , state_time_animation);
        clothAnimator.render(batch,position, offset ,state_time_animation);
        doubleClothAnimator.render(batch,position,offset , state_time_animation);
        hatAnimator.render(batch,position, offset ,state_time_animation);
        doubleHatAnimator.render(batch,position,offset,state_time_animation);
    }

    @Override
    public void update(OrthographicCamera camera) {

        if(targetPosition != null && !isReachTarget){
            if(Math.abs(targetPosition.dst(position)) <= 1f){
                isReachTarget = true;
                position.x = targetPosition.x;
                position.y = targetPosition.y;
                targetPosition = null;
            }else {
                moveToTarget();
            }
        }else{
            animationState.updateState(position, null, null);
        }

    }

    @Override
    public void lateUpdate(OrthographicCamera camera) {

    }

    @Override
    public void dispose() {


    }

    private void moveToTarget(){
        Vector2 normal = new Vector2(targetPosition.x - position.x, targetPosition.y -  position.y).nor();
        position.x += normal.x * entitySpeed * Gdx.graphics.getDeltaTime();
        position.y += normal.y  * entitySpeed * Gdx.graphics.getDeltaTime();

    }

    public void MoveToPosition(Vector2 position, Vector2 finishTarget){
        targetPosition = position;
        isReachTarget = false;
        animationState.updateState(this.position, targetPosition, finishTarget);
    }


    public Vector2 getPosition() {
        return position;
    }

    public AnimationState getAnimationState() {
        return animationState;
    }

    public void changeCloth(){
        currentCloth++;
        if(currentCloth >2) currentCloth = 0;
        if(currentCloth == 0){
            doubleClothAnimator.setActiveAnimator(false);
        }else if(currentCloth == 1){
            clothAnimator.setActiveAnimator(true);
        }else if(currentCloth == 2){
            doubleClothAnimator.setActiveAnimator(true);
            clothAnimator.setActiveAnimator(false);
        }
    }
    public void changeHat(){
        currentHat++;
        if(currentHat >2) currentHat= 0;
        if(currentHat == 0){
            doubleHatAnimator.setActiveAnimator(false);
        }else if(currentHat == 1){
            hatAnimator.setActiveAnimator(true);
        }else if(currentHat == 2){
            hatAnimator.setActiveAnimator(false);
            doubleHatAnimator.setActiveAnimator(true);
        }
    }

}
