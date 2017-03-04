package graphicsEngine;

public abstract class CanvasCommand {
	/*Erre az absztrakci�ra az�rt van sz�ks�g, hogy a Canvasnak �tadhassunk a kamer�t is mint kirajzoland�
	 elem, ami majd megkapva a v�szon kontextust csin�l azzaql valamit.Teh�t a RenderableObject
	 absztrakci� is ebb�l sz�rmazik.*/
	private int x;
	private int y;
	
	public CanvasCommand(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public abstract void render(Object graphicContext);
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}	
}