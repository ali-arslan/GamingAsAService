package secondary;
import com.esotericsoftware.kryonet.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import com.esotericsoftware.kryo.Kryo;

public class NetworkingIO {
     public Client client= new Client();
    public static String statmsg = "";

    public NetworkingIO() {
        this.client = new Client(999,999);
        client.start();
    }

     public void runn(String address, int porttcp, int portudp) throws IOException {
         client.connect(54555, InetAddress.getByName(address), porttcp, portudp);
        Network.register(client);
        client.addListener(new Listener() {
            public void received(Connection c, Object object) { // server only replies to who ever sent it a request
                if (object instanceof IOPacket) {
                    IOPacket ioPacket = (IOPacket) object;

                }
                if (object instanceof String) {
                    String s = (String) object;
                    String[] ss = s.split(",");
                    if (ss[0].equals("stat")) {
                        statmsg = ss[1];
                    }

                }
                System.out.println("L");
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
        kryo.register(byte[].class);


    }
}