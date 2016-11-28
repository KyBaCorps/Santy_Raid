package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Utils.Constants;

public class Cookies_and_milk extends InteractiveTileObject {
    public Cookies_and_milk(World world, TiledMap map, int objectLayer) {
        super(world, map, objectLayer);
        fixture.setUserData(this);
    }

    @Override
    public void onBodyHit() {
        Gdx.app.log("cookiesnmilk!", "Collision");
    }
}
