package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JRadioButton;

public class RadioButton extends JRadioButton {
  private Color fillColor = Constants.BUTTON_IDLE_COLOR;
  
  public RadioButton(String text) {
    super(text);
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        fillColor = Constants.BUTTON_IDLE_COLOR;
        repaint();
      }
      @Override
      public void mouseEntered(MouseEvent e) {
        fillColor = Constants.BUTTON_HOVER_COLOR;
        repaint();
      }
      @Override
      public void mousePressed(MouseEvent e) {
        fillColor = Constants.BUTTON_PRESS_COLOR;
        repaint();
      }
      @Override
      public void mouseReleased(MouseEvent e) {
        fillColor = Constants.BUTTON_IDLE_COLOR;
        repaint();
      }
    });
    this.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        fillColor = Constants.BUTTON_HOVER_COLOR;
        repaint();
      }
    });
  }
  
  @Override
  public void paintComponent(Graphics g) {
    if( this.isSelected() ) {
      g.setColor(Constants.BUTTON_PRESS_COLOR);
    }
    else {
      g.setColor(fillColor);
    }
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Constants.BUTTON_TEXT_COLOR);
    g.setFont(Constants.BUTTON_FONT);
    FontMetrics fm = g.getFontMetrics();
    g.drawString(getText(), (getWidth() - fm.stringWidth(getText()))/2, (getHeight() + g.getFont().getSize()-6)/2);
  }
  @Override
  public void paintBorder(Graphics g) {
    g.setColor(Constants.BUTTON_BORDER_COLOR);
    Graphics2D g2d = (Graphics2D)g;
    g2d.setStroke(new BasicStroke(3));
    g.drawRect(0, 0, getWidth()-1, getHeight()-1);
  }
}
