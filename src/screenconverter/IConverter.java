package screenconverter;

import java.awt.Rectangle;
import java.util.List;

import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.ILineViewBuilder;
import applogic.viewbuilder.IOvalViewBuilder;
import applogic.viewbuilder.IPolygonViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
/*Ezen interfacen kereszt�l �rhet� el a Converter komponens funkci�ja.*/
public interface IConverter {
	public void stickImages(List<IImageViewBuilder> describer, double renderTime);
	public void stickLines(List<ILineViewBuilder> describer);
	public void stickPolygones(List<IPolygonViewBuilder> describer);
	public void stickRectangles(List<IRectangleViewBuilder> describer);
	public void stickOvals(List<IOvalViewBuilder> describer);
	public void stickStrings(List<IStringViewBuilder> describer);
	
	public IMonitorScreenManager getMonitorScreenManager();
	
	public void moveCameraToDefault();
	public void moveCamera(int centerX,int centerY);
	public void startOfPieces();
	public void endOfPieces();/*Ez jelzi, hogy mehet a renderel�s,nincs t�bb k�panyag ebben a kirajzoland�
	f�zisban.*/
	
	public Size getCanvasSize();
	public Rectangle getCanvasRectangleOnScreen();
}
