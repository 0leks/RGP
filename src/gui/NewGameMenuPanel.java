package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import layout.TableLayout;

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
    
    double[][] classLayout = new double[][] {
      { TableLayout.FILL, Constants.BUTTON_WIDTH, Constants.BUTTON_WIDTH, Constants.BUTTON_WIDTH, Constants.BUTTON_WIDTH, Constants.BUTTON_WIDTH, Constants.BUTTON_WIDTH, Constants.BUTTON_WIDTH, TableLayout.FILL },
      { TableLayout.FILL, Constants.BUTTON_HEIGHT, TableLayout.FILL}};
    JPanel classPanel = new JPanel();
    classPanel.setLayout(new TableLayout(classLayout));
    classPanel.add(human, "1, 1");
    classPanel.add(elf, "2, 1");
    classPanel.add(dwarf, "3, 1");
    classPanel.add(scholar, "4, 1");
    classPanel.add(assassin, "5, 1");
    classPanel.add(warrior, "6, 1");
    classPanel.add(baal, "7, 1");
    
    double[][] layout = new double[][] {
      { TableLayout.FILL, Constants.BUTTON_WIDTH*2, Constants.BUTTON_WIDTH, Constants.BUTTON_WIDTH, Constants.BUTTON_WIDTH, Constants.BUTTON_WIDTH*2, TableLayout.FILL },
      { TableLayout.FILL, Constants.BUTTON_HEIGHT, Constants.BUTTON_HEIGHT, Constants.BUTTON_HEIGHT, Constants.BUTTON_HEIGHT, Constants.BUTTON_HEIGHT, Constants.BUTTON_HEIGHT, Constants.BUTTON_HEIGHT, Constants.BUTTON_HEIGHT, TableLayout.FILL}};
    setLayout( new TableLayout(layout) );
    
    add(back, "3,1");
    JLabel instructions = new Label("Choose your class");
    instructions.setHorizontalAlignment(SwingConstants.CENTER);
    add(instructions, "2,3,4,3");
    add(classPanel, "1,5,5,5");
    add(new Label(""), "1,6,5,6");
    add(start, "3,8");
    human.doClick();
  }
  public void setMenuListener( MenuListener listener ) {
    this.listener = listener;
  }
  
}
