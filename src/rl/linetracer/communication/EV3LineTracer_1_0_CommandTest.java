package rl.linetracer.communication;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rl.communication.message.TestMessage;
import rl.communication.message.TestMessageContext;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

public class EV3LineTracer_1_0_CommandTest
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
	public final void testProcess()
	{
		
	}

	//正常系(NullCommand)
	@Test
	public final void testCreateCommand_NullCommand()
	{
		TestMessageContext tmc = null;
		try
		{
			//MessageContextの準備
			tmc = new TestMessageContext(TestMessage.CommandNullCommand);
			MessageInputContext input = tmc.getMessageInputContext();
			MessageOutputContext output = tmc.getMessageOutputContext();
			
			//処理の実行
			EV3LineTracer_1_0_Command m = new EV3LineTracer_1_0_Command();
			m.process(input, output);
			
			//出力結果の取得
			StringWriter sw = tmc.getStringWriter();
			sw.flush();
			String output_string = sw.toString();
			
			//想定される出力結果
			String normal_output_string = "NullCommand\nOK\n";
			
			//出力結果が想定通りかチェック
			if(output_string.equals(normal_output_string))
			{
				//想定通りなら成功
				assertTrue(true);
			}
			else
			{
				//想定通りでないなら失敗
				fail();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//例外が発生したら失敗
			fail();
		}
	}

}
