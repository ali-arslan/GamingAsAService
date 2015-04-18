package javafxmainapp;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import secondary.IOPacket;
import secondary.NetworkingIO;

/**
 * Created by ali on 11/05/14.
 */
public class IOHelper {

    static void dispatch(IOPacket ioPacket) {
        System.out.println("dispatched");
        Main.networkingIO.client.sendTCP("yoyoyo");
        Main.networkingIO.client.sendTCP(ioPacket);

    }
    static void dispatch(String ioPacket) {
        System.out.println("dispatched");
        Main.networkingIO.client.sendTCP(ioPacket);

    }
}
