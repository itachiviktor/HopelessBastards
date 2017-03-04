package applogic.engine;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import applogic.CursorInformationProvider;
import applogic.GameLoop;
import applogic.IGarbageCollector;
import applogic.IMapLoader;
import applogic.IViewBuilderContainer;
import applogic.MapLoader;
import applogic.elements.Entity;
import applogic.elements.Player;
import applogic.elements.Tile;
import applogic.elements.controllers.PlayerRectangle;
import applogic.skills.imageicons.ImageIconTimerPicture;
import applogic.viewbuilder.HealthBarViewBuilder;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.ILineViewBuilder;
import applogic.viewbuilder.IOvalViewBuilder;
import applogic.viewbuilder.IPolygonViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.IStringViewBuilder;
import applogic.viewbuilder.MouseViewBuilder;
import applogic.viewbuilder.SkillBarViewBuilder;
import applogic.viewbuilder.entities.DoublePointtt;
import applogic.viewbuilder.entities.LineEquation;
import applogic.viewbuilder.entities.MageViewBuilder;
import screenconverter.IConverter;
import screenconverter.IMonitorScreenManager;


public class ViewBuilderContainer implements IViewBuilderContainer{
	public double newxx;
	public double newyy;
	
	
	/*Ezt a komponenst fogja tov�bbh�vni a GameState, amikor renderelnie kell.Ez a szolg�ltat�s 
	 gy�jti �ssze az �sszes kirajzoland� alakzatot, �s k�pet, �s adja �t �rtelmezhet�
	 form�ban a ScreenConverter szolg�ltat�snak.*/
	
	private List<IOvalViewBuilder> ovalBuilder;
	private List<IStringViewBuilder> stringBuilder;
	private List<IRectangleViewBuilder> rectangleBuilder;
	private List<IPolygonViewBuilder> polygonBuilder;
	private List<ILineViewBuilder> lineBuilder;
	
	private List<IImageViewBuilder> viewBuilder;/*Ebbe van l�nyeg�ben minden k�p, kiv�ve p�rat.*/
	
	private List<IImageViewBuilder> notDestroyableView;/*Ebbe a list�ba van a talajk�zet 
	�s az elpuszt�thatatlan elemek, amik mindig ugyan ott vannak, �s nem puszt�that�k el,
	ezeket az�rt raktam k�l�n list�ba, hogy a GarbageCollectornak ne kelljen ezen feleslegesen
	v�gigmennie.*/
	
	private List<IImageViewBuilder> staticviewBuilder;/*Ebbe a folyamatosan a k�ppel mozg� elemeket
	rakom, ezeket az�rt kellett k�l�n lev�lasztani a t�bbit�l, hogy ezeket mindig utols�k�nt
	rajzoljuk ki, ezek legyenek mindenek felett, gondolok itt a skilbarra �s a healthbarra, amiken
	ha kereszt�l gyalogolna a player az nem lenne t�l eszt�tikus.*/
	
	private List<IImageViewBuilder> skillAnimationViewBuilders;
	
	private List<IStringViewBuilder> cdTimes;
	
	private IGarbageCollector garbageCollector;
	
	private Entity player;
	private PlayerRectangle playerRectangle;
	private IMapLoader mapLoader;
	
	private CursorInformationProvider cursor;
	
	private IConverter converter;
	
	private File file;
	private String path;
	
