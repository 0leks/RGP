package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

//import layout.TableLayout;

@SuppressWarnings("serial")
public class NewGameMenuPanel extends JPanel{
  
  private JRadioButton human;
  private JRadioButton elf;
  private JRadioButton dwarf;
  private JRadioButton scholar;
  private JRadioButton assassin;
  private JRadioButton warrior;
  private JRadioButton baal;
  private JButton back;
  private JButton start;
  private MenuListener listener;
  private String selectedClass = "";
  private ActionListener classButtonListener;
  private ButtonGroup group;
  private void setUpClassButton(JRadioButton button) {
    button.addActionListener(classButtonListener);
    button.setHorizontalAlignment(SwingConstants.CENTER);
    button.setFocusable(false);
    group.add(button);
  }
  public NewGameMenuPanel() {
    group = new ButtonGroup();
    classButtonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        selectedClass = e.getActionCommand();
      }
    };
    human = new RadioButton("Human");
    elf = new RadioButton("Elf");
    dwarf = new RadioButton("Dwarf");
    scholar = new RadioButton("Scholar");
    assassin = new RadioButton("Assassin");
    warrior = new RadioButton("Warrior");
    baal = new RadioButton("Baal");
    setUpClassButton(human);
    setUpClassButton(elf);
    setUpClassButton(dwarf);
    setUpClassButton(scholar);
    setUpClassButton(assassin);
    setUpClassButton(warrior);
    setUpClassButton(baal);
    
    back = new MenuButton("Back");
    back.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        if( listener != null ) {
          listener.menuAction(MenuListener.BACK, null);
        }
      }
    });
    start = new MenuButton("Start");
    start.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        if( listener != null ) {
          listener.menuAction(MenuListener.START, selectedClass);
        }
      }
    });
    
    JPanel classPanel = new JPanel();
    classPanel.setLayout(new BoxLayout(classPanel, BoxLayout.LINE_AXIS));
    classPanel.add(Box.createHorizontalGlue());
    setupClassButton(human);
    classPanel.add(human);
    setupClassButton(elf);
    classPanel.add(elf);
    setupClassButton(dwarf);
    classPanel.add(dwarf);
    setupClassButton(scholar);
    classPanel.add(scholar);
    setupClassButton(assassin);
    classPanel.add(assassin);
    setupClassButton(warrior);
    classPanel.add(warrior);
    setupClassButton(baal);
    classPanel.add(baal);
    classPanel.add(Box.createHorizontalGlue());

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    add(Box.createVerticalGlue());
    setupClassButton(back);
    add(back);
    add(Box.createVerticalStrut(Constants.BUTTON_HEIGHT));
    JLabel instructions = new Label("Choose your class");
    setupClassButton(instructions);
    setComponentSize(instructions, Constants.BUTTON_WIDTH*3, Constants.BUTTON_HEIGHT);
    add(instructions);
    add(classPanel);
    // TODO display info about the selected class such as str, agi, health, etc.
    Label info = new Label("");
    setupClassButton(info);
    setComponentSize(info, Constants.BUTTON_WIDTH*3, Constants.BUTTON_HEIGHT*2);
    add(info);
    setupClassButton(start);
    add(Box.createVerticalStrut(Constants.BUTTON_HEIGHT));
    add(start);
    add(Box.createVerticalGlue());
    human.doClick();
  }
  public void setupClassButton(Component button) {
	  ((JComponent) button).setAlignmentY(Component.CENTER_ALIGNMENT);
    ((JComponent) button).setAlignmentX(Component.CENTER_ALIGNMENT);
    setComponentSize(button, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
  }
  public static void setComponentSize(Component button, int width, int height) {
	  button.setPreferredSize(new Dimension(width, height));
	  button.setMinimumSize(new Dimension(width, height));
	  button.setMaximumSize(new Dimension(width, height));
  }
  public void setMenuListener( MenuListener listener ) {
    this.listener = listener;
  }
  
}
