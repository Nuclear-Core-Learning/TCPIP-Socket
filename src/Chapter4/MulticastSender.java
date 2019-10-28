package Chapter4;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
/**
 * 
 * @TODO     (功能说明:客户端)
 * @author   PJL
 * @package  Chapter4
 * @motto    学习需要毅力，那就秀毅力
 * @file     MulticastSender.java
 * @time     2019年10月28日 下午10:52:01
 */
public class MulticastSender {

	private int port;
	private String host;
	private String data;

	public MulticastSender(String data, String host, int port) {
		this.data = data;
		this.host = host;
		this.port = port;
	}

	public void send() {
		try {
			InetAddress ip = InetAddress.getByName(this.host);
			DatagramPacket packet = new DatagramPacket(this.data.getBytes(), this.data.length(), ip, this.port);
			MulticastSocket ms = new MulticastSocket();
			ms.send(packet);
			ms.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int port = 1234;
		String host = "224.0.0.1";
		String data = "hello world.";
		MulticastSender ms = new MulticastSender(data, host, port);
		ms.send();
	}

}
