import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    // player 1
    boolean wActive, sActive;
    // player 2
    boolean upActive, downActive;
    boolean restart;
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            wActive = true;
        }
        if (code == KeyEvent.VK_S) {
            sActive = true;
        }
        if (code == KeyEvent.VK_UP) {
            upActive = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            downActive = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            wActive = false;
        }
        if (code == KeyEvent.VK_S) {
            sActive = false;
        }
        if (code == KeyEvent.VK_UP) {
            upActive = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            downActive = false;
        }
        if (e.isShiftDown() && code == KeyEvent.VK_ESCAPE) {
            restart = true;
        }
    }
}
