package applogic.elements.controllers;

import applogic.elements.Entity;

public interface Commander {
	/*Minden Commander implement�lja, aki Commander annak tudnia kell ir�ny�tani a r�b�zott entit�st
	 azaz a command() met�dus, illetve be kell tudnia �ll�ttatni az ir�ny�tand� entit�st.*/
	public void command(double appTime);
	public void setControlledEntity(Entity controlledEntity);
	
}
