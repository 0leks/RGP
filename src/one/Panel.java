package one;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.util.ArrayList;

public class Panel extends JPanel implements ActionListener, MouseListener,	MouseMotionListener {
	private static final long serialVersionUID = 1L;
	public static final int TIMER_DELAY = 8;
	public static final int BLINK_TIMER_DELAY = 200;
	private Random rand;
	private Timer timer;
	private Image image;
	private World world;
	private ArrayList<Key> keys;
	private Point mouse;
	
	boolean active = false;
	
//	public static int DIMX = 940;
//	public static int DIMY = 620;
	
	public static final int ACTCD = 5;
	private int actcd = 5;
//	public Frame frame;
	
	private Menu optionsmenu;
	private Menu customoptions;
	private Menu activemenu;
	private Menu savemenu;
	private Menu loadmenu;
	private Menu mainmenu;
	private Menu newgamemenu;
	private Menu initmenu;
	
	private boolean gamestarted = false;

	Sound menu;

	public Panel() {
		Frame.println("Initializing Panel");
//		DIMX = dimx;
//		DIMY = dimy;
		mouse = new Point(0, 0);
		addMouseListener(this);
		addMouseMotionListener(this);
		rand = new Random();
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.white);
		setDoubleBuffered(true);
		
		Frame.print("Initializing Menus: ");
		optionsmenu = new Menu();
		customoptions = new Menu();
		activemenu = new Menu();
		savemenu = new Menu();
		loadmenu = new Menu();
		mainmenu = new Menu();
		newgamemenu = new Menu();
		initmenu = new Menu();

		Frame.print("initmenu, ");
		MenuButtonGroup m = new MenuButtonGroup("Race");
		m.add(new MyButton("", 50, 120, 115, 40, "Human", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 120, 50, 40, "Elf", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 120, 95, 40, "Dwarf", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 120, 125, 40, "Scholar", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 120, 150, 40, "Assassin", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 120, 120, 40, "Warrior", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 120, 75, 40, "Baal", Color.black));
		initmenu.add(m);

