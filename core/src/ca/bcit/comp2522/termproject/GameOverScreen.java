package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Represents the screen displayed when the game is over.
 * This screen is shown when the player runs out of health points.
 * It allows the player to retry the game by pressing the space bar.
 * Handles rendering and user input for the game over screen.
 *
 * @author Edro Gonzales
 * @author Ian Chan
 * @version 2024
 */
public class GameOverScreen implements Screen {

    /** The game instance. */
    final COMP2522TermProject game;
    /** The game screen instance. */
    final GameScreen gameScreen;

    /** The texture for the game over screen. */
    private final Texture gameOver;

    /** The camera for rendering. */
    private final OrthographicCamera camera;

    /**
     * Constructs a new GameOverScreen.
     *
     * @param game The game instance.
     * @param gameScreen The game screen instance.
     */
    public GameOverScreen(final COMP2522TermProject game, final GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameScreen.SCREEN_X, GameScreen.SCREEN_Y);
        gameOver = new TextureRegion(new Texture("gameover.jpg"), 0, 0, GameScreen.SCREEN_X,
                GameScreen.SCREEN_Y).getTexture();
    }

    /**
     * Draws the game over screen elements.
     */
    public void draw() {
// Constants for screen positions
        final int gameOverX = 360;
        final int gameOverY = 500;
        final int scoreX = 335;
        final int scoreY = 480;
        final int healthPointsX = 90;
        final int healthPointsY = 125;
        final int retryX = 90;
        final int retryY = 90;
        final int closeGameX = 90;
        final int closeGameY = 55;
        final int redAmount = 255;
        final int greenAmount = 255;
        final int blueAmount = 255;
        final int alphaAmount = 1;

        ScreenUtils.clear(MainMenuScreen.CLEARRED, MainMenuScreen.CLEARGREEN, MainMenuScreen.CLEARBLUE,
                MainMenuScreen.CLEARALPHA);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.setColor(0, 0, 0, 1);
        game.batch.draw(gameOver, 0, 0, GameScreen.SCREEN_X, GameScreen.SCREEN_Y);
        game.font.draw(game.batch, "GAME OVER!", gameOverX, gameOverY);
        game.font.draw(game.batch, "Score (seconds): " + gameScreen.changeDifficulty.getTime(), scoreX, scoreY);
        game.font.draw(game.batch, "You ran out of health Points!", healthPointsX, healthPointsY);
        game.font.draw(game.batch, "Press the Space Bar to retry again.", retryX, retryY);
        game.font.draw(game.batch, "Press the Escape button to close the game.", closeGameX, closeGameY);
        game.font.setColor(redAmount, greenAmount, blueAmount, alphaAmount);
        game.batch.end();
    }

    @Override
    public void show() {
        // Unused method, no implementation needed.
    }

    /**
     * Renders the game over screen.
     *
     * @param delta The time since the last frame.
     */
    @Override
    public void render(final float delta) {
        draw();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
        } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
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

    @Override
    public void dispose() {
        // Unused method, no implementation needed.
    }
}
