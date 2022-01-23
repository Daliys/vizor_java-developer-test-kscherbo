package com.mygdx.woodcutter;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.file_loader.woodcutter.Woodcutter;

public class WoodcutterAnimator {

    private AnimationState state;
    private boolean isActiveAnimator;

    private final Animation standAnimation;
    private final Animation walkDownAnimation;
    private final Animation walkUpAnimation;
    private final Animation walkWoodDownAnimation;
    private final Animation walkWoodUpAnimation;
    private final Animation woodcutAnimation;

    private TextureRegion currentFrame;

    private final float timeFrameDuration = 0.1f/1.8f;

    public WoodcutterAnimator(AnimationState state, boolean isActive, Woodcutter woodcutter) {
        this.state = state;
        isActiveAnimator = isActive;


        standAnimation = new Animation(timeFrameDuration, woodcutter.getStandTextureRegion());
        walkDownAnimation = new Animation(timeFrameDuration, woodcutter.getWalkDownTextureRegion());
        walkUpAnimation = new Animation(timeFrameDuration, woodcutter.getWalkUpTextureRegion());
        walkWoodDownAnimation = new Animation(timeFrameDuration, woodcutter.getWalkWoodDownTextureRegion());
        walkWoodUpAnimation = new Animation(timeFrameDuration, woodcutter.getWalkWoodUpTextureRegion());
        woodcutAnimation = new Animation(timeFrameDuration, woodcutter.getWoodcutTextureRegion());

        currentFrame = (TextureRegion) standAnimation.getKeyFrame(0, true);

    }


    public void render(SpriteBatch batch, Vector2 position, Vector2 positionOffset , float time) {
        if (!isActiveAnimator) return;

        if (refreshCurrentFrame(time)) {
            batch.draw(currentFrame, position.x  + positionOffset.x + currentFrame.getRegionWidth(), position.y + positionOffset.y, -currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        } else {
            batch.draw(currentFrame, position.x + positionOffset.x, position.y + positionOffset.y);
        }
    }

    private boolean refreshCurrentFrame(float time) {
        boolean isFlipX = false;
        switch (state.state) {
            case STAY:
                currentFrame = (TextureRegion) standAnimation.getKeyFrame(time,true);
                break;
            case MOVE_DOWN_LEFT:
                currentFrame = (TextureRegion) (state.isWithWood ? walkWoodDownAnimation.getKeyFrame(time,true) : walkDownAnimation.getKeyFrame(time,true));
                break;
            case MOVE_DOWN_RIGHT:
                currentFrame = (TextureRegion) (state.isWithWood ? walkWoodDownAnimation.getKeyFrame(time,true) : walkDownAnimation.getKeyFrame(time,true));
                isFlipX = true;
                break;
            case MOVE_UP_LEFT:
                currentFrame = (TextureRegion) (state.isWithWood ? walkWoodUpAnimation.getKeyFrame(time,true) : walkUpAnimation.getKeyFrame(time,true));
                break;
            case MOVE_UP_RIGHT:
                currentFrame = (TextureRegion) (state.isWithWood ? walkWoodUpAnimation.getKeyFrame(time,true) : walkUpAnimation.getKeyFrame(time,true));
                isFlipX = true;
                break;
            case WOODCUT:
                currentFrame = (TextureRegion) woodcutAnimation.getKeyFrame(time,true);
        }
        return isFlipX;
    }


    public boolean isActiveAnimator() {
        return isActiveAnimator;
    }

    public void setActiveAnimator(boolean activeAnimator) {
        isActiveAnimator = activeAnimator;
    }

}
