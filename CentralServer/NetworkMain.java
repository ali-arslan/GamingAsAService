import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.Executors;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class NetworkMain implements Serializable {
    transient Server server = new Server();
    transient ClientManager clientManager;

    public NetworkMain() {
        clientManager = new ClientManager();
        this.server = new Server(999,999);
        server.start();
        try {
            server.bind(56666, 56777);
        } catch (IOException e) {
            System.err.println("Socket failed to bind with provided port, is it already in use?");
        }
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                backUp();
            } catch (Exception e) {
                System.err.println("Server state backup failed");
            }
        });
    }

    void runn() {
        System.out.println("Main server started'");
        Network.register(server);
        server.addListener(new Listener() {
            public void received(Connection c, Object object) { // server only replies to who ever sent it a request
                System.out.println("msg rcvd");
                if (object instanceof String) {
                    String str = (String) object;
                    String[] strr = str.split(",");
                    if (strr[0].equals("connectworker")) {
                        System.out.println("IO Connected");
                        String addrs = c.getRemoteAddressTCP().getHostString();
                        String[] addrss = addrs.split(":");
                        server.sendToTCP(c.getID(), "Connected");
                        System.out.println("Worker connected");
                        server.sendToTCP(c.getID(), "addsuccessful");

                    } else if (strr[0].equals("get") && strr[1].equals("clientsips")) {
                        System.out.println("Request received, sending worker addresses");
                        String reply = "reply,workers";
                        for (Connection connection : server.getConnections()) {
                            if (c.equals(connection)) continue;
                            reply = reply + "," +connection.getRemoteAddressTCP().getHostString();
                        }

//                        for (Client curClient : clientManager.curClients) {
//                            reply = reply + "," + curClient.inetAddress + ":" + curClient.port;
//                        }


                        server.sendToTCP(c.getID(),reply);


                    }
                    System.out.println(str);
                }
            }
        });
    }
    void backUp() throws IOException, InterruptedException {
        while (true) {
            FileOutputStream fout = new FileOutputStream("address.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
            Thread.sleep(10000);
        }
    }
    static NetworkMain tryLoad() throws IOException {
        ObjectInputStream objectinputstream = null;
        try {
            FileInputStream streamIn = new FileInputStream("address.ser");
            objectinputstream = new ObjectInputStream(streamIn);
            NetworkMain readCase = (NetworkMain) objectinputstream.readObject();
            return readCase;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (objectinputstream != null) {
                objectinputstream.close();
            }
        }
        return null;
    }
}





class Network {
    // This registers objects that are going to be sent over the network.
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(String.class);
        kryo.register(ByteBuffer.class);
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