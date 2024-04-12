package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents the main gameplay screen of the game.
 * This screen is where the game is played and managed, including player actions and enemy interactions.
 * It handles rendering, user input, and enemy spawning.
 *
 * @author Edro Gonzales
 * @author Ian Chan
 * @version 2024
 */
public class GameScreen implements Screen {
    /** The max screen width. */
    public static final int SCREEN_X = 800;

    /** The max screen height. */
    public static final int SCREEN_Y = 600;
    /** Initial health points. */
    public static final int INITIAL_HEALTH_POINTS = 5;
    /** One second in nanoseconds. */
    public static final int ONE_SECOND = 1000000000;

    /** The game instance. */
    protected COMP2522TermProject game;

    /** Sound effect for player damage. */
    protected final Sound damageNoise;

    /** The cowboy player character. */
    protected Cowboy player = new Cowboy(this);

    /** Object to manage difficulty changes during gameplay. */
    protected ChangeDifficulty changeDifficulty = new ChangeDifficulty(this);

    /** Object representing bandit enemies. */
    protected final Bandit bandit;

    /** Another object representing bandit enemies. */
    protected Bandit bandit2;

    /** Nanoseconds in one second. */
    protected int oneSecond = ONE_SECOND;

    /** Player's health points. */
    protected int healthPoints = INITIAL_HEALTH_POINTS;

    /** Formatted Time. */
    protected String formattedTime;

    /** The camera for rendering. */
    private final OrthographicCamera camera;

    /** The background texture. */
    private final Texture background;

    /** Background music for the game. */
    private final Music battleBGM;

    /** Object representing laser enemies. */
    private final Laser laser;
    /**
     * Constructs a new GameScreen.
     *
     * @param game The game instance.
     */
    public GameScreen(final COMP2522TermProject game) {
        final float musicVolume = 0.5f;
        this.game = game;

        player.cowboyStillTexture();

        background = new TextureRegion(new Texture("background.jpg"), 0, 0, SCREEN_X, SCREEN_Y).getTexture();

        damageNoise = Gdx.audio.newSound(Gdx.files.internal("hurt-sound.wav"));
        battleBGM = Gdx.audio.newMusic(Gdx.files.internal("battle-bgm.mp3"));
        battleBGM.setVolume(musicVolume);
        battleBGM.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_X, SCREEN_Y);
        player.createCowboy();

        // Inside GameScreen or where you handle asset loading
        Texture banditTexture = new Texture(Gdx.files.internal("bandit.png"));
        Texture laserTexture = new Texture(Gdx.files.internal("laser.png"));

        laser = new Laser(this, laserTexture);
        bandit = new Bandit(this, banditTexture);
        bandit2 = new Bandit(this, banditTexture);

        laser.spawnEnemy();
    }

    /**
     * Plays the background music.
     */
    @Override
    public void show() {
        battleBGM.play();
    }

    /**
     * Draws the game elements.
     */
    private void draw() {
        final int formatTimeY = 580;
        ScreenUtils.clear(MainMenuScreen.CLEARRED, MainMenuScreen.CLEARGREEN, MainMenuScreen.CLEARBLUE,
                MainMenuScreen.CLEARALPHA);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, GameScreen.SCREEN_X, GameScreen.SCREEN_Y);
        game.font.setColor(0, 0, 0, 1);
        game.font.draw(game.batch, "Health Points: " + healthPoints, 0, GameScreen.SCREEN_Y);

        formattedTime = String.format("%.2f", changeDifficulty.timeSeconds);
        game.font.draw(game.batch, "Time: " + formattedTime, 0, formatTimeY);

        laser.drawEnemy();

        bandit.drawEnemy();
        bandit2.drawEnemy();

        player.cowboyMovement();

        game.batch.end();
    }

    /**
     * Updates enemy positions and game state.
     */
    private void updateEnemy() {
        final long lowerBound = 5000000000L;
        final long upperBound = 7000000000L;
        long randomNanoseconds = ThreadLocalRandom.current().nextLong(lowerBound, upperBound);
        final int initialSpeed = 150;
        final int thirtySeconds = 30;
        bandit.setSpeed(initialSpeed);

        changeDifficulty.timer();

        if (TimeUtils.nanoTime() - laser.lastSpawnTime > oneSecond) {
            laser.spawnEnemy();
        }

        if (TimeUtils.nanoTime() - bandit.lastSpawnTime > randomNanoseconds) {
            bandit.spawnEnemy();
        }

        long randomNanoseconds2 = ThreadLocalRandom.current().nextLong(lowerBound, upperBound);

        if (changeDifficulty.timeSeconds > thirtySeconds) {
            if (TimeUtils.nanoTime() - bandit2.lastSpawnTime > randomNanoseconds2) {
                bandit2.spawnEnemy();
            }
        }

        laser.removeEnemy();
        bandit.removeEnemy();
        bandit2.removeEnemy();
    }

    /**
     * Gets the player boundaries.
     *
     * @param delta The time between frames.
     */

    @Override
    public void render(final float delta) {
        draw();
        player.isWithinBounds();
        updateEnemy();
        if (healthPoints <= 0) {
            game.setScreen(new GameOverScreen(game, this));
            dispose();
        }
    }

    @Override
    public void resize(final int width, final int height) {
        // Unused method, no implementation needed.
    }

    @Override
    public void pause() {
        // Unused method, no implementation needed.
    }

    @Override
    public void resume() {
        // Unused method, no implementation needed.
    }

    @Override
    public void hide() {
        // Unused method, no implementation needed.
    }

    /**
     * Disposes of the game assets.
     */
    @Override
    public void dispose() {
        damageNoise.dispose();
        battleBGM.dispose();
        laser.dispose();
        bandit.dispose();
        bandit2.dispose();
    }
}
