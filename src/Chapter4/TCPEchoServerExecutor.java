package Chapter4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
/**
 * @TODO     (功能说明:Java自带高级线程池管理Socket请求线程服务)
 * @author   PJL
 * @package  Chapter4
 * @motto    学习需要毅力，那就秀毅力
 * @file     TCPEchoServerExecutor.java
 * @time     2019年10月24日 下午10:45:45
 */
public class TCPEchoServerExecutor {

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

		/*
		 * int threadPoolSize=100; 
		 * Executor service =Executors.newFixedThreadPool(threadPoolSize);
		 */
		Executor service = Executors.newCachedThreadPool(); // Dispatch svc

		// Run forever, accepting and spawning threads to service each connection
		while (true) {
			Socket clntSock = servSock.accept(); // Block waiting for connection
			service.execute(new EchoProtocol(clntSock, logger));
			// set timeout socket thread
			//service.execute(new TimelimitEchoProtocol(clntSock, logger));
		}
		/* NOT REACHED */
	}
}
