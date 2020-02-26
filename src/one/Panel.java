package one;

import java.util.Random;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.Timer;

import controller.*;
import gui.Frame;
import one.Mob.*;
import sound.*;

import java.util.ArrayList;

public class Panel extends JPanel implements MouseListener,	MouseMotionListener {
	private static final long serialVersionUID = 1L;
	public static final int BLINK_TIMER_DELAY = 200;

  public static int DIMX;
  public static int DIMY;
  public static int MIDX;
  public static int MIDY;
  public static final int GUIWIDTH = 300;
  public static final int GUIHEIGHT = 30;
  
//	private World world;
	private ArrayList<Key> keys;
	private Point mouse;
	
	boolean active = false;

  private Timer menublinktimer;
	private Menu optionsmenu;
	private Menu customoptions;
	private Menu activemenu;
	private Menu savemenu;
	private Menu loadmenu;
	private Menu mainmenu;
	private Menu newgamemenu;
	
	private boolean gamestarted = false;
	
	private SoundManager soundManager;
  private GameControllerInterface gameController;

	public Panel(GameControllerInterface gameController, Race classType, SoundManager soundManager) {
		Frame.println("Initializing Panel");
    this.gameController = gameController;
		this.soundManager = soundManager;
		mouse = new Point(0, 0);
		addMouseListener(this);
		addMouseMotionListener(this);
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
		
		Frame.print("newgamemenu, ");
		newgamemenu.add(new MenuButton( 50, 50, 120, 40, "back", Color.black, mainmenu));
		
		MenuButtonGroup m = new MenuButtonGroup("Race");
		m.add(new MyButton("", 50, 120, 105, 40, "Human", Color.black));
		m.add(new MyButton("", 165, 120, 50, 40, "Elf", Color.black));
		m.add(new MyButton("", 225, 120, 90, 40, "Dwarf", Color.black));
		m.add(new MyButton("", 325, 120, 125, 40, "Scholar", Color.black));
		m.add(new MyButton("", 460, 120, 150, 40, "Assassin", Color.black));
		m.add(new MyButton("", 620, 120, 150, 40, "Warrior", Color.black));
		m.add(new MyButton("", 780, 120, 65, 40, "Baal", Color.black));
		newgamemenu.add(m);
		newgamemenu.add(new MenuButton( 50, 190, 150, 40, "newgame", Color.black, mainmenu));
		
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

		activemenu = mainmenu;
		activemenu.setactive(true);
		activemenu.setsel(0);
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

//		world = new World(soundManager);
//		soundManager.addPlayerLocation(world);
		updateSize();

		Frame.println("Creating Menu Blink Timer with delay: " + BLINK_TIMER_DELAY);
		menublinktimer = new Timer(BLINK_TIMER_DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(activemenu==newgamemenu) {
					activemenu.tic();
				}
			}
		});
		this.activemenu.setsel(0);
		Frame.println("Loading Menu Music");

		Frame.println("Starting Menu Blink Timer");
		menublinktimer.start();
		
		gameController.startNewGame(classType);
    if(!gamestarted) {
      gamestarted = true;
    }
    activemenu.setsel(0);
    soundManager.playMusic();
	}
	
	public void updateSize() {
    // initialize draw boundaries
    gameController.getWorld().MINDRAWX = 0 - World.DRAWCHECK;
    gameController.getWorld().MINDRAWY = 0 - World.DRAWCHECK;

    DIMX = this.getWidth();
    DIMY = this.getHeight();
    MIDX = (DIMX - GUIWIDTH) / 2;
    MIDY = (DIMY - GUIHEIGHT) / 2;
    
    gameController.getWorld().MAXDRAWX = DIMX - GUIWIDTH + World.DRAWCHECK;
    gameController.getWorld().MAXDRAWY = DIMY - GUIHEIGHT + World.DRAWCHECK;
	}
	
	@Override
	public void paintComponent(Graphics g) {
	  gameController.getWorld().updateMouseLocation(mouse);
    super.paintComponent(g);
	}
	public void gameTic() {
		if(active && gamestarted) {
		  gameController.getWorld().move();
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
						activateButton(b);
						
					}
				} else {
					if(k.name().equals("w")) {
					  gameController.getPlayer().moveUp();
					}
					if(k.name().equals("a")) {
					  gameController.getPlayer().moveLeft();
					}
					if(k.name().equals("s")) {
					  gameController.getPlayer().moveDown();
					}
					if(k.name().equals("d")) {
					  gameController.getPlayer().moveRight();
					}
					if(!gameController.getPlayer().isInShop()) {
						if(k.name().equals("up")) {
						  gameController.getPlayer().setAttack(AttackDirection.UP);
						}
						if(k.name().equals("left")) {
						  gameController.getPlayer().setAttack(AttackDirection.LEFT);
						}
						if(k.name().equals("down")) {
						  gameController.getPlayer().setAttack(AttackDirection.DOWN);
						}
						if(k.name().equals("right")) {
						  gameController.getPlayer().setAttack(AttackDirection.RIGHT);
						}
					}
					if(gameController.getPlayer().isInShop()) {
						if(k.name().equals("up") && k.justchecked == false) {
							k.justchecked = true;
							if(gameController.getWorld().selected-5>=0)
							  gameController.getWorld().selected-=5;
						}
						if(k.name().equals("left") && k.justchecked == false) {
							k.justchecked = true;
							if(gameController.getWorld().selected-1>=0)
							  gameController.getWorld().selected--;
						}
						if(k.name().equals("down") && k.justchecked == false) {
							k.justchecked = true;
							if(gameController.getWorld().selected+5<gameController.getWorld().inshop().onsale.size())
							  gameController.getWorld().selected+=5;
						}
						if(k.name().equals("right") && k.justchecked == false) {
							k.justchecked = true;
							if(gameController.getWorld().selected+1<gameController.getWorld().inshop().onsale.size())
							  gameController.getWorld().selected++;
						}
						if(k.name().equals("space") && k.justchecked == false && gameController.getPlayer().isInShop()) {
							k.justchecked = true;
							Shop s = gameController.getWorld().inshop();
							Item i = s.onsale.get(gameController.getWorld().selected);
							if(i.amount>0) {
								if(gameController.getPlayer().buyItem(i)) {
									s.buy(gameController.getWorld().selected);
								}
							}
						}
					}
				}
			} else {
				if(activemenu.active) {
					
				} else {
					if(k.name().equals("w")) {
					  gameController.getPlayer().stopMovingUp();
					}
					if(k.name().equals("a")) {
					  gameController.getPlayer().stopMovingLeft();
					}
					if(k.name().equals("s")) {
					  gameController.getPlayer().stopMovingDown();
					}
					if(k.name().equals("d")) {
					  gameController.getPlayer().stopMovingRight();
					}
				}
				
			}
		}
