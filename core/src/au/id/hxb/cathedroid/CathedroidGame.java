package au.id.hxb.cathedroid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import au.id.hxb.cathedroid.screens.MenuScreen;


public class CathedroidGame extends Game {

	@Override
	public void create () {
		Gdx.app.log("CathedroidGame", "Created");
		setScreen(new MenuScreen());
	}

}


