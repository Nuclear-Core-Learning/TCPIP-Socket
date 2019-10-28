package Chapter4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import Chapter3.VoteMsg;
import Chapter3.VoteMsgTextCoder;
/**
 * 
 * @TODO     (����˵��:�鲥�ಥhttps://baike.baidu.com/item/%E7%BB%84%E6%92%AD%E5%9C%B0%E5%9D%80/6095039?fr=aladdin)
 * @author   PJL
 * @package  Chapter4
 * @motto    ѧϰ��Ҫ�������Ǿ�������
 * @file     VoteMulticastReceiver.java
 * @time     2019��10��28�� ����10:02:47
 * <li>
 * 224.0.0.0��224.0.0.255ΪԤ�����鲥��ַ���������ַ������ַ224.0.0.0�����������䣬������ַ��·��Э��ʹ�ã�
 * 224.0.1.0��224.0.1.255�ǹ����鲥��ַ����������Internet��
 * 224.0.2.0��238.255.255.255Ϊ�û����õ��鲥��ַ����ʱ���ַ����ȫ����Χ����Ч��
 * 239.0.0.0��239.255.255.255Ϊ���ع����鲥��ַ�������ض��ı��ط�Χ����Ч��
 * </li>
 */
public class VoteMulticastReceiver {

	public static void main(String[] args) throws IOException {
		
		if(args.length==0) {
			args=new String[2];
			args[0]="224.0.0.221";
			args[1]="8380";
		}

		if (args.length != 2) { // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Multicast Addr> <Port>");
		}

		InetAddress address = InetAddress.getByName(args[0]); // Multicast address
		if (!address.isMulticastAddress()) { // Test if multicast address
			throw new IllegalArgumentException("Not a multicast address");
		}

		int port = Integer.parseInt(args[1]); // Multicast port
		MulticastSocket sock = new MulticastSocket(port); // for receiving
		sock.joinGroup(address); // Join the multicast group

		VoteMsgTextCoder coder = new VoteMsgTextCoder();

		// Receive a datagram
		DatagramPacket packet = new DatagramPacket(new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH],
				VoteMsgTextCoder.MAX_WIRE_LENGTH);
		sock.receive(packet);

		VoteMsg vote = coder.fromWire(Arrays.copyOfRange(packet.getData(), 0, packet.getLength()));

		System.out.println("Received Text-Encoded Request (" + packet.getLength() + " bytes): ");
		System.out.println(vote);

		sock.close();
	}
}
