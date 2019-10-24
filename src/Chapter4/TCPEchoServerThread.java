package Chapter4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
/**
 * @TODO     (功能说明:一客户一线程方式处理Socket)
 * @author   PJL
 * @package  Chapter4
 * @motto    学习需要毅力，那就秀毅力
 * @file     TCPEchoServerThread.java
 * @time     2019年10月24日 下午10:29:46
 */
public class TCPEchoServerThread {

	public static void main(String[] args) throws IOException {
		
		if (args.length == 0) {
			args = new String[1];
			args[0] = "7402";
		}

		if (args.length != 1) { // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port>");
		}

		int echoServPort = Integer.parseInt(args[0]); // Server port

		// Create a server socket to accept client connection requests
		ServerSocket servSock = new ServerSocket(echoServPort);

		Logger logger = Logger.getLogger("practical");

		// Run forever, accepting and spawning a thread for each connection
		while (true) {
			Socket clntSock = servSock.accept(); // Block waiting for connection
			// Spawn thread to handle new connection
			Thread thread = new Thread(new EchoProtocol(clntSock, logger));
			thread.start();
			logger.info("Created and started Thread " + thread.getName());
		}
		/* NOT REACHED */
	}
}
