package screenconverter;

import java.awt.Rectangle;
import java.util.List;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.ILineViewBuilder;
import applogic.viewbuilder.IOvalViewBuilder;
import applogic.viewbuilder.IPolygonViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import graphicsEngine.IRenderer;
import screenconverter.descriptors.ImageDescriptor;
import screenconverter.descriptors.LineDescriptor;
import screenconverter.descriptors.OvalDescriptor;
import screenconverter.descriptors.RectangleDescriptor;
import screenconverter.descriptors.StringDescriptor;

public class Converter implements IConverter{
	public static boolean doit;
	private IEstimator estimator;
	
	private IRenderer renderer;
	
	/*Ez itt l�nyeg�ben egy objektum pool.L�nyege, hogy 6 f�le alakzat l�tezik(k�p,oval,polygon,
	 rectangle,vonal,sz�veg), �s ezek mindegyik�hez l�trehoztam egy oszt�lyt,ami le�r egy adott alakzatot.
	 P�ld�ul egy magehez a k�pet, azaz a k�p logikai nev�t, �s az anim�ci�ban a sorsz�m�t,x,ykoord,stb.
	 Ezek a le�rok szolg�lnak arra, hogy a Renderer objektumnak a render met�dus�nak �tadjuk,
	 az pedig ezek alapj�n el tudja d�nteni, hogy mit is rajzoljon.Ezek az�rt poolt alkotnak, 
	 mert �gy nem kell mindegyikb�l t�bb p�ld�nyt l�trehozni, hanem csak az adattagjai �rt�keit
	 m�dos�tom.*/
	private OvalDescriptor oval = new OvalDescriptor(1010, 10, 0, 0,0,100,100,null,false);
	private StringDescriptor string = new StringDescriptor(100, 100,100, 0, 0,0, "alma",null,"fontName");
	private RectangleDescriptor rectangle = new RectangleDescriptor(0, 0,0 ,0,0, 80, 80,null,false);
	private ImageDescriptor image = new ImageDescriptor(400, 400, 0,0,0, 1000,100, "fire",1);
	private LineDescriptor line = new LineDescriptor(10, 100,0,0,0,200 , 200,null);
	
	private ICamera camera;
	
	/**/
	private Rectangle cameraMonitorCheckingElementsHelper;
	
	private Rectangle visibleArea;/*A Converter komponens tudja, hogy mi a kirajzoland� k�pek
	k�re, �s csak azokat a le�r�kat k�ldi kirajzol�sra, amelyek a k�perny�n l�tszani fognak, ez hatalmas
	gyors�t�st jelent.*/
	
	private IMonitorScreenManager monitorScreenManager;/*A Converter komponens egyik szolg�ltat�sa, hogy
	megutdja v�laszolni, hogy a skillk�pek,skillbar, healtbar pontosan milyen koordin�t�kon
	helyeszkednek el.Mivel azokat a kamer�val egy�tt kell mozgatni.Az APP-Logic komponens ViewBuilder
	egys�gei ett�l k�rnek adatokat szolg�ltat�s form�ly�ban.*/
	
	private ICanvasSizeRefresher canvasSizeRefresher;/*Ez a komponens friss�ti a Canvas m�ret�nek adatait.*/
	
	/*Ez a k�t v�ltoz� tartalmazza a k�z�ppontot, amihez a kamera majd viszony�tani fog.Ez a 
	 player lesz.*/
	private int centerX;
	private int centerY;
	
	public Converter(IRenderer renderer) {
		 this.renderer = renderer;
		 this.camera = new Camera();
		 this.cameraMonitorCheckingElementsHelper = new Rectangle();
		 this.visibleArea = new Rectangle();
		 this.canvasSizeRefresher = new CanvasSizeRefresher(renderer);
		 this.monitorScreenManager = new MonitorScreenManager(this.renderer,7,this.canvasSizeRefresher);
		 this.estimator = new Estimator();
	}
	
	@Override
	public void moveCameraToDefault() {
		renderer.cameraMove(0, 0);
		this.centerX = 0;
		this.centerY = 0;
	}
	
	@Override
	public void moveCamera(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		camera.setCamera(-centerX + renderer.getCanvasSize().getWIDTH()/2,-centerY + renderer.getCanvasSize().getHEIGHT()/2);
		renderer.cameraMove(camera.getX(),camera.getY());
	}
	
