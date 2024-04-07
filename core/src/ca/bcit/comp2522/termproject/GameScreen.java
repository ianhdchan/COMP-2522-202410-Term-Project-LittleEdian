package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    protected final COMP2522TermProject game;
    protected int oneSecond = 1000000000;
    protected int healthPoints = 0;
    private long timeDifference = 3000000000L;
    OrthographicCamera camera;
    Texture background;
    Sound damageNoise;
    Music battleBGM;
    Laser laser = new Laser(this);
    Bandit bandit = new Bandit(this);
    Bandit bandit2 = new Bandit(this);

    Cowboy player = new Cowboy(this);
    ChangeDifficulty changeDifficulty = new ChangeDifficulty(this);


    public GameScreen(final COMP2522TermProject game) {
        float MUSIC_VOLUME = 0.1F;
        this.game = game;

        // load the images for cowboy, 64 x 64 pixels each
        player.cowboyStillTexture();

        background = new TextureRegion(new Texture("background.jpg"), 0, 0, 800, 600).getTexture();

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
        bandit2.spawnEnemy();

    }


    @Override
    public void show() {
        // start playback of background music when screen is shown
        battleBGM.play();
    }


    private void draw() {
        // Clear the screen with dark blue colour. Arguments to clear are red, green, blue, and alpha
        // component in the range [0, 1] of the colour to be used to clear the screen
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // get camera to update its matrices
        camera.update();

        // tell the SpriteBatch to render in the coordinate system specified by the camera
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch of objects, draw the bucket and all drops
        game.batch.begin();
        game.batch.draw(background, 0, 0, 800, 600);
        game.font.draw(game.batch, "Health Points: " + healthPoints, 0, 600);

        // Draw Lasers
        laser.drawEnemy();

        // Draw bandit (left to right)
        bandit.drawEnemy();
        // Draw bandit (right to left)
        bandit2.drawEnemy();


        // Process user input
        player.cowboyMovement();

        game.batch.end();
    }

    private void updateEnemy() {
        long lowerBound = 5000000000L;
        long upperBound = 10000000000L;
        long randomNanoseconds = timeDifference + MathUtils.random(upperBound - lowerBound);
        // Check if game needs to create new object (Raindrop)

        changeDifficulty.timer();

        if (TimeUtils.nanoTime() - laser.lastSpawnTime > oneSecond) {
            laser.spawnEnemy();
        }

        if (TimeUtils.nanoTime() - bandit.lastSpawnTime > randomNanoseconds) {
            bandit.spawnEnemy();
        }

        if (TimeUtils.nanoTime() - bandit2.lastSpawnTime > randomNanoseconds) {
            bandit2.spawnEnemy();
        }

        // Remove laser, any that are beneath bottom edge of screen or that hit the cowboy.
        laser.removeEnemy();
        // Remove bandit, any that hit the cowboy or goes off the screen
        bandit.removeEnemy();
        bandit2.removeEnemy();

    }

    @Override
    public void render(float delta) {

        draw();

        player.isWithinBounds();

        updateEnemy();
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

    // todo[EDRO]: dispose all instantiated objects for GameOverScreen
    @Override
    public void dispose() {
        damageNoise.dispose();
        battleBGM.dispose();
    }
}
