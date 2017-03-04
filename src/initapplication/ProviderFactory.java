package initapplication;

import applogic.AppLogicProvider;
import applogic.IAppLogicProvider;
import applogic.IGameLoop;
import controller.ControllerProvider;
import controller.IController;
import controller.IControllerProvider;
import controller.IEventHandlerer;
import graphicsEngine.IRenderer;
import graphicsEngine.IRendererProvider;
import graphicsEngine.RendererProvider;
import screenconverter.ConverterProvider;
import screenconverter.IConverter;
import screenconverter.IConverterProvider;
import soundapi.ISoundProvider;
import soundapi.SoundProvider;

public class ProviderFactory implements IProviderFactory{
	/*A main met�dus nem csin�l semmi m�st, csak l�trehoz egy p�ld�nyt ebb�l az oszt�lyb�l,persze ezt egy
	 interfacen kereszt�l teszi.Ez a komponens hozza l�tre az �sszes t�bbi egys�g el��ll�t� komponens�t,
	 �s azok pedig m�r saj�t komponenscsomagukat el��ll�tj�k.Itt l�nyeg�ben minden nagy komponensre 
	 van adattag(Renderer,Controller,Converter,Applogic-gameLoop).*/
	
	
	private IRenderer renderer;
	private IController controller;
	private IConverter converter;
	private IGameLoop gameLoop;
	
	private IRendererProvider rendererProvider;
	private IConverterProvider converterProvider;
	private IControllerProvider controllerProvider;
	private IAppLogicProvider appLogicProvider;
	private ISoundProvider soundProvider;
	
	
	public ProviderFactory() {
		/*Itt p�ld�nyos�tom az �sszes Providert, azok pedig el��ll�tj�k a sz�ks�ges objektumokat saj�t
		 csomagukon bel�l.*/
		soundProvider = new SoundProvider();
		
		rendererProvider = new RendererProvider();
		renderer = rendererProvider.getRenderer();
		converterProvider = new ConverterProvider(renderer);
		converter = converterProvider.getConverter();
		controllerProvider = new ControllerProvider((IEventHandlerer)renderer.getCanvas(),renderer.getCanvas());
		controller = controllerProvider.getController();
		appLogicProvider = new AppLogicProvider(this,soundProvider);
		gameLoop = appLogicProvider.getGameLoop();
		
		
	}
	
	@Override
	public void buildApplication() {
	}

	@Override
	public IRenderer getRenderer() {
	
		return this.renderer;
	}
	
	@Override
	public IGameLoop getGameLoop() {
		return this.gameLoop;
	}

	@Override
	public IController getController() {

		return this.controller;
	}

	@Override
	public IConverter getConverter() {

		return this.converter;
	}

	@Override
	public ISoundProvider getSoundProvider() {
		return this.soundProvider;
	}
}