package Chapter5;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
/**
 * 
 * @TODO     (����˵��:TCP-NIO�������ͻ���)
 * @author   PJL
 * @package  Chapter5
 * @motto    ѧϰ��Ҫ�������Ǿ�������
 * @file     TCPEchoClientNonblocking.java
 * @time     2019��10��29�� ����11:13:45
 */
public class TCPEchoClientNonblocking {

	public static void main(String args[]) throws Exception {
		
		if(args.length==0) {
			args=new String[3];
			args[0]="127.0.0.1";
			args[1]="I am the man who is looking at you standing near the window.";
			args[2]="5433";
		}

		if ((args.length < 2) || (args.length > 3)) // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

		String server = args[0]; // Server name or IP address
		// Convert input String to bytes using the default charset
		byte[] argument = args[1].getBytes();

		int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

		// Create channel and set to nonblocking
		SocketChannel clntChan = SocketChannel.open();
		clntChan.configureBlocking(false);

		// һֱ�ȴ�ֱ�����ӳɹ�===æ��
		// Initiate connection to server and repeatedly poll until complete
		if (!clntChan.connect(new InetSocketAddress(server, servPort))) {
			while (!clntChan.finishConnect()) {
				System.out.print("."); // Do something else
			}
		}
		// װ��һ��ByteBuffer������
		ByteBuffer writeBuf = ByteBuffer.wrap(argument);
		// ������Ҫ����һ��ByteBuffer������
		ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
		int totalBytesRcvd = 0; // Total bytes received so far
		int bytesRcvd; // Bytes received in last read
		// ֪����ȡд������
		while (totalBytesRcvd < argument.length) {
			if (writeBuf.hasRemaining()) {
				clntChan.write(writeBuf);
			}
			if ((bytesRcvd = clntChan.read(readBuf)) == -1) {
				throw new SocketException("Connection closed prematurely");
			}
			totalBytesRcvd += bytesRcvd;
			System.out.print("."); // Do something else
		}

		System.out.println("Received: " + // convert to String per default charset
				new String(readBuf.array(), 0, totalBytesRcvd));
		clntChan.close();
	}
}
