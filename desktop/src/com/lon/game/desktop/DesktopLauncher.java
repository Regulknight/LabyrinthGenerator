package com.lon.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.lon.game.LGenGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setTitle("lgen");
		config.useVsync(true);
		config.setIdleFPS(60);

		new Lwjgl3Application(new LGenGame(), config);
	}
}
