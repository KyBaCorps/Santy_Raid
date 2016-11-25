package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Santa;
import com.mygdx.game.Utils.Constants;
import com.mygdx.game.Utils.TiledObjectUtil;

public class PlayScreen implements Screen {
    private MyGdxGame game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Santa player;

    public PlayScreen(MyGdxGame game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new StretchViewport(MyGdxGame.V_WIDTH/Constants.PPM, MyGdxGame.V_HEIGHT/Constants.PPM, gamecam);
        hud = new Hud(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("santy_raid_tile_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/Constants.PPM);
        world = new World(new Vector2(0, -9.8f), true);
        player = new Santa(world);
        gamecam.position.set(player.b2body.getPosition().x, player.b2body.getPosition().y, 0);

        //world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        //Brent's version
        //BodyDef bdef = new BodyDef();
        //PolygonShape shape = new PolygonShape();
        //FixtureDef fdef = new FixtureDef();
        //Body body;
        //for(MapObject object : map.getLayers().get(2).getObjects().getByType()) {
        //}
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision_layer").getObjects());

    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            player.b2body.applyLinearImpulse(new Vector2(0, 4), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //renderer.render();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
