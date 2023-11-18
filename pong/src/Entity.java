import java.awt.*;
import java.util.Random;

public class Entity extends Rectangle { // class for all the moving objects in game
    int x, y, width, height, speed, score;
    Color backgroundColor;
    // for player objects
    public Entity(int x, int y, int width, int height, int speed, int score, Color bg) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.score = score;
        this.backgroundColor = bg;
    }

    // for ball object
    int xVelocity, yVelocity;
    public Entity(int x, int y, int width, int height, int speed, Color bg) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.backgroundColor = bg;
    }
    public void init() {
        Random random = new Random();
        int randomXDirection = random.nextBoolean() ? -1 : 1; // Randomly select -1 or 1
        int randomYDirection = random.nextBoolean() ? -1 : 1; // Randomly select -1 or 1

        // Adjust x velocity based on the relative positions of the ball and players
        if (x < 800 / 2) {
            // Ball is on the left side, move towards player2
            xVelocity = Math.abs(randomXDirection); // Set xVelocity to a positive value
        } else {
            // Ball is on the right side, move towards player1
            xVelocity = -Math.abs(randomXDirection); // Set xVelocity to a negative value
        }

        // Adjust y velocity based on the relative positions of the ball and players
        if (y + height / 2 < 500 / 2) {
            // Ball is above the center, move downwards
            yVelocity = Math.abs(randomYDirection); // Set yVelocity to a positive value
        } else {
            // Ball is below the center, move upwards
            yVelocity = -Math.abs(randomYDirection); // Set yVelocity to a negative value
        }

        move();
    }


    public void move() {
        x += speed * xVelocity;
        y += speed * yVelocity;
    }
}
