package applogic.elements;

import java.awt.Rectangle;
import java.util.Random;

import applogic.IViewBuilderContainer;
import applogic.elements.controllers.ai.ElementDescriptionToAI;
import applogic.loginstate.LeafViewBuilder;

public class Leaf extends BasicElement{
	/*A bejelentkez� fel�leten l�v� levelek reprezent�l�ja.*/	
	private IViewBuilderContainer container;
	private Random random;
	private int speedx;
	private int speedy;
	
	public Leaf(int x, int y, int width, int height, double angle, int angleCenterX, int angleCenterY,IViewBuilderContainer container) {
		super(x, y, width, height, angle, angleCenterX, angleCenterY);
		this.container = container;
		
		this.random = new Random();
		/*V�letlenszer�en sz�m�t�dik az es�si sebess�g�k.*/
		this.speedx = random.nextInt(20) + 1;
		this.speedy = random.nextInt(20) + 1;
		
		this.container.getStaticviewBuilder().add(new LeafViewBuilder(this));
	}

	@Override
	public void tick(double appTime) {
		/*Ha kiesik a k�perny� ter�let�r�l a lev�l, akkor �jra fentr�l foglyuk ledobni.*/
		if(getX() > 2000 || getY() > 1500){
			this.speedx = random.nextInt(5) + 1;
			this.speedy = random.nextInt(5) + 1;
			setX(random.nextInt(200));
			setY(0);
		}else{
			setX(getX() + this.speedx);
			setY(getY() + this.speedy);
		}
	}

	@Override
	public ElementDescriptionToAI createElementDescriptionToAI(Entity askerEntity) {
		return null;
	}
}