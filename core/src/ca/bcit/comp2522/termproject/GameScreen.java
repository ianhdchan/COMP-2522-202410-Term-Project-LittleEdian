package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    protected final COMP2522TermProject game;
    protected final Texture[] runningLeft = new Texture[3];
    protected final Texture[] runningRight = new Texture[3];
    protected final Sprite[] runningLeftSprite = new Sprite[3];
    protected final Sprite[] runningRightSprite = new Sprite[3];
    protected final Texture cowboyStillL;
    protected final Texture cowboyStillR;
    protected final Texture laserImage;
    protected final Texture bucketImage;
    protected int dropsGathered;
    private long timeDifference = 3000000000L;
    private float lifeTime;
    OrthographicCamera camera;
    Texture cowboyL1;
    Texture cowboyL2;
    Texture cowboyL3;
    Texture cowboyR1;
    Texture cowboyR2;
    Texture cowboyR3;
    Sound damageNoise;
    Music battleBGM;
    Laser laser = new Laser(this);
    Bandit bandit = new Bandit(this);
    Cowboy player = new Cowboy(this);
    private int currentFrame = 0;
    boolean isFacingLeft = false;
    private boolean isJumping = false;
    private final boolean isFalling = false;
    private float jumpVelocity = 250;
    private float gravity = 30;
    private final float MUSIC_VOLUME = 0.1F;
    private final float GENERAL_VOLUME = 0.5F;
    private float MAX_JUMP_HEIGHT = 200;


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
        damageNoise = Gdx.audio.newSound(Gdx.files.internal("hurt-sound.wav"));
        battleBGM = Gdx.audio.newMusic(Gdx.files.internal("battle-bgm.mp3"));
        battleBGM.setVolume(MUSIC_VOLUME);
        battleBGM.setLooping(true);

        // create camera and Sprites
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        // Create object (rectangle) to represent the cowboy
        player.createCowboy();

        // create object array (laser as an example) and spawn the first object
        laser.spawnEnemy();

        // create object array (bandit) and spawn the first object
        bandit.spawnEnemy();

    }

    public void create() {
        lifeTime = System.currentTimeMillis();
    }

    @Override
    public void show() {
        // start playback of background music when screen is shown
        battleBGM.play();
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
        float delay = 10000L;
        if (lifeTime == delay) {
            timeDifference = 1000000000L;
        }

        // Process user input
        player.cowboyMovement();

        game.batch.end();

        // checks if player is within screen boundaries
       player.isWithinBounds();

        // Check if game needs to create new object (Raindrop)
        if (TimeUtils.nanoTime() - laser.lastSpawnTime > 1000000000) {
            laser.spawnEnemy();
        }

        long lowerBound = 5000000000L;
        long upperbound = 10000000000L;
        long randomNanoseconds = timeDifference + MathUtils.random(upperbound - lowerBound);

        if (TimeUtils.nanoTime() - bandit.lastSpawnTime > randomNanoseconds) {
            bandit.spawnEnemy();
        }

        // Remove laser, any that are beneath bottom edge of screen or that hit the cowboy.
        laser.removeEnemy();
        // Remove bandit, any that hit the cowboy or goes off the screen
        bandit.removeEnemy();


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
        cowboyL1.dispose();
        cowboyL2.dispose();
        cowboyL3.dispose();

        cowboyR1.dispose();
        cowboyR2.dispose();
        cowboyR3.dispose();
        cowboyStillL.dispose();
        cowboyStillR.dispose();

        damageNoise.dispose();
        battleBGM.dispose();

    }
}
