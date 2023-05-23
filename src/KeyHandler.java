import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    // player 1
    boolean wActive, sActive;
    // player 2
    boolean upActive, downActive;
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // player 1
        if (e.getKeyCode() == KeyEvent.VK_W) {
            wActive = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            sActive = true;
        }
        // player 2
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upActive = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downActive = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // player 1
        if (e.getKeyCode() == KeyEvent.VK_W) {
            wActive = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            sActive = false;
        }
        // player 2
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upActive = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downActive = false;
        }
    }
}
