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
    /** The red component of the clear color, in the range 0-1. */
    public static final float CLEARRED = 0;

    /** The green component of the clear color, in the range 0-1. */
    public static final float CLEARGREEN = 0;

    /** The blue component of the clear color, in the range 0-1. */
    public static final float CLEARBLUE = 0.2f;

    /** The alpha (transparency) component of the clear color, in the range 0-1. */
    public static final float CLEARALPHA = 1;
    /** The game instance. */
    protected final COMP2522TermProject game;

    /** The texture for the main menu background. */
    private final Texture mainMenu;

    /** The camera for rendering. */
    private final OrthographicCamera camera;

    /**
     * Constructs a new MainMenuScreen.
     *
     * @param game The game instance.
     */
    public MainMenuScreen(final COMP2522TermProject game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameScreen.SCREEN_X, GameScreen.SCREEN_Y);
        mainMenu = new TextureRegion(new Texture("mainmenu.jpg"), 0, 0, GameScreen.SCREEN_X,
                GameScreen.SCREEN_Y).getTexture();
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
    public void render(final float delta) {


        ScreenUtils.clear(CLEARRED, CLEARGREEN, CLEARBLUE, CLEARALPHA);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        final int welcomeMessageX = 100;
        final int welcomeMessageY = GameScreen.SCREEN_Y - 150;
        final int instructionMessageY = GameScreen.SCREEN_Y - 200;

        game.batch.begin();
        game.batch.draw(mainMenu, 0, 0, GameScreen.SCREEN_X, GameScreen.SCREEN_Y);
        game.font.draw(game.batch, "Welcome to Little Cowboy Edian baby!", welcomeMessageX, welcomeMessageY);
        game.font.draw(game.batch, "Press the Space Bar to begin.", welcomeMessageX, instructionMessageY);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
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

    @Override
    public void dispose() {
        // Unused method, no implementation needed.
    }
}
