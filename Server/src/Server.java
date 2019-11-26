import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

public class Server {
private LinkedList<User> clientLinkedList;

    private int primaryKey =0;

    public static void main(String[] args) {
        Server server = new Server();
        server.serverStart();
    }

    private void serverStart(){
        clientLinkedList =new LinkedList<>();
        try{
            ServerSocket serverSocket= new ServerSocket(5220);
            while(true){
                Socket socket = serverSocket.accept();
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

                User user = new User(null, printWriter);
                clientLinkedList.add(user);
                Thread t = new Thread(new ServerClient(socket));
                t.start();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class ServerClient implements Runnable{
            Socket socket;
            BufferedReader bufferedReader;

            ServerClient(Socket socketClient){
                try {
                    socket=socketClient;
                    bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        @Override
        public void run() {
                String str;
                PrintWriter pw;
                User u;

            try {

                if(clientLinkedList.get(primaryKey).getName() != null) {
                    primaryKey++;
                }
                if(clientLinkedList.get(primaryKey).getName() == null){
                    String st;
                   if((st=bufferedReader.readLine())!=null) {
                       String[] splitGet = st.split(":");
                       clientLinkedList.get(primaryKey).setName(splitGet[0]);
                       System.out.println("get "+st);
                   }
                }

                for(int i=0;i<= clientLinkedList.lastIndexOf(clientLinkedList.getLast());){
                    pw = clientLinkedList.get(i).getPrintWriter();
                    pw.print("\n" +"active: ");

                    for(int j=0;j<= clientLinkedList.lastIndexOf(clientLinkedList.getLast());){
                        pw.print( clientLinkedList.get(j).getName()+", ");
                        System.out.println(clientLinkedList.get(j).getName()+", ");
                        j++;
                    }
                    pw.println();
                 i++;
                }

                while((str = bufferedReader.readLine())!=null){

                    System.out.println("get "+str);
                    Iterator iterator = clientLinkedList.iterator();
                    String[] spliter = str.split(":");

                    while(iterator.hasNext()){

                        u = (User) iterator.next();
                        pw=u.getPrintWriter();
                        pw.flush();
                       if(spliter[0].equals(u.getName())){
                           if(spliter[2]!=null) pw.println(spliter[1]+" wrote: "+spliter[2]);
                           break;
                       }
                       else if(str.matches("disconnected")){
                               System.out.println(((User) iterator.next()).getName()+"disconnected");
                               iterator.remove();
                               pw.println(((User) iterator.next()).getName()+"disconnected");
                               primaryKey=0;
                           }
                       else pw.println("\n" +"wrong name");
                            }
                        }

            } catch (IOException e) {
                System.out.println("\n" + "The client has disconnected");
            }

        }
        }
    }

