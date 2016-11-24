package one;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static int DIMX;
	public static int DIMY;
	public static int MIDX;
	public static int MIDY;
	public static final int GUIWIDTH = 300;
	public static final int GUIHEIGHT = 30;
	public static boolean debugmode = true;
	Panel game;
//	private boolean menuopen = true;
	
    public Frame() {
      Frame.println("Initializing Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("RGP 1.4");
        setResizable(false);
        this.setUndecorated(true);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        DIMX = this.getWidth();
        DIMY = this.getHeight();
        MIDX = (DIMX - GUIWIDTH) / 2;
        MIDY = (DIMY - GUIHEIGHT) / 2;
        
        game = new Panel();
        this.add(game, BorderLayout.CENTER);
       
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