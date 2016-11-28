package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Utils.Constants;

public class Santa extends Sprite {
    public enum State {WALKING, STILL, CROUCHING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    public boolean isCrouched = false;
    private TextureRegion santaStill;
    private TextureRegion santaCrouch;
    private Animation santaWalk;
    private float stateTimer;
    private boolean walkingRight;

    public Santa(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("Santa_still"));
        this.world = world;
        currentState = State.STILL;
        previousState = State.STILL;
        stateTimer = 0;
        walkingRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        //for(int i = 1; i<3; i++)
            //frames.add(new TextureRegion(getTexture(), i*640, 363, 640, 360));
        frames.add(screen.getAtlas().findRegion("Santa_walk_one"));
        frames.add(screen.getAtlas().findRegion("Santa_walk_two"));
        santaWalk = new Animation(0.1f, frames);
        frames.clear();

        defineSanta();
        santaCrouch = new TextureRegion(getTexture(), 1, 363, 640, 360);
        santaStill = new TextureRegion(getTexture(), 1, 1, 640, 360);
        setBounds(1, 1, 640/Constants.PPM/5, 360/Constants.PPM/5);
        setRegion(santaStill);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch(currentState) {
            case WALKING:
                region = santaWalk.getKeyFrame(stateTimer, true);
                break;
            case CROUCHING:
                region = santaCrouch;
                break;
            case STILL:
            default:
                region = santaStill;
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !walkingRight) && !region.isFlipX()) {
            region.flip(true, false);
            walkingRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || walkingRight) && region.isFlipX()) {
            region.flip(true, false);
            walkingRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if(b2body.getLinearVelocity().x != 0 && !this.isCrouched)
            return State.WALKING;
        else if(this.isCrouched) {
            return State.CROUCHING;
        }
        else
            return State.STILL;
    }

    public void defineSanta() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(300/Constants.PPM, 500/Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25/Constants.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape body = new EdgeShape();
        body.set(new Vector2(30/Constants.PPM, 5 / Constants.PPM), new Vector2(30/Constants.PPM, -5/Constants.PPM));
        fdef.shape = body;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("body");
    }
}
