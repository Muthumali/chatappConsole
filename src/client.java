import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    public client(Socket socket,String userName){
        try {
            this.socket=socket;
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userName=userName;
        } catch (IOException e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public void sendMassage(){
        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner=new Scanner(System.in);
            while (socket.isConnected()){
                String messageToSend=scanner.nextLine();
                bufferedWriter.write(userName + " : " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }
        } catch (IOException e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public  void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFormGroupChat;

                while (socket.isConnected()){
                    try {
                        msgFormGroupChat=bufferedReader.readLine();
                        System.out.println(msgFormGroupChat);
                    } catch (IOException e) {
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public  void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String [] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter Your Massage");
        String username=scanner.nextLine();
        Socket socket=new Socket("localhost",5000);
        client client=new client(socket,username);
        client.listenForMessage();
        client.sendMassage();

    }
}
