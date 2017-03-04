package bufferedImageImplementation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import config.ConfigDataProvider;
import controller.Controller;
import controller.IController;
import controller.IEventHandlerer;
import graphicsEngine.CanvasCommand;
import graphicsEngine.ICanvas;
import screenconverter.Size;

public class Canvas extends java.awt.Canvas implements ICanvas,IEventHandlerer{
	/*Ez mi a franc�rt kell?*/
	private static final long serialVersionUID = 1L;
	private BufferStrategy bs;
	private Graphics g;
	
	/*Canvas location on the screen.*/
	private int BoundX = 0;
	private int BoundY = 0;
	
	private Graphics2D g2d;
	
	/*Tartalmazza a saj�t v�szon m�retet.A size az egy saj�t oszt�ly amely egybefolygalja a width �s height
	 tilajdons�gokat.*/
	private Size canvasSize;
	private Rectangle rectangleOnScreen;
	
	private ConfigDataProvider data;
	
	public Canvas(int width,int height,boolean fullScreenMode) {
		data = new ConfigDataProvider();
		
		this.rectangleOnScreen = new Rectangle();
		
		Dimension dim;
		if(fullScreenMode){
			dim = Toolkit.getDefaultToolkit().getScreenSize();/*A k�perny� m�ret�t k�rem le*/
			dim = new Dimension(dim.width,dim.height);
			//dim = new Dimension(data.getScreenWidth(),data.getScreenHeight());
			//dim = new Dimension(700,700);
		}else{
			dim = new Dimension(WIDTH,HEIGHT);
		}


		this.canvasSize = new Size(getWidth(),getHeight());
		
		setPreferredSize(dim);
		setMaximumSize(dim);
		setMinimumSize(dim);
		
		BoundX = data.getScreenX();
		BoundY = data.getScreenY();
		
		setBounds(BoundX, BoundY, (int)dim.getWidth(), (int)dim.getHeight());
	}
	
	public void init(){
		bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		/*Az aktu�lis GameState render met�dus�t h�vjuk meg*/
		//states.get(0).render(g);
		g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	          RenderingHints.VALUE_ANTIALIAS_ON);
	    
	}
	
	public void refresh(){
		if(g != null && bs != null){
			g.dispose();
			bs.show();
		}
	}
	
	@Override
	public void render(CanvasCommand renderingObject){
		if (g2d != null) {	
			renderingObject.render(g2d);
		}
	}

	@Override
	public void addListener(IController controller) {
			addKeyListener((Controller)controller);
			addMouseListener((Controller)controller);
			addMouseMotionListener((Controller)controller);
			addMouseWheelListener((Controller)controller);
	}

	@Override
	public Size getCanvasSize() {
		this.canvasSize.setWIDTH(getWidth());
		this.canvasSize.setHEIGHT(getHeight());
		return this.canvasSize;
	}

	@Override
	public Rectangle getCanvasRectangleOnScreen() {
		this.rectangleOnScreen.setBounds(BoundX, BoundY, getCanvasSize().getWIDTH(), getCanvasSize().getHEIGHT());
		return this.rectangleOnScreen;
	}
}