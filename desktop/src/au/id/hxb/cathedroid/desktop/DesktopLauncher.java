package au.id.hxb.cathedroid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import au.id.hxb.cathedroid.CathedroidGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Cathedroid";
		//config.width = 800; //htc desire HD
		//config.height = 480; //htc desire HD
		//config.width = 1920; //Samsung Galaxy s5
		//config.height = 1080; //Samsung Galaxy s5
		config.width = 1920; //Samsung Galaxy s3
		config.height = 1080; //Samsung Galaxy s3

		new LwjglApplication(new CathedroidGame(), config);
	}
}
