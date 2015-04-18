package javafxmainapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import secondary.*;

import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    public static NetworkingIO networkingIO;
    static boolean ioConnected = false;
    public static String serverAdd = "";
    public static String serverAddMain = "";

    public static void connectmain() throws IOException {
        MainServConn mainServConn = new MainServConn();
        mainServConn.runn(serverAddMain,56666,56777);
    }
    public static void connect() throws IOException {

//        VideoReciever videoReciever= new VideoReciever();
//        videoReciever.runn("172.16.201.128",54556, 54778);
//        videoReciever.client.sendTCP("videocom,start");


        networkingIO = new NetworkingIO(); // 172.16.201.131
        networkingIO.runn(serverAdd,54555, 54777);
//        networkingIO.runn(serverAdd,56666, 56777);
        ioConnected = true;
        networkingIO.client.sendTCP("get,clientsips");
        networkingIO.client.sendTCP("connectworker");
        networkingIO.client.sendTCP("connect");networkingIO.client.sendTCP("connected");networkingIO.client.sendTCP("connected");
//        IOHelper.dispatch("mousecom,40,300,rclick,press,0");
//        IOHelper.dispatch("mousecom,40,300,rclick,rls,0");



    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Gaming as a Service");
        primaryStage.setScene(new Scene(root, 770, 520));
        primaryStage.show();
        Thread.sleep(1000);
        primaryStage.close();
        Thread.sleep(1000);
        primaryStage.show();

    }


    public static void main(String[] args) throws IOException {
//        System.out.println("Enter main server address");

//        mainServConn.reqWorkerIPs();
//        String serv = new Scanner(System.in).nextLine();
//        serverAdd = serv;

        Platform.setImplicitExit(false);
        launch(args);
//        connect();


    }
}
