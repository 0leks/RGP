package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import layout.TableLayout;

@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel {
  private JButton newGame;
  private JButton exit;
  private MenuListener listener;
  public MainMenuPanel() {
    
    double[][] layout = new double[][] { 
      { TableLayout.FILL, Constants.BUTTON_WIDTH, TableLayout.FILL }, 
      { TableLayout.FILL, Constants.BUTTON_HEIGHT, Constants.BUTTON_HEIGHT, Constants.BUTTON_HEIGHT, TableLayout.FILL } };
    setLayout(new TableLayout(layout) );
    
    newGame = new MenuButton("New Game");
    exit = new MenuButton("Exit");
    
    this.add(newGame, "1,1");
    this.add(exit, "1, 3");
    
    newGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        if( listener != null ) {
          listener.menuAction(MenuListener.NEW_GAME, null);
        }
      }
    } );
    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        if( listener != null ) {
          listener.menuAction(MenuListener.EXIT, null);
        }
      }
    } );
  }
  public void setMenuListener( MenuListener listener ) {
    this.listener = listener;
  }
}
