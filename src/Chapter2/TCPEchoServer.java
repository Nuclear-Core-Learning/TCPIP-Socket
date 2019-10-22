package Chapter2;

import java.net.*; // for Socket, ServerSocket, and InetAddress
import java.io.*; // for IOException and Input/OutputStream
/**
 * @TODO     (功能说明:TCP服务端)
 * @author   PJL
 * @package  Chapter2
 * @motto    学习需要毅力，那就秀毅力
 * @file     TCPEchoServer.java
 * @time     2019年10月22日 下午10:04:23
 */
public class TCPEchoServer {

	private static final int BUFSIZE = 32; // Size of receive buffer

	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			args = new String[1];
			args[0] = "7402";
		}

		if (args.length != 1) // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port>");

		int servPort = Integer.parseInt(args[0]);

		// Create a server socket to accept client connection requests
		ServerSocket servSock = new ServerSocket(servPort);

		int recvMsgSize; // Size of received message
		byte[] receiveBuf = new byte[BUFSIZE]; // Receive buffer

		while (true) { // Run forever, accepting and servicing connections
			Socket clntSock = servSock.accept(); // Get client connection

			SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
			System.out.println("Handling client at " + clientAddress);

			InputStream in = clntSock.getInputStream();
			OutputStream out = clntSock.getOutputStream();

			// Receive until client closes connection, indicated by -1 return
			while ((recvMsgSize = in.read(receiveBuf)) != -1) {
				out.write(receiveBuf, 0, recvMsgSize);
			}

			clntSock.close(); // Close the socket. We are done with this client!
		}
		/* NOT REACHED */
	}
}
