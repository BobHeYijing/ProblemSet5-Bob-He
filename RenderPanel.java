/**
 * Introduction: The game is called Double Snake Game, and the rules are quite different from the normal
 * snake games. In this program, we have two snakes, each is controlled by  different key controllers: up,
 * down , left, right for the yellow snake and w, s, a, d for the blue snake. Press the space to start the
 * game, each player has three lives (chances) at the beginning, if one of the snake's head bump into its
 * own body, or the other snake's body, or the margin, then the player would lose one chance (life). As one
 * of the player lost all three chances, the game ended and the winner would be printed on the screen. We
 * can press the space button to restart the game.
 *
 * @author: Sienna Mao, Bob He
 */

package doublesnake;
// Importing necessary tools.
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.*;

public class RenderPanel extends JPanel implements ActionListener, KeyListener {
    public Timer timer;
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    public int direction = DOWN, direction2 = UP;
    public Random random;
    // Set initial stage of the game.
    public boolean over = true;
    public boolean start = false;
    // Set two snakes' body-length.
    public int length = 20;
    public int length2 = 20;
    // Set game scale.
    private final int HEIGHT = 800;
    private final int WIDTH = 800;
    private final int SCALE = 10;
    private final int gameScale = HEIGHT * WIDTH;
    // Set arrays for the storages of Snake body parts.
    private int[] a = new int[gameScale];
    private int[] b = new int[gameScale];
    private int[] c = new int[gameScale];
    private int[] d = new int[gameScale];
    // Set two snakes and their positions using the methods in Snake class.
    private doublesnake.Snake snake = new doublesnake.Snake(a, b, SCALE, 200);
    private doublesnake.Snake snake2 = new doublesnake.Snake(c, d, SCALE, 600);
    Graphics Graphics;
    // Each player has three lives (chances).
    private int lives1 = 3;
    private int lives2 = 3;

    // Establish basic backgrounds and attributes.
    public RenderPanel() {
        setBackground(Color.black);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        setFocusable(true);
        // Set timer! change delay numbers to set the speed of snakes directly, it's a simpler way than set and get speed in Snake class.
        timer = new Timer(30, this);
        random = new Random();
    }

