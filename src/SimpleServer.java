import java.io.*;
import java.net.*;


public class SimpleServer {
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args){
            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){

                System.out.println("Server is listening on port " + SERVER_PORT);

                while (true){
                    Socket socket = serverSocket.accept();
                    System.out.println();

                    new ServerThread(socket).start();
                }

            }
            catch (IOException ex){
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }
    }
}