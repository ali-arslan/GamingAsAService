import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Hashtable;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import javax.imageio.ImageIO;

public class VideoNetwork {
    Server server = new Server();
    byte[] prev;

    public VideoNetwork() {
        this.server = new Server(99999,9999999);
        server.start();
        try {
            server.bind(54556, 54778);
        } catch (IOException e) {
            System.err.println("Socket failed to bind with provided port, is it already in use?");
        }
    }

    void runn() {
        System.out.println("runnin'");
        Network.register(server);
        server.addListener(new Listener() {
            public void received(Connection c, Object object) { // server only replies to who ever sent it a request
                System.out.println("rnn lstner vid");
                if (object instanceof String) {
                    String str = (String) object;
                    String[] strr = str.split(",");
                    if (strr[0].equals("videocom")) {
                        if (strr[1].equals("start")){
                            while(true) {
                                try {
                                    BufferedImage bufferedImage = ScreenCapture.painduCapture();
                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                                    //ImageIO.setUseCache(false);
                                    ImageIO.write(bufferedImage, "jpg", outputStream);

                                    ArrayList<Byte> differential = new ArrayList<Byte>();

                                    byte[] imageBytes = outputStream.toByteArray();
                                    int counter = 0;
                                    for (byte x : imageBytes) {
                                        if (x != prev[counter]) {
                                            differential.add(x);
                                        }
                                    }
                                    byte[] res = new byte[differential.size()];
                                    counter = 0;
                                    for (byte x : differential) {
                                        res[counter] = x;
                                    }

                                    prev = imageBytes;
                                    server.sendToTCP(c.getID(), res);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    System.out.println(str);
                }
            }
        });
    }
}





