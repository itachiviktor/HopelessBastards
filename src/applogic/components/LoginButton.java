package applogic.components;

import applogic.components.viewbuilders.LoginButtonViewBuilder;

public class LoginButton extends Button{

	/*Ez egy olyan gomb ami m�s k�pet tartalmaz mint a standard , de m�gis gomb funkci�k 
	 vannak, ez�rt sz�rmaztatom a Button oszt�lyb�l.*/
	public LoginButton(int x, int y, int width, int height, String label, IContainer container) {
		super(x, y, width, height, container);
		setButtonImage(new LoginButtonViewBuilder(this));
		container.getImages().add(getButtonImage());
		setLabel("login");
	}
}