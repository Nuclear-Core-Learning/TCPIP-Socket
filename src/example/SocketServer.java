package example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 
 * @TODO     (����˵��:Socket��������������)
 * @author   PJL
 * @package  example
 * @motto    ѧϰ��Ҫ�������Ǿ�������
 * @file     SocketServer.java
 * @time     2019��10��31�� ����11:26:15
 */
public class SocketServer {

	BufferedWriter writer;
	BufferedReader reader;

	public static void main(String[] args) {
		SocketServer server = new SocketServer();
		server.run();
	}

	public void run() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(8090);
			System.out.println("server start...");
			while (true) {
				socket = serverSocket.accept();
				// �µ�����
				manageConnection(socket);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
				socket.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ����ͻ��˺ͷ�����������
	 */
	public void manageConnection(final Socket socket) {

		new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("client" + socket.hashCode() + "connedted");
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					String receiveMsg;
					while ((receiveMsg = reader.readLine()) != null) {
						System.out.println("client" + socket.hashCode() + "says: " + receiveMsg);
						writer.write("server says: " + receiveMsg + "\n");
						writer.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						writer.close();
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

}
