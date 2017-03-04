package graphicsEngine;

import java.util.ArrayList;
import java.util.List;

/*Ez az oszt�ly t�rolja az anim�ci�kat.Minden k�p anim�ci�ba sorolhat�, ha egy k�p van, ami csak k�p,
 akkor az egy egy elem� Animation lesz.Image objektumokat tartalmaz, ami konkr�tan tartalmazza, hogy
 milyen t�pus� k�p(BufferedImage, stb...).*/

public class Animation {
	/*Egy animation az l�nyeg�ben egy lista, �s abban t�rolja az anim�ci� k�pkock�it.*/
	private List<Object> animation;
	
	public Animation() {
		animation = new ArrayList<Object>();
	}
	/*Egy k�p hozz�ad�sa az anim�ci�hoz.*/
	public void addAnimationSlice(Object image){
		animation.add(image);
	}
	/*Egy k�p hozz�ad�sa az anim�ci�hoz index alapj�n.Az index a lej�tsz�si sorrendben sz�m�t, �s a le�r�
	 xml-ben azt is megadj�k a k�phez, hogy hanyadik anim�ci�s k�pkocka, ez�rt tudnunk kell �gy besz�rni.*/
	public void removeAnimationSliceByIndex(int index){
		animation.remove(index);
	}
	
	public void addAnimationSliceByIndex(int index,Object image){
		animation.add(index, image);
	}
	
	public Object getAnimationSlice(int index){
		return animation.get(index);
	}
	
	public List<Object> getAnimation() {
		return animation;
	}
	
	public void setAnimation(List<Object> animation) {
		this.animation = animation;
	}
}