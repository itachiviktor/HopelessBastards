package applogic.loginstate;

import java.util.ArrayList;
import java.util.List;
import applogic.CursorInformationProvider;
import applogic.GameState;
import applogic.GameStateEnum;
import applogic.IGarbageCollector;
import applogic.IViewBuilderContainer;
import applogic.components.ActionListener;
import applogic.components.Container;
import applogic.components.IComponent;
import applogic.components.IContainer;
import applogic.components.LoginButton;
import applogic.components.OptionPane;
import applogic.components.PasswordTextField;
import applogic.components.TextField;
import applogic.elements.BasicElement;
import applogic.elements.Leaf;
import controller.events.ActivationNumber;
import controller.events.CursorInformation;
import controller.events.KeyValue;
import controller.events.KeyValueConstant;
import controller.events.MovedWay;
import screenconverter.IConverter;
import soundapi.ISound;
import soundapi.ISoundProvider;
import soundapi.MP3;

public class Login extends GameState{
	/*A bejelentkez� GameState.*/
	
	private IGarbageCollector garbageCollector;
	private CursorInformationProvider cursorProvider;
	
	private IViewBuilderContainer container;

	private IContainer componentContainer;
	private boolean typeAble;
	private int typeTime;
	
	private double appTime;

	private ISound loginSong;
	/*A lev�l anim�ci�hoz sz�ks�ges leaf lista.*/
	private List<BasicElement> leafs;
	
	/*Komponensek, melyek megjelennek majd a k�perny�n.*/
	private IComponent username;
	private IComponent password;
	private IComponent loginButton;
	private IComponent optionPane;
	
	public Login(IConverter converter,ISoundProvider soundProvider) {
		super(soundProvider);
		
		loginSong = new MP3("loginSong", soundProvider);
		loginSong.play();/*r�gt�n bet�lt�d�skor m�r el is kezdj�k lej�tszani a login songot.*/
		
		/*Be�ll�tjuk a GameState inform�ci�kat.A nextGameStetet az�rt �ll�tom szint�n Loginra
		 mert ha ez marad, akkor nem v�lt gamestatet a j�t�k alapb�l.*/
		setNextGameState(GameStateEnum.LOGIN);
		setOwnGameStateType(GameStateEnum.LOGIN);
		/*Az�rt nullal h�vom meg, mert itt nem kell a k�perny�n senkit k�vetni.*/
		this.cursorProvider = new CursorInformationProvider(null, converter);
		
		this.componentContainer = new Container(cursorProvider);
		
		this.username = new TextField(400, 300, 500, 30, componentContainer,soundProvider,true);
		this.password = new PasswordTextField(400, 400, 500, 30, componentContainer,soundProvider);
		this.optionPane = new OptionPane(200, 200,752,397,"Not an existing account!",componentContainer,soundProvider);
		this.loginButton = new LoginButton(900,200,335,390,"login",componentContainer);
		
		componentContainer.addComponent(this.username);
		componentContainer.addComponent(this.password);
		componentContainer.addComponent(this.optionPane);
		componentContainer.addComponent(this.loginButton);
		
		/*Az esem�nykezel� teljesen �gy m�k�dik mint az awt-s k�rnyezetben-->kir�ly*/
		this.loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionCommand() {
				if(username.toStringTransformation().length() < 2){
					optionPane.setShowNow(true);
					optionPane.playSoundEffect();
				}else{
					/*Ha �tl�p�nk egy m�sik j�t�k�ll�sba, akkor a bejelentkez� zene ha megy
					 le�ll�tjuk*/
					loginSong.stop();
					setNextGameState(GameStateEnum.GAME);
				}	
			}
		});
	
		this.container = new LoginViewBuilderContainer(converter,this.cursorProvider,this.componentContainer);
		leafs = new ArrayList<BasicElement>();
		/*Itt k�zzel adtam hozz� a leveleket, ezek fognak folymatosan hullani.*/
		leafs.add(new Leaf(0, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(10, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(100, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(0, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(20, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(50, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(200, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(210, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(1100, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(400, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(250, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(350, 0,60,60,0,0,0,container));
	}
	
	@Override
	public void viewPrepare(double renderTime) {
		this.container.viewPrepare(renderTime);
	}
	
	@Override
	public void characterMoved(MovedWay way) {
	}

	@Override
	public void characterStopMoving(MovedWay way) {
	}

	@Override
	public void characterActivation(ActivationNumber number) {
	}

	@Override
	public void cursorClicked(CursorInformation screen) {
		this.cursorProvider.setClick(true);
		this.cursorProvider.setMove(false);
		this.cursorProvider.setLocationOnScreen(screen.getLocationOnScreen());
		this.cursorProvider.setXOnScreen(screen.getxOnScreen());
		this.cursorProvider.setYOnScreen(screen.getyOnScreen());
	}

	@Override
	public void cursorMOved(CursorInformation screen) {
		this.cursorProvider.setClick(false);
		this.cursorProvider.setMove(true);
		this.cursorProvider.setLocationOnScreen(screen.getLocationOnScreen());
		this.cursorProvider.setXOnScreen(screen.getxOnScreen());
		this.cursorProvider.setYOnScreen(screen.getyOnScreen());
	}

	@Override
	public void cursorRelease() {
		this.cursorProvider.setClick(false);
		this.cursorProvider.setMove(false);
	}

	@Override
	public void characterTyped(KeyValue value) {
		if(value.getKeyType() != null && value.getKeyType() == KeyValueConstant.ESCAPE){
			System.exit(0);
		}else if(value.getKeyType() != null && value.getKeyType() == KeyValueConstant.ENTER){
			
		}else{
			/*Ez a r�sz az�rt kell, hogy a 60 fps miatt egy billenty�le�t�s ne okozzon sok
			 karakterbevitelt, hanem m�sodpercenk�nt csak egy karaktert lehessen be�tni,
			 akkor is ha r�tenyerelnek a bilenty�zetre.*/
			if(typeTime < appTime){
				typeAble = true;
			}else{
				typeAble = false;
			}
			for(int i=0;i<componentContainer.getComponents().size();i++){
				if(typeAble == true && componentContainer.getComponents().get(i).getFocus().getFocusOwnerComponent() == componentContainer.getComponents().get(i)){
					componentContainer.getComponents().get(i).keyhandle(value);
					typeAble = false;
				}
			}
		}
	}

	@Override
	public GameStateEnum tick(double appTime) {
		this.appTime = appTime;
		
		if(getNextGameState() == GameStateEnum.LOGIN){
			this.cursorProvider.tick(appTime);
			componentContainer.tick(appTime);
			for(int i=0;i<leafs.size();i++){
				leafs.get(i).tick(appTime);
			}
			return null;
		}else{
			return getNextGameState();
		}
	}

	@Override
	public void init() {
	}
}