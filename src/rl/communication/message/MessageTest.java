package rl.communication.message;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageTest
{
	private String NormalMessage;
	private String InvalidMessageVersionMessage;
	private String NoBlankLineMessage;
	


	@Before
	public void setUp() throws Exception
	{

		//正常なメッセージ(正常系向け)
		NormalMessage
			=TestMessage.NormalMessageNullCommand;
		
		//MessageVersionが不正なメッセージ
		InvalidMessageVersionMessage
		="AAAAAAAAAAAA\n"				//MessageVersionが不正
		+TestMessage.EV3Version
		+TestMessage.CommandNullCommand
		+TestMessage.BlankLine;
	
		//メッセージ末尾の改行がないメッセージ
		NoBlankLineMessage
		=TestMessage.MessageVersion
		+TestMessage.EV3Version
		+TestMessage.CommandNullCommand;
		
		
	}

	@After
	public void tearDown() throws Exception
	{
	}
	
	//正常系
	@Test
	public void testMessage()
	{
		TestMessageContext tmc = null;
		BufferedReader r = null;
		
		try
		{
			tmc = new TestMessageContext(NormalMessage);

			
			Message m = new Message();
			m.process(tmc.getMessageInputContext(), tmc.getMessageOutputContext());
			
			//ここではclass Messageのテストのみ行うので
			//例外が発生しないことと出力の末尾に空行があることのみ検証する
			
			//出力結果の読み取り用バッファ
			r = new BufferedReader(new StringReader(tmc.getStringWriter().toString()));
			
			//末尾の行を取得する
			//バッファの１行
			String line;
			//lineの直前の行
			String previousLine = null;
			//バッファの最後の行を取得する
			while((line=r.readLine())!=null)
			{
				previousLine = line;
			}
			if(previousLine!=null)
			{
				//バッファに１行以上ある場合
				if(previousLine.equals(""))
				{
					//最後の行が空行の場合は成功
					assertTrue(true);
				}
				else
				{
					//最後の行が空行でない場合は失敗
					fail();
				}
			}
			else
			{
				//バッファに一行もない場合は失敗
				fail();
			}
		}
		catch(Exception e)
		{
			//例外が発生したら失敗
			e.printStackTrace();
			fail();
		}
		finally
		{
			try{r.close();}catch(IOException e){}
			tmc.close();
		}
		
		
	}
	//異常系(メッセージバージョンの不正)
	@Test
	public void testMessage_InvalidMessageVersion()
	{
		//ここではclass Messageのテストのみ行う
		//例外が発生することを検証する
		
		TestMessageContext tmc = null;
		
		try
		{
			tmc = new TestMessageContext(InvalidMessageVersionMessage);

			Message m = new Message();
			m.process(tmc.getMessageInputContext(), tmc.getMessageOutputContext());
			
			//例外が発生しなければ失敗
			fail();
		}
		catch(Exception e)
		{
			//例外が発生すれば成功
			e.printStackTrace();
			assertTrue(true);
		}
		finally
		{
			tmc.close();
		}
		
		
		
	}
	//異常系(メッセージ末尾の空行無し)
	@Test
	public void testMessage_NoBlankLine()
	{
		//ここではclass Messageのテストのみ行う
		//例外が発生することを検証する

		TestMessageContext tmc = null;
		
		try
		{
			tmc = new TestMessageContext(NoBlankLineMessage);
			
			Message m = new Message();
			m.process(tmc.getMessageInputContext(), tmc.getMessageOutputContext());
			
			//例外が発生しなければ失敗
			fail();
		}
		catch(Exception e)
		{
			//例外が発生すれば成功
			e.printStackTrace();
			assertTrue(true);
		}
		finally
		{
			tmc.close();
		}
		
		

	}

}
