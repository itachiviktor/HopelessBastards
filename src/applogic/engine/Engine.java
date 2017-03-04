package applogic.engine;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

import applogic.CursorInformationProvider;
import applogic.GameState;
import applogic.GameStateEnum;
import applogic.GarbageCollector;
import applogic.IGarbageCollector;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.Player;
import applogic.elements.Tile;
import applogic.elements.controllers.Environment;
import applogic.elements.controllers.IEnvironment;
import applogic.elements.controllers.PlayerRectangle;
import controller.events.ActivationNumber;
import controller.events.CursorInformation;
import controller.events.KeyValue;
import controller.events.KeyValueConstant;
import controller.events.MoveWayEnum;
import controller.events.MovedWay;
import io.socket.client.Socket;
import network.Connector;
import network.IConnector;
import network.INetworkProvider;
import network.INetworkSetup;
import network.IWorldUpdater;
import network.NetworkSetup;
import network.WorldUpdater;
import screenconverter.IConverter;
import soundapi.ISoundProvider;

public class Engine extends GameState{
	
	private IConverter converter;
	private IViewBuilderContainer viewBuilderContainer;
	
	private List<Tile> tiles = new ArrayList<Tile>();
	private List<Tile> nonBlockingTile = new ArrayList<Tile>();/*Ezeket az�rt vessz�k k�l�n, hogy 
	�tk�z�svizsg�latkor v�letlen�l se ellen�rizze senki, hogy ezekkel �tk�zik-e
	mert ezeken �t lehet gyalogolni, ilyen a talajk�zet �s stb...*/
	
	private Player player;
	private PlayerRectangle playerRectangle;
	
	private IGarbageCollector garbageCollector;
	private IEnvironment environment;
	private CursorInformationProvider cursorProvider;
	
	private IConnector networkConnector;
	private INetworkSetup networkSetup;
	private IWorldUpdater worldUpdater;
	
	private int updateServer;
	
	public Engine(IConverter converter,ISoundProvider soundProvider) {
		super(soundProvider);
		networkConnector = new Connector();
		networkConnector.connect("http://playground2.iit.uni-miskolc.hu:8080/");
		
		this.playerRectangle = new PlayerRectangle();
		
		
		setNextGameState(GameStateEnum.GAME);
		this.converter  = converter;
		this.garbageCollector = new GarbageCollector();
		viewBuilderContainer = new ViewBuilderContainer(tiles, nonBlockingTile, this.converter, player, playerRectangle, garbageCollector);
		this.environment = new Environment(tiles,viewBuilderContainer,this.converter,soundProvider, playerRectangle);
		
		networkSetup = new NetworkSetup((Socket)networkConnector.getLivingConnection(), environment);
		networkSetup.setupNetwork();
		worldUpdater = new WorldUpdater((Socket)networkConnector.getLivingConnection());
		
		this.cursorProvider = this.environment.getCursorInformationProvider();
		
		/*Be�ll�tja a saj�t gamestate t�pus�t, ez egy enum.*/
		setOwnGameStateType(GameStateEnum.GAME);
	}
	
	/*A GameStateContainer ezt a met�dust h�vja a saj�t render met�dus�b�l.Itt l�nyeg�ben a 
	 ViewBuilderContainer komponens viewPrepare() met�dus�t h�vjuk tov�bb, ami ismeri az �sszes 
	 Buildert(View,Line , stb..), �s oda tudja adni a k�vetkez� nagy komponensnek, a ScreenConverternek.*/
	@Override
	public void viewPrepare(double renderTime) {
		viewBuilderContainer.viewPrepare(renderTime);
		
	}
	
