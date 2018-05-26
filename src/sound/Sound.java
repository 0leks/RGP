package sound;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

public class Sound implements Runnable {
	private Thread mythread;
	private ArrayList<Integer> states;
	public static final int NOTHING = 0;
	public static final int FADINGIN = -1;
	public static final int FADINGOUT = -2;
	public static final int STOPPLAYING = -3;
	public static final int STARTPLAYING = -4;
	public static final int CHANGEVOLUME = -5;
	private Clip clip;
	private String url;
	private float fadespeed;
	private float currentvolume;
	private float maxvolume;
	private FloatControl volumecontrol;
	private boolean running;
	private boolean threadstarted;
	private int framelength;
	private boolean loop;
	public Sound(String url, float smaxvolume, boolean sloop) {
		maxvolume = smaxvolume;
		running = true;
		this.url = url;
		loop = sloop;
		mythread = new Thread(this);
		threadstarted = false;
		states = new ArrayList<Integer>();
	}
	public String getUrl() {
		return url;
	}
	private void addState(int toadd) {
		states.add(toadd);
	}
	private int removeTopState() {
		if(states.size()>0)
			return states.remove(0);
		return Sound.NOTHING;
	}
	private int getTopState() {
		if(states.size()>0)
			return states.get(0);
		else
			return Sound.NOTHING;
	}
//	public void setVolume() {
////		clip.
//	}
	public void start() {
		mythread.start();
	}
	/**
	 * starts the sound at specified volume
	 * @param vol can be between -40 and 0
	 */
	public void play(double vol) {
		currentvolume = (float)vol;
		addState(Sound.CHANGEVOLUME);
		addState(Sound.STARTPLAYING);
	}
	public void stopplaying() {
		addState(Sound.STOPPLAYING);
	}
	public void fadeOut(double speed) {
		removeAll(Sound.FADINGIN);
		fadespeed = (float) speed;
		addState(Sound.FADINGOUT);
	}
	public void fadeIn(double speed) {
		removeAll(Sound.FADINGOUT);
		play(-40);
		fadespeed = (float) speed;
		addState(Sound.FADINGIN);
	}
	private void removeAll(int what) {
		for(int a=states.size()-1; a>=0; a--) {
			if(states.get(a)==what) {
				states.remove(a);
			}
		}
	}
	@Override
	public void run() {
//		fadepersecond = 0;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("resources/sounds/" + url).getAbsoluteFile());
			clip.open(inputStream);
			volumecontrol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			clip.setLoopPoints(0, -1);
			framelength = clip.getFrameLength();
//			System.out.println(framelength);
			running = true;
			long timesincelastvolumechange = System.currentTimeMillis();
			while(running) {
				if(states.size()>0) {
					int stat = removeTopState();
					if(stat==Sound.CHANGEVOLUME) {
//						System.out.println(stat);
						while(System.currentTimeMillis()-timesincelastvolumechange<10) {
							
						}
						volumecontrol.setValue((float) currentvolume);
						timesincelastvolumechange = System.currentTimeMillis();
					}
					if(stat == Sound.STARTPLAYING) {
						clip.start();
					}
					if(stat == Sound.STOPPLAYING) {
						clip.stop();
					}
					if(stat == Sound.FADINGOUT) {
//						System.out.println("Fading out");
						currentvolume = currentvolume-fadespeed;
						addState(Sound.CHANGEVOLUME);
						if(currentvolume<-40) {
							fadespeed = 0;
							addState(Sound.STOPPLAYING);
						} else {
							addState(Sound.FADINGOUT);
						}
					}
					if(stat == Sound.FADINGIN) {
						currentvolume = currentvolume+fadespeed;
						addState(Sound.CHANGEVOLUME);
						if(currentvolume>=maxvolume) {
							fadespeed = 0;
						} else {
							addState(Sound.FADINGIN);
						}
					}
				} else {
					Thread.sleep(10);
				}
				if(clip.getLongFramePosition()>=framelength-1) {
					clip.setMicrosecondPosition(1);
					if(loop) {
						clip.start();
					}
				}
			}
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}