package applogic.elements;

import applogic.IViewBuilderContainer;
import applogic.elements.controllers.EnemyAndFriendlyEntityProvider;
import applogic.elements.controllers.IEnvironment;
import soundapi.ISoundProvider;

public abstract class Player extends Entity{

	private String networkId;
	private CharacterType characterType;
	
	public Player(int x, int y, int width, int height, double angle, int health, int maxhealth,
			int mana, int maxMana,String networkId,CharacterType characterType,int skillCount,IViewBuilderContainer container,
			IEnvironment environment,EnemyAndFriendlyEntityProvider provider,ISoundProvider soundProvider) {
		super(x, y, width, height, angle, health, maxhealth, mana, maxMana,skillCount, networkId, container,environment,provider,soundProvider);
		this.networkId = networkId;
		this.characterType = characterType;
	}
	
	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public CharacterType getCharacterType() {
		return characterType;
	}

	public void setCharacterType(CharacterType characterType) {
		this.characterType = characterType;
	}
}