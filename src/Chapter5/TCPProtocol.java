package Chapter5;

import java.nio.channels.SelectionKey;
import java.io.IOException;
/**
 * 
 * @TODO     (功能说明:NIO模式读写和建立连接方法)
 * @author   PJL
 * @package  Chapter5
 * @motto    学习需要毅力，那就秀毅力
 * @file     TCPProtocol.java
 * @time     2019年10月29日 下午11:33:43
 */
public interface TCPProtocol {
	void handleAccept(SelectionKey key) throws IOException;

	void handleRead(SelectionKey key) throws IOException;

	void handleWrite(SelectionKey key) throws IOException;
}
