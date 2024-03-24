package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ca.bcit.comp2522.termproject.COMP2522TermProject;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Little Cowboy Edian");
		config.setWindowedMode(800, 600);
		config.useVsync(true);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new COMP2522TermProject(), config);
	}
}
