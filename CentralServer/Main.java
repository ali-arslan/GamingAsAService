import java.io.*;

/**
 * Created by ali on 13/05/14.
 */

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        NetworkMain networkMain = new NetworkMain();
//        if (!networkMain.tryLoad().equals(null)) {
//            networkMain = NetworkMain.tryLoad();
//        } else {
//            networkMain = new NetworkMain();
//        }
        networkMain.runn();
    }
}
