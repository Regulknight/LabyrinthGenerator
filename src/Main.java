import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.List;

public class Main extends JFrame {
    private static final WorldMap map = new WorldMap(100, 100);
    private static final int CELL_SIZE = 7;
    private static final double ROTATE_ANGLE = Math.PI/8;
    private static final double VIEW_ANGLE = Math.PI/2;

    private static int coneRadius = 15;
    private static Angle angle = new Angle(0);

    private static int playerX = 25;
    private static int playerY = 25;

    public Main() {
        super();

        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void paint(Graphics g) {
        System.out.println("Draw map");
        drawWorldMap(g, map);
    }

    public static void main(String[] args) {
        JFrame frame = new Main();

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_UP) {
                    coneRadius++;
                    updateMap(frame);
                }

                if (keyCode == KeyEvent.VK_DOWN) {
                    coneRadius--;
                    updateMap(frame);
                }

                if (keyCode == KeyEvent.VK_RIGHT) {
                    angle = new Angle(angle.getAngle() - ROTATE_ANGLE);
                    updateMap(frame);
                }

                if (keyCode == KeyEvent.VK_LEFT) {
                    angle = new Angle(angle.getAngle() + ROTATE_ANGLE);
                    updateMap(frame);
                }

                if (keyCode == KeyEvent.VK_D) {
                    playerX++;
                    updateMap(frame);
                }

                if (keyCode == KeyEvent.VK_A) {
                    playerX--;
                    updateMap(frame);
                }

                if (keyCode == KeyEvent.VK_W) {
                    playerY--;
                    updateMap(frame);
                }

                if (keyCode == KeyEvent.VK_S) {
                    playerY++;
                    updateMap(frame);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public void drawWorldMap(Graphics g, WorldMap map) {
        for (List<Cell> row: map.getMap()) {
            for (Cell cell: row) {
                drawCell(g, cell);
            }
        }
    }

    private static void markConeOfView(Cell observer, ConeOfView coneOfView) {
        for (List<Cell> row: map.getMap()) {
            for (Cell cell: row) {
                if (coneOfView.isPointContainInCone(getPointFromCell(observer), getPointFromCell(cell))) {
                    cell.setColor(Color.YELLOW);
                }
            }
        }
    }

    private static void markPlayer(int x, int y) {
        map.getMap().get(y).get(x).setColor(Color.RED);
    }

    private static void clearMap(JFrame frame) {
        for (List<Cell> row: map.getMap()) {
            for (Cell cell: row) {
                cell.setColor(Color.DARK_GRAY);
            }
        }
        frame.repaint();
    }

    private static Point2D getPointFromCell(Cell cell) {
        return new Point2D.Double(cell.getX(), cell.getY());
    }

    private void drawCell(Graphics g, Cell cell) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(cell.getCellColor());

        g2d.fill3DRect(cell.getX() * CELL_SIZE, cell.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, true);
    }

    private static void updateMap(JFrame frame) {
        clearMap(frame);
        Sector sector = new Sector(angle, VIEW_ANGLE);
        ConeOfView coneOfView = new ConeOfView(coneRadius, sector);
        markConeOfView(map.getCell(playerX, playerY), coneOfView);
        markPlayer(playerX, playerY);
        frame.repaint();
    }
}
