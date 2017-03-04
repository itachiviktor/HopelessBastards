package bufferedImageImplementation;

import java.awt.Graphics2D;

import graphicsEngine.CanvasCommand;

public class ScreenTranslater extends CanvasCommand{
	/*Ez egy olyan elem, ami szint�n bufferedImage implement�ci� f�gg�.Ez arr�b transzform�lja a k�pet,
	 ez val�s�tja meg a cameramozgat�st. */
	private Graphics2D g2d;
	
	public ScreenTranslater(int x, int y) {
		super(x, y);
	}

	@Override
	public void render(Object graphicContext) {
		g2d = (Graphics2D)graphicContext;
	
		/*A render met�dus amit a f� ciklus megh�v.Ez fogja minden p�lyaelem render met�dus�t megh�vni.
		 El�sz�r eltolja a k�perny�t a camera objektum szerint �gy,hogy a player legyen k�z�pen.*/
		g2d.translate(getX(),getY());
	}
}