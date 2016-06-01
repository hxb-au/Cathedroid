package au.id.hxb.cathedroid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import au.id.hxb.cathedroid.mechanics.Player;
import au.id.hxb.cathedroid.screens.GameScreen;
import au.id.hxb.cathedroid.screens.InfoScreen;
import au.id.hxb.cathedroid.screens.MenuScreen;
import au.id.hxb.cathedroid.screens.SettingsScreen;


public class CathedroidGame extends Game {

	// game screens
	MenuScreen menuScreen;
	GameScreen gameScreen;
	InfoScreen infoScreen;
	SettingsScreen settingsScreen;
	Screen previousScreen;

	// game config
	Player startingPlayer;
	boolean alternateStarts, randomStartPlayer;
	Player aiPlayer;
	boolean aiOn;
	boolean aiRandom;

	@Override
	public void create () {
		Gdx.app.log("CathedroidGame", "Created");

		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		infoScreen = new InfoScreen(this);
		settingsScreen =  new SettingsScreen(this);

		//default rules
		//TODO load from last time
		setStartingPlayerRandom();
		setAlternateStarts(true);
		setAIRandom();

		//screen control
		previousScreen = null;
		setScreen(menuScreen);
	}

	public void	startGameScreen (boolean newGame, boolean vsAI){
		//start game
		//all games are new until the loading feature is in
		if(newGame) {
			if (vsAI) {
				enableAI();
				if (aiRandom) {
					aiPlayer = (Math.random() < 0.5) ? Player.LIGHT : Player.DARK;
				}
			} else {
				disableAI();
			}
			setScreen(gameScreen);
			gameScreen.startNewGame(this.getStartingPlayer());
		}
		else //load
		{
			aiOn = vsAI;
			setScreen(gameScreen);
			gameScreen.loadGame();
		}
	}

	public void setMenuScreen() {
		setScreen(menuScreen);
	}

	public void setSettingsScreen() {
		setScreen(settingsScreen);
	}

	public void setInfoScreen(Screen previousScreen) {
		this.previousScreen = previousScreen;
		setScreen(infoScreen);
	}

	public void returnFromInfoScreen() {
		if (previousScreen == null)
		{
			//just in case
			setMenuScreen();
		}
		else
		{
			setScreen(previousScreen);
		}
	}


	//starting player logic
	public void setAlternateStarts( Boolean setting ) { alternateStarts  = setting; }
	public Boolean getAlternateStarts() { return alternateStarts; }

	public void setStartingPlayerRandom () { randomStartPlayer = true; }
	public void setStartingPlayerLight() { startingPlayer = Player.LIGHT; randomStartPlayer = false; }
	public void setStartingPlayerDark()  { startingPlayer = Player.DARK; randomStartPlayer = false; }

	public Boolean isStartingPlayerRandom() { return randomStartPlayer; }
	public Boolean isStartingPlayerLight() { return !randomStartPlayer && startingPlayer == Player.LIGHT;}
	public Boolean isStartingPlayerDark() { return !randomStartPlayer && startingPlayer == Player.DARK;}

	private Player getStartingPlayer() {
		if (randomStartPlayer)
		{
			startingPlayer = (Math.random() < 0.5) ? Player.LIGHT : Player.DARK ;
		}

		if (alternateStarts) {
			//only random the first time, can't be random if it's going to alternate
			randomStartPlayer = false;
			Player tmp = startingPlayer;
			if (startingPlayer == Player.LIGHT)
				startingPlayer = Player.DARK;
			else
				startingPlayer = Player.LIGHT;

			return tmp;
		}
		else
		{
			return startingPlayer;
		}
	}

	//ai player logic
	//TODO this seems a bit improv. add to settings screen and give proper control here
	public boolean isAI(Player p){
		return (aiOn && aiPlayer != null && p == aiPlayer);
	}

	public void enableAI() { aiOn = true; }
	public void disableAI() { aiOn = false; }
	public boolean isAiOn() {return aiOn; }
	public void setAILight() { aiPlayer = Player.LIGHT; aiRandom = false; }
	public void setAIDark()  { aiPlayer = Player.DARK;  aiRandom = false;  }
	public void setAIRandom() { aiRandom = true; }

	public boolean isAILight() { return !aiRandom && aiPlayer == Player.LIGHT; }
	public boolean isAIDark() { return !aiRandom && aiPlayer == Player.DARK; }
	public boolean isAiRandom() { return aiRandom; }

}


