package applogic;

public class GameLoop implements Runnable,IGameLoop{
	/*A GameLoop megval�s�t�sa, ez k�l�n sz�lon fog futni.*/
	
	private Thread thread;
	private boolean running = false;/*Sz�l ind�t�sn�l van szerepe.*/
	
	private double delta;
	private boolean runFlag;
	
	private double currTime;
	
	
	private double lastTickTime;
	
	private double lastRenderTime;
	private double nextRendererTime;
	private double renderTime;
	
	
	public static double lastTick;
	public static double lastlastTick;
	public static double nextTick;
	public static double currentTick;
	
	private double startingAppTime;
	/*Van referenci�ja egy GameStateContainerre, aminek tov�bbhivja a tick �s render met�dus�t,
	 �gy a gameloop, az nem tud semmit az aktu�lis GameStater�l, teh�t nem is ismeri.K�zvetve,
	 a GameStateContaineren kereszt�l h�vogatja.*/
	private AbstractGameStateContainer gameStateContainer;
	
	public GameLoop(AbstractGameStateContainer gameStateContainer) {
		this.gameStateContainer = gameStateContainer;
		start();/*Elind�tjuk a sz�lat.*/
	}
	
	private void gameLoopInitialization(){
		 runFlag = true;
	     delta = 0.1;
	     /*nextTime = (double)System.nanoTime() / 1000000000.0;
	     maxTimeDiff = 0.5;
	     skippedFrames = 1;
	     maxSkippedFrames = 5;*/
	}
	
	private synchronized void start(){
		/*Sz�l ind�t�sa*/
		if(running){
			return;
		}
		running = true;
		thread = new Thread(this,"Thread");
		thread.start();
	}
	@Override
	public void tick(double appTime){
		gameStateContainer.tick(appTime);
	}
	@Override
	public void render(double renderTime){
		gameStateContainer.render(renderTime);
	}
	
	private synchronized void stop(){
		if(!running){
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		gameLoopInitialization();
	    int nticks = 0;
		startingAppTime = (double)System.nanoTime() / 1000000000.0; 
		
		
       /* while(runFlag){
            currTime = (double)System.nanoTime() / 1000000000.0;
            if((currTime - nextTime) > maxTimeDiff){
            	nextTime = currTime;
            }
            if(currTime >= nextTime){
                
                nextTime += delta;
                tick(currTime - startingAppTime);
                nticks++;
                double tickTime = (currTime - startingAppTime)/ nticks;
               
                if((currTime < nextTime) || (skippedFrames > maxSkippedFrames)){
                    render();
                   
                    skippedFrames = 1;
                }else{
                    skippedFrames++;
                }
            }else{
                sleepTime = (int)(1000.0 * (nextTime - currTime));
                if(sleepTime > 0)
                {
                    try
                    {
                        Thread.sleep(sleepTime);
                    }
                    catch(InterruptedException e)
                    {
                    }
                }
            }
        }*/
		
		  while(runFlag){
	    	   currTime = (double)System.nanoTime() / 1000000000.0;
	    	   
	            if(lastTickTime + delta <= currTime){

	                tick(currTime - startingAppTime);
	                
	                currTime = (double)System.nanoTime() / 1000000000.0; 
	                lastTickTime = currTime;
	                
                	
	                while(currTime < lastTickTime + delta){
	                	currTime = (double)System.nanoTime() / 1000000000.0;
	                	
	                	lastRenderTime = (double)System.nanoTime() / 1000000000.0;
	                	render(renderTime);
	                	nextRendererTime = (double)System.nanoTime() / 1000000000.0;
	               
	                	//System.out.println(dd);
	                	renderTime = nextRendererTime - lastRenderTime;
	                	//System.out.println(nextRendererTime - lastRenderTime);
	                	
	                }
	            }
	       }
        stop();
	}
}