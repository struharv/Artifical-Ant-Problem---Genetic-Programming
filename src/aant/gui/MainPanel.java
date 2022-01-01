package aant.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import aant.engine.Map;

public class MainPanel extends JPanel implements MouseListener{
    int SLIDE = 1;
    Map data = null;
    MainForm mainForm;

    public MainPanel(Map data, MainForm mainForm) {
        this.data = data;
        this.mainForm = mainForm;
        this.addMouseListener(this);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        setSize(new Dimension(400, 400));
        setMinimumSize(new Dimension(400, 400));
        setPreferredSize(new Dimension(400, 400));
        setMaximumSize(new Dimension(400, 400));
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);


        g.fillRect(0, 0,
                this.getWidth(), this.getHeight());

        if (data != null) {
            
            drawMap(g);
            drawGrid(g);
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
            drawAnt(g);
        }
    }

    public void drawGrid(Graphics g) {
        double w = (double) this.getWidth() / data.getWidth();
        double h = (double) this.getHeight() / data.getHeight();

        g.setColor(Color.BLACK);
        for (int i = 0; i < data.getWidth(); i++) {
            g.drawLine((int) Math.round(i * w), 0, (int) Math.round(i * w), this.getHeight());
        }
        for (int i = 0; i < data.getHeight(); i++) {
            g.drawLine(0, (int) Math.round(i * h), this.getWidth(), (int) Math.round(i * h));
        }
    }

    public void drawMap(Graphics g) {
        double w = (double) this.getWidth() / data.getWidth();
        double h = (double) this.getHeight() / data.getHeight();

        for (int y = 0; y < data.getHeight(); y++) {
            for (int x = 0; x < data.getWidth(); x++) {
                if (data.getMap()[y][x] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect((int) Math.round(x * w), (int) Math.round(y * h),
                            (int) Math.ceil(w), (int) Math.ceil(h));

                }
                if (data.getMap()[y][x] == -1) {
                    g.setColor(Color.PINK);
                    g.fillRect((int) Math.round(x * w), (int) Math.round(y * h),
                            (int) Math.ceil(w), (int) Math.ceil(h));

                }
            }
        }
    }

    public void drawAnt(Graphics g) {
        double w = (double) this.getWidth() / data.getWidth();
        double h = (double) this.getHeight() / data.getHeight();

            g.setColor(Color.WHITE);
            g.fillRect((int) Math.round(data.getAntX() * w), (int) Math.round(data.getAntY() * h),
                    (int) Math.round(w), (int) Math.round(h));

            g.setColor(Color.BLACK);
            if (data.getDirection() == 0) {
                g.drawLine((int) Math.round(data.getAntX() * w + w / 2), (int) Math.round(data.getAntY() * h + SLIDE),
                        (int) Math.round(data.getAntX() * w + w / 2), (int) Math.round(data.getAntY() * h + h - SLIDE));
                g.drawLine((int) Math.round(data.getAntX() * w + SLIDE), (int) Math.round(data.getAntY() * h + h - SLIDE),
                        (int) Math.round(data.getAntX() * w + w - SLIDE), (int) Math.round(data.getAntY() * h + h - SLIDE));
            }

            if (data.getDirection() == 2) {
                g.drawLine((int) Math.round(data.getAntX() * w + w / 2), (int) Math.round(data.getAntY() * h + SLIDE),
                        (int) Math.round(data.getAntX() * w + w / 2), (int) Math.round(data.getAntY() * h + h - SLIDE));
                g.drawLine((int) Math.round(data.getAntX() * w + SLIDE), (int) Math.round(data.getAntY() * h + SLIDE),
                        (int) Math.round(data.getAntX() * w + w - SLIDE), (int) Math.round(data.getAntY() * h + SLIDE));
            }

            if (data.getDirection() == 1) {
                g.drawLine((int) Math.round(data.getAntX() * w + SLIDE), (int) Math.round(data.getAntY() * h + SLIDE),
                        (int) Math.round(data.getAntX() * w + SLIDE), (int) Math.round(data.getAntY() * h + h - SLIDE));
                g.drawLine((int) Math.round(data.getAntX() * w + SLIDE), (int) Math.round(data.getAntY() * h + h / 2),
                        (int) Math.round(data.getAntX() * w + w - SLIDE), (int) Math.round(data.getAntY() * h + h / 2));
            }

            if (data.getDirection() == 3) {
                g.drawLine((int) Math.round(data.getAntX() * w + w - SLIDE), (int) Math.round(data.getAntY() * h + SLIDE),
                        (int) Math.round(data.getAntX() * w + w - SLIDE), (int) Math.round(data.getAntY() * h + h - SLIDE));
                g.drawLine((int) Math.round(data.getAntX() * w + SLIDE), (int) Math.round(data.getAntY() * h + h / 2),
                        (int) Math.round(data.getAntX() * w + w - SLIDE), (int) Math.round(data.getAntY() * h + h / 2));
            }
    }

    public void setData(Map data) {
        this.data = data;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (mainForm.isInterpretted()) {
            mainForm.mapReset();
        }
        double c_width = this.getWidth() / (double)data.getWidth();
        double c_height = this.getHeight() / (double)data.getHeight();
        int cx = (int)(e.getX() / c_width);
        int cy = (int)(e.getY() / c_height);
        if (data.map[cy][cx] == 1) {
        data.map[cy][cx] = 0;
        } else {
            data.map[cy][cx] = 1;
        }
        repaint();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
