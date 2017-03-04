package applogic.components;

import java.util.ArrayList;
import java.util.List;

import controller.events.KeyValue;

public abstract class IComponent {
	/*Ez minden komponens �se(gombok�,beviteli sz�vegdobozok�,stb).*/
	private int x;
	private int y;
	private int width;
	private int height;
	
	private boolean hovered;/*Tudja mag�r�l minden komponens, hogy most �pp felette van-e az eg�r.*/
	private boolean deletable;/*A GarbageCollector sz�m�ra nagyon fontos inform�ci�.Ha el kell t�ntetni
	ezt a komponenst, akkor ennek az adattagnak az �rt�ke true;*/
	
	private List<ActionListener> actionListeners;/*A komponens esem�nyeire feliratkoz�
	Listenerek, amelyek esem�nyt kezelnek.*/
	
	private IContainer container;
	
	private IFocus focus;/*Ismernie kell a focust.*/
	
	private boolean showNow;/*Megjelenik-e a k�perny�n vagy sem.Ezt legink�bb az optionPane
	miatt kellett bevezetni.*/
	
	public IComponent(int x, int y,int width,int height,IContainer container) {
		super();
		this.container = container;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.actionListeners = new ArrayList<ActionListener>();
		this.focus = container.getFocus();
	}
	
	public abstract void keyhandle(KeyValue value);
	
	public abstract String toStringTransformation();/*Minden komponens reprezent�l valami
	Stringek, vagy tartalmaz feliratot, vagy sz�veget jelen�t meg.Ezzel a met�dussal k�rhetj�k
	el t�le.Olyan funkci�ja van mint a toStringnek.*/
	
	/*Minden komponens tud hangot kiadni, �s k�v�lr�l ezen met�duson kereszt�l k�nyszer�thetj�k
	 hang lej�tsz�s�ra.*/
	public abstract void playSoundEffect();
	
	/*Ezzel a met�dussal �ll�t be focusbirtokost a komponens.Itt mag�t �s m�st is be�ll�that.*/
	public void setFocusOwner(IComponent component){
		focus.setFocusOwnerComponent(component);
	}
	
	public IFocus getFocus() {
		return focus;
	}
	
	public abstract void tick();
	
	
	public boolean isShowNow() {
		return showNow;
	}

	public void setShowNow(boolean showNow) {
		this.showNow = showNow;
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

	public IContainer getContainer() {
		return container;
	}

	public void setContainer(IContainer container) {
		this.container = container;
	}

	public boolean isHovered() {
		return hovered;
	}

	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}

	public void addActionListener(ActionListener listener){
		actionListeners.add(listener);
	}
	
	public void removeActionListener(ActionListener listener){
		actionListeners.remove(listener);
	}
	
	public boolean isDeletable() {
		return deletable;
	}
	
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public List<ActionListener> getActionListeners() {
		return actionListeners;
	}

	public void setActionListeners(List<ActionListener> actionListeners) {
		this.actionListeners = actionListeners;
	}
}