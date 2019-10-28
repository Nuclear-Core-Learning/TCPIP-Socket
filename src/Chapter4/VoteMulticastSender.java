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
 * @TODO     (功能说明:组播多播https://baike.baidu.com/item/%E7%BB%84%E6%92%AD%E5%9C%B0%E5%9D%80/6095039?fr=aladdin)
 * @author   PJL
 * @package  Chapter4
 * @motto    学习需要毅力，那就秀毅力
 * @file     VoteMulticastSender.java
 * @time     2019年10月28日 下午10:03:11
 * <li>
 * 224.0.0.0～224.0.0.255为预留的组播地址（永久组地址），地址224.0.0.0保留不做分配，其它地址供路由协议使用；
 * 224.0.1.0～224.0.1.255是公用组播地址，可以用于Internet；
 * 224.0.2.0～238.255.255.255为用户可用的组播地址（临时组地址），全网范围内有效；
 * 239.0.0.0～239.255.255.255为本地管理组播地址，仅在特定的本地范围内有效。
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

		// MulticastSocket 实例实际上就是一个 UDP 套接字（DatagramSocket）
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
