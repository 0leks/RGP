package gui;

import java.awt.*;

import javax.swing.*;

import one.Panel;

public class GamePanel extends JPanel {
  
  /** change to true when switching to new game ui */
  private static boolean MODE = false;

  private Panel gamePanel;
  private JPanel UIRight;
  private double TOP_BORDER = 0.03f;
  private double BOT_BORDER = 0.03f;
  private double LEFT_BORDER = 0.02f;
  private double RIGHT_BORDER = 0.02f;
  private int UI_WIDTH = 200;
  public GamePanel() {
    setLayout(null);
    UIRight = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.blue);
        g.fillRect(10, 20, 30, 40);
      }
    };
    UIRight.setFocusable(false);
    UIRight.setBackground(Color.WHITE);
    this.setFocusable(false);
    this.setBackground(new Color(10, 0, 50));
  }
  public void addGamePanel(Panel gamePanel) {
    this.gamePanel = gamePanel;
    this.add(gamePanel);
    this.add(UIRight);
  }
  @Override
  public void doLayout() {
    super.doLayout();
    if( gamePanel != null ) {
      int borderWidth = (int) (getWidth()*RIGHT_BORDER);
      if( MODE ) {
        gamePanel.setBounds((int)(getWidth()*LEFT_BORDER), (int)(getHeight()*TOP_BORDER), (int)(getWidth() - 3*borderWidth - UI_WIDTH), (int)(getHeight()*(1.0 - TOP_BORDER - BOT_BORDER)));
        UIRight.setBounds((int)(getWidth() - borderWidth - UI_WIDTH), (int)(getHeight()*TOP_BORDER), UI_WIDTH, (int)(getHeight()*(1.0 - TOP_BORDER - BOT_BORDER)));
      }
      else {
        gamePanel.setBounds((int)(getWidth()*LEFT_BORDER), (int)(getHeight()*TOP_BORDER), (int)(getWidth() - 2*borderWidth), (int)(getHeight()*(1.0 - TOP_BORDER - BOT_BORDER)));
      }
      gamePanel.requestFocusInWindow();
    }
  }
  
}
