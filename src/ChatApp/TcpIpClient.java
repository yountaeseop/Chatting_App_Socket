package ChatApp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class TcpIpClient extends Thread {
	String serverIp;
	int portNum;

	public TcpIpClient(String serverIp, int portNum) {
		this.serverIp = serverIp;
		this.portNum = portNum;
	}

	public void run() {
	      DataInputStream dis = null;
	      Socket socket = null;

	      try {
	         // System.out.println("[Client] Connecting Server with IP:port(" + serverIp + ":" + portNum + ")");

	         while (true) {
	            try {
	               socket = new Socket(serverIp, portNum); 
	               Thread.sleep(1000);
	               break;
	            } catch(ConnectException ce) {
	               // retry
	            }
	         }
	         System.out.println("[Client] Connected!");

	         dis = new DataInputStream(socket.getInputStream());
	         while (true) {
	            // Receive message
	            String message = dis.readUTF();
	            if (message.equals("quit")) {
	               System.out.println("[Server] Bye!");
	               System.out.println("[Client] Bye!");
	               dis.close();
	               socket.close();
	               System.exit(0);
	            }
	            // System.out.println("[Client] Received data:" + message);
	            System.out.printf("%40s\n", message);

	         } // while
	      } catch(ConnectException ce) {
	         ce.printStackTrace();
	      } catch(IOException ie) {
	         System.out.println("[Client] Bye!");
	         try {
	            dis.close();
	            socket.close();
	         } catch(Exception e) {
	            e.printStackTrace();  
	         }  
	         System.exit(0);
	         // ie.printStackTrace();
	      } catch(Exception e) {
	         e.printStackTrace();  
	      }  
	   } // run
}
