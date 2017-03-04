package applogic.skills;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.viewbuilder.MageLightningViewBuilder;
import math.RotatePoints;
import soundapi.ISound;
import soundapi.ISoundProvider;
import soundapi.MP3;

public class MageLightning extends AbstractSkill{
		
	private ISound lightning;
	
	 private int damageValue = 100;/*Ennyi �leter�t vesz le arr�l,akit �r a t�mad�s*/
	 private int manacost = 100;/*Ennyi man�t vesz le a haszn�lata a t�mad�snak*/
	 
	 private double x, y, angle; // x,y and angle(sz�g)
	 private int width, height; //width and height 
	 
	 private Point[] collideAreaPoints;
	   
	 public MageLightning(Entity skillOwner,IEnvironment environment,IViewBuilderContainer container,int skillnumber,ISoundProvider soundProvider) {
		 super(skillOwner,environment,container,skillnumber);
		 setCdtime(2);
		 this.collideAreaPoints = new Point[4];
		 for(int i=0;i<collideAreaPoints.length;i++){
			 this.collideAreaPoints[i] = new Point();
		 }
		 
		 this.lightning = new MP3("lightning", soundProvider);
		 
		 setSkillViewBuilder(new MageLightningViewBuilder(this,container, skillOwner));
		 setWidth(512);
		 setHeight(300);
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
			this.lightning.play();
			
			/*A playern�l leveszem a man�t,amibe a skill ker�lt.*/
			if(getSkillOwner().getMana() - this.manacost < 0){
				getSkillOwner().setMana(0);
			}else{
				getSkillOwner().setMana(getSkillOwner().getMana()-this.manacost);
			}
			   
			/*be�ll�tom a param�terben kapott koordin�t�kat �s sz�get, ami a player saj�t helyzete is egyben.*/
			this.x = getSkillOwner().getX() + getSkillOwner().getWidth();
			this.y = getSkillOwner().getY() - 118;
			this.angle = getSkillOwner().getAngle();
			this.width = 512;
			this.height = 300;
			   
			 //animaionStillRuning = true;/*most m�r kirajzolhatjuk az anim�ci�t.*/
			
			 /*Ezzel �ll�tjuk be a playern�l, hogy ez a sorsz�m� k�pess�ge el lett tolva �s volt r�
			 elegend� man�ja is.*/
			 getSkillOwner().setSkillStarted(getSkillnumber(), true);
			   
			 setNetworkActivate(true);
			 //player.skill0started = true; 
		}
	}

	@Override
	public void activateSkillByServer(double appTime) {
		setSkillStartedMainTime(appTime);/*A skillkezd�si id�t be�ll�tom a j�t�k f�idej�re*/
		setIsactivated(true);/*akt�vnak tekintj�k innent�l a skillt*/
		
		setViewBuilderActivate(true);
		this.lightning.play();
		
		/*A playern�l leveszem a man�t,amibe a skill ker�lt.*/
		if(getSkillOwner().getMana() - this.manacost < 0){
	    	getSkillOwner().setMana(0);
	    }else{
	    	getSkillOwner().setMana(getSkillOwner().getMana()-this.manacost);
	    }
		
		 /*be�ll�tom a param�terben kapott koordin�t�kat �s sz�get, ami a player saj�t helyzete is egyben.*/
		this.x = getSkillOwner().getX() + getSkillOwner().getWidth();
		this.y = getSkillOwner().getY() - 118;
		this.angle = getSkillOwner().getAngle();
		this.width = 512;
		this.height = 300;
		
		getSkillOwner().setSkillStarted(getSkillnumber(), true);
	
		   
		getSkillOwner().setSkillStarted(getSkillnumber(), false);
		//player.skill0started = false;
	}

	@Override
	public void tick(double appTime) {
		/*A tick met�dus kisz�molja,hogy h�ny m�sodperc van a cd lej�r�s�ig,ez az�rt kell,hogy a MonitorScreenManager
		 kitudja �rni visszasz�ml�l�s form�j�ban minden m�sopercben.*/
		
		/*A tick met�dus�ban megvizsg�lom,hogy most sebez-e a skill.Ezt a v�ltoz�t az activateSkill met�dusban �ll�tom igazra.
	    V�gigmegy az �sszes entit�son, �s ellen�rzi,hogy melyik entit�snak van mettsz�spontja a vill�ml�si
	    ter�lettel(a bolt oszt�ly polygonja), �s azoknak az entit�soknak az �let�t cs�kkenti.*/
		
	   if(isIsactivated()){
		   
		   for(int i=0;i<getSkillOwner().getEnemyEntities().size();i++){
			   Entity en = getSkillOwner().getEnemyEntities().get(i);
			   if(getPolygon().intersects(en.getCollideArea())){
				   en.setHealth(-damageValue);
				   //player.handler.damagetext.add(new DamagingText(en.x, en.y,String.valueOf(damageValue),true, player.handler));
			   }
		   }
		   
		   for(int i = 0;i < getSkillOwner().getEnemyPlayers().size();i++){
			   Entity player = getSkillOwner().getEnemyPlayers().get(i);
			   if(getPolygon().intersects(player.getCollideArea())){
				   player.setHealth(-damageValue);
			   }
			   
		   }
		   
		 /*Mivel csak egyszer szeretn�nk,hogy sebezzen a skill,nem pedig m�sodpercenk�nt 60-szor, ez�rt r�gt�n az els� haszn�lat
		 ut�n hamisra �ll�tjuk a damagingNow v�ltoz�t, �gy a sebz�s nem fog megt�rt�nni t�bbet, csak ha �jra elnyomjuk a skillt
		 teh�t ez a skill egyszeri sebz�s,nem olyan,hogy m�sodpercenk�nt sebez.*/
		//isactivated = false;
		   
		setIsactivated(false);
		getSkillOwner().setSkillStarted(getSkillnumber(), false);
	   }
	}
	
	 public Polygon getPolygon(){
		/*Visszaadja egy bizonyos sz�ggel elforgatott polygont.Ez a polygon vizsg�lja,hogy van-e mettsz�spontja vmelyik
		entit�ssal,mert akkor sebz�s az mehet.*/
		this.collideAreaPoints[0].setLocation((int)x,(int)y);
		this.collideAreaPoints[1].setLocation((int)x + width,(int)y);
		this.collideAreaPoints[2].setLocation((int)x+width,(int)y+height);
		this.collideAreaPoints[3].setLocation((int)x,(int)y+height);
		return RotatePoints.rotate(collideAreaPoints[0],collideAreaPoints[1],collideAreaPoints[2],
				  collideAreaPoints[3],angle,(int)(getSkillOwner().getX() + getSkillOwner().getWidth()/2), (int)(getSkillOwner().getY() + getSkillOwner().getHeight()/2));
	   }

	@Override
	public Rectangle getBounds() {
		return null;
	}
}