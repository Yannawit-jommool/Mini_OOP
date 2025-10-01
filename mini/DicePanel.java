import java.awt.*;
import javax.swing.*;

public class DicePanel extends JPanel {
    private int value = 1;

    public void setValue(int value) {
        this.value = value;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = Math.min(getWidth(), getHeight()) - 10;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(5, 5, size, size);

        int dotSize = size / 5;
        int centerX = getWidth() / 2 - dotSize / 2;
        int centerY = getHeight() / 2 - dotSize / 2;

        switch (value) {
            case 1 -> drawDot(g, centerX, centerY, dotSize);
            case 2 -> {
                drawDot(g, centerX - size / 4, centerY - size / 4, dotSize);
                drawDot(g, centerX + size / 4, centerY + size / 4, dotSize);
            }
            case 3 -> {
                drawDot(g, centerX - size / 4, centerY - size / 4, dotSize);
                drawDot(g, centerX, centerY, dotSize);
                drawDot(g, centerX + size / 4, centerY + size / 4, dotSize);
            }
            case 4 -> {
                drawDot(g, centerX - size / 4, centerY - size / 4, dotSize);
                drawDot(g, centerX + size / 4, centerY - size / 4, dotSize);
                drawDot(g, centerX - size / 4, centerY + size / 4, dotSize);
                drawDot(g, centerX + size / 4, centerY + size / 4, dotSize);
            }
            case 5 -> {
                drawDot(g, centerX - size / 4, centerY - size / 4, dotSize);
                drawDot(g, centerX + size / 4, centerY - size / 4, dotSize);
                drawDot(g, centerX, centerY, dotSize);
                drawDot(g, centerX - size / 4, centerY + size / 4, dotSize);
                drawDot(g, centerX + size / 4, centerY + size / 4, dotSize);
            }
            case 6 -> {
                drawDot(g, centerX - size / 4, centerY - size / 4, dotSize);
                drawDot(g, centerX + size / 4, centerY - size / 4, dotSize);
                drawDot(g, centerX - size / 4, centerY, dotSize);
                drawDot(g, centerX + size / 4, centerY, dotSize);
                drawDot(g, centerX - size / 4, centerY + size / 4, dotSize);
                drawDot(g, centerX + size / 4, centerY + size / 4, dotSize);
            }
        }
    }

    private void drawDot(Graphics g, int x, int y, int size) {
        g.fillOval(x, y, size, size);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }
}
