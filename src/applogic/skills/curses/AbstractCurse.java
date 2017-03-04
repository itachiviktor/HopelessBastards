package applogic.skills.curses;

import applogic.elements.Entity;

public abstract class AbstractCurse {
	/*Curse oszt�lyra az�rt van sz�ks�g, hogy ezeket r�rakjuk a karakterre, akkor ez mint
	 a parazita rajta lesz, a karakter ennek megh�vja a tick met�dus�t, �s �gy tudja
	 lass�tani, m�rgezni, meglopni stb..*/
	
	private Entity cursedEntity;/*Ezt fogja m�rgezni a curse*/
	private Entity curseOwner;/*Ez a curse tulajdonosa, ez rakta az �tkot, n�ha ezt is j� ismerni.*/
	
	public AbstractCurse(Entity cursedEntity,Entity curseOwner) {
		this.cursedEntity = cursedEntity;
		this.curseOwner = curseOwner;
	}
	
	public Entity getCursedEntity() {
		return cursedEntity;
	}

	public void setCursedEntity(Entity cursedEntity) {
		this.cursedEntity = cursedEntity;
	}

	public Entity getCurseOwner() {
		return curseOwner;
	}

	public void setCurseOwner(Entity curseOwner) {
		this.curseOwner = curseOwner;
	}

	public abstract void tick(double appTime);
}