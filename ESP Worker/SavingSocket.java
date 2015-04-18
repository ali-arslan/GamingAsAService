import java.awt.AWTException;
        import java.awt.Rectangle;
        import java.awt.Robot;
        import java.awt.Toolkit;
        import java.awt.image.BufferedImage;
        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.text.NumberFormat;
        import java.util.Iterator;
        import javax.imageio.ImageIO;
        import javax.imageio.ImageWriter;
        import javax.imageio.event.IIOWriteProgressListener;

public class SavingSocket {
    ServerSocket videoSocket;

    public SavingSocket() throws IOException {
            ServerSocket videoSocket = new ServerSocket(6789);
            try {
            while (true) {
                Socket socket = videoSocket.accept();
                new Thread(new SocketHandler(socket)).start();
            }
        } catch (IOException e) {
        }
        }



    protected static String readRequest(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder(128);
        int in = -1;
        if ((in = is.read()) != '\n') {
            sb.append((char) in);
            while ((in = is.read()) != '\n') {
                sb.append((char) in);
            }
        }
        return sb.toString();
    }


    protected static void grabScreen(OutputStream os) throws AWTException, IOException {        Rectangle screenRect = new Rectangle(0,0,640,480);
        BufferedImage capture = new Robot().createScreenCapture(screenRect);
        System.out.println("getting scrnsht");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(capture, "jpg", baos);
        baos.close();
        os.write((Integer.toString(baos.size()) + "\n").getBytes());
        os.write(baos.toByteArray());
        System.out.println("screenshot dispatched");
    }


    public static class SocketHandler implements Runnable {

        private Socket socket;

        public SocketHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String request = null;
            InputStream is = null;
            OutputStream os = null;
            try {
                System.out.println("Processing client");
                is = socket.getInputStream();
                os = socket.getOutputStream();
                do {
                    System.out.println("Waiting mode");
                    request = readRequest(is);
                    System.out.println("Received request = " + request);
                    if ("grab".equalsIgnoreCase(request)) {
                        grabScreen(os);
                    }
                } while (!"done".equalsIgnoreCase(request) && !"shutdown".equalsIgnoreCase(request));
                System.out.println("Client shut");
            } catch (IOException | AWTException exp) {
                exp.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
            if ("shutdown".equalsIgnoreCase(request)) {
                System.exit(0);
            }
        }
    }
}