import java.util.Random;

public class Ball {
    int x, y, radius, speed;
    int dx, dy; // Movement direction
    Random random = new Random();

    public Ball(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = 1;
        initializeMovementDirection();
    }

    private void initializeMovementDirection() {
        // gen random numbers between -3 and 3 for dx and dy
        dx = random.nextInt(7) - 3;
        dy = random.nextInt(7) - 3;
        // ensure the ball moves in at least one direction
        if (dx == 0 && dy == 0) {
            dx = 1;
        }
    }
}
