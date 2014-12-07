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

	private void testCreateCommand(String input_string,
			String normal_output_string, boolean normaltest)
	{
		TestMessageContext tmc = null;
		try
		{
			// MessageContextの準備
			tmc = new TestMessageContext(input_string);
			MessageInputContext input = tmc.getMessageInputContext();
			MessageOutputContext output = tmc.getMessageOutputContext();

			// 処理の実行
			TestEV3LineTracer_1_0_Command m = new TestEV3LineTracer_1_0_Command();
			m.process(input, output);

			// 出力結果の取得
			output.flush();
			StringWriter sw = tmc.getStringWriter();
			String output_string = sw.toString();

			// 出力結果が想定通りかチェック
			if (output_string.equals(normal_output_string))
			{
				// 想定通りなら成功
				if (normaltest)
				{
					assertTrue(true);
				} else
				{
					fail();
				}
			} else
			{
				// 想定通りでないなら失敗
				if (normaltest)
				{
					fail();
				} else
				{
					assertTrue(true);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			// 例外が発生したら失敗
			if (normaltest)
			{
				fail();
			} else
			{
				assertTrue(true);
			}
		}
	}

	// 正常系(NullCommand)
	@Test
	public final void testCreateCommand_NullCommand()
	{

		String normal_output_string = EV3LineTracer_1_0_Command.VERSION_STRING
				+ "\n" + CommandNullCommand.COMMAND_STRING + "\nOK\n";
		testCreateCommand(TestMessage.CommandNullCommand, normal_output_string,
				true);
	}

	// 正常系(ExecEpisode)
	@Test
	public final void testCreateCommand_ExecEpisode()
	{
		String normal_output_string = EV3LineTracer_1_0_Command.VERSION_STRING
				+ "\n" + TestCommandExecEpisode.NORMAL_OUTPUT;
		testCreateCommand(TestMessage.CommandExecEpisode, normal_output_string,
				true);
	}

	// 正常系(SetMDP)
	@Test
	public final void testCreateCommand_SetMDP()
	{
		String normal_output_string = EV3LineTracer_1_0_Command.VERSION_STRING
				+ "\n" + CommandSetMDP.COMMAND_STRING + "\n"
				+ CommandSetMDP.RESULT_OK + "\n";
		testCreateCommand(TestMessage.CommandSetMDP, normal_output_string, true);
	}

	// 異常系(存在しないコマンド)
	@Test
	public final void testCreateCommand_InvalidCommand()
	{
		String input_string = "AAAAAAAAAAAAA\n";
		String normal_output_string = "\n";

		testCreateCommand(input_string, normal_output_string, false);
	}

}
