package applogic;

public class AnimationHandler implements IAnimationHandler{

	private int frameChangeSpeed;/*Ha 60 fps a j�t�k, akkor ha ennek az �rt�ke 4, akkor 4 fps-enk�nt fog
	anim�ci�s k�pkock�t cser�lni.*/
	private int frame;
	private int framePerSec;
	private int frameLimit;/*Ez adja meg, hogy h�ny k�pb�l �ll az anim�ci�.*/
	private int returnValue;/*Ez a visszat�r� �rt�k, ami azt adja, hogy az anim�ci�nak �ppen
	hanyadik elem�t kell visszaadni.*/
	
	private boolean animationAble;/*Ki kell e rajzolni az anim�ci�t vagy sem.*/
	private boolean infinityAnimation;/*Ez a v�ltoz� igaz, ha az anim�ci�nak t�bb mint egyszer kell 
	lep�r�gnie, ilyen p�ld�ul a mage anim�ci�ja, hogy folyamatosan mozog.Viszont ha ez false,
	akkor azt jelenti, hogy az anim�ci�t egyszer kell lej�ttszani, �ilyen p�ld�ul a vill�ml�s t�mad�sa
	 a magenak.*/
	
	private String logicname;/*Az anim�ci� k�panyag�nak logikai el�r�s�nek neve,*/
	
	public AnimationHandler(int frameChangeSpeed,int frameLimit,boolean infinityAnimation) {
		this.frameChangeSpeed = frameChangeSpeed;/*4*/
		this.frameLimit = frameLimit;/*anim�ci� m�rete*/
		this.infinityAnimation = infinityAnimation;
	}
	
	public AnimationHandler(int frameChangeSpeed,int frameLimit,boolean infinityAnimation,String logicname) {
		this.frameChangeSpeed = frameChangeSpeed;/*4*/
		this.frameLimit = frameLimit;/*anim�ci� m�rete*/
		this.infinityAnimation = infinityAnimation;
		this.logicname=logicname;
	}
	
	/*Ez a met�dus arra j�, hogy mindig visszaadja, a k�vetkez� anim�ci�s sorsz�mot, teh�t
	 kisz�molja, hogy kell e a k�vetkez� anim�ci�s elem, vagy m�g maradjon amit eddig rajzolt.*/
	@Override
	public int animationPiece() {
		
		if(framePerSec < frameChangeSpeed){
			if(frame < frameLimit){
				this.returnValue = frame;
			}
			framePerSec++;
		}else{
		  /*Ha a FramePerSec nagyobb mint frameChangeSpeed,akkor azt jelenti,hogy a k�vetkez� k�pet kell kirajzolni az 
		   anim�ci�ban.Ez�rt megint null�ra megy a sz�ml�l�.*/
		  framePerSec = 0;
		  
		  /*Ha a kirajzolt k�p m�g nem az utols� anim�ci�s k�p, akkor n�velj�k az index�rt�ket,hisz nem lesz
		   ArrayOutIndexException.*/
		  	if(frame<frameLimit - 1){
				this.returnValue = frame;
				frame++;
			}else{
				/*Viszont ,ha m�r az utols� anim�ci�s k�pkocka vet�t�si ideje is lej�rt,akkor minden kezd�dik
				 el�lr�l.*/
				
				if(!infinityAnimation){
					/*Ha ez az anim�ci� nem v�gtelen ism�tl�d�s�, akkor az animationable boolean �rt�k�t az
					 anim�ci� lej�tsz�sakor falsera kell �ll�tanunk, hogy m�g1x aut�matikusan ne j�ttsza le.*/
					this.animationAble = false;
				}
				frame = 0;
				framePerSec = 0;
				this.returnValue = frame;
			}
		}
		return this.returnValue;
	}

	@Override
	public boolean getAnimationable() {
		return this.animationAble;
	}

	@Override
	public void setAnimationable(boolean animationable) {
		this.animationAble = animationable;
	}

	@Override
	public String getLogicName() {
		return this.logicname;
	}
}