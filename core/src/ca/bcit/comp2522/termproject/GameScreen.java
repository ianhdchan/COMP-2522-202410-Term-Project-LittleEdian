package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    final COMP2522TermProject game;
    Texture cowboyStillL;
    Texture cowboyStillR;
    Texture cowboyL1;
    Texture cowboyL2;
    Texture cowboyL3;
    Texture cowboyR1;
    Texture cowboyR2;
    Texture cowboyR3;
    Texture cowboyJumpR;
    Texture cowboyJumpL;
    Texture ufo;

    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle cowboy;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;

    /** Determines isJumping state of character. */
    private boolean isJumping = false;

    /** Determines isJumping state of character. */
    private boolean isFalling = false;

    /** How fast the jump action will be. */
    private float jumpVelocity = 250;

    /** How fast the falling portion of the jump will be. */
    private float gravity = 30;

    /** Max Jump Height. */
    private float MAX_JUMP_HEIGHT = 200;

    public GameScreen(final COMP2522TermProject game) {
        this.game = game;

        // load the images for droplet and bucket, 64 x 64 pixels each
        dropImage = new Texture(Gdx.files.internal("drop.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        // load cowboy sprite images, 64 x 64 pixels each
        cowboyStillL = new Texture(Gdx.files.internal("Cowboy_StillL.png"));
        cowboyStillR = new Texture(Gdx.files.internal("Cowboy_StillR.png"));
        cowboyJumpL = new Texture(Gdx.files.internal("Cowboy_LJump.png"));
        cowboyJumpR = new Texture(Gdx.files.internal("Cowboy_RJump.png"));
        cowboyL1 = new Texture(Gdx.files.internal("Cowboy_L1.png"));
        cowboyL2 = new Texture(Gdx.files.internal("Cowboy_L2.png"));
        cowboyL3 = new Texture(Gdx.files.internal("Cowboy_L3.png"));
        cowboyR1 = new Texture(Gdx.files.internal("Cowboy_R1.png"));
        cowboyR2 = new Texture(Gdx.files.internal("Cowboy_R2.png"));
        cowboyR3 = new Texture(Gdx.files.internal("Cowboy_R3.png"));
        ufo = new Texture(Gdx.files.internal("ufo_sprite.png"));


        // load the drop sound effect and include rain background music
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        // create camera and Sprites
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        // Create hitbox (rectangle) to represent the cowboy
        cowboy = new Rectangle();
        cowboy.x = 800 / 2 - 64 / 2; // This centers the bucket horizontally (x-axis)
        cowboy.y = 20; // bottom

        cowboy.width = 64; // 64 pixels
        cowboy.height = 64; // 64 pixels

        // create object array (raindrop as an example) and spawn the first object
        raindrops = new Array<Rectangle>();
        spawnRainDrop();
    }

    private void spawnRainDrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 600;
        raindrop.width = 64; // 64 pixels wide
        raindrop.height = 64; // 64 pixels height
        raindrops.add(raindrop); // add the raindrops into the array
        lastDropTime = TimeUtils.nanoTime(); // Calculates the time from when the last object dropped
    }

    @Override
    public void show() {
        // start playback of background music when screen is shown
        rainMusic.play();

    }

    @Override
    public void render(float delta) {
        // Clear the screen with dark blue colour. Arguments to clear are red, green, blue, and alpha
        // component in the range [0, 1] of the colour to be used to clear the screen
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // get camera to update its matrices
        camera.update();

        // tell the SpriteBatch to render in the coordinate system specified by the camera
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch of objects, draw the bucket and all drops
        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 600);
        game.batch.draw(cowboyStillL, cowboy.x, cowboy.y, cowboy.width, cowboy.height);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();


        // process user's input
        // LEFT KEY PRESSED
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cowboy.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        // RIGHT KEY PRESSED
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cowboy.x += 200 * Gdx.graphics.getDeltaTime();
        }

        // todo: ugly smelly code please fix
        //  y
        // JUMP LOGIC
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && !isJumping && !isFalling) {
            isJumping = true;
        }

        if (isJumping) {
            cowboy.y += jumpVelocity * Gdx.graphics.getDeltaTime();
            if (cowboy.y >= MAX_JUMP_HEIGHT - 1) {
                isJumping = false;
                isFalling = true;
            }
        } else if (isFalling) {
            cowboy.y -= jumpVelocity * Gdx.graphics.getDeltaTime();
            if (cowboy.y <= 0) {
                isJumping = false;
                isFalling = false;
            }
        }

        if (cowboy.y < 0) {
            cowboy.y  = 0;
        }

        // Ensure bucket stays within screen bounds
        if (cowboy.x < 0) {
            cowboy.x = 0;
        }
        if (cowboy.x > 800 - 64) {
            cowboy.x = 800 - 64;
        }

        // Check if game needs to create new object (Raindrop)
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRainDrop();
        }

        // Move raindrops, remove any that are beneath bottom edge of screen or that hit the bucket.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) {
                iter.remove();
            }
            if (raindrop.overlaps(cowboy)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

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
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();

    }
}
