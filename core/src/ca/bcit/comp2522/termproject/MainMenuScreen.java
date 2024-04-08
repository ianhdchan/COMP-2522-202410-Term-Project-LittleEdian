package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Represents the main menu screen of the game.
 * This screen is displayed when the game is launched and provides options for starting the game.
 * It handles user input to transition to the game screen.
 *
 * @author Edro
 * @author Ian
 * @version 2024
 */
public class MainMenuScreen implements Screen {

    /** The game instance. */
    final COMP2522TermProject game;

    /** The texture for the main menu background. */
    private final Texture mainMenu;

    /** The camera for rendering. */
    OrthographicCamera camera;

    /**
     * Constructs a new MainMenuScreen.
     *
     * @param game The game instance.
     */
    public MainMenuScreen(COMP2522TermProject game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        mainMenu = new TextureRegion(new Texture("mainmenu.jpg"), 0, 0, 800, 600).getTexture();
    }

    @Override
    public void show() {
        // Unused method, no implementation needed.
    }

    /**
     * Renders the main menu screen.
     *
     * @param delta The time since the last frame.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(mainMenu, 0, 0, 800, 600);
        game.font.draw(game.batch, "Welcome to Little Cowboy Edian baby!", 100, 150);
        game.font.draw(game.batch, "Press the Space Bar to begin.", 100, 100);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
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
        // Unused method, no implementation needed.
    }
}
