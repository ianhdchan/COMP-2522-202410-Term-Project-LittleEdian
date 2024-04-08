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

    /** The game instance. */
    protected COMP2522TermProject game;

    /** The camera for rendering. */
    OrthographicCamera camera;

    /** The background texture. */
    Texture background;

    /** Sound effect for player damage. */
    Sound damageNoise;

    /** Background music for the game. */
    Music battleBGM;

    /** The cowboy player character. */
    Cowboy player = new Cowboy(this);

    /** Object to manage difficulty changes during gameplay. */
    ChangeDifficulty changeDifficulty = new ChangeDifficulty(this);

    /** Object representing laser enemies. */
    Laser laser;

    /** Object representing bandit enemies. */
    Bandit bandit;

    /** Another object representing bandit enemies. */
    Bandit bandit2;

    /** Nanoseconds in one second. */
    protected int oneSecond = 1000000000;

    /** Player's health points. */
    protected int healthPoints = 5;

    /**
     * Constructs a new GameScreen.
     *
     * @param game The game instance.
     */
    public GameScreen(final COMP2522TermProject game) {
        float MUSIC_VOLUME = 0.1F;
        this.game = game;

        player.cowboyStillTexture();

        background = new TextureRegion(new Texture("background.jpg"), 0, 0, 800, 600).getTexture();

        damageNoise = Gdx.audio.newSound(Gdx.files.internal("hurt-sound.wav"));
        battleBGM = Gdx.audio.newMusic(Gdx.files.internal("battle-bgm.mp3"));
        battleBGM.setVolume(MUSIC_VOLUME);
        battleBGM.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        player.createCowboy();

        // Inside GameScreen or where you handle asset loading
        Texture banditTexture = new Texture(Gdx.files.internal("ufo_sprite.png"));
        Texture laserTexture = new Texture(Gdx.files.internal("laser.png"));

        laser = new Laser(this, laserTexture);
        bandit = new Bandit(this, banditTexture);
        bandit2 = new Bandit(this, banditTexture);

        laser.spawnEnemy();
    }

    @Override
    public void show() {
        battleBGM.play();
    }

    /**
     * Draws the game elements.
     */
    private void draw() {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, 800, 600);
        game.font.setColor(0,0,0,1);
        game.font.draw(game.batch, "Health Points: " + healthPoints, 0, 600);

        String formattedTime = String.format("%.2f", changeDifficulty.timeSeconds);
        game.font.draw(game.batch, "Time: " + formattedTime, 0, 580);

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
        long lowerBound = 5000000000L;
        long upperBound = 7000000000L;
        long randomNanoseconds = ThreadLocalRandom.current().nextLong(lowerBound, upperBound);

        changeDifficulty.timer();

        if (TimeUtils.nanoTime() - laser.lastSpawnTime > oneSecond) {
            laser.spawnEnemy();
        }

        if (TimeUtils.nanoTime() - bandit.lastSpawnTime > randomNanoseconds) {
            bandit.spawnEnemy();
        }

        long randomNanoseconds2 = ThreadLocalRandom.current().nextLong(lowerBound, upperBound);

        if (changeDifficulty.timeSeconds > 30) {
            if (TimeUtils.nanoTime() - bandit2.lastSpawnTime > randomNanoseconds2) {
                bandit2.spawnEnemy();
            }
        }

        laser.removeEnemy();
        bandit.removeEnemy();
        bandit2.removeEnemy();
    }

    /**
     * Gets the game state.
     *
     * @return The game state.
     */
    COMP2522TermProject getGame() {
        return game;
    }

    @Override
    public void render(float delta) {
        draw();
        player.isWithinBounds();
        updateEnemy();
        if (healthPoints <= 0) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
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

    @Override
    public void dispose() {
        damageNoise.dispose();
        battleBGM.dispose();
        laser.dispose();
        bandit.dispose();
        bandit2.dispose();
    }
}