	/*Ez az oszt�ly megval�s�tja a listenereket, �gy a k�l�nb�z� felhaszn�l�i iterakci�kra ez tud 
	 v�laszt gener�lni.*/
	
	
	/*Az EntityCommander interfacet megval�s�t� UserActionCommand oszt�lyb�l k�sz�tett objektum neve 
	 userAction.*/
	@Override
	public void characterMoved(MovedWay way) {
		/*Mivel van egy komponensem, ami ir�ny�tja a Playert, legyen az mesters�ges intelligencia,
		 vagy a felhsazn�l�i iter�ci�k.Itt a felhaszn�l�i iter�ci�s ir�ny�t�nak tov�bb�tom, hogy
		 milyen sem�ny keletkezett, teh�t ezek f�ggv�ny�ben parancsolgasson a Playernek.*/
		if(way.getWay() == MoveWayEnum.DOWN){
			
			environment.getUserAction().setDown(true);
		}else if(way.getWay() == MoveWayEnum.LEFT){
			environment.getUserAction().setLeft(true);
			
		}else if(way.getWay() == MoveWayEnum.RIGHT){
			environment.getUserAction().setRight(true);
			
		}else if(way.getWay() == MoveWayEnum.UP){
			environment.getUserAction().setUp(true);
			
		}
	}

	@Override
	public void characterStopMoving(MovedWay way) {
		if(way.getWay() == MoveWayEnum.DOWN){
			environment.getUserAction().setDown(false);
		}else if(way.getWay() == MoveWayEnum.LEFT){
			environment.getUserAction().setLeft(false);
		}else if(way.getWay() == MoveWayEnum.RIGHT){
			environment.getUserAction().setRight(false);
		}else if(way.getWay() == MoveWayEnum.UP){
			environment.getUserAction().setUp(false);
		
		}
	}
	
	@Override
	public void characterActivation(ActivationNumber number) {
		environment.getUserAction().setSkillActivated(number.getNumber(),true);
	}

	@Override
	public void cursorClicked(CursorInformation screen) {
		this.cursorProvider = environment.getCursorInformationProvider();
		
		this.cursorProvider.setClick(true);
		this.cursorProvider.setMove(false);
	
		this.cursorProvider.setLocationOnScreen(screen.getLocationOnScreen());
		this.cursorProvider.setXOnScreen(screen.getxOnScreen());
		this.cursorProvider.setYOnScreen(screen.getyOnScreen());
	}

	@Override
	public void cursorMOved(CursorInformation screen) {
		this.cursorProvider = environment.getCursorInformationProvider();
		
		this.cursorProvider.setClick(false);
		this.cursorProvider.setMove(true);
	
		this.cursorProvider.setLocationOnScreen(screen.getLocationOnScreen());
		this.cursorProvider.setXOnScreen(screen.getxOnScreen());
		this.cursorProvider.setYOnScreen(screen.getyOnScreen());
	}

	@Override
	public void cursorRelease() {
		this.cursorProvider = environment.getCursorInformationProvider();
		
		this.cursorProvider.setClick(false);
		this.cursorProvider.setMove(false);
	}

	@Override
	public void characterTyped(KeyValue value) {
		if(value.getKeyType() != null && value.getKeyType() == KeyValueConstant.ESCAPE){
			setNextGameState(GameStateEnum.LOGIN);;
		}
	}
	
	/*A GameStatek tick met�dusa �gy van megcsin�lva, hogy tov�bb h�vja az �sszes object�nek megh�vja
	 a tick met�dus�t, majd visszaadja, hogy kell -e �j gamestatera v�ltani.Ha nem kell, p�ld�ul
	 nem kattintottak r� semelyik gombra, akkor nullt ad vissza.*/
	@Override
	public GameStateEnum tick(double appTime) {
		this.garbageCollector.cleanEntities(this.environment.getEnemyEntities(),this.environment.getFriendlyEntities());
		this.garbageCollector.cleanEntities(this.environment.getEnemyPlayers(),this.environment.getFriendlyPlayers());
		
		if(getNextGameState() == GameStateEnum.GAME){
			/*for(int i=0;i<environment.getPlayer();i++){
				players.get(i).tick();
			}*/
			environment.tick(appTime);
		
			worldUpdater.updateServer(environment);
			
			
			
			
			return null;
		}else{
			return getNextGameState();
		}
	}

	@Override
	public void init() {
	}

	public Entity getPlayer() {
		return environment.getPlayer();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}