package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Launches the game application.
 *
 * @author Edro Gonzales A01257468
 * @author Ian Chan A00910012
 * @version 2024
 */

public class DesktopLauncher {
	private static final int FPS = 60;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	/**
 	 * Drives the program.
	 * @param arg unused
	 */
	public static void main(final String[] arg) {

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Little Cowboy Edian");
		config.setWindowedMode(WIDTH, HEIGHT);
		config.useVsync(true);
		config.setForegroundFPS(FPS);
		new Lwjgl3Application(new COMP2522TermProject(), config);
	}
}
