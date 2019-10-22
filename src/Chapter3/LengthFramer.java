package Chapter3;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * 
 * @TODO     (功能说明:消息成帧和解析（通过长度判定消息是否完整）适用于长度小于 65535)
 * @author   PJL
 * @package  Chapter3
 * @motto    学习需要毅力，那就秀毅力
 * @file     LengthFramer.java
 * @time     2019年10月22日 下午10:11:14
 */
public class LengthFramer implements Framer {
	
	public static final int MAXMESSAGELENGTH = 65535;
	
	public static final int BYTEMASK = 0xff;
	
	public static final int SHORTMASK = 0xffff;
	
	public static final int BYTESHIFT = 8;

	private DataInputStream in; // wrapper for data I/O

	public LengthFramer(InputStream in) throws IOException {
		this.in = new DataInputStream(in);
	}

	/**
	 * 完整帧消息
	 */
	public void frameMsg(byte[] message, OutputStream out) throws IOException {
		// validate message length
		if (message.length > MAXMESSAGELENGTH) {
			throw new IOException("message too long");
		}
		// write length prefix
		out.write((message.length >> BYTESHIFT) & BYTEMASK);
		out.write(message.length & BYTEMASK);
		// write message
		out.write(message);
		out.flush();
	}

	/**
	 * 字节读取消息
	 */
	public byte[] nextMsg() throws IOException {
		int length;
		try {
			length = in.readUnsignedShort(); // read 2 bytes
		} catch (EOFException e) { // no (or 1 byte) message
			return null;
		}
		// 0 <= length <= 65535
		byte[] msg = new byte[length];
		in.readFully(msg); // if exception, it's a framing error.
		return msg;
	}
}
