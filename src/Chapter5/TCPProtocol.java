package Chapter5;

import java.nio.channels.SelectionKey;
import java.io.IOException;
/**
 * 
 * @TODO     (����˵��:NIOģʽ��д�ͽ������ӷ���)
 * @author   PJL
 * @package  Chapter5
 * @motto    ѧϰ��Ҫ�������Ǿ�������
 * @file     TCPProtocol.java
 * @time     2019��10��29�� ����11:33:43
 */
public interface TCPProtocol {
	void handleAccept(SelectionKey key) throws IOException;

	void handleRead(SelectionKey key) throws IOException;

	void handleWrite(SelectionKey key) throws IOException;
}
