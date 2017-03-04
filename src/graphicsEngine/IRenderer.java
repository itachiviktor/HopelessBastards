package graphicsEngine;

import java.awt.Rectangle;

import screenconverter.Size;
import screenconverter.descriptors.ImageDescriptor;
import screenconverter.descriptors.LineDescriptor;
import screenconverter.descriptors.OvalDescriptor;
import screenconverter.descriptors.PolygonDescriptor;
import screenconverter.descriptors.RectangleDescriptor;
import screenconverter.descriptors.StringDescriptor;

/*A Renderer szolg�ltat�s ezen az interfacen kereszt�l �rhet� el.*/
public interface IRenderer {
	/*Itt t�lterhelt met�dusokat l�thatunk.*/
	public void render(ImageDescriptor describer);
	public void render(RectangleDescriptor describer);
	public void render(OvalDescriptor describer);
	public void render(LineDescriptor describer);
	public void render(StringDescriptor describer);
	public void render(PolygonDescriptor describer);
	public ICanvas getCanvas();
	public void cameraMove(int x,int y);
	public Size getCanvasSize();
	public Rectangle getCanvasRectangleOnScreen();
	
	public void canvasInit();
	public void canvasRefresh();
}