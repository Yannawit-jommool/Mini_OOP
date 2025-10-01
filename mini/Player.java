import java.awt.Color;

public class Player {
    private String name;
    private Color color;
    private int position;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.position = 1; // เริ่มที่ช่องแรก
    }

    public String getName() { return name; }
    public Color getColor() { return color; }
    public int getPosition() { return position; }
    public void setPosition(int pos) { this.position = pos; }
}
