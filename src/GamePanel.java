import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    Player player1, player2;
    Ball ball;

    public GamePanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(keyHandler);
    }

    public void init() {
        gameThread = new Thread(this);
        player1 = new Player(5, getHeight() / 2, 10, 70, 5);
        player2 = new Player(getWidth() - 15, getHeight() / 2, 10, 70, 5);
        ball = new Ball(getWidth() / 2, getHeight() / 2, 5);

        int initialDirection = Math.random() < 0.5 ? -1 : 1; // randomly choose left (-1) or right (1)
        int initialSpeed = 2 + (int) (Math.random() * 3); // random initial speed between 2 and 4
        ball.dx = initialDirection * initialSpeed;
        ball.dy = 0;

        gameThread.start();
    }

    public void run() {
        // game loop
        while (gameThread != null) {
            update();
            repaint();

            try {
                Thread.sleep(1_000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        // player 1
        if (keyHandler.wActive && player1.y > 0) {
            player1.y -= player1.speed;
        }
        if (keyHandler.sActive && player1.y + player1.height < getHeight()) {
            player1.y += player1.speed;
        }
        // player 2
        if (keyHandler.upActive && player2.y > 0) {
            player2.y -= player2.speed;
        }
        if (keyHandler.downActive && player2.y + player2.height < getHeight()) {
            player2.y += player2.speed;
        }

        if (keyHandler.restart) {
            gameThread = null;
            Main main = new Main();
        }

        // Update ball
        ball.y += ball.dy;
        ball.x += ball.dx;

        // checking to see if ball touches bounds
        if (ball.y - ball.radius <= 0 || ball.y + ball.radius >= getHeight()) {
            ball.dy = -ball.dy;
        }

        // checking collision with players
        if (ball.x - ball.radius <= player1.x + player1.width &&
                ball.y >= player1.y &&
                ball.y <= player1.y + player1.height) {
            // if ball touches a player then increase speed & make it bounds opposite direction
            ball.dx = -ball.dx;
            ball.speed *= 1.1; // speed increase
        }

        if (ball.x + ball.radius >= player2.x &&
                ball.y >= player2.y &&
                ball.y <= player2.y + player2.height) {
            ball.dx = -ball.dx;
            ball.speed *= 1.1;
        }

        // checking ball collision left & right bounds. End game if either side is touched
        if (ball.x - ball.radius <= 0) {
            gameThread = null;
            return;
        } else if (ball.x + ball.radius >= getWidth()) {
            gameThread = null;
            return;
        }

        ball.y += ball.dy * ball.speed;
        ball.x += ball.dx * ball.speed;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(player1.x, player1.y, player1.width, player1.height);
        g2.fillRect(player2.x, player2.y, player2.width, player2.height);
        g2.fillOval(ball.x - ball.radius, ball.y - ball.radius, 2 * ball.radius, 2 * ball.radius);
        g2.dispose();
    }

}