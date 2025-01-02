package server;

import data.db.CentralDatabase;
import data.db.Player;
import util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    volatile CentralDatabase db;
    private FileOperations fileOperations;
    private volatile List<Player> transferPlayerList;
    private volatile HashMap<String, ClientInfo> clientMap;
    // volatile use kore different thread ke modify kora jabe

    public static void main(String[] args) {
        int port = 45045;
        new Server(port);
    }

    public Server(int port) {
        clientMap = new HashMap<>();
        transferPlayerList = new ArrayList<>();
        try {
            loadDatabase();
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Player> getTransferPlayerList() {
        return transferPlayerList;
    }

    private void loadDatabase() throws IOException {
        db = new CentralDatabase();
        fileOperations = new FileOperations();
        fileOperations.readFromFile(db);;
    }

    private void serve(Socket socket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(socket);
        new ThreadServer(networkUtil, this);
    }

    public synchronized boolean loginClub(String username, String password) {
        if (clientMap.containsKey(username) && clientMap.get(username).getPassword().equals(password)
                && !clientMap.get(username).isLoggedIn()) {
            clientMap.get(username).setLoggedIn(true);
            return true;
        }
        return false;
    }
    
    public synchronized boolean sellPlayer(String playerName, String newClubName) {
        boolean b = false;
        try {
            Player player = db.searchPlayerByName(playerName);
            if (player.isInTransferList()) {
                transferPlayerList.remove(player);
                player.setInTransferList(false);
                player.setClub(newClubName);
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    public synchronized boolean addToTransferWindow(String playerName, double playerPrice) {
        boolean b = false;
        try {
            db.removePlayerFromClub(playerName);
            Player player = db.searchPlayerByName(playerName);
            player.setPrice(playerPrice);
            player.setInTransferList(true);
            transferPlayerList.add(player);
            b = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    public synchronized boolean registerClub(String clubName, String password, NetworkUtil networkUtil) {
        for (String clientName : clientMap.keySet()) {
            if (clientName.equalsIgnoreCase(clubName)) {
                return false;
            }
        }
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setClubName(clubName);
        clientInfo.setPassword(password);
        clientInfo.setNetworkUtil(networkUtil);
        clientInfo.setLoggedIn(false);
        clientMap.put(clubName, clientInfo);
        return true;
    }

    public synchronized boolean logoutClub(String username) {
        if (clientMap.containsKey(username) && clientMap.get(username).isLoggedIn()) {
            clientMap.get(username).setLoggedIn(false);
            return true;
        }
        return false;
    }

    public synchronized List<String> sendClubList() {
        List<String> clubList = new ArrayList<>();
        db.getClubList().forEach(e -> clubList.add(e.getClubName()));
        System.out.println(clubList);
        return clubList;
    }

    public synchronized boolean changePassword(String username, String oldPassword, String newPassword) {
        if (clientMap.containsKey(username) && clientMap.get(username).getPassword().equals(oldPassword)
                && clientMap.get(username).isLoggedIn()) {
            clientMap.get(username).setPassword(newPassword);
            return true;
        }
        return false;
    }
}
