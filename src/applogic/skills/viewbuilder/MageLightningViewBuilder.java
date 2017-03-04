package applogic.skills.viewbuilder;

import applogic.AnimationHandler;
import applogic.IAnimationHandler;
import applogic.IViewBuilderContainer;
import applogic.elements.BasicElement;
import applogic.elements.Entity;
import applogic.skills.AbstractSkill;
import applogic.skills.imageicons.ImageIcons;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.simpleshapes.SimplePolygon;
import screenconverter.descriptors.ImageDescriptor;

public class MageLightningViewBuilder extends IImageViewBuilder{

	private AbstractSkill skill;
	private ImageDescriptor[] describers;
	/*Ett�l k�ri el az anim�ci� k�vetkez� k�pdarabk�j�t.*/
	private IAnimationHandler animationHandler;

	private Entity player;
	
	
	private IViewBuilderContainer container;
	private SimplePolygon polygon;
	
	private ImageIcons icon;
	
	public MageLightningViewBuilder(AbstractSkill skill,IViewBuilderContainer container,Entity player) {
		
		this.icon = new ImageIcons(skill, container.getMonitorScreenManager(), player,"magelight");
		this.container = container;
		
		this.skill = skill;
		describers = new ImageDescriptor[2];
		
		describers[0] = new ImageDescriptor(10, 10,0,0,0,skill.getWidth(),skill.getHeight(),"bolt", 0);
		describers[1] = new ImageDescriptor(10, 10,0,0,0,52,52,"magelight", 0);
		animationHandler = new AnimationHandler(30, 10,false);
		
		this.player = player;
		
		polygon = new SimplePolygon(skill);
		
		this.container.getPolygonBuilder().add(polygon);
		
		this.container.getViewBuilder().add(this);
		this.container.getStaticviewBuilder().add(this.icon);
	
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		this.icon.setDeletable(deletable);
	}
	
	@Override
	public ImageDescriptor[] getImageDescriptor() {
		/*A skill anim�ci� kezel�kn�l az a l�nyeg, ha a skill be�ll�tja, hogy elkezd�dhet az anim�ci�
		 kirajzol�sa, akkor be�ll�tjuk, hogy az animationHanlder�nk mostm�r anim�lhat, �s a skill 
		 viewBuilderActivate booleanj�t hamisra �ll�tjuk, mivel m�r egyszer elkezdt�k a kirajzol�st,
		 �s azt elkezdeni csak egyszer kell.*/
		if(skill.isViewBuilderActivate()){
			animationHandler.setAnimationable(true);
			skill.setViewBuilderActivate(false);
		}
		
		/*Erre a blokkra csak akkor fut r�, ha az animationHandler animatoanable boleanja true,
		 ekkor ad k�vetkez� k�pkock�t az animation handler*/
		if(animationHandler.getAnimationable()){
			
			describers[0].setX((int)player.getX() + player.getWidth());
			describers[0].setY((int)player.getY()-118);
			describers[0].setAngle(player.getAngle());
			describers[0].setAngleCenterPointX((int)player.getX() + player.getWidth()/2);
			describers[0].setAngleCenterPointY((int)player.getY() + player.getHeight()/2);
			describers[0].setImageLogicalName("bolt");
			describers[0].setAnimation(animationHandler.animationPiece());
			describers[0].setWidth(this.skill.getWidth());
			describers[0].setHeight(this.skill.getHeight());
		}else{
			/*Egy�bk�nt ,hogy ne a k�perny�re rajzolja a skill egyik anim�ci�s k�pkock�j�t,
			 ez�rt a skill kordini�t�it, ha �pp nincs sz�ks�g a kirajzol�s�ra, akkor be�ll�tjuk 
			 irr�lis �rt�kekre, kirajzolni �gy �gysem foglya semmi, hisz sosem lesz benne a k�perny�ben,
			 erre a Converter komponens a garancia.*/
			describers[0].setX(200000);
			describers[0].setY(-20000);
		}					
		
		return this.describers;
	}

	@Override
	public BasicElement getTheRepresentetedElement() {
		return null;
	}
}