	public ViewBuilderContainer(List<Tile> tiles,List<Tile> nonBlockingTile,IConverter converter,Player player, PlayerRectangle playerRectangle, IGarbageCollector garbageCollector) {
		this.player = player;
		this.playerRectangle = playerRectangle;
		this.converter = converter;
		this.garbageCollector = garbageCollector;
		
		skillAnimationViewBuilders = new ArrayList<IImageViewBuilder>();
		notDestroyableView = new ArrayList<IImageViewBuilder>();
		
		staticviewBuilder = new ArrayList<IImageViewBuilder>();
		viewBuilder = new ArrayList<IImageViewBuilder>();
		
		this.polygonBuilder = new ArrayList<IPolygonViewBuilder>();
		this.rectangleBuilder = new ArrayList<IRectangleViewBuilder>();
		this.ovalBuilder = new ArrayList<IOvalViewBuilder>();
		this.lineBuilder = new ArrayList<ILineViewBuilder>();
		this.stringBuilder = new ArrayList<IStringViewBuilder>();
			
		try {
			path = new File(".").getCanonicalPath() + "./res/";
			
			/*A mapLoader kopmponenst haszn�lva bet�ltj�k az �sszes statikus p�lyaelemet a mem�ri�ba.*/
			file = new File(path + "bitMap.png");
			BufferedImage bitmap = ImageIO.read(file);
			file = new File(path + "tileMap.png");
			BufferedImage tileBitMap = ImageIO.read(file);
			this.mapLoader = new MapLoader();
			this.mapLoader.loadMap(bitmap,tileBitMap, tiles,nonBlockingTile,notDestroyableView);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		staticviewBuilder.add(new SkillBarViewBuilder(converter.getMonitorScreenManager(), this));
		staticviewBuilder.add(new HealthBarViewBuilder(converter.getMonitorScreenManager(), this));
		
		this.cdTimes = new ArrayList<IStringViewBuilder>();
		
		//stringBuilder.add(new SimpleText(500, 500,0, 0,0, "mukodj", Color.red));
		//rectangleBuilder.add(new SimpleRectangle(600, 600, 45,600,600, 50, 50, Color.green, false));
		//lineBuilder.add(new SimpleLine(0, 0, 0,0,0, 1000,1000, Color.blue));
		//polygonBuilder.add(new SimplePolygon(800, 800,0,0,0,Color.pink, true, new Polygon()));
		//ovalBuilder.add(new SimpleOval(800,800, 0,0,0,1000,1000, Color.pink, true));
		
	}

	@Override
	public void viewPrepare(double renderTime) {
		/*Ezt a met�dust h�vja tov�bb a GameState, amikor renderelni szeretne.*/
		
		
		/*Els� l�p�sben megh�vom a GarbageCollectort, hogy minden olyan grafikai elemet t�r�lj�n,
		 ami m�r meg kell, hogy sz�nj�n.Itt a Collectornak csak azokat a list�kat adom �t,
		 ahol grafikus elemle�r�k vannak, azokat megsz�ri, �s csak �gy foglya a met�dus tov�bb adni
		 egy m�sik komponensnek.*/
		this.garbageCollector.cleanViewBuilders(rectangleBuilder,viewBuilder,ovalBuilder,stringBuilder,lineBuilder,polygonBuilder);
		
		converter.startOfPieces();	
		
		
		/*El�sz�r a kamer�t �ll�ttatjuk be vele, megadva, hogy melyik pont legyen a k�perny� k�z�ppontj�ban.*/
		converter.moveCamera((int)playerRectangle.getX() + playerRectangle.getWidth()/2, (int)playerRectangle.getY() + playerRectangle.getHeight()/2);
		//converter.moveCamera(3000, 3000);
		/*Majd a k�p,vonal,poly le�r�kat adjuk �t, �s ez sz�pen �sszeragaszt bel�le egy kirajzolhat� k�pet.
		 A k�ple�r�knak k�t lista is van, a viewBuilder, �s a staticviewBuilder.Ezekre az�rt van sz�ks�g, hogy
		 ki tudjuk egym�sut�ni sorrendet rakni.P�ld�ul a staticviewBuilderben vannak azok az elemek, 
		 amelyek mindig a k�perny�n vannak, gondolok itt a healtbarra �s stb, teh�t ezeknek mindenek felett
		 kell kirajzol�dnia, azaz mindenek ut�n.*/
		
		converter.stickImages(notDestroyableView, renderTime);
		
		converter.stickImages(viewBuilder, renderTime);
		converter.stickImages(skillAnimationViewBuilders, renderTime);
		
		
		converter.stickLines(lineBuilder);
		converter.stickOvals(ovalBuilder);
		converter.stickPolygones(polygonBuilder);
		converter.stickRectangles(rectangleBuilder);
		converter.stickStrings(stringBuilder);
		
		
		
		converter.stickImages(staticviewBuilder, renderTime);
		
		converter.stickStrings(cdTimes);
		
		/*Itt jelezz�k a ScreenManager komponens fel�, hogy ebben a kirajzol�si ciklusban nincs t�bb k�p,
		 sem alakzat, mostm�r teljes a k�perny�.*/
		converter.endOfPieces();
	}
	
	@Override
	public CursorInformationProvider getCursorInformationProvider() {
		return this.cursor;
	}
	
	@Override
	public void setCursor(CursorInformationProvider cursor) {
		this.cursor = cursor;
		staticviewBuilder.add(new MouseViewBuilder(this.cursor));
	}

	@Override
	public List<IOvalViewBuilder> getOvalBuilder() {
		return ovalBuilder;
	}
	
	@Override
	public void setOvalBuilder(List<IOvalViewBuilder> ovalBuilder) {
		this.ovalBuilder = ovalBuilder;
	}
	
	@Override
	public List<IStringViewBuilder> getStringBuilder() {
		return stringBuilder;
	}
	
	@Override
	public void setStringBuilder(List<IStringViewBuilder> stringBuilder) {
		this.stringBuilder = stringBuilder;
	}
	
	@Override
	public List<IRectangleViewBuilder> getRectangleBuilder() {
		return rectangleBuilder;
	}
	
	@Override
	public void setRectangleBuilder(List<IRectangleViewBuilder> rectangleBuilder) {
		this.rectangleBuilder = rectangleBuilder;
	}
	
	@Override
	public List<IPolygonViewBuilder> getPolygonBuilder() {
		return polygonBuilder;
	}
	
	@Override
	public void setPolygonBuilder(List<IPolygonViewBuilder> polygonBuilder) {
		this.polygonBuilder = polygonBuilder;
	}
	
	@Override
	public List<ILineViewBuilder> getLineBuilder() {
		return lineBuilder;
	}
	
	@Override
	public void setLineBuilder(List<ILineViewBuilder> lineBuilder) {
		this.lineBuilder = lineBuilder;
	}
	
	@Override
	public List<IImageViewBuilder> getNotDestroyableView() {
		return notDestroyableView;
	}
	
	@Override
	public void setNotDestroyableView(List<IImageViewBuilder> notDestroyableView) {
		this.notDestroyableView = notDestroyableView;
	}
	
	@Override
	public void setViewBuilder(List<IImageViewBuilder> viewBuilder) {
		this.viewBuilder = viewBuilder;
	}
	
	@Override
	public void setStaticviewBuilder(List<IImageViewBuilder> staticviewBuilder) {
		this.staticviewBuilder = staticviewBuilder;
	}
	
	@Override
	public IMonitorScreenManager getMonitorScreenManager(){
		return converter.getMonitorScreenManager();
	}

	@Override
	public List<IImageViewBuilder> getStaticviewBuilder() {
		return this.staticviewBuilder;
	}

	@Override
	public List<IImageViewBuilder> getViewBuilder() {
		return this.viewBuilder;
	}

	@Override
	public void setPlayer(Entity player) {
		this.player = player;
		for(int i=0;i<7;i++){
			cdTimes.add(new ImageIconTimerPicture(i, converter.getMonitorScreenManager(), player));
		}
	}

	@Override
	public Entity getPlayer() {
		return this.player;
	}

	@Override
	public void setSkillAnimationViewBuilders(List<IImageViewBuilder> skillanimations) {
		this.skillAnimationViewBuilders = skillanimations;
	}

	@Override
	public List<IImageViewBuilder> getSkillAnimationViewBuilders() {
		return this.skillAnimationViewBuilders;
	}
}