
import java.net.*;
import java.io.*;

public class IMMultiServer {
    public static void main(String[] args) throws IOException {

      //  if (args.length != 1) {
        //    System.err.println("Usage: java KKMultiServer <port number>");
          //  System.exit(1);
        //}

        //FUCK

        //retrieve port number from command line argument
        int portNumber = 4444;
        boolean listening = true;

        //listen on a specific port for a connection request
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new IMMultiServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}