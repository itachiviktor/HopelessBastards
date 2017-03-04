package applogic.elements.controllers;

import applogic.elements.Entity;

public class DoNothingCommander extends AiCommander{
	/*Ezt az�rt hoztam l�tre, mivel minden entit�snak kell valami commander,
	 �s ha szimpl�n azt szeretn�m, hogy ne csin�ljon semmit az adott entit�st, akkor 
	 Commandernek ezt �ll�tom be neki.*/

	@Override
	public void command(double appTime) {
		
	}

	@Override
	public void setControlledEntity(Entity controlledEntity) {
			
	}
}