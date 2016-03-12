package au.id.hxb.cathedroid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import au.id.hxb.cathedroid.screens.GameScreen;
import au.id.hxb.cathedroid.screens.MenuScreen;


public class CathedroidGame extends Game {

	MenuScreen menuScreen;
	GameScreen gameScreen;

	@Override
	public void create () {
		Gdx.app.log("CathedroidGame", "Created");
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
		gameScreen = new GameScreen(this);

	}

	public void	setPlayScreen (boolean newGame, boolean vsAI){
		setScreen(gameScreen);
	}

	public void setMenuScreen() {
		setScreen(menuScreen);
	}

}


