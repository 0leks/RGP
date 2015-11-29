package one;

public abstract class SoundArea {
	private Sound sound;
	public SoundArea(Sound ssound) {
		sound = ssound;
	}
	public Sound getSound() {
		return sound;
	}
	public abstract boolean in(int x, int y);
}
