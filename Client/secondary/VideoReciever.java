package secondary;
import com.esotericsoftware.kryonet.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Hashtable;
import com.esotericsoftware.kryo.Kryo;

import javax.imageio.ImageIO;

public class VideoReciever {
    public Client client= new Client();

    public VideoReciever() {
        this.client = new Client(999,999);
        client.start();

    }

    public void runn(String address, int porttcp, int portudp) throws IOException {
        client.connect(54555, InetAddress.getByName(address), porttcp, portudp);
        Network.register(client);
        client.addListener(new Listener() {
            public void received(Connection c, Object object) { // server only replies to who ever sent it a request
                if (object instanceof byte[]) {
                    System.out.println("Received image bytes");
                    byte[] imageBytes = (byte[]) object;

                    ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                    BufferedImage bufferedImage;
                    try {
                        bufferedImage = ImageIO.read(bais);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        ImageIO.write(bufferedImage, "JPG", new File("scruueenShot" + ".jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        ImageIO.write(bufferedImage, "JPG", new File("screenShot" + ".jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println("L");
            }
        });



    }
}





