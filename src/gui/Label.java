package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class Label extends JLabel {

  private Color fillColor = Constants.LABEL_BACKGROUND_COLOR;
  public Label(String text) {
    super(text);
  }
  @Override
  public void paintComponent(Graphics g) {
    g.setColor(fillColor);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Constants.LABEL_TEXT_COLOR);
    g.setFont(Constants.BUTTON_FONT);
    FontMetrics fm = g.getFontMetrics();
    g.drawString(getText(), (getWidth() - fm.stringWidth(getText()))/2, (getHeight() + g.getFont().getSize()-6)/2);
  }
  @Override
  public void paintBorder(Graphics g) {
    g.setColor(Color.white);
    Graphics2D g2d = (Graphics2D)g;
    g2d.setStroke(new BasicStroke(3));
    
    g.drawRect(0, 0, getWidth()-1, getHeight()-1);
  }
}
