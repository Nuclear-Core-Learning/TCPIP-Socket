package Chapter3;

import java.io.IOException;

/**
 * 
 * @TODO     (����˵��:VoteMsg�����ӿ�)
 * @author   PJL
 * @package  Chapter3
 * @motto    ѧϰ��Ҫ�������Ǿ�������
 * @file     VoteMsgCoder.java
 * @time     2019��10��22�� ����10:28:42
 */
public interface VoteMsgCoder {
	
	byte[] toWire(VoteMsg msg) throws IOException;

	VoteMsg fromWire(byte[] input) throws IOException;
}