		m = new MenuButtonGroup("Weapon");
		m.add(new MyButton("", 50, 190, 145, 25, "wooden sword", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 190, 140, 25, "wooden spear", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 190, 135, 25, "wooden mace", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 190, 150, 25, "wooden dagger", Color.black));
    m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 190, 170, 25, "wooden battleaxe", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 190, 100, 25, "shortbow", Color.black));
		initmenu.add(m);
		initmenu.add(new MenuButton( 50, 245, 150, 40, "newgame", Color.black, mainmenu));
		

		Frame.print("newgamemenu, ");
		newgamemenu.add(new MenuButton( 50, 50, 120, 40, "back", Color.black, mainmenu));
		
		m = new MenuButtonGroup("Race");
		m.add(new MyButton("", 50, 120, 105, 40, "Human", Color.black));
		m.add(new MyButton("", 165, 120, 50, 40, "Elf", Color.black));
		m.add(new MyButton("", 225, 120, 90, 40, "Dwarf", Color.black));
		m.add(new MyButton("", 325, 120, 125, 40, "Scholar", Color.black));
		m.add(new MyButton("", 460, 120, 150, 40, "Assassin", Color.black));
		m.add(new MyButton("", 620, 120, 150, 40, "Warrior", Color.black));
		m.add(new MyButton("", 780, 120, 65, 40, "Baal", Color.black));
		newgamemenu.add(m);
		
		m = new MenuButtonGroup("Weapon");
		m.add(new MyButton("", 50, 190, 145, 25, "wooden sword", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 190, 140, 25, "wooden spear", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 190, 135, 25, "wooden mace", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 190, 150, 25, "wooden dagger", Color.black));
		m.add(new MyButton("", m.buts.get(m.buts.size()-1).getX()+m.buts.get(m.buts.size()-1).getWidth()+10, 190, 100, 25, "shortbow", Color.black));
		newgamemenu.add(m);
		newgamemenu.add(new MenuButton( 50, 245, 150, 40, "newgame", Color.black, mainmenu));
		

		Frame.print("savemenu, ");
		savemenu.add(new MenuButton( 50, 50, 120, 40, "back", Color.black, mainmenu));
		savemenu.add(new MenuButton( 50, 120, 120, 40, "slot1", Color.black, null));
		savemenu.add(new MenuButton( 50, 190, 120, 40, "slot2", Color.black, null));
		savemenu.add(new MenuButton( 50, 260, 120, 40, "slot3", Color.black, null));
		savemenu.add(new MenuButton( 50, 330, 120, 40, "slot4", Color.black, null));
		savemenu.add(new MenuButton( 50, 400, 120, 40, "slot5", Color.black, null));

		Frame.print("loadmenu, ");
		loadmenu.add(new MenuButton( 50, 50, 120, 40, "back", Color.black, mainmenu));
		loadmenu.add(new MenuButton( 50, 120, 120, 40, "slot1", Color.black, null));
		loadmenu.add(new MenuButton( 50, 190, 120, 40, "slot2", Color.black, null));
		loadmenu.add(new MenuButton( 50, 260, 120, 40, "slot3", Color.black, null));
		loadmenu.add(new MenuButton( 50, 330, 120, 40, "slot4", Color.black, null));
		loadmenu.add(new MenuButton( 50, 400, 120, 40, "slot5", Color.black, null));

		Frame.print("customoptions, ");
		customoptions.add(new MenuButton( 50, 50, 120, 40, "back", Color.black, optionsmenu));
		customoptions.add(new MenuButton( 50, 120, 245, 40, "useattackimage", Color.black, null));
		customoptions.add(new MenuButton( 50, 190, 120, 40, "3d", Color.black, null));

		Frame.print("options, ");
		optionsmenu.add(new MenuButton( 50, 50, 120, 40, "back", Color.black, mainmenu));
		optionsmenu.add(new MenuButton( 50, 120, 120, 40, "low", Color.black, null));
		optionsmenu.add(new MenuButton( 50, 190, 120, 40, "med", Color.black, null));
		optionsmenu.add(new MenuButton( 50, 260, 120, 40, "high", Color.black, null));
		optionsmenu.add(new MenuButton( 50, 330, 120, 40, "custom", Color.black, customoptions));
    optionsmenu.add(new MenuButton( 50, 400, 120, 40, "music", Color.black, null));
		
		Frame.print("mainmenu");
		mainmenu.add(new MenuButton( 50, 50, 120, 40, "play", Color.black, null));
		mainmenu.add(new MenuButton( 50, 120, 150, 40, "newgame", Color.black, newgamemenu));
		mainmenu.add(new MenuButton( 50, 190, 120, 40, "save", Color.black, savemenu));
		mainmenu.add(new MenuButton( 50, 260, 120, 40, "load", Color.black, loadmenu));
		mainmenu.add(new MenuButton( 50, 330, 120, 40, "options", Color.black, optionsmenu));
		mainmenu.add(new MenuButton( 50, 400, 120, 40, "exit", Color.black, null));

		activemenu = initmenu;
		activemenu.setactive(true);
		activemenu.setsel(1);
		Frame.println();

		Frame.println("Initializing keys");
		keys = new ArrayList<Key>();
		keys.add(new Key(KeyEvent.VK_W, "w"));
		keys.add(new Key(KeyEvent.VK_A, "a"));
		keys.add(new Key(KeyEvent.VK_S, "s"));
		keys.add(new Key(KeyEvent.VK_D, "d"));
		keys.add(new Key(KeyEvent.VK_UP, "up"));
		keys.add(new Key(KeyEvent.VK_1, "1"));
		keys.add(new Key(KeyEvent.VK_2, "2"));
		keys.add(new Key(KeyEvent.VK_3, "3"));
		keys.add(new Key(KeyEvent.VK_4, "4"));
		keys.add(new Key(KeyEvent.VK_5, "5"));
		keys.add(new Key(KeyEvent.VK_DOWN, "down"));
		keys.add(new Key(KeyEvent.VK_LEFT, "left"));
		keys.add(new Key(KeyEvent.VK_RIGHT, "right"));
		keys.add(new Key(KeyEvent.VK_SPACE, "space"));
		keys.add(new Key(KeyEvent.VK_ENTER, "enter"));

		world = new World();
		// initialize draw boundaries
		world.MINDRAWX = 0 - World.DRAWCHECK;
    world.MINDRAWY = 0 - World.DRAWCHECK;
    world.MAXDRAWX = Frame.DIMX - Frame.GUIWIDTH + World.DRAWCHECK;
    world.MAXDRAWY = Frame.DIMY - Frame.GUIHEIGHT + World.DRAWCHECK;
		
		Frame.println("Creating Game Timer with delay: " + TIMER_DELAY);
		timer = new Timer(TIMER_DELAY, this);

		Frame.println("Creating Menu Blink Timer with delay: " + BLINK_TIMER_DELAY);
		menublinktimer = new Timer(BLINK_TIMER_DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(activemenu==newgamemenu || activemenu==initmenu) {
					activemenu.tic();
				}
			}
		});
		this.activemenu.setsel(0);
		Frame.println("Loading Menu Music");
		menu = World.initSound("mainmenu.wav", -10, true);
		if(world.playmusic)
		  menu.play(-5);

		Frame.println("Starting Game Timer and Menu Blink Timer");
		timer.start();
		menublinktimer.start();
	}
	Timer menublinktimer;
	public void actionPerformed(ActionEvent e) {
		if(active && gamestarted) {
			if(actcd <= 0) {
				world.move();
				actcd = ACTCD;
			} else {
				actcd--;
			}
			world.tic(mouse);
		}
		
		for(Key k : keys) {
			if(k.pressed()) {
				if(activemenu.active) {
					if(k.name().equals("up") && !k.justchecked) {
						if(activemenu.getsel()>0) {
							activemenu.setsel(activemenu.getsel()-1);
						}
						k.justchecked = true;
					}
					if(k.name().equals("right") && !k.justchecked) {
						if(activemenu.buts.get(activemenu.getsel()) instanceof MenuButtonGroup) {
							MenuButtonGroup m = (MenuButtonGroup)activemenu.buts.get(activemenu.getsel());
							m.setSelected(m.selected+1);
						}
						k.justchecked = true;
					}
					if((k.name().equals("down")||(k.name().equals("enter") && activemenu.buts.get(activemenu.getsel()) instanceof MenuButtonGroup)) && !k.justchecked) {
						if(activemenu.getsel()+1<activemenu.buts.size()) {
							activemenu.setsel(activemenu.getsel()+1);
						}
						k.justchecked = true;
					}
					if(k.name().equals("left") && !k.justchecked) {
						if(activemenu.buts.get(activemenu.getsel()) instanceof MenuButtonGroup) {
							MenuButtonGroup m = (MenuButtonGroup)activemenu.buts.get(activemenu.getsel());
							m.setSelected(m.selected-1);
						}
						k.justchecked = true;
					}
					if((k.name().equals("space")||k.name().equals("enter")) && k.justchecked == false) {
						k.justchecked = true;
						MenuButton b = activemenu.geton();
						if(activemenu == mainmenu) {
							if(b.is("play")) {
								togglemenu();
							}
							if(b.is("exit")) {
								System.err.println("Exiting Game");
								System.exit(0);
							}
						} else if(activemenu == optionsmenu) {
							if(b.is("low")) {
								world.drawimage = false;
								world.draw3d = false;
							}
							if(b.is("med")) {
								world.drawimage = true;
								world.draw3d = false;
							}
							if(b.is("high")) {
								world.drawimage = true;
								world.draw3d = true;
							}
							if( b.is("music")) {
                if( world.playmusic ) {
                  world.stopMusic();
                }
                else {
                  world.startMusic();
                }
							  menu.stopplaying();
							}
						} else if(activemenu == customoptions) {
							if(b.is("useattackimage")) {
								world.drawimage = !world.drawimage;
							}
							if(b.is("3d")) {
								world.draw3d = !world.draw3d;
							}
						} else if(activemenu == savemenu) {
							if(b.is("slot1")) 
								world.save("slot1");
							if(b.is("slot2")) 
								world.save("slot2");
							if(b.is("slot3")) 
								world.save("slot3");
							if(b.is("slot4")) 
								world.save("slot4");
							if(b.is("slot5")) 
								world.save("slot5");
						} else if(activemenu == loadmenu) {
							if(b.is("slot1")) 
								world.load("slot1");
							if(b.is("slot2")) 
								world.load("slot2");
							if(b.is("slot3")) 
								world.load("slot3");
							if(b.is("slot4")) 
								world.load("slot4");
							if(b.is("slot5")) 
								world.load("slot5");
						} else if(activemenu == newgamemenu) {
							if(b.is("newgame")) {
								String race = "";
								String weap = "";
								for(MenuButton m : activemenu.buts) {
									if(m instanceof MenuButtonGroup) {
										MenuButtonGroup mbg = (MenuButtonGroup)m;
										if(mbg.is("Race")) {
											race = mbg.getSelected().name();
										}
										if(mbg.is("Weapon")) {
											weap = mbg.getSelected().name();
										}
									}
								}
								world.newgame(race, weap);
								if(!gamestarted) {
									gamestarted = true;
								}
								activemenu.setsel(0);
							}
							if(b.is("back")) {
								if(!gamestarted) {
									world.newgame("human", "spartan laser");
									gamestarted = true;
								} else {
									activemenu.setsel(0);
								}
							}
						}
						if(activemenu == initmenu) {
							if(b.is("newgame")) {
								String race = "";
								String weap = "";
								for(MenuButton m : activemenu.buts) {
									if(m instanceof MenuButtonGroup) {
										MenuButtonGroup mbg = (MenuButtonGroup)m;
										if(mbg.is("Race")) {
											race = mbg.getSelected().name();
										}
										if(mbg.is("Weapon")) {
											weap = mbg.getSelected().name();
										}
									}
								}
								world.newgame(race, weap);
								if(!gamestarted) {
									gamestarted = true;
								}
								activemenu.setsel(1);
								if(menu!=null) {
									menu.fadeOut(.1);
									world.changeSound(world.grass);
								}
//								if(sounds != null) {
//									sounds.get(0).fadeOut(.1);
//									sounds.get(1).fadeIn(.1);
//								}
							}
						}
						if(b.getmenu() != null) {
							activemenu = b.getmenu();
							activemenu.setactive(true);
						}
						
					}
				} else {
					if(k.name().equals("w")) {
						world.p.setspeed(0, -1);
					}
					if(k.name().equals("a")) {
						world.p.setspeed(-1, 0);
					}
					if(k.name().equals("s")) {
						world.p.setspeed(0, 1);
					}
					if(k.name().equals("d")) {
						world.p.setspeed(1, 0);
					}
					if(!world.p.inshop) {
						if(k.name().equals("up")) {
							world.p.setAttack("up");
						}
						if(k.name().equals("left")) {
							world.p.setAttack("left");
						}
						if(k.name().equals("down")) {
							world.p.setAttack("down");
						}
						if(k.name().equals("right")) {
							world.p.setAttack("right");
						}
					}
					if(world.p.inshop) {
						if(k.name().equals("up") && k.justchecked == false) {
							k.justchecked = true;
							if(world.selected-5>=0)
								world.selected-=5;
						}
						if(k.name().equals("left") && k.justchecked == false) {
							k.justchecked = true;
							if(world.selected-1>=0)
								world.selected--;
						}
						if(k.name().equals("down") && k.justchecked == false) {
							k.justchecked = true;
							if(world.selected+5<world.inshop().onsale.size())
								world.selected+=5;
						}
						if(k.name().equals("right") && k.justchecked == false) {
							k.justchecked = true;
							if(world.selected+1<world.inshop().onsale.size())
								world.selected++;
						}
						if(k.name().equals("space") && k.justchecked == false && world.p.inshop) {
							k.justchecked = true;
							Shop s = world.inshop();
							Item i = s.onsale.get(world.selected);
							if(i.amount>0) {
								if(world.p.buyItem(i)) {
									s.buy(world.selected);
								}
							}
						}
					}
				}
			} else {
				if(activemenu.active) {
					
				} else {
					if(k.name().equals("w")) {
						world.p.setspeed(0, -2);
					}
					if(k.name().equals("a")) {
						world.p.setspeed(-2, 0);
					}
					if(k.name().equals("s")) {
						world.p.setspeed(0, 2);
					}
					if(k.name().equals("d")) {
						world.p.setspeed(2, 0);
					}
				}
				
			}
		}
		repaint();
		
	}

	public void log(String msg) {
		System.out.print(msg);
	}
	public void setactive(boolean a) {
		active = !active;
		if(active) {
			timer.start();
		} else {
			timer.stop();
		}
	}
	public void paint(Graphics g) {
	
		super.paint(g);
		
		Font small = new Font("Helvetica", Font.BOLD, 15);
		FontMetrics metr = this.getFontMetrics(small);
		g.setColor(Color.black);
		g.setFont(small);
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);

		g2d.setColor(Color.blue);
		
		if(gamestarted)
			world.draw(g2d);

    if( world.p.dead ) {
      g.setColor(new Color( 0, 0, 0, (world.deathTransparency++)/4) );
      if( world.deathTransparency > 1020) {
        world.deathTransparency = 1020;
      }
      g2d.fillRect(0, 0, Frame.DIMX, Frame.DIMY);
    }
		
		activemenu.draw(g2d);
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public synchronized void mousePressed(MouseEvent e) {
	}

	public synchronized void mouseReleased(MouseEvent e) {
	}

	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseMoved(MouseEvent e) {
		mouse.x = e.getX();
		mouse.y = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
	}

	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			for(Key k : keys) {
				if(k.id() == key) {
					k.down();
				}
			}
		}

		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			for(Key k : keys) {
				if(k.id() == key) {
					k.up();
				}
				k.justchecked = false;
				
			}
			if(key == KeyEvent.VK_U) {
				world.p.agilitybuff += 1000;
				world.p.strengthbuff += 1000;
				world.p.regenbuff += 1;
				world.p.lvlup();
			}
			if(key == KeyEvent.VK_O) {
				world.p.agilitybuff += 100;
				world.p.lvlup();
			}
			if(key == KeyEvent.VK_I) {
				world.p.accel++;
				world.p.lvlup();
			}
			if(key == KeyEvent.VK_C) {
				world.p.money +=100;
				world.p.lvlup("",  0);
			}
			if(key == KeyEvent.VK_ESCAPE) {
				openmenu();
			}
			
		}
	}
	public void togglemenu() {
		activemenu.setactive(!activemenu.active);
		active = !active;
	}
	
	public void openmenu() {
		world.changeSound(null);
		activemenu.setactive(true);
		active = false;
	}
}
