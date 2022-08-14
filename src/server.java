import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private ServerSocket serverSocket;

    public server(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }

    public void startServer(){
        try {
            while (!serverSocket.isClosed()){
                Socket socket=serverSocket.accept();
                System.out.println("a new client Connected");
                ClientHandler clientHandler=new ClientHandler(socket);
                Thread thread=new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void classServerSocket(){
        try {
            if (serverSocket!=null){
                serverSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String [] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(5000);
        server server=new server(serverSocket);
        server.startServer();

    }
}
