package secondary;
import com.esotericsoftware.kryonet.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import javafxmainapp.Controller;
import javafxmainapp.Main;

public class MainServConn{
    static public Client client= new Client();
    ArrayList<String> workersIPAdds;

    public MainServConn() {
        this.client = new Client(999,999);
        client.start();
    }

    public void runn(String address, int porttcp, int portudp) throws IOException {
        client.connect(54555, InetAddress.getByName(address), porttcp, portudp);
        Network.register(client);
        client.addListener(new Listener() {
            public void received(Connection c, Object object) { // server only replies to who ever sent it a request
                if (object instanceof String) {
                    String s = (String) object;
                    String[] ss = s.split(",");
                    if (ss[0].equals("reply") && ss[1].equals("workers")) {
                        System.out.println("reply triggered");
                        workersIPAdds = new ArrayList<String>();
                        workersIPAdds.clear();
                        for (int i = 2; i < ss.length; i++) {
                            String s1 = ss[i];
//                            s1 = s1.substring(1);
                            s1 = s1.split(":")[0];
                            System.out.println(s1);
                            workersIPAdds.add(s1);
                            if (workersIPAdds.isEmpty()) {
                                NetworkingIO.statmsg = "Failed to fetch server list";
                            } else {
                                Main.serverAdd = workersIPAdds.get(0);
                                NetworkingIO.statmsg = "Connected to first available server; press start video/connect IO";
                            }
                        }
                    }

                }
                System.out.println("L");
            }
        });
    }
     static public void reqWorkerIPs() {
        client.sendTCP("get,clientsips");
    }
    ArrayList<String> getWorkerAddresss() {
        return workersIPAdds;
    }
}