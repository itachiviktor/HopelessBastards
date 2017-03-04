package screenconverter.descriptors;

import java.awt.Color;

public class LineDescriptor extends DrawObjectDescriptor{
	private int x2;/*Egy vonal 2 pont �sszek�t�s�b�l �ll.Ez a k�t extra adattag a m�sik v�gpont
	le�r�ja l�nyeg�ben.*/
	private int y2;
	
	public LineDescriptor(int x, int y, double angle, int angleCenterPointX, int angleCenterPointY,
			int x2, int y2,Color color) {
		super(x, y, angle, angleCenterPointX, angleCenterPointY,color,false);
		this.x2 = x2;
		this.y2 = y2;
	}

	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	public int getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}
}