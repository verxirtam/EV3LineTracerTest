package rl.communication.message;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rl.linetracer.communication.CommandNullCommand;

public class Message_1_0_BodyTest
{
	String normalMessage;
	String invalidEV3VersionMessage;
	String invalidEV3VersionMessage2;

	@Before
	public void setUp() throws Exception
	{
		normalMessage = 
				TestMessage.EV3Version 
				+ TestMessage.CommandNullCommand
				+ TestMessage.BlankLine;
		invalidEV3VersionMessage = 
				"AAAAAAAAAAA\n"
				+ TestMessage.CommandNullCommand 
				+ TestMessage.BlankLine;
		String ev3version_without_return=
				TestMessage.EV3Version.substring(0,
						TestMessage.EV3Version.length()-1);
		invalidEV3VersionMessage2 = 
				ev3version_without_return+"\tAAA"
				+ TestMessage.CommandNullCommand
				+ TestMessage.BlankLine;

	}

	@After
	public void tearDown() throws Exception
	{
	}

	// 正常系
	@Test
	public void testProcess()
	{
		TestMessageContext tmc = null;
		try
		{
			tmc = new TestMessageContext(normalMessage);
			Message_1_0_Body m = new Message_1_0_Body();

			m.process(tmc.getMessageInputContext(),
					tmc.getMessageOutputContext());

			//出力結果が正しいことを確認する
			//出力バッファの内容を書き出す
			tmc.getMessageOutputContext().flush();
			// 出力結果を文字列として取得する
			String out = tmc.getStringWriter().toString();
			//想定される出力結果
			String normalout = Message_1_0_Body.VERSION_STRING + "\n"
					+ CommandNullCommand.COMMAND_STRING+"\n"
					+ CommandNullCommand.RESULT_OK + "\n";
			//想定される出力結果と一致しているか確認する
			if (out.equals(normalout))
			{
				assertTrue(true);
			} else
			{
				fail();
			}
		} catch (Exception e)
		{
			//例外が発生したら失敗
			e.printStackTrace();
			fail();
		}
		finally
		{
			tmc.close();
		}
	}

	// 異常系(EV3バージョンが不正な場合)
	// この場合に例外が発生することを確認する
	@Test
	public void testProcess_InvalidEV3Version()
	{
		TestMessageContext tmc = null;
		try
		{
			tmc = new TestMessageContext(invalidEV3VersionMessage);
			Message_1_0_Body m = new Message_1_0_Body();
			
			m.process(tmc.getMessageInputContext(), tmc.getMessageOutputContext());
			
			//例外が発生せずにこの行に達したら失敗
			fail();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//例外が発生したら成功
			assertTrue(true);
			
		}
		finally
		{
			tmc.close();
		}
	}

	// 異常系(EV3バージョンの行に更にTokenがあった場合)
	// この場合に例外が発生することを確認する
	@Test
	public void testProcess_InvalidEV3Version2()
	{
		TestMessageContext tmc = null;
		try
		{
			tmc = new TestMessageContext(invalidEV3VersionMessage2);
			Message_1_0_Body m = new Message_1_0_Body();

			m.process(tmc.getMessageInputContext(),
					tmc.getMessageOutputContext());

			// 例外が発生せずにこの行に達したら失敗
			fail();
		} catch (Exception e)
		{
			e.printStackTrace();
			// 例外が発生したら成功
			assertTrue(true);

		} finally
		{
			tmc.close();
		}
	}
}