//		repaint();
		
	}
	public void activateButton(MenuButton b) {

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
        gameController.getWorld().drawimage = false;
        gameController.getWorld().draw3d = false;
      }
      if(b.is("med")) {
        gameController.getWorld().drawimage = true;
        gameController.getWorld().draw3d = false;
      }
      if(b.is("high")) {
        gameController.getWorld().drawimage = true;
        gameController.getWorld().draw3d = true;
      }
      if( b.is("music")) {
        gameController.getWorld().toggleMusic();
      }
    } else if(activemenu == customoptions) {
      if(b.is("useattackimage")) {
        gameController.getWorld().drawimage = !gameController.getWorld().drawimage;
      }
      if(b.is("3d")) {
        gameController.getWorld().draw3d = !gameController.getWorld().draw3d;
      }
    } else if(activemenu == savemenu) {
      if(b.is("slot1")) 
        gameController.saveGame("slot1");
      if(b.is("slot2")) 
        gameController.saveGame("slot2");
      if(b.is("slot3")) 
        gameController.saveGame("slot3");
      if(b.is("slot4")) 
        gameController.saveGame("slot4");
      if(b.is("slot5")) 
        gameController.saveGame("slot5");
    } else if(activemenu == loadmenu) {
      if(b.is("slot1")) 
        gameController.loadGame("slot1");
      if(b.is("slot2")) 
        gameController.loadGame("slot2");
      if(b.is("slot3")) 
        gameController.loadGame("slot3");
      if(b.is("slot4")) 
        gameController.loadGame("slot4");
      if(b.is("slot5")) 
        gameController.loadGame("slot5");
    } else if(activemenu == newgamemenu) {
      if(b.is("newgame")) {
        Race race = null;
        String weap = "";
        for(MenuButton m : activemenu.buts) {
          if(m instanceof MenuButtonGroup) {
            MenuButtonGroup mbg = (MenuButtonGroup)m;
            if(mbg.is("Race")) {
              race = Race.parse(mbg.getSelected().name());
            }
            if(mbg.is("Weapon")) {
              weap = mbg.getSelected().name();
            }
          }
        }
        soundManager.unlockSound();
        gameController.startNewGame(race);
        if(!gamestarted) {
          gamestarted = true;
        }
        activemenu.setsel(0);
      }
      if(b.is("back")) {
        if(!gamestarted) {
          gameController.startNewGame(Race.HUMAN);
          gamestarted = true;
        } else {
          activemenu.setsel(0);
        }
      }
    }
    if(b.getmenu() != null) {
      activemenu = b.getmenu();
      activemenu.setactive(true);
    }
	}

	public void log(String msg) {
		System.out.print(msg);
	}
	public void paint(Graphics g) {
	
		super.paint(g);
		
		Font small = new Font("Helvetica", Font.BOLD, 15);
		FontMetrics metr = this.getFontMetrics(small);
		g.setColor(Color.black);
		g.setFont(small);
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);

		g2d.setColor(Color.blue);
		
		if(gamestarted)
			gameController.getWorld().draw(g2d);

    if( gameController.getPlayer().isDead() ) {
      g.setColor(new Color( 0, 0, 0, (gameController.getWorld().deathTransparency++)/4) );
      if( gameController.getWorld().deathTransparency > 1020) {
        gameController.getWorld().deathTransparency = 1020;
      }
      g2d.fillRect(0, 0, DIMX, DIMY);
    }
		
		activemenu.draw(g2d);
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	@Override
	public void doLayout() {
	  super.doLayout();
	  System.err.println("Panel doLayout");
    updateSize();
	}

	public synchronized void mousePressed(MouseEvent e) {
	}

	public synchronized void mouseReleased(MouseEvent e) {
    if( activemenu.active ) {
      if( activemenu.geton().ison(e.getX(), e.getY()) ) {
        activateButton(activemenu.geton());
      }
    }
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
		if( activemenu.active ) {
		  for( int index = 0; index < activemenu.buts.size(); index++ ) {
		    MenuButton b = activemenu.buts.get(index);
        if( b.ison(mouse.x, mouse.y) ) {
          activemenu.setsel(index);
        }
		  }
		}
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
			  gameController.getPlayer().agilitybuff += 1000;
			  gameController.getPlayer().strengthbuff += 1000;
			  gameController.getPlayer().regenbuff += 1;
			  gameController.getPlayer().lvlup();
			}
      if(key == KeyEvent.VK_P) {
        World.NO_COLLISION = !World.NO_COLLISION;
      }
      if(key == KeyEvent.VK_K) {
        gameController.getPlayer().experience += 4000;
        gameController.getPlayer().lvlup();
      }
      if(key == KeyEvent.VK_L) {
        World.ZOOM = (World.ZOOM==8)?1:8;
      }
			if(key == KeyEvent.VK_O) {
			  gameController.getPlayer().agilitybuff += 100;
			  gameController.getPlayer().lvlup();
			}
			if(key == KeyEvent.VK_I) {
			  gameController.getPlayer().accel++;
			  gameController.getPlayer().lvlup();
			}
			if(key == KeyEvent.VK_C) {
			  gameController.getPlayer().money +=100;
			  gameController.getPlayer().rescale();
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
	  soundManager.fadeOutMusic();
		activemenu.setactive(true);
		active = false;
	}

}
