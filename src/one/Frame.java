package one;

import java.awt.Event;

import javax.swing.JFrame;

public class Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static final int DIMX = 940+300;
	public static final int DIMY = 650;
	public static boolean debugmode = true;
	Panel game;
//	private boolean menuopen = true;
	
    public Frame() {
      Frame.println("Initializing Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game = new Panel(DIMX, DIMY);
        add(game);
        setTitle("RGP 1.3");
        setResizable(false);
        setSize(DIMX+6, DIMY+21);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void print(String debug) {
    	if(debugmode) {
    		System.err.print(debug);
    	}
    }
    public static void println(String debug) {
    	if(debugmode) {
    		System.err.println(debug);
    	}
    }
    public static void println() {
    	if(debugmode) {
    		System.err.println();
    	}
    }
    public void exit() {
    	System.exit(0);
    }
}