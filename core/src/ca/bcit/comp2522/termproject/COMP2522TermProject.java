package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;

/**
 * Holds all the logic of rendering the game.
 *
 * @author Edro Gonzales A01257468
 * @author Ian Chan A00910012
 * @version 2024
 */
public class COMP2522TermProject extends ApplicationAdapter {
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	private long lastDropTime;

	/**
	 * Creates the scene for game and initializes assets.
	 */
	@Override
	public void create() {
		// 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("drop.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		// audio files
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// loop the music, so it repeats
		rainMusic.setLooping(true);
		// play the music
		rainMusic.play();

		// create camera object
		camera = new OrthographicCamera();
		// set the camera to focus on our entire gui
		camera.setToOrtho(false, 800 , 600);

		// create new sprite
		batch = new SpriteBatch();

		// create hitbox
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		raindrops = new Array<Rectangle>();
		spawnRainDrop();
	}

	/**
	 * Renders objects onto orthographic camera screen.
	 */
	@Override
	// Render sprite/images
	public void render() {
		// dark blue colour (r, g, b, a)
		ScreenUtils.clear(0, 0, 0.2f, 1);

		// each movement, need to update screen
		camera.update();

		// Set the matrix, combine camera with height and width (800 x 600) of GUI
		batch.setProjectionMatrix(camera.combined);
		// Like FileIO, open the file, draw whatever is in .draw, then close file to update
		batch.begin();

		batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle raindrop: raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}

		batch.end();

		// input keys
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 400 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 400 * Gdx.graphics.getDeltaTime();
//		// TODO: Need to create logic for jump, goes up for some time, stop, then go down
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.y += 400 * Gdx.graphics.getDeltaTime();

		// border conditions
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;
		if(bucket.y > 600 - 64) bucket.y = 600 - 64;
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRainDrop();

		// [Raindrop1, Raindrop2]
		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) iter.remove();
			if (raindrop.overlaps(bucket)) {
				dropSound.play();
				iter.remove();
			}
		}
	}

	/*
	 * Spawns rain drops.
	 */
	private void spawnRainDrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);
		raindrop.y = 600;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	// todo: for pause and resume look into ApplicationAdapter.pause() and ApplicationAdapter.resume() methods
}