	/*A Converter komponens szolgaltat�s met�dusa.Itt a Renderer komponens render() t�lterhelt met�dus�t
	 h�vjuk meg annyiszor amennyi elemet �tadott az AppLogic, �s m�g n�h�ny saj�tot is hozz�rak,
	 pl.k�sza madarak, stb jelent�ktelen vizu�lis effektek.*/
	@Override
	public void stickImages(List<IImageViewBuilder> describer, double renderTime) {       
        for(int i=0;i<describer.size();i++){
        	if(describer.get(i).getImageDescriptor() != null){
        		
        		if(describer.get(i).getPositionEstimate() != null && !describer.get(i).getPositionEstimate().getEntity().isThisEntityIsThePlayer()){
					
        			estimator.estimateNewPosition(describer.get(i), describer.get(i).getPositionEstimate(), renderTime);
        			
        		}
        		
        		for(int j=0;j<describer.get(i).getImageDescriptor().length;j++){
     				/*A megvizsg�lt elemet ebbe rakjuk, ez seg�dv�ltoz�.*/
     				this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getImageDescriptor()[j].getX(),describer.get(i).getImageDescriptor()[j].getY() ,describer.get(i).getImageDescriptor()[j].getWidth() ,describer.get(i).getImageDescriptor()[j].getHeight());
     				
     				/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
     				k�perny�n.*/
     				if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
     					//describer.get(i).getImageDescriptor()[j].
     					
     						
     				//	doit = true;
     					renderer.render(describer.get(i).getImageDescriptor()[j]);
     					//doit = false;
     				}
        		}
        	}
        }
	}

	@Override
	public void stickLines(List<ILineViewBuilder> describer) {
		 for(int i=0;i<describer.size();i++){
				for(int j=0;j<describer.get(i).getLineDescriptor().length;j++){
						/*A megvizsg�lt elemet ebbe rakjuk, ez seg�dv�ltoz�.*/
						this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getLineDescriptor()[j].getX(),describer.get(i).getLineDescriptor()[j].getY() ,10,10);
						
						/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
						 k�perny�n.*/
						if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
							renderer.render(describer.get(i).getLineDescriptor()[j]);
						}
				}
	     }
	}

	@Override
	public void stickPolygones(List<IPolygonViewBuilder> describer) {
		 for(int i=0;i<describer.size();i++){
				for(int j=0;j<describer.get(i).getPolygonDescriptor().length;j++){
						/*A megvizsg�lt elemet ebbe rakjuk, ez seg�dv�ltoz�.*/
						this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getPolygonDescriptor()[j].getX(),describer.get(i).getPolygonDescriptor()[j].getY() ,10 ,10);
						
						/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
						 k�perny�n.*/
						if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
							renderer.render(describer.get(i).getPolygonDescriptor()[j]);
						}
				}
	     }
	}

	@Override
	public void stickRectangles(List<IRectangleViewBuilder> describer) {
		 for(int i=0;i<describer.size();i++){
				for(int j=0;j<describer.get(i).getRectangleDescriptor().length;j++){
						/*A megvizsg�lt elemet ebbe rakjuk, ez seg�dv�ltoz�.*/
						this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getRectangleDescriptor()[j].getX(),describer.get(i).getRectangleDescriptor()[j].getY() ,describer.get(i).getRectangleDescriptor()[j].getWidth() ,describer.get(i).getRectangleDescriptor()[j].getHeight());
						
						/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
						 k�perny�n.*/
						if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
							renderer.render(describer.get(i).getRectangleDescriptor()[j]);
						}
				}
	     }
	}

	@Override
	public void stickOvals(List<IOvalViewBuilder> describer) {
		 for(int i=0;i<describer.size();i++){
				for(int j=0;j<describer.get(i).getOvalDescriptor().length;j++){
						/*A megvizsg�lt elemet ebbe rakjuk, ez seg�dv�ltoz�.*/
						this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getOvalDescriptor()[j].getX(),describer.get(i).getOvalDescriptor()[j].getY() ,describer.get(i).getOvalDescriptor()[j].getWidth() ,describer.get(i).getOvalDescriptor()[j].getHeight());
						
						/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
						 k�perny�n.*/
						if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
							renderer.render(describer.get(i).getOvalDescriptor()[j]);
						}
				}
	     }
	}

	@Override
	public void stickStrings(List<IStringViewBuilder> describer) {
		 for(int i=0;i<describer.size();i++){
				for(int j=0;j<describer.get(i).getStringDescriptor().length;j++){
						/*A megvizsg�lt elemet ebbe rakjuk, ez seg�dv�ltoz�.*/
						this.cameraMonitorCheckingElementsHelper.setBounds(describer.get(i).getStringDescriptor()[j].getX(),describer.get(i).getStringDescriptor()[j].getY() ,10,10);
						
						/*Csak azokat a komponenseket rajzoljuk ki, melyek benne lesznek a kirajzolt
						 k�perny�n.*/
						if(this.cameraMonitorCheckingElementsHelper.intersects(getVisibleArea())){
							renderer.render(describer.get(i).getStringDescriptor()[j]);
						}
				}
	     }
	}
	
	private Rectangle getVisibleArea(){
		/*A describer objektumban megkapjuk a k�vetend� player poz�ci�j�t.*/
		
		/*Ez az�rt kell,hogy ne renderelj�k m�r mindent ki,csak azt ami a kivet�tett k�perny�n lesz,ez kev�s megjelen�tend�
		 dologn�l nem sz�ks�ges,de ha sok minden van a k�perny�n,akkor n�lk�l�zhetetlen.*/
		
		/*-5 az�rt kell,mert a k�perny�n k�v�l 5 pixelt vastagon m�g kirajzolunk friss�t�nk.*/
		/*10 mivel k�toldalr�l 5 plussz pixel*/
		
		
		/*Az alap�rtelmezett �rt�k az a (0,0) amikoris nincs elmozd�tva a kamera,ez a bejelentkez�
		 k�perny� ilyen, akkor az a speck� eset kell,  ha pedig van kamera a k�pben, akkor
		 az als�.*/
		if(centerX == 0 && centerY == 0){
			this.visibleArea.setBounds(0,0,renderer.getCanvasSize().getWIDTH(),renderer.getCanvasSize().getHEIGHT());
		}else{
			this.visibleArea.setBounds(this.centerX-(renderer.getCanvasSize().getWIDTH()/2),this.centerY-(renderer.getCanvasSize().getHEIGHT()/2),renderer.getCanvasSize().getWIDTH(),renderer.getCanvasSize().getHEIGHT());
		}
		
		return this.visibleArea;
	}

	@Override
	public IMonitorScreenManager getMonitorScreenManager() {
		return this.monitorScreenManager;
	}

	@Override
	public Size getCanvasSize() {
		return renderer.getCanvasSize();
	}

	@Override
	public void endOfPieces() {
		renderer.canvasRefresh();
	}

	@Override
	public void startOfPieces() {
		renderer.canvasInit();
	}

	@Override
	public Rectangle getCanvasRectangleOnScreen() {
		return this.renderer.getCanvasRectangleOnScreen();
	}
}