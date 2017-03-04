package xmlhandlers;

import java.util.List;

import graphicsEngine.ImageDescriptor;

/*Ezen az interfacen kereszt�l lehet a DescriptorLoadert el�rni(N�lam a saxparsert ezen kereszt�l �rj�k el).
 Ennek az a l�nyege, hogy k�ls� forr�sb�l el��ll�tsa a k�ple�r� objektum list�t �s visszaadja a h�v�nak.*/
public interface DescriptorLoader {
	public List<ImageDescriptor> Parse(String documentPath);	
}