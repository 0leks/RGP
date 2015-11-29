package one;

public class Key {
	private  int id;
	private String s;
	private boolean pressed;
	public boolean justchecked;
	public Key(int sid, String ss) {
		s = ss;
		id = sid;
		pressed = false;
	}
	public int id() {
		return id;
	}
	public boolean pressed() {
		return pressed;
	}
	public void down() {
		pressed = true;
	}
	public void up() {
		pressed = false;
	}
	public String name() {
		return s;
	}
}
