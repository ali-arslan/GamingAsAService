import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Hashtable;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class IONetwork {
    Server server = new Server();

    public IONetwork() {
        this.server = new Server(999,999);
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            System.err.println("Socket failed to bind with provided port, is it already in use?");
        }
    }

    void runn() {
        System.out.println("runnin'");
        Network.register(server);
        server.addListener(new Listener() {
            public void received(Connection c, Object object) { // server only replies to who ever sent it a request
                System.out.println("rnn lstner");
                if (object instanceof IOPacket) {
                    IOPacket ioPacket = (IOPacket) object;
                    try {
                        IOHandler.applier(ioPacket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("up");
                }

                if (object instanceof String) {
                    String str = (String) object;
                    String[] strr = str.split(",");
                    if (strr[0].equals("connect")) {
                        System.out.println("IO Connected");
                        server.sendToTCP(c.getID(), "stat,Connected");

                    } else if (strr[0].equals("keycom")) {
                        System.out.println("key comms received");
                        boolean pr = false;
                        if (strr[2].equals("true")) pr = true;
                        if (strr[2].equals("false")) pr = false;
                        try {
                            IOHandler.keyboardHandler(Integer.parseInt(strr[1]), pr);
                        } catch (AWTException e) {
                        }

                    } else if (strr[0].equals("mousecom")) {
                        System.out.println("mouse comms received");
                        boolean rclck = false;
                        boolean lclck = false;
                        boolean pr = false;
                        if (strr[3].equals("rclick")) {
                            lclck = true;
                            System.out.println("engaging click");
                        }
                        if (strr[3].equals("lclick")) rclck = true;
                      if (strr[4].equals("press")) pr = true;
                        if (strr[4].equals("rls")) {
                            pr = false;
                            System.out.println("code press");
                        }
                        try {
                            IOHandler.mouseMovementsApplier(Integer.parseInt(strr[1]), Integer.parseInt(strr[2]),Integer.parseInt(strr[5]),rclck, lclck, pr);
                        } catch (AWTException e) {
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(str);
                }
            }
        });
    }
}





    class Network {
    // This registers objects that are going to be sent over the network.
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(String.class);
        kryo.register(ByteBuffer.class);
        kryo.register(IOPacket.class);
        kryo.register(BufferedImage.class);
        kryo.register(java.awt.image.DirectColorModel.class);
        kryo.register(java.awt.color.ICC_ColorSpace.class);
        kryo.register(float[].class);
        kryo.register(double[].class);
        kryo.register(int[].class);
        kryo.register(char[].class);
        kryo.register(java.awt.color.ICC_ProfileRGB.class);
        kryo.register(short[].class);
        kryo.register(byte[].class);
        kryo.register(sun.awt.image.IntegerInterleavedRaster.class);
    }
}