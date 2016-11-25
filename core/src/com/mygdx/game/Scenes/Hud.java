package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private Integer health;
    private Integer ammo;
    private Integer lives;
    private Integer score;

    Label healthLabelLabel;
    Label ammoLabelLabel;
    Label livesLabelLabel;
    Label scoreLabelLabel;

    Label healthLabel;
    Label ammoLabel;
    Label livesLabel;
    Label scoreLabel;

    public Hud(SpriteBatch sb) {
        health = 300;
        ammo = 10;
        lives = 3;
        score = 0;

        viewport = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        healthLabelLabel = new Label("HEALTH", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        ammoLabelLabel = new Label("AMMO", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        livesLabelLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabelLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        healthLabel = new Label(String.format("%03d", health), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        ammoLabel = new Label(String.format("%02d", ammo), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        livesLabel = new Label(String.format("%01d", lives), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        table.add(healthLabelLabel).expandX().padTop(10);
        table.add(ammoLabelLabel).expandX().padTop(10);
        table.add(livesLabelLabel).expandX().padTop(10);
        table.add(scoreLabelLabel).expandX().padTop(10);

        table.row();

        table.add(healthLabel).expandX().padTop(10);
        table.add(ammoLabel).expandX().padTop(10);
        table.add(livesLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }
}
