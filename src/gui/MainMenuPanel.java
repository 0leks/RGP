package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

//import layout.TableLayout;

@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel {
  private JButton newGame;
  private JButton continueGame;
  private JButton exit;
  private MenuListener listener;
  public MainMenuPanel() {
    
//    double[][] layout = new double[][] { 
//      { TableLayout.FILL, Constants.BUTTON_WIDTH, TableLayout.FILL }, 
//      { TableLayout.FILL, Constants.BUTTON_HEIGHT, Constants.BUTTON_HEIGHT, Constants.BUTTON_HEIGHT, TableLayout.FILL } };
//    setLayout(new TableLayout(layout) );
//    setLayout(new GridLayout(2, 0, Constants.BUTTON_HEIGHT, Constants.BUTTON_WIDTH));
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    this.add(Box.createVerticalGlue());
    
    newGame = new MenuButton("New Game");
    newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
    newGame.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
    newGame.setMinimumSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
    newGame.setMaximumSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
    this.add(newGame);
    this.add(Box.createVerticalStrut(Constants.BUTTON_HEIGHT));
    

    continueGame = new MenuButton("Continue");
    continueGame.setAlignmentX(Component.CENTER_ALIGNMENT);
    continueGame.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
    continueGame.setMinimumSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
    continueGame.setMaximumSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
    this.add(continueGame);
    this.add(Box.createVerticalStrut(Constants.BUTTON_HEIGHT));
    
    exit = new MenuButton("Exit");
    exit.setAlignmentX(Component.CENTER_ALIGNMENT);
    exit.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
    exit.setMinimumSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
    exit.setMaximumSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
    this.add(exit);
    this.add(Box.createVerticalGlue());
    
    newGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        if( listener != null ) {
          listener.menuAction(MenuListener.NEW_GAME, null);
        }
      }
    } );
    continueGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        if( listener != null ) {
          listener.menuAction(MenuListener.CONTINUE, null);
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
