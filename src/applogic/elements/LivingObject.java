package applogic.elements;

import java.awt.Rectangle;

import applogic.elements.controllers.ai.ElementDescriptionToAI;

public abstract class LivingObject extends BasicElement{
	/*Erre az absztrakci�ra az �leter�vel rendelkez� �p�letek miatt van sz�ks�g.*/

	private double health;
	private double maxhealth;
	
	private ElementDescriptionToAI elementToAI;
	
	public LivingObject(int x, int y, int width, int height, double angle,int angleCenterX,int angleCenterY,double health,double maxhealth) {
		super(x, y, width, height, angle,angleCenterX,angleCenterY);
		this.health = health;
		this.maxhealth = maxhealth;
		elementToAI = new ElementDescriptionToAI();
	}
	
	@Override
	public ElementDescriptionToAI createElementDescriptionToAI(Entity askerEntity) {
		
		elementToAI.setCollidedArea(getOperations().fogOfWarLocalLocation(askerEntity.getFogOfWar(), getCollideArea()));
		if(elementToAI.getCollidedArea() != null){
			elementToAI.setElementType("LivingObject");
			elementToAI.setEnemy(true);
			elementToAI.setHealth(getHealth());
			elementToAI.setMaxHealth(getMaxhealth());
			
			return this.elementToAI;
		}
		
		return null;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setMaxhealth(double maxhealth) {
		this.maxhealth = maxhealth;
	}
	
	public double getMaxhealth() {
		return maxhealth;
	}
}