package ChatApp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TcpIpServer extends Thread{
	
	int portNum;
	
	public TcpIpServer(int portNum) {
		this.portNum = portNum;
	}

	public void run() {
		
		ServerSocket serverSocket = null; // 서버소켓
		Socket socket = null; // 소켓
		DataOutputStream dos = null; // DataOutputStream 
		// 이거 3개 선언해주고 시작.
		
		
		try {
			serverSocket = new ServerSocket(portNum);
			// 서버소켓은 클라이언트의 연결요청이 올 때까지 실행을 멈추고 계속 기다린다.
			// 클라이언트의 연결요청이 오면 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.
			System.out.println("[Server] Ready (port: "+portNum+")");
			
			socket = serverSocket.accept();
			System.out.println("[Server] Accepted from " + socket.getInetAddress());
			
			// 소켓의 출력스트림을 얻는다.
			OutputStream out = socket.getOutputStream();
			dos = new DataOutputStream(out);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
	         try {
	            // Input message for sending
	            // System.out.print("Enter msg or quit:");
	            String message = new Scanner(System.in).nextLine();

	            if (message.equals("quit")) {
	               dos.writeUTF(message);
	               System.out.println("[Server] Bye!");
	               Thread.sleep(1000);
	               dos.close();
	               socket.close();
	               break;
	            }

	            // Send message
	            dos.writeUTF(message);
	            // System.out.println("[Server] Sent data:" + message);

	         } catch (IOException e) {
	            e.printStackTrace();
	            // break;
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	      }
	}
}
