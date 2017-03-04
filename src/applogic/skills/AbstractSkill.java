package applogic.skills;

import java.awt.Polygon;
import java.awt.Rectangle;
import applogic.IViewBuilderContainer;
import applogic.elements.Entity;
import applogic.elements.controllers.IEnvironment;
import applogic.viewbuilder.IImageViewBuilder;
import soundapi.ISoundProvider;

public abstract class AbstractSkill {
	/*Ez az oszt�ly arra szolg�l,hogy defini�ljon k�z�s elemeket, ami minden skillben benne kell,hogy legyen.
	 Ez az oszt�ly ez�rt is j�, mert a skill referencia v�ltoz�knak ez lesz a statikus t�pusa,�gy a 7 skillt egy 7 elem�
	 AbstractSkill t�mbben fogom t�rolni.*/

	private Entity skillOwner;/*Minden skill ismeri az entit�st,ami haszn�lja �t*/
	
	private IImageViewBuilder skillViewBuilder;
	
	private double skillStartedMainTime = 0;/*Ezt null�ra �ll�tom,ez az az id�, ami a skill kezd�si ideje lesz*/
	
	private int timeuntilcdend;/*Ebbe az id�t t�rolom m�sodpercben,ami m�g h�travan a cd lej�r�s�ig,azaz am�g �jra
	haszn�lhatok egy k�pess�get*/
	
	private int cdtime;/*cd ideje m�sodpercben*/
	
	private int secWhileActive;/*Egy skill ak�r hosszabb ideig is akt�v lehet pl.buff, de a l�v�s csak adott pillanatban
	akt�v*/
	
	private boolean deletable;/*A skilleket is lehet t�r�lni, ugyeb�r ha egy entit�s meghal,
	akkor a neki l�trehozott k�pess�geket is t�r�lni kell.*/
	
	private boolean isactivated = false;/*aktiv�lva van-e a skill, ez az�rt fontos , mert a player objektumban
	minden skillre van egy objektum, �s ez a v�ltoz� jelzi,hogy a skill m�k�dik-e vagy sem.*/
	
	// Nem biztos, hogy sz�ks�g lesz r�public boolean animaionStillRuning = false;/*Ez a v�ltoz� am�g igaz, addig kell kirajzolni a k�pess�ghez tartoz� anim�ci�t.*/
	
	private int skillnumber;/*Minden skillnek tudnia kell mag�r�l, hogy hanyadik skill.*/
	
	private IEnvironment environment;
	
	private IViewBuilderContainer container;
	
	private boolean networkActivate;
	
	private int width;
	private int height;
	
	private boolean viewBuilderActivate;/*Ez jelzi, hogy aktiv�lni kell-e a skillhez tartoz� k�pi
	anim�ci�t vagy sem.*/
	
	public AbstractSkill(Entity skillOwner,IEnvironment environment,IViewBuilderContainer container,int skillnumber) {
		this.skillOwner = skillOwner;
		this.environment = environment;
		this.skillnumber = skillnumber;
		this.container = container;
	}
	
	public abstract void activateSkill(double appTime);/*Minden skillnek kell lennie egy aktiv�l� met�dusnak*/
	
	public abstract void activateSkillByServer(double appTime);
	
	public abstract void tick(double appTime);
	
	public abstract Polygon getPolygon();
	
	public abstract Rectangle getBounds();/*Minden skill vissza kell tudjon adni egy k�r�lvev� t�glalapot,de pl van aminek nincs
	az egyszer�en nullt ad vissza, pl a gyors fut�snak nincs anim�ci�ja, sem ter�leti sebz�se, az csak null returnnel oldja meg.*/

	public void setDeletebale(boolean deletable){
		this.deletable = deletable;
		this.skillViewBuilder.setDeletable(deletable);
	}
	
	public boolean isDeletable(){
		return this.deletable;
		
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getSkillStartedMainTime() {
		return skillStartedMainTime;
	}

	public void setSkillStartedMainTime(double skillStartedMainTime) {
		this.skillStartedMainTime = skillStartedMainTime;
	}

	public IViewBuilderContainer getContainer() {
		return container;
	}

	public void setContainer(IViewBuilderContainer container) {
		this.container = container;
	}

	public IImageViewBuilder getSkillViewBuilder() {
		return skillViewBuilder;
	}

	public void setSkillViewBuilder(IImageViewBuilder skillViewBuilder) {
		this.skillViewBuilder = skillViewBuilder;
	}

	public void setSkillStartedMainTime(int skillStartedMainTime) {
		this.skillStartedMainTime = skillStartedMainTime;
	}

	public int getTimeuntilcdend() {
		return timeuntilcdend;
	}

	public void setTimeuntilcdend(int timeuntilcdend) {
		this.timeuntilcdend = timeuntilcdend;
	}

	public int getCdtime() {
		return cdtime;
	}

	public void setCdtime(int cdtime) {
		this.cdtime = cdtime;
	}

	public int getSecWhileActive() {
		return secWhileActive;
	}

	public void setSecWhileActive(int secWhileActive) {
		this.secWhileActive = secWhileActive;
	}

	public boolean isIsactivated() {
		return isactivated;
	}

	public void setIsactivated(boolean isactivated) {
		this.isactivated = isactivated;
	}

	public int getSkillnumber() {
		return skillnumber;
	}

	public void setSkillnumber(int skillnumber) {
		this.skillnumber = skillnumber;
	}

	public Entity getSkillOwner() {
		return skillOwner;
	}

	public void setSkillOwner(Entity skillOwner) {
		this.skillOwner = skillOwner;
	}

	

	public IEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	public boolean isViewBuilderActivate() {
		return viewBuilderActivate;
	}

	public void setViewBuilderActivate(boolean viewBuilderActivate) {
		this.viewBuilderActivate = viewBuilderActivate;
	}

	public boolean isNetworkActivate() {
		return networkActivate;
	}

	public void setNetworkActivate(boolean networkActivate) {
		this.networkActivate = networkActivate;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
}