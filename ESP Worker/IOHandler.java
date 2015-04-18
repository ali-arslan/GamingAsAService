import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Created by ali on 10/05/14.
 */
public class IOHandler {
    static void applier(IOPacket ioPacket) throws InterruptedException, AWTException {
        mouseMovementsApplier(ioPacket.mouseX, ioPacket.mouseY, ioPacket.scrollOffset, ioPacket.rClick, ioPacket.lClick, ioPacket.mousePRessOrRel);
        keyboardHandler(ioPacket.key, ioPacket.pressedOrReleases);
        System.out.println("IOPacket received");
    }
    static void mouseMovementsApplier(double x, double y, int scroll, boolean rClick, boolean lClick, boolean PorR) throws AWTException, InterruptedException {
        Robot bot = new Robot();
        int x1 = (int) x;
        int y1 = (int) y;
        System.out.println(x1 +" " + y1);
        bot.mouseMove(x1, y1);
        if (lClick) {
            if (PorR) {
                bot.mousePress(InputEvent.BUTTON1_MASK);
            } else {
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
        }
        if (rClick) {
            if (PorR) {
                bot.mousePress(InputEvent.BUTTON3_MASK);
            } else {
                bot.mouseRelease(InputEvent.BUTTON3_MASK);
            }
        }
        bot.mouseWheel(scroll);
    }
    static void keyboardHandler(int key, boolean pressedOrReleases) throws AWTException {
        Robot bot = new Robot();
        if (pressedOrReleases) {
            bot.keyPress(key);
        }
        if (!pressedOrReleases) {
            bot.keyRelease(key);
        }
    }

}
