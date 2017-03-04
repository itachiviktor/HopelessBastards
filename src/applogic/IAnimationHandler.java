package applogic;

public interface IAnimationHandler {
	/*Egy animationHandlernek vissza kell tudni, adnia, hogy melyik darabja j�n az anim�ci�nak.*/
	public int animationPiece();
	
	/*Vissza kell tudni adnia, hogy ez kianim�lhat�-e*/
	public boolean getAnimationable();
	
	/*Illetve biztos�tanie kell szolg�ltat�st, hogy �ll�that�nak kell legyen, hogy
	 anim�ljon-e vagy sem.*/
	public void setAnimationable(boolean animationable);
	
	public String getLogicName();
}
