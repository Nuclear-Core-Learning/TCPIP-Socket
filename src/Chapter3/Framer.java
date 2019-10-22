package Chapter3;

import java.io.IOException;
import java.io.OutputStream;
/**
 * @TODO     (功能说明:定义帧接口)
 * @author   PJL
 * @package  Chapter3
 * @motto    学习需要毅力，那就秀毅力
 * @file     Framer.java
 * @time     2019年10月22日 下午10:11:58
 */
public interface Framer {
	
	void frameMsg(byte[] message, OutputStream out) throws IOException;

	byte[] nextMsg() throws IOException;
}
