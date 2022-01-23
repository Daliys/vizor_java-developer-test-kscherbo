package com.mygdx.woodcutter;

import com.badlogic.gdx.math.Vector2;

public class AnimationState {

    public enum State{
        STAY, MOVE_UP_LEFT, MOVE_UP_RIGHT, MOVE_DOWN_LEFT, MOVE_DOWN_RIGHT, WOODCUT
    }
    public boolean isWithWood;
    public State state = State.STAY;

    public AnimationState(){

    }

    public void updateState(Vector2 position, Vector2 target, Vector2 finishTarget){
        if(state == State.WOODCUT) return;

        if(target == null || finishTarget == null) {
            state = State.STAY;
            return;
        }
        if(position.y - target.y > 0.01f){ // down
           if((position.x - target.x) > 0.01f) state= State.MOVE_DOWN_LEFT;
           else if((target.x - position.x) > 0.01f) state = State.MOVE_DOWN_RIGHT;
           else{
               state =  finishTarget.x - position.x > 0? State.MOVE_DOWN_RIGHT : State.MOVE_DOWN_LEFT;
           }
        }else if ( target.y - position.y > 0.01f ) { // up
            if((position.x - target.x) > 0.01f) state= State.MOVE_UP_LEFT;
            else if((target.x - position.x) > 0.01f) state = State.MOVE_UP_RIGHT;
            else{
                state = finishTarget.x - position.x> 0? State.MOVE_UP_RIGHT : State.MOVE_UP_LEFT;
            }
        }
        else if(position.x - target.x > 0.01f){ // left
            if(( finishTarget.y - position.y) > 0.01f) state = State.MOVE_UP_LEFT;
            else state = State.MOVE_DOWN_LEFT;
        }else{
            if((finishTarget.y - position.y) > 0.01f) state = State.MOVE_UP_RIGHT;
            else state = State.MOVE_DOWN_RIGHT;
        }

    }

}
