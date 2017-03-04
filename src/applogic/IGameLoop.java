package applogic;

public interface IGameLoop {
	/*Egy gameloopnak olyannak kell lennie, hogy valemilyen algoritmus alkalmaz�s�ban,h�vogatnia kell
	 a tick �s a render met�dusokat.*/
	public void tick(double appTime);
	public void render(double renderTime);
}
