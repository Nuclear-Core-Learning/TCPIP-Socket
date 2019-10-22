package Chapter3;

import java.io.IOException;

/**
 * 
 * @TODO     (功能说明:VoteMsg编解码接口)
 * @author   PJL
 * @package  Chapter3
 * @motto    学习需要毅力，那就秀毅力
 * @file     VoteMsgCoder.java
 * @time     2019年10月22日 下午10:28:42
 */
public interface VoteMsgCoder {
	
	byte[] toWire(VoteMsg msg) throws IOException;

	VoteMsg fromWire(byte[] input) throws IOException;
}
