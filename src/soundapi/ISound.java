package soundapi;

public interface ISound {
	/*A sound interface, melyet az MP3 �s  Wav val�s�t meg.*/
	public void play();
	public void stop();
	public void close();
	public void setLogicName(String logicName);
}