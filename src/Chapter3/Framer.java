package Chapter3;

import java.io.IOException;
import java.io.OutputStream;
/**
 * @TODO     (����˵��:����֡�ӿ�)
 * @author   PJL
 * @package  Chapter3
 * @motto    ѧϰ��Ҫ�������Ǿ�������
 * @file     Framer.java
 * @time     2019��10��22�� ����10:11:58
 */
public interface Framer {
	
	void frameMsg(byte[] message, OutputStream out) throws IOException;

	byte[] nextMsg() throws IOException;
}
