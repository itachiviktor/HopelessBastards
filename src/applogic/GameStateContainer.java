package applogic;

import applogic.engine.Engine;
import applogic.loginstate.Login;
import controller.IController;
import screenconverter.IConverter;
import soundapi.ISoundProvider;

public class GameStateContainer extends AbstractGameStateContainer{

	public GameStateContainer(IConverter converter, IController controller,ISoundProvider soundProvider) {
		super(converter, controller,soundProvider);
		
		/*Ennek sz�ks�ge van a soundProviderre, mert a fels�bb szintekt�l ez tudja elk�rni,
		 �s tov�bbadni lejebb, mert ebben van benne a logikai-fizikai sound resource �sszerendel�s.*/

		/*Kezd�skor ez legyen a gamestate.*/
		setActualGameState(new Engine(converter,getSoundProvider()));
		//setActualGameState(new Login(converter,getSoundProvider()));
		/*Az al�bbiakban a GameStatenkat �tadjuk a controllernek, hogy ez lesz az esem�ny
		 figyel� oszt�lyom.*/
		if(getActualGameState().getOwnGameStateType() == GameStateEnum.GAME){
			getController().addGameListener(getActualGameState());
		}else if(getActualGameState().getOwnGameStateType() == GameStateEnum.MENU){
			getController().addMenuListener(getActualGameState());
		}else if(getActualGameState().getOwnGameStateType() == GameStateEnum.LOGIN){
			getController().addMenuListener(getActualGameState());
		}
	}

	/*Ezt a tick met�dust h�vja meg a GameLoop.*/
	@Override
	public void tick(double appTime) {
		
		/*A GameStatek tick met�dusa egy GameStateEnumot ad vissza(LOGIN,GAME,MENU), ami azt jelzi, hogy
		 mi legyen a k�vetkez� gamestate.Teh�t az Engine addig null-t fog visszaadni, am�g nem kell
		 GameStatet v�ltani, viszont ha kell akkor a nextGameStetet adja vissza.*/
		setNextGameState(getActualGameState().tick(appTime));
		
		/*Nullt ad vissza a gamestate, ha nem kell m�s gamestatera v�latni.Viszont ha nem null,
		 akkor �j gamestatenk van, �s be kell jegyezn�nk mint �j figyel�t.*/
		if(getNextGameState() != null){
			getController().removeAllListeners();
			if(getNextGameState() == GameStateEnum.GAME){
				setActualGameState(new Engine(getConverter(),getSoundProvider()));
				getController().addGameListener(getActualGameState());
			}else if(getNextGameState() == GameStateEnum.MENU){
				setActualGameState(new Menu(getSoundProvider()));
				getController().addMenuListener(getActualGameState());
			}else if(getNextGameState() == GameStateEnum.LOGIN){
				setActualGameState(new Login(getConverter(),getSoundProvider()));
				getController().addMenuListener(getActualGameState());
			}
		}
	}

	/*A render met�dusban, amit a GameLoop h�v meg annyit csin�lunk, hogy az aktu�lis GameStatenak
	 megh�vjuk a viewPreapre met�dus�t, ami �sszerakja �s kirajzolja a guit.*/
	@Override
	public void render(double renderTime) {
		getActualGameState().viewPrepare(renderTime);	
	}
}
