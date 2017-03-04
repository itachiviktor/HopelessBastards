package graphicsEngine;

public class ImageDescriptor {
	/*K�ple�r� objektum.L�nyege, hogy ez alapj�n el��ll�that� Animation objektum.Az xml docb�l
	 ezt �ll�t el� a SaxParserem.*/
	
	private String path;/*k�p filerendszerbeli helye*/
	private int column;/*Ha spriter�l van sz�, ami a nagyk�pen egy kicsi, akkor ez az, hogy hanyadik
	elem a sorban*/
	private int row;/*Ha spriter�l van sz�, ami a nagyk�pen egy kicsi, akkor ez az, hogy hanyadik sorban 
	kell keresni*/
	private int sheetwidth;/*sheet sz�less�ge*/
	private int sheetheight;/*sheet magass�ga*/
	private String logicName;/*A k�p logikai hivatkoz�si neve, az anim�ci�nak is ez a hivatkoz�si neve.*/
	private int animation;/*Egy sorsz�m, ami azt mutatja, hogy ez a k�p hanyadik elem az anim�ci�ban.
	Az indexel�s null�val kezd�dik.*/
	private boolean isSprite;/*Egy �rt�k, ami azt mutatja, hogy ez a k�p egy �n�ll� k�p, vagy egy 
	Sprite, ami egy nagyobb k�p r�sze.*/
	
	
	/*K�t konstruktort defini�ltam ehhez az oszt�lyhoz, mivel van sima teljes k�p, �s Sprite, �s 
	 �gy aut�matikusan kit�lt�m az �rt�keket a k�l�nb�z� esetben.*/
	public ImageDescriptor(String path, int column, int row, int sheetwidth,
			int sheetheight,String logicName,int animation, boolean isSprite) {
		
		/*Ha a k�p Sprite, akkor ezzel a konstruktorral kell l�trehozni ezt a le�r� objektumot, mivel minden
		 adattagnak �rt�ket kell kapnia.*/
		this.isSprite = isSprite;
		this.path = path;
		this.column = column;
		this.row = row;
		this.sheetwidth = sheetwidth;
		this.sheetheight = sheetheight;
		this.animation = animation;
		this.logicName = logicName;
	}
	public ImageDescriptor(String path,String logicName,int animation,boolean isSprite) {
		/*Ha egy k�pr�l van sz�, akkor azt nem kell v�gdosni, �s a Spritera vonatkoz� t�teleket ez
		 aut�matikusan kit�lti.*/
		this.isSprite = isSprite;
		this.path = path;
		this.column = -1;
		this.row = -1;
		this.sheetwidth = -1;
		this.sheetheight = -1;
		this.animation = animation;
		this.logicName = logicName;
	}
	/*Alul getterek �s setterek tal�lhat�ak.*/
	public int getAnimation() {
		return animation;
	}
	public void setAnimation(int animation) {
		this.animation = animation;
	}
	public boolean isSprite() {
		return isSprite;
	}
	public void setSprite(boolean isSprite) {
		this.isSprite = isSprite;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getColumn() {
		return column;
	}
	public int getRow() {
		return row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getSheetwidth() {
		return sheetwidth;
	}
	public void setSheetwidth(int sheetwidth) {
		this.sheetwidth = sheetwidth;
	}
	public int getSheetheight() {
		return sheetheight;
	}
	public void setSheetheight(int sheetheight) {
		this.sheetheight = sheetheight;
	}
	public String getLogicName() {
		return logicName;
	}
	public void setLogicName(String logicName) {
		this.logicName = logicName;
	}
	/*Test miatt hagytam benne.*/
	@Override
	public String toString() {
		return "Path:" + this.path  + " sheetx:" +this.column + " sheety:" + this.row +
				 " sheetwidth:" + this.sheetwidth + " sheetheight:" + this.sheetheight + 
				 " logicname:" + this.logicName + " animation:" + this.animation + 
				 " sprite:" + this.isSprite;
	}
}