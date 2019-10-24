package Chapter4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @TODO     (功能说明:模拟线程池等待Socket请求处理)
 * @author   PJL
 * @package  Chapter4
 * @motto    学习需要毅力，那就秀毅力
 * @file     TCPEchoServerPool.java
 * @time     2019年10月24日 下午10:36:47
 */
public class TCPEchoServerPool {

	public static void main(String[] args) throws IOException {
		
		if (args.length == 0) {
			args = new String[2];
			args[0] = "7402";
			args[1] = "10";
		}

		if (args.length != 2) { // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port> <Threads>");
		}

		int echoServPort = Integer.parseInt(args[0]); // Server port
		int threadPoolSize = Integer.parseInt(args[1]);

		// Create a server socket to accept client connection requests
		final ServerSocket servSock = new ServerSocket(echoServPort);

		final Logger logger = Logger.getLogger("practical");

		// Spawn a fixed number of threads to service clients
		for (int i = 0; i < threadPoolSize; i++) {
			Thread thread = new Thread() {
				public void run() {
					while (true) {
						try {
							Socket clntSock = servSock.accept(); // Wait for a connection
							EchoProtocol.handleEchoClient(clntSock, logger); // Handle it
						} catch (IOException ex) {
							logger.log(Level.WARNING, "Client accept failed", ex);
						}
					}
				}
			};
			thread.start();
			logger.info("Created and started Thread = " + thread.getName());
		}
	}
}
