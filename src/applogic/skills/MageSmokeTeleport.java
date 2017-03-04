package applogic.skills;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.viewbuilder.MageSmokeTeleportViewBuilder;
import math.RotatePoints;

public class MageSmokeTeleport extends AbstractSkill{

	private int oldPlayerX;/*a player eredeti koordin�t�ja,ahonnan elteleport�l l�nyeg�ben*/
	private int oldPlayerY;
	private Point behing500px;
	
	public MageSmokeTeleport(Entity skillOwner, IEnvironment environment, IViewBuilderContainer container,
			int skillnumber) {
		super(skillOwner, environment, container, skillnumber);
		
		setCdtime(3);
		
		setWidth(128);
		setHeight(128);
		
		setSkillViewBuilder(new MageSmokeTeleportViewBuilder(this, container, skillOwner));
	}

	@Override
	public void activateSkill(double appTime) {
		if((getSkillStartedMainTime() + this.getCdtime() < appTime || getSkillStartedMainTime() == 0)){
			setSkillStartedMainTime(appTime);/*A skillkezd�si id�t be�ll�tom a j�t�k f�idej�re*/
			setIsactivated(true);/*akt�vnak tekintj�k innent�l a skillt*/
			
			
			setViewBuilderActivate(true);

			this.oldPlayerX = (int)getSkillOwner().getX();/*player alap koordin�t�j�nak be�ll�t�sa,ahonnan elteleport�l*/
			this.oldPlayerY = (int)getSkillOwner().getY();
			

			/*Itt kisz�moljuk azt a pontot,ami a player k�zep�t�l 500 pixellel m�g�tte van.
			 Az a l�nyeg,hogy alaphelyzetben a player jobboldalra n�z,teh�t a m�g�tte l�v� pontot �gy kapom meg,
			 hogy az x koordin�t�j�b�l kivonok 500-at.Majd ezt a pontot annyival forgatom a player k�z�ppontja k�r�l,
			 ah�ny fokkal el van fordulva a player, �s k�sz, megvan a pontosan m�g�tte l�v� pont.*/
			behing500px = RotatePoints.rotatePoint(new Point((int)getSkillOwner().getX() + getSkillOwner().getWidth()/2-300, (int)getSkillOwner().getY() + getSkillOwner().getHeight()/2), getSkillOwner().getAngle(), (int)getSkillOwner().getX() + getSkillOwner().getWidth()/2,(int)getSkillOwner().getY() + getSkillOwner().getHeight()/2);
			
			/*Az �j hely koordin�t�inak meghat�roz�sa.*/
			getSkillOwner().setX(behing500px.x - getSkillOwner().getWidth()/2);
			getSkillOwner().setY(behing500px.y - getSkillOwner().getHeight()/2);
			/*player.x = behing500px.x - player.width/2;
			player.y = behing500px.y - player.height/2;*/
	
			getSkillOwner().setSkillStarted(getSkillnumber(), true);
			
			setNetworkActivate(true);
		}
	}

	@Override
	public void activateSkillByServer(double appTime) {
		setSkillStartedMainTime(appTime);/*A skillkezd�si id�t be�ll�tom a j�t�k f�idej�re*/
		setIsactivated(true);/*akt�vnak tekintj�k innent�l a skillt*/
		
		setViewBuilderActivate(true);
		
		
		this.oldPlayerX = (int)getSkillOwner().getX();/*player alap koordin�t�j�nak be�ll�t�sa,ahonnan elteleport�l*/
		this.oldPlayerY = (int)getSkillOwner().getY();
		

		/*Itt kisz�moljuk azt a pontot,ami a player k�zep�t�l 500 pixellel m�g�tte van.
		 Az a l�nyeg,hogy alaphelyzetben a player jobboldalra n�z,teh�t a m�g�tte l�v� pontot �gy kapom meg,
		 hogy az x koordin�t�j�b�l kivonok 500-at.Majd ezt a pontot annyival forgatom a player k�z�ppontja k�r�l,
		 ah�ny fokkal el van fordulva a player, �s k�sz, megvan a pontosan m�g�tte l�v� pont.*/
		behing500px = RotatePoints.rotatePoint(new Point((int)getSkillOwner().getX() + getSkillOwner().getWidth()/2-300, (int)getSkillOwner().getY() + getSkillOwner().getHeight()/2), getSkillOwner().getAngle(), (int)getSkillOwner().getX() + getSkillOwner().getWidth()/2,(int)getSkillOwner().getY() + getSkillOwner().getHeight()/2);
		
		/*Az �j hely koordin�t�inak meghat�roz�sa.*/
		getSkillOwner().setX(behing500px.x - getSkillOwner().getWidth()/2);
		getSkillOwner().setY(behing500px.y - getSkillOwner().getHeight()/2);
		
		
		
		getSkillOwner().setSkillStarted(getSkillnumber(), true);
	
		   
		getSkillOwner().setSkillStarted(getSkillnumber(), false);
		//player.skill0started = false;
	}

	@Override
	public void tick(double appTime) {
		if(isIsactivated()){   
			
			
			setIsactivated(false);
			getSkillOwner().setSkillStarted(getSkillnumber(), false);
		}
	}

	@Override
	public Polygon getPolygon() {
		return null;
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	public int getOldPlayerX() {
		return oldPlayerX;
	}

	public int getOldPlayerY() {
		return oldPlayerY;
	}
	
}