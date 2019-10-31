package example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * 
 * @TODO     (功能说明:SocketClient建立长连接)
 * @author   PJL
 * @package  example
 * @motto    学习需要毅力，那就秀毅力
 * @file     SocketClient.java
 * @time     2019年10月31日 下午11:29:16
 */
public class SocketClient {

	public static final int PORT = 8090;

	public static void main(String[] args) {
		SocketClient client = new SocketClient();
		client.run();
	}

	public void run() {
		Socket socket = null;
		BufferedReader reader = null;
		BufferedReader inputReader = null;
		BufferedWriter writer = null;
		try {
			socket = new Socket(InetAddress.getLocalHost().getHostAddress(), PORT);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			inputReader = new BufferedReader(new InputStreamReader(System.in));
			startServerReplyLinstener(reader);
			String inputCountent;
			int count = 0;
			while (!(inputCountent = inputReader.readLine()).equals("bye")) {
				writer.write(inputCountent);
				if (count % 2 == 0) {
					writer.write("\n");
				}
				count++;
				writer.flush();
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				writer.close();
				reader.close();
				inputReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void startServerReplyLinstener(final BufferedReader reader) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String response;
					while ((response = reader.readLine()) != null) {
						System.out.println(response);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

}
