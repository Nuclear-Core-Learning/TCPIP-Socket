package Chapter4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import Chapter3.VoteMsg;
import Chapter3.VoteMsgCoder;
import Chapter3.VoteMsgTextCoder;
/**
 * 
 * @TODO     (����˵��:�鲥�ಥhttps://baike.baidu.com/item/%E7%BB%84%E6%92%AD%E5%9C%B0%E5%9D%80/6095039?fr=aladdin)
 * @author   PJL
 * @package  Chapter4
 * @motto    ѧϰ��Ҫ�������Ǿ�������
 * @file     VoteMulticastSender.java
 * @time     2019��10��28�� ����10:03:11
 * <li>
 * 224.0.0.0��224.0.0.255ΪԤ�����鲥��ַ���������ַ������ַ224.0.0.0�����������䣬������ַ��·��Э��ʹ�ã�
 * 224.0.1.0��224.0.1.255�ǹ����鲥��ַ����������Internet��
 * 224.0.2.0��238.255.255.255Ϊ�û����õ��鲥��ַ����ʱ���ַ����ȫ����Χ����Ч��
 * 239.0.0.0��239.255.255.255Ϊ���ع����鲥��ַ�������ض��ı��ط�Χ����Ч��
 * </li>
 */
public class VoteMulticastSender {

	public static final int CANDIDATEID = 475;

	public static void main(String args[]) throws IOException {
		
		if(args.length==0) {
			args=new String[3];
			args[0]="224.0.0.221";
			args[1]="8380";
			args[2]="255";
		}

		if ((args.length < 2) || (args.length > 3)) { // Test # of args
			throw new IllegalArgumentException("Parameter(s): <Multicast Addr> <Port> [<TTL>]");
		}

		InetAddress destAddr = InetAddress.getByName(args[0]); // Destination
		if (!destAddr.isMulticastAddress()) { // Test if multicast address
			throw new IllegalArgumentException("Not a multicast address");
		}

		int destPort = Integer.parseInt(args[1]); // Destination port
		int TTL = (args.length == 3) ? Integer.parseInt(args[2]) : 1; // Set TTL

		// MulticastSocket ʵ��ʵ���Ͼ���һ�� UDP �׽��֣�DatagramSocket��
		MulticastSocket sock = new MulticastSocket();
		sock.setTimeToLive(TTL); // Set TTL for all datagrams

		VoteMsgCoder coder = new VoteMsgTextCoder();

		VoteMsg vote = new VoteMsg(true, true, CANDIDATEID, 1000001L);

		// Create and send a datagram
		byte[] msg = coder.toWire(vote);
		DatagramPacket message = new DatagramPacket(msg, msg.length, destAddr, destPort);
		System.out.println("Sending Text-Encoded Request (" + msg.length + " bytes): ");
		System.out.println(vote);
		sock.send(message);

		sock.close();
	}
}
