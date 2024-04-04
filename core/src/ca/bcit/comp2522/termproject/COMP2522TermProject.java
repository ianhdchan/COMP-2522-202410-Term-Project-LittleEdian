/**
 * Initializes Game Instance.
 *
 * @author Edro Gonzales
 * @author Ian Chan
 * @version 2024
 *
 */

package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class COMP2522TermProject extends Game {
	/** Sprites to be used in game. */
	public SpriteBatch batch;

	/** Fonts to be used in game. */
	public BitmapFont font;

	/**
	 * Creates a new instance of COMP2522TermProject
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // This is default Arial font
		this.setScreen(new MainMenuScreen(this));
	}

	/**
	 * Uses super class method render from Game.
	 */
	public void render() {
		super.render();
	}

	/**
	 * Gets rid of batch and font to clear out memory.
	 */
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}