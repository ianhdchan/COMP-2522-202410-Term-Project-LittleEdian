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

    /** The texture for the game over screen. */
    private final Texture gameOver;

    /** The camera for rendering. */
    OrthographicCamera camera;

    /**
     * Constructs a new GameOverScreen.
     *
     * @param game The game instance.
     */
    public GameOverScreen(COMP2522TermProject game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        gameOver = new TextureRegion(new Texture("gameover.jpg"), 0, 0, 800, 600).getTexture();
    }

    /**
     * Draws the game over screen elements.
     */
    public void draw() {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.setColor(0,0,0,1);
        game.batch.draw(gameOver, 0, 0, 800, 600);
        game.font.draw(game.batch, "GAME OVER!", 360, 500);
        game.font.draw(game.batch, "You ran out of health Points!", 90, 125);
        game.font.draw(game.batch, "Press the Space Bar to retry again.", 90, 90);
        game.font.draw(game.batch, "Press the Escape button to close the game.", 90, 55);
        game.font.setColor(255,255,255,1);
        game.batch.end();
    }

    @Override
    public void show() {
        // Unused method, no implementation needed.
    }

    @Override
    public void render(float delta) {
        draw();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
        } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
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
        // Unused method, no implementation needed.
    }
}
