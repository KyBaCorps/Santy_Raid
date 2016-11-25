package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Utils.Constants;

public class Santa extends Sprite {
    public World world;
    public Body b2body;

    public Santa(World world) {
        this.world = world;
        defineSanta();
    }

    public void defineSanta() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(300/Constants.PPM, 500/Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        //THIS MAY BE WRONG
        shape.setRadius(5/Constants.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
