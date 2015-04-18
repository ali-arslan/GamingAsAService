import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Administrator on 5/10/14.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException, AWTException, IOException {
//        VideoNetwork videoNetwork = new VideoNetwork();
//        videoNetwork.runn();
        System.out.println("Please enter IP address of main server and press enter");
        String s = new Scanner(System.in).nextLine();
        MainServConn mainServConn = new MainServConn();
        mainServConn.runn(s, 56666, 56777);
        IONetwork ioNetwork = new IONetwork();
        ioNetwork.runn();
        SavingSocket savingSocket= new SavingSocket();

//        IOHandler.keyboardHandler(KeyEvent.VK_8, true);
//        IOHandler.keyboardHandler(KeyEvent.VK_8, false);
//        System.out.println(KeyEvent.VK_A);


//        ScreenCapture.painduCapture();
    }
}
