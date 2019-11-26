import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int PORT= 5200;
    private static final String IP ="127.0.0.1";
    Scanner sc = new Scanner(System.in);

    private BufferedReader bufferedReader;
    private String name;
    private String chatMateName;

    public static void main(String[] args) {
        Client c = new Client();
        c.startClient();

    }

    private void startClient(){
        System.out.print("Enter your name: ");
        name = sc.nextLine();

        try{
            Socket socket = new Socket(IP,PORT);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread t = new Thread(new Recipient());
            t.start();

            printWriter.println(name + ":" + " Hello");
            printWriter.println("----------------------------------------------------");
            printWriter.flush();

            setChatMate();

            while (true){
                String str = sc.nextLine();
                printWriter.flush();
                System.out.println("if you want to change your chatmate press 1");
                if(str.equalsIgnoreCase("1")) {
                    chatMateName=null;
                    setChatMate();
                }
                else{
                    if(!str.equalsIgnoreCase("q")){
                        printWriter.println(chatMateName+":"+ name + ":" + str);
                        printWriter.println("");
                        printWriter.flush();
                    }else {
                        printWriter.println(name +"-"+"disconnected");
                        printWriter.flush();
                        printWriter.close();
                        socket.close();
                    }
                }
            }

        }catch (Exception ex){
            System.out.println("Server is not working");
        }
    }
    public class Recipient implements Runnable{

        @Override
        public void run() {
            String message;
            try{
                while ((message=bufferedReader.readLine())!=null){
                    if(message.equals("\n" +"wrong name")){
                        System.out.println(message);
                        chatMateName=null;
                    }
                    else {
                        System.out.println(message);
                    }
                }
            }catch (Exception e){
                System.out.println("Connection completed");
            }
        }
    }
    public void setChatMate(){
        while(chatMateName==null) {
            System.out.println("\n" + "Enter the name of the person you want to write to: ");
            chatMateName = sc.nextLine();
            if(chatMateName==null) System.out.println("no name provided");
        }
    }
}
