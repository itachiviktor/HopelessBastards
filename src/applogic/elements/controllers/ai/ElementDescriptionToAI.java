package applogic.elements.controllers.ai;

import java.awt.Rectangle;

public class ElementDescriptionToAI {
	/*Ilyen objektumokat kap az �gens inputk�nt a vil�gr�l.Minden elem el� tud �ll�tani mag�b�l
	 egy ilyet.*/
	
	private Rectangle collidedArea;
	private String elementType;
	private double health;
	private double maxHealth;
	
	/*Itt az�rt power, mert csak az sz�m�t, hogy mennyi van az er�b�l, nem az, hogy mana, vagy rage.
	 ez egy �tlagsz�m�t�shoz kell majd.*/
	private double power;
	private double maxPower;
	
	private boolean isEnemy;/*Ez ellens�g-e a k�r� entit�s sz�m�ra.Teh�t ha ellens�ges zombie k�rdi
	a maget, hogy ellens�f-e neki, akkor ezt true-ra �ll�tja.*/
	
	public ElementDescriptionToAI() {
		
	}
	
	public ElementDescriptionToAI(Rectangle collidedArea, String elementType, int health, int maxHealth, int power,
			int maxPower) {
		super();
		this.collidedArea = collidedArea;
		this.elementType = elementType;
		this.health = health;
		this.maxHealth = maxHealth;
		this.power = power;
		this.maxPower = maxPower;
	}

	public Rectangle getCollidedArea() {
		return collidedArea;
	}

	public void setCollidedArea(Rectangle collidedArea) {
		this.collidedArea = collidedArea;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public double getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(double maxPower) {
		this.maxPower = maxPower;
	}

	public boolean isEnemy() {
		return isEnemy;
	}

	public void setEnemy(boolean isEnemy) {
		this.isEnemy = isEnemy;
	}

	
	
}