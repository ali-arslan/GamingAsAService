import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;


public class Client implements Serializable {
    String inetAddress;
    int port;
    int id;
    int performance;
    boolean occupied;

    public Client(String inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
        this.id = 0;
        this.performance = 0;
        this.occupied = false;
    }
}

class ClientManager {
    ArrayList<Client> curClients = new ArrayList<>();
    int clientIDcounter = 0;


    boolean isClient(Client input) {
        for (Client curClient : curClients) {
            if (input.inetAddress.equals(curClient.inetAddress) && input.port == curClient.port) {
                return true;
            }
        }
        return false;
    }

    void addClient(Client toAdd) {
        curClients.add(toAdd);
    }

    int size() {
        return curClients.size();
    }

    Client getClient(InetAddress inetAddress, int port) {
        for (Client curClient : curClients) {
            if (curClient.inetAddress.equals(inetAddress) && curClient.port == port) {
                return curClient;
            }
        }
        return null;
    }

    void setAllAvailable() {
        for (Client curClient : curClients) {
            curClient.occupied = false;
        }

    }

    int clientIDgen() {
        return clientIDcounter++;
    }
}