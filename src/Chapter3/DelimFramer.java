package Chapter3;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * @TODO     (功能说明:消息成帧和解析（通过分隔符判定消息是否完整）)
 * @author   PJL
 * @package  Chapter3
 * @motto    学习需要毅力，那就秀毅力
 * @file     DelimFramer.java
 * @time     2019年10月22日 下午10:06:06
 */
public class DelimFramer implements Framer {

	private InputStream in; // data source
	
	private static final byte DELIMITER = '\n'; // message delimiter

	public DelimFramer(InputStream in) {
		this.in = in;
	}

	/**
	 * 完整帧消息
	 */
	public void frameMsg(byte[] message, OutputStream out) throws IOException {
		// ensure that the message does not contain the delimiter
		for (byte b : message) {
			if (b == DELIMITER) {
				throw new IOException("Message contains delimiter");
			}
		}
		out.write(message);
		out.write(DELIMITER);
		out.flush();
	}

	/**
	 * 字节读取消息
	 */
	public byte[] nextMsg() throws IOException {
		ByteArrayOutputStream messageBuffer = new ByteArrayOutputStream();
		int nextByte;

		// fetch bytes until find delimiter
		while ((nextByte = in.read()) != DELIMITER) {
			if (nextByte == -1) { // end of stream?
				if (messageBuffer.size() == 0) { // if no byte read
					return null;
				} else { // if bytes followed by end of stream: framing error
					throw new EOFException("Non-empty message without delimiter");
				}
			}
			messageBuffer.write(nextByte); // write byte to buffer
		}

		return messageBuffer.toByteArray();
	}
}
