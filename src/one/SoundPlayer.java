package one;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class SoundPlayer {
//	ArrayList<Clip> clipList;// this is the Clip that will be played
//	ArrayList<String> fileList;
//
//	public SoundPlayer() {
//		clipList = new ArrayList<Clip>();
//		fileList = new ArrayList<String>();
////		fileList.add("shuffle.wav");// 0
////		fileList.add("roll.wav");// 1
////		fileList.add("ding.wav");// 2
////		fileList.add("squish.wav");// 3
////		fileList.add("danger.wav");// 4
////		fileList.add("pop_champ.wav");// 5
////		fileList.add("fallB.wav");// 6
////		fileList.add("fallD.wav");// 7
//		loadFiles();
//		for (Clip c : clipList) {// play them all to see if working...
//									// cacophony!
//			if (c != null)
//				c.start();
//		}
//	}
//
//	public void loadFiles() {
//		Clip clip = null;
//		for (String fn : fileList) {
//			try {
//				clip = null;
//				// From file that has been placed in the project folder
//				// String fname = "ding.wav", music = "spacemusic.au";
//				AudioInputStream stream = AudioSystem
//						.getAudioInputStream(new File(fn));
//				// AudioInputStream stream2 =
//				// AudioSystem.getAudioInputStream(new File("ding"));
//
//				AudioFormat format = stream.getFormat();
//				if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
//					format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
//							format.getSampleRate(),
//							format.getSampleSizeInBits() * 2,
//							format.getChannels(), format.getFrameSize() * 2,
//							format.getFrameRate(), true); // big endian
//					stream = AudioSystem.getAudioInputStream(format, stream);
//				}
//
//				// Create the clip
//				DataLine.Info info = new DataLine.Info(Clip.class,
//						stream.getFormat(),
//						((int) stream.getFrameLength() * format.getFrameSize()));
//				clip = (Clip) AudioSystem.getLine(info);
//
//				// This method does not return until the audio file is
//				// completely loaded
//				clip.open(stream);
//				System.out.println(fn + " clip is: " + clip);
//
//			} catch (Exception e) {
//				System.out.println("Problem with sound loading \n" + e);
//
//			}
//			clipList.add(clip);
//		}
//	}
//
//	public void playFallD() {
//		System.out.println("Diamond falling!");
//		play(7);
//	}
//
//	public void playFallB() {
//		System.out.println("Boulder falling!");
//		play(6);
//	}
//
//	public void playLose() {
//		System.out.println("Lost.  And here is sound! " + clipList.get(5));
//		play(5);
//	}
//
//	public void playWin() {
//		System.out.println("Win sounding!");
//		play(4);
//	}
//
//	public void playSquish() {
//		System.out.println("Sounds squishy!");
//		play(3);
//	}
//
//	public void playExplode() {
//		play(2);
//	}
//
//	public void playShuffle() {
//		System.out.println("Sounds like shuffling!");
//		play(0);
//	}
//
//	public void playRoll() {
//		play(1);
//	}
//
//	private void play(int i) {
//		// TODO Auto-generated method stub
//
//		// if(i<clipList.size()){
//		Clip c = clipList.get(i);
//		if (c != null && !c.isRunning()) {
//			c.setFramePosition(0);
//			c.start();
//		}
//		// }
//		//
//		// else{
//		// System.out.println("not in list: " + clipList.size());
//		// }
//	}

}