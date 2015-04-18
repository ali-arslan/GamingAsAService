
import com.esotericsoftware.kryonet.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import com.esotericsoftware.kryo.Kryo;

public class MainServConn{
    public Client client= new Client();

    public MainServConn() {
        this.client = new Client(999,999);
        client.start();
    }

    public void runn(String address, int porttcp, int portudp) throws IOException {
        client.connect(54555, InetAddress.getByName(address), porttcp, portudp);
        client.sendTCP("connectworker");
        Network.register(client);
        client.addListener(new Listener() {
            public void received(Connection c, Object object) { // server only replies to who ever sent it a request
                if (object instanceof String) {
                    String s = (String) object;
                    String[] ss = s.split(",");
                    if (ss[0].equals("addsuccessful")) {

                    }

                }
                System.out.println("L");
            }
        });



    }
}




