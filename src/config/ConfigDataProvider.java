package config;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class ConfigDataProvider {
	
	/*Itt az interf�szt �s a megold�st �t kell m�g gondolni..*/
	/*A Canvas �s a WindowFrame implement�ci�kba raktam bele ezta szart.*/
	
	private String serverURL;
	private int screenX;
	private int screenY;
	private int screenWidth;
	private int screenHeight;
	
	public ConfigDataProvider() {
		Wini ini;
		try {
			/*Az ini f�jl kezel�se, innen olvasom ki a szervert url-t �s t�bbi konstansokat. */
			ini = new Wini(new File("./config.ini"));
			serverURL = ini.get("Game", "serverURL", String.class);
	        
	        screenX = ini.get("Game", "screenX", int.class);
	        screenY = ini.get("Game", "screenY", int.class);
	        screenWidth = ini.get("Game", "screenWidth", int.class);
	        screenHeight = ini.get("Game", "screenHeight", int.class);

		} catch (InvalidFileFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public int getScreenX() {
		return screenX;
	}

	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}

	public int getScreenY() {
		return screenY;
	}

	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	
	
}
