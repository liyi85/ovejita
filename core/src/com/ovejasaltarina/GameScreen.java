package com.ovejasaltarina;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ovejasaltarina.entities.FloorEntity;
import com.ovejasaltarina.entities.PlayerEntity;
import com.ovejasaltarina.entities.SpikeEntity;
import com.ovejasaltarina.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends BaseScreen{

	private Stage stage;
	private World world;
	private PlayerEntity player;
    private List<FloorEntity> floorList = new ArrayList<FloorEntity>();
    private List<SpikeEntity> spikeList = new ArrayList<SpikeEntity>();

    private Sound jumpSound, dieSound;
    private Music backMusic;
    private Vector3 position;


    private Integer worldTimer;
    private boolean timeUp;
    private float timeCount;
    private Integer score;

    Label countDownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label wordlLabel;



	public GameScreen(MainGame game) {
        super(game);

        worldTimer = 300;
        timeCount = 0;
        score = 0;

        jumpSound = game.getManager().get("audio/jump.ogg");
        dieSound = game.getManager().get("audio/die.ogg");
        backMusic = game.getManager().get("audio/song.ogg");

        stage = new Stage(new FitViewport(640, 360));
        position = new Vector3(stage.getCamera().position);
        world = new World(new Vector2(0, -10), true);

        world.setContactListener(new GameContactListener());

    }

    @Override
    public void show() {

        Texture playerTexture = game.getManager().get("player.png");
        Texture floorTexture = game.getManager().get("floor.png");
        Texture overfloorTexture = game.getManager().get("overfloor.png");
        Texture spikeTexture = game.getManager().get("spike.png");

        player = new PlayerEntity(world, playerTexture, new Vector2(1.5f, 1.5f));


        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  0, 1000, 1));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  15, 10, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  40, 8, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  60, 8, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  80, 5, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  120, 8, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  160, 5, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  240, 8, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  250, 5, 3));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  265, 10, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  305, 8, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  330, 5, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  380, 8, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  390, 5, 3));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  405, 10, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture,  440, 5, 2));
        spikeList.add(new SpikeEntity(spikeTexture, world, 8, 1));
        spikeList.add(new SpikeEntity(spikeTexture, world, 23, 2));
        spikeList.add(new SpikeEntity(spikeTexture, world, 35, 1));
        spikeList.add(new SpikeEntity(spikeTexture, world, 50, 1));
        spikeList.add(new SpikeEntity(spikeTexture, world, 63, 2));
        spikeList.add(new SpikeEntity(spikeTexture, world, 75, 1));
        spikeList.add(new SpikeEntity(spikeTexture, world, 105, 1));
        spikeList.add(new SpikeEntity(spikeTexture, world, 150, 1));
        spikeList.add(new SpikeEntity(spikeTexture, world, 189, 1));
        spikeList.add(new SpikeEntity(spikeTexture, world, 225, 1));
        spikeList.add(new SpikeEntity(spikeTexture, world, 300, 1));
        spikeList.add(new SpikeEntity(spikeTexture, world, 315, 1));
        spikeList.add(new SpikeEntity(spikeTexture, world, 567, 2));
        spikeList.add(new SpikeEntity(spikeTexture, world, 675, 1));
        spikeList.add(new SpikeEntity(spikeTexture, world, 698, 1));


        for (FloorEntity floor : floorList)
            stage.addActor(floor);
        for (SpikeEntity spike : spikeList)
            stage.addActor(spike);

        stage.addActor(player);

        stage.getCamera().position.set(position);
        stage.getCamera().update();

        backMusic.setVolume(0.5f);
        backMusic.play();

    }

	@Override
	public void hide() {


        stage.clear();
		player.detach();


        for (FloorEntity floor : floorList){
            floor.detach();
            floor.remove();
        }
        for (SpikeEntity spike : spikeList){
            spike.detach();
            spike.remove();
        }

        spikeList.clear();
        floorList.clear();
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 0.2f, 0.098f, 1);
        Gdx.gl.glClearColor(0.275f, 0.510f, 0.706f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
        world.step(delta, 6, 2);

        if (player.getX() > 150 && player.isAlive()) {
            float speed = Constants.PLAYER_SPEED * delta * Constants.PIXELS_IN_METERS;
            stage.getCamera().translate(speed, 0, 0);
        }

		stage.draw();

	}

    public void update (float dt){
        timeCount += dt;
        if (timeCount >= 1){

        }
    }

	@Override
	public void dispose() {
		stage.dispose();
		world.dispose();
	}

    private class GameContactListener implements ContactListener {

        private boolean areCollided(Contact contact, Object userA, Object userB) {
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            if (userDataA == null || userDataB == null) {
                return false;
            }

            return (userDataA.equals(userA) && userDataB.equals(userB)) ||
                    (userDataA.equals(userB) && userDataB.equals(userA));
        }

        @Override
        public void beginContact(Contact contact) {
            // The player has collided with the floor.
            if (areCollided(contact, "player", "floor")) {
                player.setJumping(false);

                // If the screen is still touched, you have to jump again.
                if (Gdx.input.isTouched()) {
                    jumpSound.play();
                    player.setMustJump(true);
                }
            }

            // The player has collided with something that hurts.
            if (areCollided(contact, "player", "spike")) {

                // Check that is alive. Sometimes you bounce, you don't want to die more than once.
                if (player.isAlive()) {
                    player.setAlive(false);
                    backMusic.stop();
                    dieSound.play();

                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(1f),
                                    Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            game.setScreen(game.gameOverScreen);
                                        }
                                    })
                            )
                    );
                }
            }
        }

        @Override
        public void endContact(Contact contact) {
            // The player is jumping and it is not touching the floor.
            if (areCollided(contact, "player", "floor")) {
                if (player.isAlive()) {
                    jumpSound.play();
                }
            }
        }

        @Override public void preSolve(Contact contact, Manifold oldManifold) { }
        @Override public void postSolve(Contact contact, ContactImpulse impulse) { }
    }
}
