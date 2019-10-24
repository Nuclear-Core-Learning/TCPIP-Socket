package Chapter4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 
 * @TODO     (功能说明:Socket日志处理线程)
 * @author   PJL
 * @package  Chapter4
 * @motto    学习需要毅力，那就秀毅力
 * @file     EchoProtocol.java
 * @time     2019年10月24日 下午10:20:18
 */
public class EchoProtocol implements Runnable {
	private static final int BUFSIZE = 32; // Size (in bytes) of I/O buffer
	private Socket clntSock; // Socket connect to client
	private Logger logger; // Server logger

	public EchoProtocol(Socket clntSock, Logger logger) {
		this.clntSock = clntSock;
		this.logger = logger;
	}

	/**
	 * Socket处理日志方法
	 * @param clntSock
	 * @param logger
	 */
	public static void handleEchoClient(Socket clntSock, Logger logger) {
		try {
			// Get the input and output I/O streams from socket
			InputStream in = clntSock.getInputStream();
			OutputStream out = clntSock.getOutputStream();

			int recvMsgSize; // Size of received message
			int totalBytesEchoed = 0; // Bytes received from client
			byte[] echoBuffer = new byte[BUFSIZE]; // Receive Buffer
			// Receive until client closes connection, indicated by -1
			while ((recvMsgSize = in.read(echoBuffer)) != -1) {
				out.write(echoBuffer, 0, recvMsgSize);
				totalBytesEchoed += recvMsgSize;
			}

			logger.info("Client " + clntSock.getRemoteSocketAddress() + ", echoed " + totalBytesEchoed + " bytes.");

		} catch (IOException ex) {
			logger.log(Level.WARNING, "Exception in echo protocol", ex);
		} finally {
			try {
				clntSock.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 线程执行方法
	 */
	public void run() {
		handleEchoClient(clntSock, logger);
	}
}
