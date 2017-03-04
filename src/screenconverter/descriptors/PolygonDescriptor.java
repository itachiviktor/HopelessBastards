package screenconverter.descriptors;

import java.awt.Color;
import java.awt.Polygon;

public class PolygonDescriptor extends DrawObjectDescriptor{
	private Polygon polygon;/*itt lehet m�s t�rol�si eszk�z kellene a rugalmass�g v�gett.*/
	/*Ez m�g nincs k�sz*/
	
	public PolygonDescriptor(int x, int y, double angle, int angleCenterPointX, int angleCenterPointY,
			Color color,boolean draw,Polygon polygon) {
		super(x, y, angle, angleCenterPointX, angleCenterPointY,color,draw);
		this.polygon = polygon;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}
	
	
}
