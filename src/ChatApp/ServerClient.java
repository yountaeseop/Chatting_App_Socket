package ChatApp;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class ServerClient {

	public static void main(String args[]) {
		System.out.println("Private Chatting Program");
		if (args.length != 3) {
			System.out.println("usage: <run> <server-port> <remote-IP>"
			 + " (e.g., 6000 10.0.0.1 7000)");
			
			for (int i = 0; i < args.length; ++i) {
				System.out.println("args[" + i + "]:" + args[i]);
			}
			System.exit(0);
		}
		// wifi에 연결하기 위한 IP를 따오기 때문에 wifi가 바뀌면
		// 네트워크 설정에서 IP를 다시 확인해주는 것이 좋다.
		
		String IPADDR = getIP(); // server IP
		// 여기서 localIp가 다르게 나오는 이유는 네트워크 설정에서
		// VMware관련한 네트워크 어댑터가 존재하기 때문에 getIp를 했을때,
		// VMware에 있는 ip를 얻어온다 따라서 상대방쪽에서 remote server Ip를 설정할때
		// 여기서 getIP로 확인한 Ip말고 설정에서 확인한 IP를 써주어야 한다.
		
		int PORTNUM = Integer.parseInt(args[0]); // server port-number
		String remoteIP = args[1]; // peer IP
		int remotePort = Integer.parseInt(args[2]); // peer port-number

		System.out.println("Local Server");
		System.out.println(" IP:" + IPADDR + " PORT:" + PORTNUM);
		System.out.println("Remote Server");
		System.out.println(" IP:" + remoteIP + " PORT:" + remotePort);
		System.out.println();

		TcpIpServer server = new TcpIpServer(PORTNUM);
		TcpIpClient client = new TcpIpClient(remoteIP, remotePort);

		server.start(); // start Server thread
		client.start(); // start Client thread
	} // main

	public static String getIP() { // get local IP
		try {
			Enumeration<NetworkInterface> netIf = NetworkInterface.getNetworkInterfaces();
			while (netIf.hasMoreElements()) {
				for (InterfaceAddress ifAddr : netIf.nextElement().getInterfaceAddresses()) {
					if (ifAddr.getAddress().isSiteLocalAddress())
						return ifAddr.getAddress().getHostAddress();
				} // for
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
