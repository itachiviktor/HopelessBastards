package screenconverter;

public class Size {
	/*Saj�t m�ret oszt�ly, l�nyege hogy egys�gbe foglalja a sz�less�g �s a magass�g tulajdons�gokat.*/
	private int width;
	private int height;
	
	public Size() {
		/*Param�ter n�lk�li konstruktor.*/
	}
	
	public Size(int width,int height){
		this.width = width;
		this.height = height;
	}

	public int getWIDTH() {
		return this.width;
	}

	public void setWIDTH(int width) {
		this.width = width;
	}

	public int getHEIGHT() {
		return this.height;
	}

	public void setHEIGHT(int height) {
		this.height = height;
	}	
}