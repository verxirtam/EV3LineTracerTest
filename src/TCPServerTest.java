import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rl.communication.TCPServer;
import rl.communication.message.TestMessage;
import rl.linetracer.communication.CommandNullCommand;


//Interpreterパターンに則った方法に切り替えるためこのファイルの内容は全面的に書き換えられる

public class TCPServerTest
{


	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}




	@Test
	public void testRun()
	{
		try
		(
			//テスト用のソケットの生成
			TestSocket ts = new TestSocket(TestMessage.NormalMessageNullCommand);
		)
		{
			//TCPServerの生成(MessageProcedureはテスト用を指定)
			TCPServer t = new TCPServer();
			
			//run()の実行
			t.run(ts);
			//結果の取得
			String outputstring =((ByteArrayOutputStream) ts.getOutputStream()).toString("UTF-8");
			String normal_output = TestMessage.MessageVersion
					+TestMessage.EV3Version
					+TestMessage.CommandNullCommand
					+CommandNullCommand.RESULT_OK+"\n"
					+TestMessage.BlankLine;
			
			//結果が想定と同じか確認
			if(!outputstring.equals(normal_output))
			{
				fail();
			}

		} catch (Exception e)
		{
			fail();
		}
	}
	//不正なメッセージを受信した時例外が発出されることを確認する
	@Test
	public void testRun_Exception()
	{
		try
		(
			//テスト用のソケットの生成
			TestSocket ts = new TestSocket("\n");
		)
		{
			//TCPServerの生成(MessageProcedureはテスト用を指定)
			TCPServer t = new TCPServer();
			
			//run()の実行
			t.run(ts);
			fail();
			

		} catch (Exception e)
		{
			assertTrue(true);
		}
	}
}
