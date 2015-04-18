package secondary;
import javafxmainapp.Main;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import javax.imageio.ImageIO;

public class newvid {

    private Socket videoSocket;

    public BufferedImage run() throws IOException {
         return networkFetch();
    }

    public newvid() {
        Socket socket;

        try {
            socket = new Socket(Main.serverAdd, 6789);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public BufferedImage networkFetch() throws IOException {
        videoSocket = new Socket(Main.serverAdd, 6789);
        System.out.println("ytiggr");
        if (videoSocket != null) {
            System.out.println("ytiggr");
            InputStream inputStream = null;
            OutputStream outputStream = null;

            ByteArrayOutputStream byteArrayOutputStream = null;
            ByteArrayInputStream byteArrayInputStream = null;

            try {
                inputStream = videoSocket.getInputStream();
                outputStream = videoSocket.getOutputStream();
                writer(outputStream, "grab");
                String size = reader(inputStream);
                int estimateCount = Integer.parseInt(size);
                System.out.println("Expecting are: " + estimateCount);
                byteArrayOutputStream = new ByteArrayOutputStream(estimateCount);
                byte[] bufferarray = new byte[1024];

                int bytesRead = 0;
                int bytesIn = 0;
                if (bytesRead < estimateCount) {
                    do {
                        bytesIn = inputStream.read(bufferarray);
                        bytesRead += bytesIn;
                        byteArrayOutputStream.write(bufferarray, 0, bytesIn);
                    } while (bytesRead < estimateCount);
                }
                System.out.println("Read " + bytesRead);
                byteArrayOutputStream.close();
                byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

                 BufferedImage image = ImageIO.read(byteArrayInputStream);
                byteArrayInputStream.close();
//                return image;
//                ImageIO.write(image, "JPG", new File("screenShot" + ".jpg"));
                return image;
//                System.out.println("up");
            } catch (IOException exp) {
                exp.printStackTrace();
            } finally {
                try {
                    byteArrayInputStream.close();
                } catch (Exception exp) {
                }
                try {
                    byteArrayOutputStream.close();
                } catch (Exception exp) {
                }
            }
        }
        return null;
    }

    protected String reader(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder(128);
        int in = -1;
        if ((in = is.read()) != '\n') {
            do sb.append((char) in); while ((in = is.read()) != '\n');
        }
        return sb.toString();
    }



    protected void writer(OutputStream os, String request) throws IOException {
        os.write((request + "\n").getBytes());
        os.flush();
    }

    public void close() throws IOException {
        try {
            try {
                writer(videoSocket.getOutputStream(), "shutdown");
            } finally {
                try {
                    videoSocket.getOutputStream().close();
                } finally {
                    try {
                        videoSocket.getInputStream().close();
                    } finally {
                        videoSocket.close();
                    }
                }
            }
        } finally {
            videoSocket = null;
        }
    }

}

