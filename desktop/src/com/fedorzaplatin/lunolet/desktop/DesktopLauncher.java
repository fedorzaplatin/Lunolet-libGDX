package com.fedorzaplatin.lunolet.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fedorzaplatin.lunolet.MainClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Lunolet";
		config.width = 800;
		config.height = 600;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		//config.addIcon("icons/windows.png", Files.FileType.Classpath);
		new LwjglApplication(new MainClass(), config);
	}
}
