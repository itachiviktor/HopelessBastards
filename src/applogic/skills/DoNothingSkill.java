package applogic.skills;

import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;

public class DoNothingSkill extends AbstractSkill{

	public DoNothingSkill(Entity skillOwner, IEnvironment environment, IViewBuilderContainer container,
			int skillnumber) {
		super(skillOwner, environment, container, skillnumber);
		
	}

	@Override
	public void activateSkill(double appTime) {
		/*Csak akkor aktiv�l�dik a  skill, ha a jelenlegi j�t�kid� nagyobb,mint a skillkezd�s �s a skillcd id�
		 �sszeadva, azaz ha lej�rt a cd,csak akkor aktiv�lhatom �jra a skillt.Illetve hs a skillstartedtime == 0, az azt jelenti
		 hogy mi�ta megy a j�t�k,azaz inicializ�lva lett a skill, az�ta m�g nem volt haszn�lva,teh�t cd sem lehet rajta.*/
		if(getSkillStartedMainTime() + this.getCdtime() < appTime || getSkillStartedMainTime() == 0){
			setSkillStartedMainTime(appTime);/*A skillkezd�si id�t be�ll�tom a j�t�k f�idej�re*/
			setIsactivated(true);/*akt�vnak tekintj�k innent�l a skillt*/
			//player.monitorScreenmanager.skill0useable = false;/*A skillbaron elsz�rk�tj�k a k�pet, ami a skillt k�pviseli*/
			
			
			setViewBuilderActivate(true);
		//	boltSong.play();
			
			   
			  
			   
			   
			
			   //animaionStillRuning = true;/*most m�r kirajzolhatjuk az anim�ci�t.*/
			
			   /*Ezzel �ll�tjuk be a playern�l, hogy ez a sorsz�m� k�pess�ge el lett tolva �s volt r�
			    elegend� man�ja is.*/
			   getSkillOwner().setSkillStarted(getSkillnumber(), true);
			   //player.skill0started = true;
			   
		}
		
	}

	@Override
	public void activateSkillByServer(double appTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(double appTime) {
		 if(isIsactivated()){

			   getSkillOwner().setX(getSkillOwner().getX() + 100);
			   
			
			   
			setIsactivated(false);
			getSkillOwner().setSkillStarted(getSkillnumber(), false);
		   }
		
	}

	@Override
	public Polygon getPolygon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
