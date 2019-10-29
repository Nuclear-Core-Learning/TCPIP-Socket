package Chapter5;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.ByteBuffer;
import java.io.IOException;
/**
 * 
 * @TODO     (功能说明:NIO 连接、读、写方法实现)
 * @author   PJL
 * @package  Chapter5
 * @motto    学习需要毅力，那就秀毅力
 * @file     EchoSelectorProtocol.java
 * @time     2019年10月29日 下午11:34:34
 */
public class EchoSelectorProtocol implements TCPProtocol {

	private int bufSize; // Size of I/O buffer

	public EchoSelectorProtocol(int bufSize) {
		this.bufSize = bufSize;
	}

	public void handleAccept(SelectionKey key) throws IOException {
		SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
		clntChan.configureBlocking(false); // Must be nonblocking to register
		// Register the selector with new channel for read and attach byte buffer
		clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
	}

	public void handleRead(SelectionKey key) throws IOException {
		// Client socket channel has pending data
		SocketChannel clntChan = (SocketChannel) key.channel();
		ByteBuffer buf = (ByteBuffer) key.attachment();
		long bytesRead = clntChan.read(buf);
		if (bytesRead == -1) { // Did the other end close?
			clntChan.close();
		} else if (bytesRead > 0) {
			// Indicate via key that reading/writing are both of interest now.
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
	}

	public void handleWrite(SelectionKey key) throws IOException {
		/*
		 * Channel is available for writing, and key is valid (i.e., client channel not
		 * closed).
		 */
		// Retrieve data read earlier
		ByteBuffer buf = (ByteBuffer) key.attachment();
		buf.flip(); // Prepare buffer for writing
		SocketChannel clntChan = (SocketChannel) key.channel();
		clntChan.write(buf);
		if (!buf.hasRemaining()) { // Buffer completely written?
			// Nothing left, so no longer interested in writes
			key.interestOps(SelectionKey.OP_READ);
		}
		buf.compact(); // Make room for more data to be read in
	}

}
