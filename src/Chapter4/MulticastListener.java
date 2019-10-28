package Chapter4;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
/**
 * 
 * @TODO     (����˵��:�����)
 * @author   PJL
 * @package  Chapter4
 * @motto    ѧϰ��Ҫ�������Ǿ�������
 * @file     MulticastListener.java
 * @time     2019��10��28�� ����10:52:20
 */
public class MulticastListener {

	private int port;
	private String host;

	public MulticastListener(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void listen() {
		byte[] data = new byte[256];
		try {
			InetAddress ip = InetAddress.getByName(this.host);
			MulticastSocket ms = new MulticastSocket(this.port);
			//ms.setBroadcast(true);
			ms.joinGroup(ip);
			DatagramPacket packet = new DatagramPacket(data, data.length);
			ms.receive(packet);
			String message = new String(packet.getData(), 0, packet.getLength());
			System.out.println(message);
			ms.send(packet);
			ms.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int port = 1234;
		String host = "224.0.0.1";
		MulticastListener ml = new MulticastListener(host, port);
		while (true) {
			ml.listen();
		}
	}

}
