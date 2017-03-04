package applogic.engine;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import applogic.elements.Entity;
import applogic.elements.Tile;

public class CollisionDetection implements ICollisionDetection{
	/*Ebben az oszt�lyban defini�lok �tk�z�svizsg�l� met�dusokat, melyek param�terben kapnak
	 megvizsg�land� elemeket, �s azokr�l eld�ntik, hogy �tk�znek-e vagy sem.*/
	
	/*Sz�m�t�s seg�t� v�ltoz�k, ezekre az�rt van sz�ks�g, hogy ne mindig new Point()-ot toljak.*/
	private Rectangle countingHelper;
	private Point[] countingHelperPoints;
	
	public CollisionDetection() {
		this.countingHelper = new Rectangle();
		this.countingHelperPoints = new Point[4];
	}
	
	/*Az �tk�z�svizsg�l� met�dusok igaz �rt�kkel t�rnek vissza, ha a vizsg�lt elemek �tk�znek.*/
	@Override
	public boolean entityCollideTileWithPoints(Entity entity, Tile tile) {
		this.countingHelper = tile.getCollideArea();
		this.countingHelperPoints = entity.getCollideAreaPoints();
		
		if(tile.isHasCollision() && (countingHelper.contains(countingHelperPoints[0]) || countingHelper.contains(countingHelperPoints[1]) ||
				countingHelper.contains(countingHelperPoints[2]) || countingHelper.contains(countingHelperPoints[3]) )){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean entityCollideEntityWithPoints(Entity mover, Entity other) {
		this.countingHelper = other.getCollideArea();
		this.countingHelperPoints = mover.getCollideAreaPoints();
		
		if(countingHelper.contains(countingHelperPoints[0]) || countingHelper.contains(countingHelperPoints[1]) ||
				countingHelper.contains(countingHelperPoints[2]) || countingHelper.contains(countingHelperPoints[3])){
			return true;
		}
		return false;
	}
	
	/*Ez a met�dus eld�nti k�t polygonr�l,hogy mettszik-e egym�st.*/
	private boolean DoesPolygonIntersectPolygon(Polygon p1, Polygon p2) {
		Point p; 
		for(int i = 0; i < p2.npoints;i++) {
			p = new Point(p2.xpoints[i],p2.ypoints[i]); 
			if(p1.contains(p)) return true; 
		} 
		
		for(int i = 0; i < p1.npoints;i++) { 
			p = new Point(p1.xpoints[i],p1.ypoints[i]); 
			if(p2.contains(p))
				return true; 
			} 
		return false; 
	}

	@Override
	public boolean entityCollideEntityWithRectangle(Entity mover, Entity other) {
		if(mover.getCollideArea().intersects(other.getCollideArea())){
			return true;
		}
		return false;
	}

	@Override
	public boolean entityCollideEntityWithPolygon(Entity mover, Entity other) {
		if(DoesPolygonIntersectPolygon(mover.getCollideAreaPolygon(), other.getCollideAreaPolygon())){
			return true;
		}
		return false;
	}
}