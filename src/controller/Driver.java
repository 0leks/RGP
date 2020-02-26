package controller;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import controller.*;

public class Driver {
	public static void main(String[] arg) {
//		int prev = 0;
//		for(int rank = 1; rank <=7; rank++) {
//			int multiplier = (int) Math.pow(rank, 1.7);
//			int delta = multiplier - prev;
//			prev = multiplier;
//			System.out.println("Rank: "+rank+" ->\t"+multiplier + " ->\t" + delta);
//		}
	  
//		System.out.println(World.getNextWeapon("wooden mace"));
//		System.out.println(World.getNextWeapon("iron dagger"));
//		System.out.println(World.getNextWeapon("steel axe"));
//		System.out.println(World.getNextWeapon("mithril spear"));
//		System.out.println(World.getNextWeapon("adamant sword"));
//		System.out.println(World.getNextWeapon("rune mace"));
	  
//	  System.out.println(Driver.class.getClassLoader().getResource("image2.png"));
//	  try {
//      BufferedImage image = ImageIO.read(Driver.class.getClassLoader().getResource("image2.png"));
//      JFrame f = new JFrame();
//      f.setSize(new Dimension(500, 500));
//      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      JPanel p = new JPanel() {
//        @Override
//        public void paintComponent(Graphics g) {
//          g.drawImage(image, 0, 0, null);
//        }
//      };
//      f.add(p);
//      f.setVisible(true);
//      
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//	  System.out.println(Driver.class.getResource("/").getPath());
		new GameController();
	}
}