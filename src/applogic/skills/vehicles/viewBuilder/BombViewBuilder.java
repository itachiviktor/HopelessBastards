package applogic.skills.vehicles.viewBuilder;

import applogic.AnimationHandler;
import applogic.IAnimationHandler;
import applogic.elements.BasicElement;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.descriptors.ImageDescriptor;

public class BombViewBuilder extends IImageViewBuilder{

	private ImageDescriptor[] describers;
	private IAnimationHandler animationHandler;
	
	public BombViewBuilder(int x, int y, int width, int height) {
		this.describers = new ImageDescriptor[1];
		this.describers[0] = new ImageDescriptor(x, y,0,0,0,width, height,"bomb",0);
		animationHandler = new AnimationHandler(30, 9,false);
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		/*Itt az a l�nyeg, hogy amikor elt�ntetj�k a bomb�t, sz�l a Bomb komponens, hogy 
		 belefutottak �s sz�nj�n meg a k�p, akkor m�g egy robban�s anim�ci�t is letolunk,
		 teh�t itt fel�ldefini�ljuk a met�dust, teh�t nem �ll�tjuk be a deletablet truera,
		 hanem csak be�ll�tjuk, hogy a robban�s anim�ci�t p�rgesse le, majd mikor annak v�ge
		 , akkor h�vjuk meg az �soszt�lyban defini�lt deletable-t truera �ll�t�t,
		 �s akkor m�r t�nyleg megsz�ntethet� ez a k�ple�r�.*/
		this.describers[0].setImageLogicalName("bombtrapexplosion");
		animationHandler.setAnimationable(true);/*Be�ll�tjuk, hogy az anim�ci� elkezd�dhet.*/
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		if(this.describers[0].getImageLogicalName().equals("bombtrapexplosion")){
			this.describers[0].setAnimation(animationHandler.animationPiece());
			if(!animationHandler.getAnimationable()){/*Ha v�ge van az anim�ci�nak,
			akkor a deletablet be�ll�thatjuk v�gre truera, �s a garbageCollector kis�p�rheti
			a mem�ri�b�l.*/
				super.setDeletable(true);
			}
		}
		return this.describers;
	}

	@Override
	public BasicElement getTheRepresentetedElement() {
		return null;
	}
}