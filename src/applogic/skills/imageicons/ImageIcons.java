package applogic.skills.imageicons;

import java.awt.Point;

import applogic.elements.BasicElement;
import applogic.elements.Entity;
import applogic.skills.AbstractSkill;
import applogic.viewbuilder.IImageViewBuilder;
import screenconverter.IMonitorScreenManager;
import screenconverter.descriptors.ImageDescriptor;

public class ImageIcons extends IImageViewBuilder{
	/*Ez az oszt�ly a skillbarban l�v� kis k�pet �rja le.*/
	
	private IMonitorScreenManager monitorScreenManager;
	
	/*Ismernie kell a skillt amit k�pvisel.*/
	private AbstractSkill skill;
	
	/*Ez a location pontja.*/
	private Point skillPoint;
	
	/*A player aki haszn�lja*/
	private Entity player;
	
	/*Kis skillk�pekn�l ez mindig egy elem� t�mb, hisz csak egy kis 52x52-es k�pet kell el��ll�tania.*/
	private ImageDescriptor[] describers;
	
	private String logicalName;
	
	public ImageIcons(AbstractSkill skill,IMonitorScreenManager monitorScreenManager,Entity player,String logicalName) {
		this.monitorScreenManager = monitorScreenManager;
		this.logicalName = logicalName;
		describers = new ImageDescriptor[2];
		describers[0] = new ImageDescriptor(10, 10,0,0,0,52,52,this.logicalName, 0);
		describers[1] = new ImageDescriptor(10, 10,0,0,0,52,52,"skillwaiting", 0);
		this.skillPoint = new Point();
		
		this.skill = skill;
		this.player = player;
	}
	
	public void setLogicName(String logicName){
		this.logicalName = logicName;
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		/*A skill.getSkillnumber() visszaadja,hogy ez a skill hanyadik skillje az �t tartalmaz� playernek,
		 �s mi ez alapj�n rakjuk be a skillbarba.*/
		
		if(player.isControlledByPlayer()){
			this.skillPoint = this.monitorScreenManager.getSkillImageLocation(skill.getSkillnumber(),(int)player.getX() + player.getWidth()/2,(int)player.getY() + player.getHeight()/2);
			describers[0].setX(skillPoint.x);
			describers[0].setY(skillPoint.y);
			/*Ez itt a cd skillk�ps�t�t�t�vel foglalkozik.*/
			if(skill.getSkillStartedMainTime() + skill.getCdtime() >= player.getAppTime()){
				describers[1].setX(skillPoint.x);
				describers[1].setY(skillPoint.y);
			}else{
				describers[1].setX(200000);
				describers[1].setY(200000);
			}
		}else{
			describers[0].setX(-20);
			describers[0].setY(-20);
			describers[1].setX(200000);
			describers[1].setY(200000);
		}
		
		describers[0].setImageLogicalName(this.logicalName);
		
		return this.describers;
	}

	@Override
	public BasicElement getTheRepresentetedElement() {
		return null;
	}
}