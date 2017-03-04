package graphicsEngine;

import java.util.Map;

/*A k�pbet�lt� interface, ezt implement�lja a k�pbet�lt� komponens�k, ezen az interfacen kereszt�l tudjuk
 el�rni.*/
public interface IimageLoader {
	public Map<String, Animation> imageLoad();
}
