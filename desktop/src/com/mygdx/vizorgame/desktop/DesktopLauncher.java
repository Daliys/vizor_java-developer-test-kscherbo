package com.mygdx.vizorgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.vizorgame.GameScreen;
import com.mygdx.vizorgame.VizorGameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = GameScreen.WIDTH;
		config.height = GameScreen.HEIGHT;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		config.resizable = false;
		new LwjglApplication(new VizorGameMain(), config);

	}
}
