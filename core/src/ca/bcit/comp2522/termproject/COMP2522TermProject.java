package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Initializes the Game Instance for the COMP2522 Term Project.
 * This class extends the LibGDX Game class and serves as the entry point for the game.
 * It initializes necessary resources such as sprites and fonts, and sets the initial screen
 * to the main menu screen.
 *
 * @author Edro Gonzales
 * @author Ian Chan
 * @version 2024
 */
public class COMP2522TermProject extends Game {
	/** The SpriteBatch to be used for rendering sprites. */
	protected SpriteBatch batch;

	/** The BitmapFont to be used for rendering text. */
	protected BitmapFont font;

	/**
	 * Creates a new instance of COMP2522TermProject.
	 * Initializes the SpriteBatch and BitmapFont.
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // This is the default Arial font
		this.setScreen(new MainMenuScreen(this));
	}

	/**
	 * Renders the game.
	 * Invokes the render method of the super class (Game).
	 */
	@Override
	public void render() {
		super.render();
	}

	/**
	 * Disposes of resources to free up memory.
	 * Disposes of the SpriteBatch and BitmapFont.
	 */
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
