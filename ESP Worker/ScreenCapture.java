import de.ruedigermoeller.serialization.FSTObjectOutput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 5/11/14.
 */
public class ScreenCapture {
    public static BufferedImage painduCapture() throws AWTException, IOException, InterruptedException {
        ArrayList<BufferedImage> imagebuffer = new ArrayList<BufferedImage>();
        double lStartTime = new Date().getTime();
        Robot robot = new Robot();
        Rectangle rec = new Rectangle(0, 0, 120, 160);
        BufferedImage screenShot = robot.createScreenCapture(rec);
//        byte[] imageBytes = ((DataBufferByte) screenShot.getData().getDataBuffer()).getData();

//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        //ImageIO.setUseCache(false);
//        ImageIO.write(screenShot, "jpg", outputStream);
//
//        byte[] imageBytes = outputStream.toByteArray();


//        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
//        BufferedImage bufferedImage;
//        try {
//            bufferedImage = ImageIO.read(bais);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        ImageIO.write(bufferedImage, "JPG", new File("scruueenShot" + ".jpg"));

        return screenShot;
//        ByteBuffer p = new byte[1000000];
//        ImageIO.write(screenShot, "JPG", p);
    }

}
