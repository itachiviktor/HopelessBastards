package network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import applogic.elements.Entity;
import applogic.elements.EntityPositionEstimate;
import applogic.elements.controllers.IEnvironment;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import math.RotatePoints;

public class NetworkSetup implements INetworkSetup{

	private Socket socket;
	private IEnvironment environment;
	private EntityPositionEstimate estimatePosition;
	
	public NetworkSetup(Socket socket,IEnvironment environment) {
		super();
		this.socket = socket;
		this.environment = environment;
	}

	@Override
	public void setupNetwork() {
		/*az els� a kapcsol�d�si esem�nyre reag�l*/
		this.socket.on("socketID", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				JSONObject data = (JSONObject)arg0[0];
				try{
					System.out.println("socketid");
					String id = data.getString("id");
					
					/*Amikor l�trehozzuk a kapcsolatot a szerverrel, akkor az ad nek�nk egy egy�ni azonos�t�t,
					 �s kiv�ltja ezta socketid esem�nyt, amire ez a met�dus h�v�dik meg.Mivel regisztr�ljuk magunkat
					 a playerek k�z� j� lenne eljutttatni a szervernek, hogy mi a jelenlegi �jj j�t�kos
					 karakter�nek t�pusa.Ezt itt m�r tudjuk a dui var�zsl�ban kiv�lasztja a player
					 �s kiv�ltunk egy charactertype esem�nyt a szerver fel�, ahol elk�ldj�k neki
					 a karakter t�pus�t, �s �gy � ezt tov�bb�tani tudja minden m�s playernek mint 
					 newPlayer esem�ny  egy id �s egy karaktert�pus el�g ahhoz, hogy
					 l�trehozhassa minden m�s karakter ezt az �j j�t�kost ellenf�lk�nt
					 a saj�t kliens�ben.*/
					
					JSONObject ob = new JSONObject();
					
					environment.makePlayer(2600, 3048, id, environment.getSelectedCharacterType().toString());
						
					ob.put("characterType",environment.getSelectedCharacterType().toString());	
					socket.emit("characterType",ob);
				
				}catch(JSONException e){
					e.getMessage();
				}
			}
		}).on("newPlayer", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				JSONObject data = (JSONObject)arg0[0];
				try{
					System.out.println("newPlayer");
					String playerId = data.getString("id");
					String characterType = data.getString("characterType");
					
					/*Ha �j j�t�kos csatlakozik, elk�ldi mindenkinek az id-j�t �s a karakter t�pus�t
					 ezek szerint l�tre tudjua minden enemy hozni �t a saj�t lok�lis ter�ben.*/
					
					
					environment.makeEnemyPlayer(3400, 3048, playerId, characterType);
					
					
				}catch(JSONException e){
					e.getMessage();
				}
			}
		}).on("playerDisconnected", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				System.out.println("disconnect");
				JSONObject data = (JSONObject)arg0[0];
				try{
					String id = data.getString("id");
					/*id alapj�n t�r�lj�k a list�b�l(nincs k�sz)*/
					for(int i=0;i<environment.getEnemyPlayers().size();i++){
						if(environment.getEnemyPlayers().get(i).getId().equals(id)){
							environment.getEnemyPlayers().get(i).setDeletable(true);
							environment.getEnemyPlayers().remove(i);
						}
					}
				}catch(JSONException e){
					e.getMessage();
				}
			}
		}).on("getPlayers", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				JSONArray objects = (JSONArray)arg0[0];
				
				try{
					for(int i=0;i<objects.length();i++){
						String id = objects.getJSONObject(i).getString("id");
						double x = objects.getJSONObject(i).getDouble("x");
						double y = objects.getJSONObject(i).getDouble("y");
						String characterType = objects.getJSONObject(i).getString("characterType");
						
						/*Minden player karakter�t �gy hozzuk l�tre, hogy a jsonb�l kiszedj�k,
						 hogy mi a karakter�nek t�pusa, �s aszerint hozzuk l�tre.*/
						System.out.println("getPlayers");
						
						environment.makeEnemyPlayer((int)x, (int)y, id, characterType);
					}
				}catch(JSONException e){
					e.getMessage();
				}
			}
		}).on("nodeTick", new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				JSONArray objects = (JSONArray)arg0[0];

				//System.out.println("network update: " + time.getDouble("tickTime"));
				try{
					Entity enemy = null;
					
					for(int i=0;i<objects.length();i++){
						String id = objects.getJSONObject(i).getString("id");
						boolean updated = objects.getJSONObject(i).getBoolean("livingconnection");
						enemy = null;
						
						/*Az updated azt jelenti, hogy friss�tve volt szerveroldalon az entit�s,
						 azaz �j inform�ci� van r�la.*/
						if(updated){
							for(int j=0;j<environment.getEnemyPlayers().size();j++){
								if(environment.getEnemyPlayers().get(j).getId().equals(id)){
									enemy = environment.getEnemyPlayers().get(j);
									
									if(enemy != null){
										//Player entity = enemies.getById(playerId);
										//entity.username = data.getString("username");
										/*ezzel a sorral �ll�tom be a user
										nev�t a Musclemanoknak meg sat�bbiknek, nem pedig kosntruktor�ba(�gy m�k�dik
										am�gy pedig nem igaz�n akart.)*/
										
										Entity selectedEntity = null;
										
										
										
										for(int k=0;k<environment.getEnemyPlayers().size();k++){
											if(environment.getEnemyPlayers().get(k).getId().equals(objects.getJSONObject(i).getString("selectedPlayer"))){
												selectedEntity = environment.getEnemyPlayers().get(k);
												break;
											}
										}
										
										for(int k=0; k < environment.getFriendlyPlayers().size();k++){
											if(environment.getFriendlyPlayers().get(k).getId().equals(objects.getJSONObject(i).getString("selectedPlayer"))){
												selectedEntity = environment.getFriendlyPlayers().get(k);
												break;
											}
										}
										
										enemy.setSelectedEntity(selectedEntity);
										
										EntityPositionEstimate estimatePosition = enemy.getPositionEstimate();
										double now = (double)System.nanoTime() / 1000000000.0;
										if(now - estimatePosition.getTickTime() > 0.05){
											
											estimatePosition.setTickTime(now);
											
											estimatePosition.setLastlastTick(estimatePosition.getLastTick());
											estimatePosition.setLastTick(objects.getJSONObject(i).getDouble("lastTickTime"));
											
											estimatePosition.setOldx2(estimatePosition.getOldx1());
											estimatePosition.setOldx1(objects.getJSONObject(i).getDouble("x"));
		
											estimatePosition.setOldy2(estimatePosition.getOldy1());
											estimatePosition.setOldy1(objects.getJSONObject(i).getDouble("y"));
										
											estimatePosition.setOldangle2(estimatePosition.getOldangle1());
											estimatePosition.setOldangle1(objects.getJSONObject(i).getDouble("angle"));
											
											if(estimatePosition.getOldangle1() - estimatePosition.getOldangle2() == 0){
												estimatePosition.setInteranglespeed(RotatePoints.twoAngleDistance(estimatePosition.getInterangle(), estimatePosition.getOldangle1()));
											}else{	
												estimatePosition.setInteranglespeed(RotatePoints.twoAngleDistance(estimatePosition.getOldangle2(), estimatePosition.getOldangle1()));									
											}
											
											if(estimatePosition.getOldx1() - estimatePosition.getOldx2() == 0){
												estimatePosition.setInterspeedx(estimatePosition.getOldx1() - estimatePosition.getInterx());
											}else{
												estimatePosition.setInterspeedx(estimatePosition.getOldx1() - estimatePosition.getOldx2());
											}
											
											if(estimatePosition.getOldy1() - estimatePosition.getOldy2() == 0){
												estimatePosition.setInterspeedy(estimatePosition.getOldy1() - estimatePosition.getIntery());
											}else{
												estimatePosition.setInterspeedy(estimatePosition.getOldy1() - estimatePosition.getOldy2());
											}
											
											enemy.setX(objects.getJSONObject(i).getDouble("x"));
											enemy.setY(objects.getJSONObject(i).getDouble("y"));
											enemy.setAngle(objects.getJSONObject(i).getDouble("angle"));
										}
										
										
										enemy.setNetworkHealth(objects.getJSONObject(i).getInt("health"));
										enemy.setMaxhealth(objects.getJSONObject(i).getInt("maxhealth"));
										enemy.setMana(objects.getJSONObject(i).getInt("mana"));
										enemy.setMaxMana(objects.getJSONObject(i).getInt("maxmana"));
										enemy.setDead(objects.getJSONObject(i).getBoolean("dead"));
										
										if(objects.getJSONObject(i).getInt("skillStarted") >= 0){
											enemy.setSkillStarted(objects.getJSONObject(i).getInt("skillStarted"), true);
										}
										
										for(int k=0;k<enemy.getSkillCount();k++){	
											if(enemy.getSkillStarted(k)){
												enemy.getSkills()[k].activateSkillByServer(enemy.getAppTime());
												enemy.setSkillStarted(k, false);
												enemy.getSkills()[k].setNetworkActivate(false);
												break;
											}
										}
									}
									break;
								}
							}
						}
					}
				}catch(JSONException e){
					e.getMessage();
				}	
			}
		});	
	}
}