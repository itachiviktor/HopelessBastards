package applogic;

import java.lang.Object;
import java.util.List;

import applogic.elements.Tile;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;

public interface IMapLoader {
	/*Nagyon rugalmasra lett elk�sz�tve ez a funkci�.A loadMap() met�dus l�nyeg�ben bet�lti egy bitmapb�l, a
	 statikus p�lyaelemeket.Objectk�nt van megadva, de ezt k�nyelmesen lehet kasztolni BufferedImagere
	 vagy amiben t�rolva van az inform�ci�.A tiles lista tartalmazza a nem mozg� p�lyaelemeket
	 ez�rt azt a list�t is �tadjuk neki, hogy pakoljon bele, illetve a viewBuilder le�r�t is, 
	 amiben a k�ple�r�k vannak, azt is adja hozz�, teh�t hogy melyik csempe , h�z,stb hol
	 helyeszkedik el.*/
	public void loadMap(Object bitMap,Object bitTileMap,List<Tile> tiles,List<Tile>nonBlockingTile,List<IImageViewBuilder> viewBuilder);
}
