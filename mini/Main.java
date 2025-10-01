import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Main {
    private static final int SIZE = 4; // กระดาน 4x4
    private static JPanel[][] cells = new JPanel[SIZE][SIZE];

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snakes and Ladders 4x4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        // สร้างกระดาน
        JPanel boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        for (int row = SIZE - 1; row >= 0; row--) {
            for (int col = 0; col < SIZE; col++) {
                JPanel cell = new JPanel();
                cell.setBorder(BorderFactory.createLineBorder(Color.black));
                cell.setBackground(Color.white);
                cells[row][col] = cell;
                boardPanel.add(cell);
            }
        }

        // Panel ควบคุม
        JPanel controlPanel = new JPanel();
        JTextField player1Name = new JTextField("Player 1", 8);
        JTextField player2Name = new JTextField("Player 2", 8);
        JButton startBtn = new JButton("Start");
        JButton rollBtn = new JButton("Roll Dice");
        rollBtn.setEnabled(false);

        DicePanel dicePanel = new DicePanel();
        JTextArea log = new JTextArea(5, 30);
        log.setEditable(false);

        controlPanel.add(player1Name);
        controlPanel.add(player2Name);
        controlPanel.add(startBtn);
        controlPanel.add(rollBtn);
        controlPanel.add(dicePanel);

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(log), BorderLayout.SOUTH);

        // ผู้เล่น
        Player[] players = new Player[2];
        int[] turn = {0};
        Random rand = new Random();

        // กระดาน
        Board board = new Board();

        // Start button
        startBtn.addActionListener(e -> {
            players[0] = new Player(player1Name.getText(), Color.RED);
            players[1] = new Player(player2Name.getText(), Color.BLUE);
            players[0].setPosition(1);
            players[1].setPosition(1);

            log.setText(players[0].getName() + " starts!\n");
            rollBtn.setEnabled(true);
            clearBoard();
            drawSnakesAndLadders(board);
            updateBoard(players, board);
        });

        // Roll Dice button
        rollBtn.addActionListener(e -> {
            Player current = players[turn[0]];
            int roll = rand.nextInt(6) + 1;
            dicePanel.setValue(roll);

            int newPos = current.getPosition() + roll;
            if (newPos > SIZE * SIZE) {
                newPos = current.getPosition(); // ถ้าเกินอยู่ที่เดิม
            }

            newPos = board.checkPosition(newPos, log, current);

            current.setPosition(newPos);
            log.append(current.getName() + " rolled " + roll + " -> " + newPos + "\n");

            updateBoard(players, board);

            if (newPos == SIZE * SIZE) {
                log.append(current.getName() + " wins!\n");
                rollBtn.setEnabled(false);
            } else {
                turn[0] = 1 - turn[0];
                log.append("Next turn: " + players[turn[0]].getName() + "\n");
            }
        });

        frame.setVisible(true);
    }

    private static void clearBoard() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                cells[r][c].removeAll();
                cells[r][c].setBackground(Color.white);
                cells[r][c].repaint();
            }
        }
    }

    private static void drawSnakesAndLadders(Board board) {
        // วาดบันได
        for (int start : board.ladders.keySet()) {
            int[] rc = getCell(start);
            cells[rc[0]][rc[1]].setBackground(Color.GREEN);
        }

        // วาดงู
        for (Map.Entry<Integer, Integer> entry : board.snakes.entrySet()) {
            int head = entry.getKey();
            int tail = entry.getValue();

            int[] headRC = getCell(head);
            int[] tailRC = getCell(tail);

            cells[headRC[0]][headRC[1]].setBackground(Color.DARK_GRAY); // หัวเข้ม
            cells[tailRC[0]][tailRC[1]].setBackground(Color.LIGHT_GRAY); // หางอ่อน
        }
    }

    private static void updateBoard(Player[] players, Board board) {
        clearBoard();
        drawSnakesAndLadders(board);

        for (Player p : players) {
            int pos = p.getPosition();
            if (pos > 0) {
                int[] rc = getCell(pos);
                JPanel cell = cells[rc[0]][rc[1]];

                JPanel piece = new JPanel();
                piece.setBackground(p.getColor());
                piece.setPreferredSize(new Dimension(20, 20));

                cell.add(piece);
                cell.revalidate();
                cell.repaint();
            }
        }
    }

    private static int[] getCell(int pos) {
        int row = SIZE - 1 - (pos - 1) / SIZE;
        int col = (pos - 1) % SIZE;
        if (((SIZE - 1 - row) % 2) == 1) {
            col = SIZE - 1 - col;
        }
        return new int[]{row, col};
    }
}
