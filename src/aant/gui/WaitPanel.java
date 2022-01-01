package aant.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class WaitPanel extends JPanel {

    String TEXT = "Searching ...";
    String textProgress;

    public WaitPanel() {
        textProgress = "";
    }

    @Override
    public void paint(Graphics g) {
        int font_h = g.getFontMetrics().getHeight();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawString(TEXT, this.getWidth() / 2 - g.getFontMetrics().stringWidth(TEXT) / 2, this.getHeight() / 2);
        g.drawString(textProgress, this.getWidth() / 2 - g.getFontMetrics().stringWidth(textProgress) / 2, this.getHeight() / 2 + font_h);
    }

    public void setTextProgress(String textProgress) {
        this.textProgress = textProgress;
    }
}
