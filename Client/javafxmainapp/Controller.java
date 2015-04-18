package javafxmainapp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import secondary.IOPacket;
import secondary.MainServConn;
import secondary.NetworkingIO;
import secondary.newvid;

import javax.imageio.ImageIO;


public class Controller {
    boolean videoStopper = false;

    @FXML
    private Text status;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button1;

    @FXML
    private Rectangle captureRectangle;

    @FXML
    private AnchorPane mainAncPane;

    @FXML
    private Button updatebutton;

    @FXML
    private Button stopbutt;

    @FXML
    private Button connectserv;

    @FXML
    private TextField addressfield;

    @FXML
    void handle(Event event) {

        connectserv.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.serverAddMain = addressfield.getText();
                try {
                    Main.connectmain();
                } catch (IOException e) {
                    System.err.println("Failed to connect to main server");
                    NetworkingIO.statmsg = "Failed to connect to main server";
                }
                MainServConn.reqWorkerIPs();
                Executors.newSingleThreadExecutor().execute(() -> {
                    autoUpdateStatus();
                });
            }
        });

        stopbutt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                videoStopper = true;
                Image img = new Image("hjk.jpg");
                viewver.setImage(img);
                NetworkingIO.statmsg = "Connected, Video stopped";
            }
        });
            updatebutton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
////                    System.out.println("You clicked me!");
//                    try {
//                        hiller();
////
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    //start the background task
                    statusUpdater(NetworkingIO.statmsg);
                    videoStopper = false;
                    Thread t=new Thread(new Task<Void>(){
                        public Void call(){
                            startTransformations();
                            return null;
                        }
                    });
                    t.start();
                    NetworkingIO.statmsg = "Connected, Video started";
                }
            });
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("You clicked me!");

                try {
                    Main.connect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                statusUpdater(NetworkingIO.statmsg);
            }
        });
        mainAncPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                        @Override
                                        public void handle(KeyEvent ke) {
                                            System.out.println("Key Pressed: " + ke.getText());
                                            System.out.println(ke.getCode().impl_getCode());
                                            if (Main.ioConnected) {
//                    IOHelper.dispatch(new IOPacket(0, 0, 0, false, false, false, ke.getCode().impl_getCode(), true));
                                                IOHelper.dispatch("keycom," + ke.getCode().impl_getCode() + ",true");
                                            }

                                        }}
        );
        mainAncPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                System.out.println("Key Released: " + ke.getText());
                if (Main.ioConnected) {
//                    IOHelper.dispatch(new IOPacket(0, 0, 0, false, false, false, ke.getCode().impl_getCode(), false));
                    IOHelper.dispatch("keycom," + ke.getCode().impl_getCode() + ",false");                }
            }
        });
        mainAncPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int)event.getX();
                int y = (int)event.getY();
                System.out.println(x + " " + y);
                if (Main.ioConnected) {
//                    IOHelper.dispatch(new IOPacket(x, y, 0, false, false, false, 0, false));
                    IOHelper.dispatch("mousecom," + x + "," + y+",0,0,0");
                }
            }
        });
//        captureRectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                MouseButton mouseButton = event.getButton();
//                double x = event.getX();
//                double y = event.getY();
//                System.out.println(x + " " + y + "" + mouseButton.toString());
//                if (Main.ioConnected) {
//                    if (mouseButton.toString().equals("PRIMARY")) {
//                        IOHelper.dispatch(new IOPacket(x, y, 0, false, true, false, 0, false));
//                    } else {
//                        IOHelper.dispatch(new IOPacket(x, y, 0, true, false, false, 0, false));
//                    }
//                }
//            }
//        });
        mainAncPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MouseButton mouseButton = event.getButton();
                int x = (int)event.getX();
                int y = (int)event.getY();
                System.out.println(x + " " + y + "" + mouseButton.toString());
                if (Main.ioConnected) {
                    if (mouseButton.toString().equals("PRIMARY")) {
//                        IOHelper.dispatch(new IOPacket(x, y, 0, false, true, true, 0, false));
                        System.out.println("mousecom," + x + "," + y+"," + "lclick" +"," + "press,0");
                        IOHelper.dispatch("mousecom," + x + "," + y+"," + "lclick" +"," + "press,0");
                    } else if(mouseButton.toString().equals("SECONDARY")) {
//                        IOHelper.dispatch(new IOPacket(x, y, 0, true, false, true, 0, false));
                        System.out.println("mousecom," + x + "," + y + "," + "rclick" + "," + "press,0");
                        IOHelper.dispatch("mousecom," + x + "," + y+"," + "rclick" +"," + "press,0");
                        IOHelper.dispatch("mousecom,40,300,rclick,rls,0");

                    }
                }
            }
        });
        mainAncPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MouseButton mouseButton = event.getButton();
                int x = (int)event.getX();
                int y = (int)event.getY();
                System.out.println(x + " " + y + "" + mouseButton.toString());
                if (Main.ioConnected) {
                    if (mouseButton.toString().equals("PRIMARY")) {
//                        IOHelper.dispatch(new IOPacket(x, y, 0, false, true, false, 0, false));
                        IOHelper.dispatch("mousecom," + x + "," + y+"," + "rclick" +"," + "rls"+ ",0");

                    } else if(mouseButton.toString().equals("SECONDARY")) {
//                        IOHelper.dispatch(new IOPacket(x, y, 0, true, false, false, 0, false));
                        IOHelper.dispatch("mousecom," + x + "," + y+"," + "lclick" +"," + "rls" + ",0");
//                        IOHelper.dispatch("mousecom,40,300,rclick,rls,0");
                    }
                }
            }
        });
    }

    @FXML
    void initialize() throws IOException, InterruptedException {
        assert button1 != null : "fx:id=\"button1\" was not injected: check your FXML file 'sample.fxml'.";
        assert captureRectangle != null : "fx:id=\"captureRectangle\" was not injected: check your FXML file 'sample.fxml'.";
        assert mainAncPane != null : "fx:id=\"mainAncPane\" was not injected: check your FXML file 'sample.fxml'.";
        assert viewver != null : "fx:id=\"viewver\" was not injected: check your FXML file 'sample.fxml'.";



    }

    @FXML
    private ImageView viewver;

//    Task<Integer> task = new Task<Integer>(){
//        @Override protected Integer call() throws Exception{
//            int iterations = 0;
//            hiller();
//
//            return iterations;
//        }
//    };
//
protected void startTransformations(){
    while (true) {
        if (videoStopper) {
            break;
        }

        try {
            newvid newvid = new newvid();
            final BufferedImage bufferedImage = newvid.run();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateview(bufferedImage);
                }
            });

                Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    }


    void updateview(BufferedImage bufferedImage) {
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        viewver.setImage(image);
    }

    void statusUpdater(String s) {
        status.setText(s);
    }
    protected void autoUpdateStatus() {
        while (true) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        statusUpdater(NetworkingIO.statmsg);
                    }
                });
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
