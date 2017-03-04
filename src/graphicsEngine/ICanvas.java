package graphicsEngine;

import java.awt.Rectangle;
import screenconverter.Size;

/*Ezt az interfacet implement�lja a Canvas vagy openGL.Annyit tud, hogy kirajzol a k�perny�re.*/
public interface ICanvas {
	public void render(CanvasCommand renderingObject);
	public void init();
	public void refresh();
	public Size getCanvasSize();
	public Rectangle getCanvasRectangleOnScreen();
}