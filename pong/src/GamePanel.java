import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    Thread game; // game loop thread
    KeyHandler key; // key input handler
    Entity player1, player2, ball; // game objects
    public GamePanel() {
        setPreferredSize(new Dimension(800, 500));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);

        init();
    }
    public void init() { // initialize the objects
        game = new Thread(this);
        key = new KeyHandler();
        addKeyListener(key); // add key as the key input listener
        // for some reason `getHeight()` & `getWidth()` don't work below hence why I manually wrote width & height
        player1 = new Entity(0, 500 / 2 - 100, 20, 100, 5, 0, Color.RED); // 800 & 500 are width & height
        player2 = new Entity(800 - 35, 500 / 2, 20, 100, 5, 0, Color.BLUE); // `800-35` idk it just works
        ball = new Entity(800 / 2 - 20, 500 / 2 - 20, 20, 20, 5, Color.WHITE); // takeaway width & height from x & y
        ball.init();
        game.start(); // begin the game
    }

    @Override
    public void run() {
        while (game != null) { // game loop
            update();
            repaint(); // repaint components, calls `paintComponents()`

            try {
                Thread.sleep(1_000 / 60); // 60 = fps limit
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void update() { // update all entity x & y
        // player 1
        if (key.wActive && player1.y > 0) { // prevents player 1 from leaving map
            player1.y -= player1.speed;
        }
        if (key.sActive && player1.y + player1.height + player1.speed < getHeight()) {
            player1.y += player1.speed;
        }
        // player 2
        if (key.upActive && player2.y > 0) {
            player2.y -= player2.speed;
        }
        if (key.downActive && player2.y + player2.height + player2.speed < getHeight()) {
            player2.y += player2.speed;
        }

        if (ball.xVelocity == 0 && ball.yVelocity == 0) { // to keep the ball moving at all times
            Random random = new Random();
            int randomXDirection = random.nextInt(2) == 0 ? -1 : 1;
            int randomYDirection = random.nextInt(2) == 0 ? -1 : 1;
            ball.xVelocity = randomXDirection;
            ball.yVelocity = randomYDirection;
        }

        // ball border collision
        if (ball.y <= 0) {
            ball.yVelocity *= -1;
        }
        if (ball.y >= getHeight() - ball.height) {
            ball.yVelocity *= -1;
        }

        // ball player collision for player 1
        if (ball.x - ball.width / 2 <= player1.x + player1.width &&
                ball.y >= player1.y &&
                ball.y <= player1.y + player1.height) {
            // if ball touches a player then increase speed & make it bounce in the opposite direction
            ball.xVelocity = -ball.xVelocity;
            ball.speed *= 1.1; // speed increase
        }
        // ball collision for player 2
        if (ball.x + ball.width / 2 >= player2.x &&
                ball.y >= player2.y &&
                ball.y <= player2.y + player2.height) {
            ball.xVelocity = -ball.xVelocity;
            ball.speed *= 1.1;
        }


        // score
        if (ball.x < 0) {
            player2.score += 1;
            reset();
        }
        if (ball.x > 800 - ball.width) {
            player1.score += 1;
            reset();
        }

        ball.move();
    }

    public void paintComponent(Graphics g) { // draw entities
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Verdana", Font.BOLD, 25));
        // player 1
        g2.setColor(player1.backgroundColor);
        g2.fillRect(player1.x, player1.y, player1.width, player1.height);
        g2.drawString(String.valueOf(player1.score), 300, 30);
        // player 2
        g2.setColor(player2.backgroundColor);
        g2.fillRect(player2.x, player2.y, player2.width, player2.height);
        g2.drawString(String.valueOf(player2.score), 500, 30);
        // ball
        g2.setColor(ball.backgroundColor);
        g2.fillOval(ball.x, ball.y, ball.width, ball.height);
        g2.dispose();

    }

    public void reset() { // resets y pos of all entities so game can restart (& x for ball)
        try {
            Thread.sleep(500);
        } catch (Exception ignore) {

        }
        player1.y = 500 / 2 - 100;
        player2.y = 500 / 2 - 100;
        ball.x = 800 / 2 - 20;
        ball.y = 500 / 2 - 20;
    }
}
