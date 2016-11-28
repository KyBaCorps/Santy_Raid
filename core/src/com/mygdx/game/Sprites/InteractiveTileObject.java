package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Utils.Constants;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected int objectLayer;
    protected Body body;

    protected Fixture fixture;

    public InteractiveTileObject(World world, TiledMap map, int objectLayer) {
        this.world = world;
        this.map = map;
        this.objectLayer = objectLayer;

        for(MapObject object : map.getLayers().get(objectLayer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            BodyDef bdef = new BodyDef();
            FixtureDef fdef = new FixtureDef();
            PolygonShape shape = new PolygonShape();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Constants.PPM, rect.getHeight() / 2 / Constants.PPM);
            fdef.shape = shape;
            fixture = body.createFixture(fdef);
        }
    }

    public abstract void onBodyHit();
}
