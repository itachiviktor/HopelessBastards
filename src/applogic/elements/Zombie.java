package applogic.elements;

import java.util.List;

import applogic.IViewBuilderContainer;
import applogic.elements.controllers.EnemyAndFriendlyEntityProvider;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.ChangePlayerSkill;
import applogic.skills.ZombieSimpleAttack;
import applogic.viewbuilder.entities.ZombieViewBuilder;
import soundapi.ISoundProvider;

public class Zombie extends Entity{
	
	/*Zombien�l a kiv�lasztott meg�lend� ellenf�l az a selectedEntity tartalm�ban van.Azt a v�ltoz�t
	 az �soszt�ly defini�lja.*/
	
	/*A zombie tulajdonosa, ezt m�g ki kell dolgozni.*/
	private Entity owner;

	public Zombie(int x, int y, int health, int maxhealth, int mana, int maxMana,
			int skillCount, String networkId, IViewBuilderContainer container,IEnvironment environment,EnemyAndFriendlyEntityProvider provider,
			ISoundProvider soundProvider) {
		super(x, y, 64, 64,0 , health, maxhealth, mana, maxMana, skillCount, networkId, container,environment,provider,soundProvider);
		
		getSkills()[0] = new ZombieSimpleAttack(this, environment,container, 0);
		getSkills()[6] = new ChangePlayerSkill(this, environment, container,6);
		container.getViewBuilder().add(new ZombieViewBuilder(this,container));
		
	}

	@Override
	public void activateSkill(int skillnumber,double appTime) {
		getSkills()[skillnumber].activateSkill(appTime);
	}

	@Override
	public boolean isDead() {
		return false;
	}
}