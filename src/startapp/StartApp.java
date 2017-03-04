package startapp;

import java.awt.EventQueue;
import initapplication.IProviderFactory;
import initapplication.ProviderFactory;

public class StartApp {
	/*Az alkalmaz�s main() f�ggv�ny�t tartalmazza.Ez p�ld�nyos�tja a ProviderFayctory komponenst,
	 ami a konstruktor�ban sz�pen fel�p�ti a f�gg�s�geket, l�trehoz minden sz�ks�ges komponenst a 
	 j�t�k g�rd�l�keny futtat�s�hoz.Ezt az esem�nykezel� sz�lon int�zem, a konkurencia
	 probl�ma elker�l�se v�gett.*/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IProviderFactory factory = new ProviderFactory();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}