package com.ovejasaltarina;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by andrearodriguez on 1/12/17.
 */
public class MainGame extends Game{

    private AssetManager manager;

//    public GameScreen gameScreen;
//    public GameOverScreen gameOverScreen;
//    public MenuScreen menuScreen;
//    public CreditsScreen creditsScreen;

    public BaseScreen loadingScreen, menuScreen, gameScreen, gameOverScreen, creditsScreen;
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("player.png", Texture.class);
        manager.load("floor.png", Texture.class);
        manager.load("spike.png", Texture.class);
        manager.load("overfloor.png", Texture.class);
        manager.load("audio/song.ogg", Music.class);
        manager.load("audio/jump.ogg", Sound.class);
        manager.load("audio/die.ogg", Sound.class);
        manager.load("gameover.png", Texture.class);
        manager.load("logo.png", Texture.class);

        loadingScreen = new LoadingScreen(this);

//        manager.finishLoading();

        setScreen(loadingScreen);
    }

    public void finishLoading() {
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        gameOverScreen = new GameOverScreen(this);
        creditsScreen = new CreditsScreen(this);
        setScreen(menuScreen);
    }

    public AssetManager getManager() {
        return manager;
    }

    @Override
    public void dispose() {
        manager.dispose();
        batch.dispose();
    }

    @Override
    public void render() {
        super.render();
    }
}
