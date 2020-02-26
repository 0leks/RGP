package sound;

public class SoundArea {
	private Sound sound;
	private int minimumX;
	private int maximumX;
	private int minimumY;
	private int maximumY;
	
	public SoundArea(Sound sound, int minimumX, int maximumX, int minimumY, int maximumY) {
    this.sound = sound;
    this.minimumX = minimumX;
    this.maximumX = maximumX;
    this.minimumY = minimumY;
    this.maximumY = maximumY;
  }
  public Sound getSound() {
		return sound;
	}
	public boolean in(int x, int y) {
	  return x > minimumX && x < maximumX
	      && y > minimumY && y < maximumY;
	}
}
