package applogic;

import java.awt.Point;

import applogic.components.Container;
import applogic.components.IContainer;
import controller.GameListener;
import controller.MenuListener;
import soundapi.ISoundProvider;

public abstract class GameState implements GameListener,MenuListener{
	/*J�t�k�ll�s(j�t�k,f�men�,opci� men� stb..)
	 Ebb�l az oszt�lyb�l sz�rmazik az �sszes,teh�t itt defini�lom a k�z�s dolgokat.
	 Ez az oszt�ly implement�lja a Listenereket,hisz a j�tkban a katint�sra m�shogy kell reag�lni,mint a f�men�n, ez�rt
	 minden GameState mag�nak k�l�n meghat�rozza az esem�nykezel�ket, �s A Canvasnak mag�t a GameStatetet �t lehet adni mint Listenert*/
	
	private int mouseMovedX;/*az eg�r x mozdul�sa*/
	private int mouseMovedY;/*az eg�r y mozdul�sa*/
	
	private Point mouse = new Point(0,0);/*Az eg�r melyik pontra mutat*/
	
	/*Minden gamestatenak van egy warning joptionpanehez hasonl� eszk�ze.A boolean azt mutatja, hogy
	 kikell-e rajzolni a figyelmeztet�st vagy sem.Ha warning van, akkor semmi m�s esem�nyre nem 
	 reag�lunk, csak a warning elfogad�s gombj�ra.*/
	//public Warning warning;
	private boolean isWarningSituation = false;
	
	private ISoundProvider soundProvider;
	
	private GameStateEnum ownGameStateType;
	private GameStateEnum nextGameState;
	
	public GameState(ISoundProvider soundProvider) {
		this.soundProvider = soundProvider;
	}
	
	public abstract GameStateEnum tick(double appTime);
	public abstract void init();
	public abstract void viewPrepare(double rednerTime);

	public ISoundProvider getSoundProvider() {
		return soundProvider;
	}

	public void setSoundProvider(ISoundProvider soundProvider) {
		this.soundProvider = soundProvider;
	}

	public GameStateEnum getNextGameState() {
		return nextGameState;
	}

	public void setNextGameState(GameStateEnum nextGameState) {
		this.nextGameState = nextGameState;
	}

	public GameStateEnum getOwnGameStateType() {
		return ownGameStateType;
	}

	public void setOwnGameStateType(GameStateEnum ownGameStateType) {
		this.ownGameStateType = ownGameStateType;
	}

	public int getMouseMovedX() {
		return mouseMovedX;
	}
	public void setMouseMovedX(int mouseMovedX) {
		this.mouseMovedX = mouseMovedX;
	}
	public int getMouseMovedY() {
		return mouseMovedY;
	}
	public void setMouseMovedY(int mouseMovedY) {
		this.mouseMovedY = mouseMovedY;
	}
	public Point getMouse() {
		return mouse;
	}
	public void setMouse(Point mouse) {
		this.mouse = mouse;
	}
	public boolean isWarningSituation() {
		return isWarningSituation;
	}
	public void setWarningSituation(boolean isWarningSituation) {
		this.isWarningSituation = isWarningSituation;
	}	
}