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
 * @TODO     (功能说明:组播多播https://baike.baidu.com/item/%E7%BB%84%E6%92%AD%E5%9C%B0%E5%9D%80/6095039?fr=aladdin)
 * @author   PJL
 * @package  Chapter4
 * @motto    学习需要毅力，那就秀毅力
 * @file     VoteMulticastReceiver.java
 * @time     2019年10月28日 下午10:02:47
 * <li>
 * 224.0.0.0～224.0.0.255为预留的组播地址（永久组地址），地址224.0.0.0保留不做分配，其它地址供路由协议使用；
 * 224.0.1.0～224.0.1.255是公用组播地址，可以用于Internet；
 * 224.0.2.0～238.255.255.255为用户可用的组播地址（临时组地址），全网范围内有效；
 * 239.0.0.0～239.255.255.255为本地管理组播地址，仅在特定的本地范围内有效。
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
