package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;

public class GameScreen implements Screen {
    final COMP2522TermProject game;
    Texture[] runningLeft = new Texture[3];
    Texture[] runningRight = new Texture[3];
    Sprite[] runningLeftSprite = new Sprite[3];
    Sprite[] runningRightSprite = new Sprite[3];


    Texture cowboyStillL;
    Texture cowboyImage;
    Texture cowboyStillR;
    Texture cowboyL1;
    Texture cowboyL2;
    Texture cowboyL3;
    Texture cowboyR1;
    Texture cowboyR2;
    Texture cowboyR3;
    Texture laserImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle cowboy;
    Array<Rectangle> enemies;
    double lastSpawnTime;
    int dropsGathered;
    long lowerbound = 5000000000L;
    long upperbound = 10000000000L;
    long timeDifference = 3000000000L;

    private float lifeTime;
    private float delay = 10000L; // 1000 milli per sec

    public void create() {
        lifeTime = System.currentTimeMillis();
    }

    /** Tracks current frame of sprites **/
    private int currentFrame = 0;
    /** Checks if sprite is facing left **/
    boolean isFacingLeft = false;


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
    Laser laser = new Laser(this);
    Bandit bandit = new Bandit(this);


    public GameScreen(final COMP2522TermProject game) {
        this.game = game;

        // load the images for droplet and bucket, 64 x 64 pixels each
        laserImage = new Texture(Gdx.files.internal("laser.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        // load cowboy sprite images for running left, 64 x 64 pixels each
        runningLeft[0] = new Texture(Gdx.files.internal("Cowboy_L1.png"));
        runningLeft[1] = new Texture(Gdx.files.internal("Cowboy_L2.png"));
        runningLeft[2] = new Texture(Gdx.files.internal("Cowboy_L3.png"));
        for (int i = 0; i < 3; i++) {
            runningLeftSprite[i] = new Sprite(runningLeft[i]);
        }
        // load cowboy sprite images for running right, 64 x 64 pixels each
        runningRight[0] = new Texture(Gdx.files.internal("Cowboy_R1.png"));
        runningRight[1] = new Texture(Gdx.files.internal("Cowboy_R2.png"));
        runningRight[2] = new Texture(Gdx.files.internal("Cowboy_R3.png"));
        for (int i = 0; i < 3; i++) {
            runningRightSprite[i] = new Sprite(runningRight[i]);
        }

        // load cowboy sprite images for no movement, 64 x 64 pixels each
        cowboyStillL = new Texture(Gdx.files.internal("Cowboy_StillL.png"));
        cowboyStillR = new Texture(Gdx.files.internal("Cowboy_StillR.png"));


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

        // create object array (laser as an example) and spawn the first object
        laser.spawnEnemy();

        // create object array (bandit) and spawn the first object
        bandit.spawnEnemy();

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

        // Draw Lasers
        laser.drawEnemy();

        // Draw bandit (left to right)
        bandit.drawEnemy();


        lifeTime += Gdx.graphics.getDeltaTime();
        if (lifeTime == delay) {
            timeDifference = 1000000000L;
        }
        System.out.println(lifeTime);



        // Process user input

        // LEFT KEY PRESSED
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            updateSprite(runningLeftSprite);
            cowboy.x -= 200 * Gdx.graphics.getDeltaTime();
        // Right key pressed
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            updateSprite(runningRightSprite);
            cowboy.x += 200 * Gdx.graphics.getDeltaTime();
        // Checks if facing left direction
        } else {
            Texture stillSprite = isFacingLeft ? cowboyStillL : cowboyStillR;
            game.batch.draw(stillSprite, cowboy.x, cowboy.y);
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
        game.batch.end();


        if (cowboy.y < 0) {
            cowboy.y  = 0;
        }

        // Ensure cowboy stays within screen bounds
        if (cowboy.x < 0) {
            cowboy.x = 0;
        }
        if (cowboy.x > 800 - 64) {
            cowboy.x = 800 - 64;
        }

        // Check if game needs to create new object (Raindrop)
        if (TimeUtils.nanoTime() - laser.lastSpawnTime > 1000000000) {
            laser.spawnEnemy();
        }

        long randomNanoseconds = timeDifference + MathUtils.random(upperbound - lowerbound);

        if (TimeUtils.nanoTime() - bandit.lastSpawnTime > randomNanoseconds) {
            bandit.spawnEnemy();
            System.out.println("Random nanoseconds: " + randomNanoseconds);

        }

        // Remove laser, any that are beneath bottom edge of screen or that hit the cowboy.
        laser.removeEnemy();
        // Remove bandit, any that hit the cowboy or goes off the screen
        bandit.removeEnemy();


    }

    private void updateSprite(Sprite[] runningSprite) {
        currentFrame = (currentFrame + 1) % runningSprite.length;
        runningSprite[currentFrame].setPosition(cowboy.x, cowboy.y);
        runningSprite[currentFrame].draw(game.batch);
        isFacingLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT); // update facing direction
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
        laserImage.dispose();
        bucketImage.dispose();
        cowboyImage.dispose();
        cowboyL1.dispose();
        cowboyL2.dispose();
        cowboyL3.dispose();

        cowboyR1.dispose();
        cowboyR2.dispose();
        cowboyR3.dispose();
        cowboyStillL.dispose();
        cowboyStillR.dispose();

        dropSound.dispose();
        rainMusic.dispose();

    }
}
