package Chapter2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
/**
 * 
 * @TODO    (����˵��:UDP�����)
 * @author  PJL
 * @package Chapter2
 * @motto   ѧϰ��Ҫ�������Ǿ�������
 * @file    UDPEchoServer.java
 * @time    2019��10��22�� ����10:02:14
 */
public class UDPEchoServer {

	private static final int ECHOMAX = 255; // Maximum size of echo datagram

	public static void main(String[] args) throws IOException {
		
		if (args.length == 0) {
			args = new String[1];
			args[0] = "8402";
		}

		if (args.length != 1) { // Test for correct argument list
			throw new IllegalArgumentException("Parameter(s): <Port>");
		}

		int servPort = Integer.parseInt(args[0]);

		DatagramSocket socket = new DatagramSocket(servPort);
		DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

		while (true) { // Run forever, receiving and echoing datagrams
			socket.receive(packet); // Receive packet from client
			System.out.println(
					"Handling client at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());
			socket.send(packet); // Send the same packet back to client
			packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
		}
		/* NOT REACHED */
	}
}
