package applogic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import applogic.elements.Tile;
import applogic.elements.TileType;
import applogic.viewbuilder.IImageViewBuilder;
import applogic.viewbuilder.IRectangleViewBuilder;
import applogic.viewbuilder.TileViewBuilder;
import applogic.viewbuilder.simpleshapes.SimpleRectangle;

public class MapLoader implements IMapLoader{

	@Override
	public void loadMap(Object bitMap,Object bitTileMap,List<Tile> tiles,List<Tile> nonBlockingTile,List<IImageViewBuilder> viewBuilder) {
		
		Random random = new Random();
		
		BufferedImage bitmap = (BufferedImage)bitMap;
		BufferedImage tileMap = (BufferedImage)bitTileMap;
		
		/*Ez a met�dus rakja �ssze egy k�p alapj�n a p�ly�t, t�lti fel a list�kat(entity,tile)*/
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		int[] wallX = new int[2];
		int[] wallY = new int[2];
		int k = 0;

		/*Ez a for ciklus csak annyit csin�l, hogy az elkeer�t� n�gysz�g k�t sarokpontj�nak n�zi
		 meg az elhelyeszked�s�t.*/
		for(int x = 0;x<width;x++){
			for(int y=0;y<height;y++){
				int pixel = bitmap.getRGB(x,y);
	
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255 && green == 255 && blue == 255 ){
					wallX[k] = x;
					wallY[k] = y;
					k++;
		
				}
			}
		}		
		
		
		for(int x = 0;x<width;x++){
			for(int y=0;y<height;y++){
				
				
				if(x > wallX[0] && y > wallY[0] && x < wallX[1] && y < wallY[1]){
					nonBlockingTile.add(new Tile(x*128,y*128,128,128,0,0,0,false,TileType.GRASS));
					viewBuilder.add(new TileViewBuilder(nonBlockingTile.get(nonBlockingTile.size()-1)));
					
				}else{
					nonBlockingTile.add(new Tile(x*128,y*128,128,128,0,0,0,false,TileType.WATER));
					viewBuilder.add(new TileViewBuilder(nonBlockingTile.get(nonBlockingTile.size()-1)));
				}
				
				
					/*fekete pixel*/
		
					/*NonBlockingTile az �tk�z�svizsg�latn�l nem sz�m�t , ez�rt ezeket ide rakom,
					 nem is iter�lunk �t rajtuk.*/
					/*nonBlockingTile.add(new Tile(x*64,y*64,64,64,0,0,0,false,TileType.EARTH));
					viewBuilder.add(new TileViewBuilder(nonBlockingTile.get(nonBlockingTile.size()-1)));*/
				
				
					/*feh�r pixel*/
					/*Itt az�rt a tilesba rakjuk mert ezek �tkoz�svizsg�latn�l sz�m�tanak.*/
				/*	tiles.add(new Tile(x*64,y*64,64,64,0,0,0,true,TileType.WALL));
					viewBuilder.add(new TileViewBuilder(tiles.get(tiles.size()-1)));*/
					
				
			}
		}	
		
		
		tiles.add(new Tile(wallX[0]*128,wallY[0]*128,128,128,0,0,0,true,TileType.WALLCORNER));
		viewBuilder.add(new TileViewBuilder(tiles.get(tiles.size()-1)));
		
		tiles.add(new Tile(wallX[1]*128,wallY[0]*128,128,128,90,wallX[1]*128 + 64,wallY[0]*128 + 64,true,TileType.WALLCORNER));
		viewBuilder.add(new TileViewBuilder(tiles.get(tiles.size()-1)));
		
		tiles.add(new Tile(wallX[1]*128,wallY[1]*128,128,128,180,wallX[1]*128 + 64,wallY[1]*128 + 64,true,TileType.WALLCORNER));
		viewBuilder.add(new TileViewBuilder(tiles.get(tiles.size()-1)));
		
		tiles.add(new Tile(wallX[0]*128,wallY[1]*128,128,128,270,wallX[0]*128 + 64,wallY[1]*128 + 64,true,TileType.WALLCORNER));
		viewBuilder.add(new TileViewBuilder(tiles.get(tiles.size()-1)));
		
		int wallnumber;
		
		for(int i=wallX[0] + 1;i < wallX[1];i++){
			wallnumber = random.nextInt(2);
			
			if(wallnumber == 0){
				tiles.add(new Tile(i*128,wallY[0]*128,128,128,270,i*128 + 64,wallY[0]*128 + 64,true,TileType.WALL1));
			}else{
				tiles.add(new Tile(i*128,wallY[0]*128,128,128,270,i*128 + 64,wallY[0]*128 + 64,true,TileType.WALL2));
			}
		
			viewBuilder.add(new TileViewBuilder(tiles.get(tiles.size()-1)));
			
		}
		
		for(int i=wallY[0] + 1;i < wallY[1];i++){
			wallnumber = random.nextInt(2);
			
			if(wallnumber == 0){
				tiles.add(new Tile(wallX[1]*128,i*128,128,128,0,wallX[1]*128 + 64,i*128 + 64,true,TileType.WALL1));
				
			}else{
				tiles.add(new Tile(wallX[1]*128,i*128,128,128,0,wallX[1]*128 + 64,i*128 + 64,true,TileType.WALL2));
				
			}
			
			viewBuilder.add(new TileViewBuilder(tiles.get(tiles.size()-1)));
		}
		
		for(int i=wallX[1] - 1;i > wallX[0];i--){
			wallnumber = random.nextInt(2);
			if(wallnumber == 0){
				tiles.add(new Tile(i*128,wallY[1]*128,128,128,90,i*128 + 64,wallY[1]*128 + 64,true,TileType.WALL1));
				
			}else{
				tiles.add(new Tile(i*128,wallY[1]*128,128,128,90,i*128 + 64,wallY[1]*128 + 64,true,TileType.WALL2));
				
			}
			
			viewBuilder.add(new TileViewBuilder(tiles.get(tiles.size()-1)));
		}
		
		for(int i=wallY[1] - 1;i > wallY[0];i--){
			wallnumber = random.nextInt(2);
			
			if(wallnumber == 0){
				tiles.add(new Tile(wallX[0]*128,i*128,128,128,180,wallX[0]*128 + 64,i*128 + 64,true,TileType.WALL1));
				
			}else{
				tiles.add(new Tile(wallX[0]*128,i*128,128,128,180,wallX[0]*128 + 64,i*128 + 64,true,TileType.WALL2));
				
			}
			
			viewBuilder.add(new TileViewBuilder(tiles.get(tiles.size()-1)));
		}
		
		
		width = tileMap.getWidth();
		height = tileMap.getHeight();
		
		for(int y = 0;y<height;y++){
			for(int x=0;x<width;x++){
				int pixel = tileMap.getRGB(x,y);
	
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
			
				if(red == 0 && green == 128 && blue == 0){
					/*s�t�tebbz�ld*/
					tiles.add(new Tile(x*128,y*128,120,120,0,0,0,true,TileType.TREE));
					viewBuilder.add(new TileViewBuilder(tiles.get(tiles.size()-1)));
					
				}else if(red == 128 && green == 64 && blue == 0){
					/*barna*/
					tiles.add(new Tile(x*128,y*128,65,65,0,0,0,true,TileType.TRUNK));
					viewBuilder.add(new TileViewBuilder(tiles.get(tiles.size()-1)));
				}
			}
		}		
		
		
		//addTile(new ManaPotion(1200, 1300, 32, 32, this, "mana", 2, ImageAssets.manapotion.getBufferedImage()));	
	}
}