package com.ovejasaltarina;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by andrearodriguez on 1/20/17.
 */

public class CreditsScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private Label credits;
    private TextButton back;


    public CreditsScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        back = new TextButton("Back", skin);

        credits = new Label("Sheep save yourself v1.0.0\n" +
                "Copyright (C) 2017 Andrea Rodriguez\n" +
                "This game for refound money for street animal.\n\n" +

                "Music: \"LOS MINIONS - ELECTRÓNICA [ ORIGINAL 2015 ]\n" +
                "(https://www.youtube.com/watch?v=GDWo7F8Q9YI)\n" +
                "Licensed under Creative Commons: By Attribution 1.0", skin);

        back.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });

        credits.setPosition(20, 340 - credits.getHeight());
        back.setSize(180, 60);
        back.setPosition(40, 50);

        stage.addActor(back);
        stage.addActor(credits);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
