package gui;

import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import gui.Label;
import resources.*;

public class ContinueGameMenuPanel extends JPanel {
  
  private JButton back;
  private JButton start;
  private Label info;
  private MenuListener listener;
  private ActionListener saveButtonListener;
  private ButtonGroup group;
  private SaveInstance selectedSave;
  private void setUpClassButton(JRadioButton button) {
    button.addActionListener(saveButtonListener);
    button.setHorizontalAlignment(SwingConstants.CENTER);
    button.setFocusable(false);
    group.add(button);
  }
  
  private class SaveButton extends RadioButton {
    private SaveInstance save;
    public SaveButton(SaveInstance save) {
      super(save.getFileNameNoExtension());
      this.save = save;
    }
    public String getInfoText() {
      return save.getRace() + "    " + NumberConverter.reduce(save.getXP()) + " exp    $" + NumberConverter.reduce(save.getMoney());
    }
  }
  public ContinueGameMenuPanel() {
    List<SaveInstance> saves = SaveManager.getSaves();
    group = new ButtonGroup();
    saveButtonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        info.setText(((SaveButton)e.getSource()).getInfoText());
        selectedSave = ((SaveButton)e.getSource()).save;
      }
    };

    JPanel classPanel = new JPanel();
    classPanel.setLayout(new BoxLayout(classPanel, BoxLayout.LINE_AXIS));
    classPanel.add(Box.createHorizontalGlue());
    
    SaveButton first = null;
    for( SaveInstance save : saves ) {
      save.loadInfo();
      SaveButton button = new SaveButton(save);
      setUpClassButton(button);
      setupClassButton(button);
      classPanel.add(button);
      if( first == null ) {
        first = button;
      }
    }
    classPanel.add(Box.createHorizontalGlue());
    
    back = new MenuButton("Back");
    back.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        if( listener != null ) {
          listener.menuAction(MenuListener.BACK, null);
        }
      }
    });
    start = new MenuButton("Load");
    start.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        if( listener != null ) {
          listener.menuAction(MenuListener.START, selectedSave);
        }
      }
    });
    

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    add(Box.createVerticalGlue());
    setupClassButton(back);
    add(back);
    add(Box.createVerticalStrut(Constants.BUTTON_HEIGHT));
    JLabel instructions = new Label("Choose your save");
    setupClassButton(instructions);
    setComponentSize(instructions, Constants.BUTTON_WIDTH*3, Constants.BUTTON_HEIGHT);
    add(instructions);
    add(classPanel);
    // TODO display info about the selected class such as str, agi, health, etc.
    info = new Label("");
    setupClassButton(info);
    setComponentSize(info, Constants.BUTTON_WIDTH*3, Constants.BUTTON_HEIGHT*2);
    add(info);
    setupClassButton(start);
    add(Box.createVerticalStrut(Constants.BUTTON_HEIGHT));
    add(start);
    add(Box.createVerticalGlue());
    if( first != null ) {
      first.doClick();
    }
    else {
      info.setText("No saves found");
    }
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
