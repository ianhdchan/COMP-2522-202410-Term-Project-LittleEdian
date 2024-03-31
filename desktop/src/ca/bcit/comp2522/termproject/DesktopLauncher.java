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

	/**
	 * Drives the program
	 * @param arg unused
	 */
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Little Cowboy Edian");
		config.setWindowedMode(800, 600);
		config.useVsync(true);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new COMP2522TermProject(), config);

	}
}