    @Override
    public void paintComponent(Graphics g) {
        // Set Super to make the running of the painting as the priority.
        super.paintComponent(g);
        //Establishing Graphics
        Graphics = g;

        // Set the color and draw the snakes.
        // Draw snake1 as yellow color rectangle.
        g.setColor(Color.yellow);
        for (int i = length - 1; i >= 0; i--) {
            g.fillRect(a[i], b[i], SCALE, SCALE);
        }

        // Draw snake2 as cyan (blue) color rectangle.
        g.setColor(Color.cyan);
        for (int j = length2 - 1; j >= 0; j--) {
            g.fillRect(c[j], d[j], SCALE, SCALE);
        }

        // Set color and font and draw the instruction and signals.
        g.setColor(Color.white);
        g.setFont(new Font("Sans Serif", Font.BOLD, 50));
        if (over && !start){
            g.drawString("Press Space to Start", 140, 400);
        }

        // Check and update the life status for each player.
        checkWinner();
        if (gameEnd()) {
            g.drawString("Y Life "+ lives1, 100, 100);
            g.drawString("B Life "+ lives2, 500, 100);
            gameOver(Graphics);
        }
        else{
            g.drawString("Y Life "+ lives1, 100, 100);
            g.drawString("B Life "+ lives2, 500, 100);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    // check whether the game should end or not: when any player losses three times update the end of the game.
    boolean gameEnd() {
        if ((lives1 == 0) && (lives2 == 0)){
            return true;
        }
        if (lives1 <= 0){
            return true;
        }
        if (lives2 <= 0){
            return true;
        }

        return false;
    }

    // Constantly checking the competition and judge the winner for every turn, the loser would be minus one chance.
    String checkWinner() {
        // When yellow snake's head crush into itself.
        for (int i = 2; i < length; i++) {
            if (a[0] == a[i - 1] && b[0] == b[i - 1]) {
                lives1--;
                restart();
            }
        }
        // When blue snake's head crush into itself.
        for (int i = 2; i < length2; i++) {
            if (c[0] == c[i - 1] && d[0] == d[i - 1]) {
                lives2--;
                restart();
            }
        }
        // When yellow snake's head crush into blue snake's body.
        for (int i = 1; i < length2; i++) {
            if (a[0] == c[i] && b[0] == d[i]) {
                lives1--;
                restart();
            }
        }
        // When blue snake's head crush into yellow snake's body.
        for (int i = 1; i < length; i++) {
            if (c[0] == a[i] && d[0] == b[i]) {
                lives2--;
                restart();
            }
        }
        // When two snakes crush into each other by head at the same time.
        if (a[0] == c[0] && b[0] == d[0]) {
            lives1--;
            lives2--;
            restart();
        }
        // When they hit the edges together.
        if (b[0] <= 0 || b[0] >= HEIGHT || a[0] <= 0 || a[0] >= WIDTH) {
            if (d[0] <= 0 || d[0] >= HEIGHT || c[0] <= 0 || c[0] >= WIDTH) {
                lives1--;
                lives2--;
                restart();
            }
            // When Yellow hit the edge while blue snake is still alive.
            else {
                lives1--;
                restart();
            }
        }
        // When Blue hit the edge.
        if (d[0] <= 0 || d[0] >= HEIGHT || c[0] <= 0 || c[0] >= WIDTH) {
            lives2--;
            restart();
        }

        // Judge the final winner based on life amounts.
        if ((lives1 == 0) && (lives2 == 0)){
            return "It is a Tie!";
        }
        if (lives1 <= 0){
            return "Blue Win";
        }
        if (lives2 <= 0){
            return "Yellow Win";
        }
        return null;
    }

    // When game over, stop timer and print instructions.
    void gameOver(Graphics g) {
        timer.stop();
        g.setColor(Color.white);
        g.setFont(new Font("Sans Serif", Font.BOLD, 50));
        g.drawString("GAME OVER!", 240, 400);
        g.drawString(checkWinner(), 280, 500);
        g.setFont(new Font("Sans Serif", Font.BOLD, 30));
        g.drawString("Press Space to Restart", 240, 550);
    }

    // When Restart, reset the snake object and storage of body parts, and start timer.
    void restart(){
        direction = DOWN;
        direction2 = UP;
        a = new int[gameScale];
        b = new int[gameScale];
        c = new int[gameScale];
        d = new int[gameScale];
        snake = new doublesnake.Snake(a, b, SCALE, 200);
        snake2 = new doublesnake.Snake(c, d, SCALE, 600);

        timer.start();
    }

    @Override
    // Set the class connected with key pressed class, for the key controllers are used for controlling action performedof the snakes.
    public void actionPerformed(ActionEvent e) {
        repaint();

        // call move methods from Snake class to change the values of arrays for two snakes.
        snake.move(a, b);
        snake2.move(c, d);

        // Turning Mechanisms of snake1.
        if (direction == UP) {
            b[0] = b[0] - SCALE;
        }
        if (direction == DOWN) {
            b[0] = b[0] + SCALE;
        }
        if (direction == LEFT) {
            a[0] = a[0] - SCALE;
        }
        if (direction == RIGHT) {
            a[0] = a[0] + SCALE;
        }

        // Turning Mechanisms of snake2.
        if (direction2 == UP) {
            d[0] = d[0] - SCALE;
        }
        if (direction2 == DOWN) {
            d[0] = d[0] + SCALE;
        }
        if (direction2 == LEFT) {
            c[0] = c[0] - SCALE;
        }
        if (direction2 == RIGHT) {
            c[0] = c[0] + SCALE;
        }
        repaint();
    }

    @Override
    // Set two key controllers to control two snakes' moving directions.
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();

        // Key controllers for snake1, with up, down, left, right button.
        if (i == KeyEvent.VK_LEFT && direction != RIGHT) {
            direction = LEFT;
        }

        if (i == KeyEvent.VK_RIGHT && direction != LEFT) {
            direction = RIGHT;
        }

        if (i == KeyEvent.VK_UP && direction != DOWN) {
            direction = UP;
        }

        if (i == KeyEvent.VK_DOWN && direction != UP) {
            direction = DOWN;
        }

        // Key controllers for snake2, with w, s, a, d button.
        if (i == KeyEvent.VK_A && direction2 != RIGHT) {
            direction2 = LEFT;
        }

        if (i == KeyEvent.VK_D && direction2 != LEFT) {
            direction2 = RIGHT;
        }

        if (i == KeyEvent.VK_W && direction2 != DOWN) {
            direction2 = UP;
        }

        if (i == KeyEvent.VK_S && direction2 != UP) {
            direction2 = DOWN;
        }

        // When Space pressed, start the game or reset the game.
        if (i == KeyEvent.VK_SPACE) {
            if (!start){
                start = true;
            }
            if (over) {
                restart();
                lives1 = 3;
                lives2 = 3;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}