package au.id.hxb.cathedroid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import au.id.hxb.cathedroid.screens.GameScreen;
import au.id.hxb.cathedroid.screens.InfoScreen;
import au.id.hxb.cathedroid.screens.MenuScreen;


public class CathedroidGame extends Game {

	MenuScreen menuScreen;
	GameScreen gameScreen;
	InfoScreen infoScreen;
	Screen previousScreen;

	@Override
	public void create () {
		Gdx.app.log("CathedroidGame", "Created");

		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		infoScreen = new InfoScreen(this);

		previousScreen = null;
		setScreen(menuScreen);
	}

	public void	startGameScreen (boolean newGame, boolean vsAI){
		setScreen(gameScreen);
	}

	public void setMenuScreen() {
		setScreen(menuScreen);
	}

	public void setInfoScreen(Screen previousScreen) {
		this.previousScreen = previousScreen;
		setScreen(infoScreen);
	}

	public void returnFromInfoScreen() {
		if (previousScreen == null)
		{
			setMenuScreen();
		}
		else
		{
			setScreen(previousScreen);
		}
	}

}


