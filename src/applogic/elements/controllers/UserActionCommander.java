package applogic.elements.controllers;

public class UserActionCommander extends EntityCommander{
	
	
	public UserActionCommander(IEnvironment environment) {
		super(environment);
	}

	/*Ez implement�lja az EntityCommander interfacet.Itt l�nyeg�ben a felhaszn�l�i beavatkoz�sok
	 hat�s�ra parancsol, az �ltala ir�ny�tott entit�snak.*/
	@Override
	public void command(double appTime) {
		
		/*El�sz�r be�ll�tom, hogy az ir�ny�tott entit�s most nem mozog, azt�n, ha valamerre m�gis
		 mozog az �gyis �t�ll�tja, ha pedig nem akkor a nemmozg�st �gyesen be�ll�tottuk.*/
		
		getControlledEntity().setMoving(false);
		
		if(!getControlledEntity().isStunned()){
			/*Csak akkor csin�lhat b�rmit is a karakter, ha az nincs lestunnolva.*/
			if(isUp()){
				/*if(getControlledEntity().getMovementSpeed() < getControlledEntity().getMaxMovementSpeed()){
					getControlledEntity().setMovementSpeed(getControlledEntity().getMovementSpeed() + 0.1);
				}*/
				getControlledEntity().moveForward();
				getControlledEntity().setMoving(true);
				getEnvironment().PlayerMoved(getControlledEntity());
				
			}/*else if(getControlledEntity().getMovementSpeed() > 1){
				/*Az else �g az�rt kell, mertha nemnyomja a gombot a player, mozogni a lassul�s
				 v�gett az�rt m�g kell*/
				/*getControlledEntity().setMovementSpeed(getControlledEntity().getMovementSpeed() - 0.1);
				getControlledEntity().moveForward();
				getControlledEntity().setMoving(true);
				getEnvironment().PlayerMoved(getControlledEntity());
			}*/
			
			if(isDown()){
				getControlledEntity().moveBack();
				getControlledEntity().setMoving(true);
				getEnvironment().PlayerMoved(getControlledEntity());
				
			}
			
			if(isLeft()){
				getControlledEntity().turnLeft();
				getControlledEntity().setMoving(true);
				
			}
			
			if(isRight()){
				getControlledEntity().trunRight();
				getControlledEntity().setMoving(true);
				
			}
			
			
			for(int i=0;i<getSkillActivated().length;i++){
				if(getSkillActivated()[i]){
					getControlledEntity().activateSkill(i,appTime);
					/*Ezt innen ki kell majd t�r�lni, ha askillek meglesznek rendesen csin�lva.*/
					setSkillActivated(i, false);
				}
			}
		}else{
			/*Ha valaki le van stunnolva, akkor a lenyomott skillaktivit�sokat vissza�ll�tjuk
			 hamisra, hogy ne t�rt�njen meg ha kij�n stunnb�l.*/
			for(int i=0;i<getSkillActivated().length;i++){
				setSkillActivated(i, false);
			}
		}
	}
}