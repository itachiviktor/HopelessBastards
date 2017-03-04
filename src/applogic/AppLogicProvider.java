package applogic;

import initapplication.IProviderFactory;
import soundapi.ISoundProvider;

public class AppLogicProvider implements IAppLogicProvider{

	/*Ez biztos�tja, hogy az APP-Logic komponenshez sz�ks�ges minden oszt�lyb�l p�ld�nyos�tsunk.*/
	private IProviderFactory factory;
	private IGameLoop gameLoop;
	private AbstractGameStateContainer gameStateContainer;
	
	public AppLogicProvider(IProviderFactory factory,ISoundProvider soundProvider) {
		this.factory = factory;
		this.gameStateContainer = new GameStateContainer(this.factory.getConverter(), this.factory.getController(),soundProvider);
		gameLoop = new GameLoop(this.gameStateContainer);	
	}
	
	@Override
	public IGameLoop getGameLoop() {
		return gameLoop;
	}
}