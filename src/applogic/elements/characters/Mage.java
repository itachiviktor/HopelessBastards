package applogic.elements.characters;

import applogic.IViewBuilderContainer;
import applogic.elements.CharacterType;
import applogic.elements.Player;
import applogic.elements.controllers.EnemyAndFriendlyEntityProvider;
import applogic.elements.controllers.IEnvironment;
import applogic.skills.ChangePlayerSkill;
import applogic.skills.IceBlock;
import applogic.skills.MageHealthSteal;
import applogic.skills.MageLightning;
import applogic.skills.MageSmokeTeleport;
import applogic.skills.ZombieSimpleAttack;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.entities.MageViewBuilder;
import soundapi.ISoundProvider;

public class Mage extends Player{
	
	private IImageViewBuilder mageView;
	
	public Mage(int x, int y, int health, int maxhealth, int mana, int maxMana,
			String networkId, CharacterType characterType,int skillCount,IViewBuilderContainer container,IEnvironment environment,EnemyAndFriendlyEntityProvider provider,
			ISoundProvider soundProvider) {
		super(x, y, 63, 63, 0, health, maxhealth, mana, maxMana, networkId, characterType,skillCount,container,environment,provider,soundProvider);
		
		
		
		getSkills()[0] = new ZombieSimpleAttack(this, environment,container, 0);
		getSkills()[1] = new IceBlock(this, environment,container, 1,soundProvider);
		getSkills()[2] = new MageLightning(this,environment,container,2,soundProvider);
		getSkills()[3] = new MageHealthSteal(this, environment,container, 3);
		getSkills()[4] = new MageSmokeTeleport(this,environment,container,4);
		getSkills()[6] = new ChangePlayerSkill(this, environment, container,6);
		
		this.mageView = new MageViewBuilder(this,container);
		container.getViewBuilder().add(mageView);
		
		/*a skilleihez viewBuildert is hozz�ad.*/
				
	}
	
	@Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		mageView.setDeletable(deletable);
	}

	
	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public void activateSkill(int skillnumber,double appTime) {
		getSkills()[skillnumber].activateSkill(appTime);
	}
	
	/*Fontos a getDamagedAreas met�dust defini�ljuk fel�l, ha sz�kebb k�pmetszetet szeretn�nk mint az alapb�l defini�lt!!!!!!!!!*/
}