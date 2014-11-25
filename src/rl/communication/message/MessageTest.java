package rl.communication.message;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.communication.message.context.TSVInputContext;
import rl.communication.message.context.TSVOutputContext;


public class MessageTest
{
	private String NormalMessage;
	private String InvalidMessageVersionMessage;
	private String NoBlankLineMessage;
	
	private MessageInputContext mic;
	private MessageOutputContext moc;
	
	private Reader sr;
	private BufferedReader br;
	private StringWriter sw;
	private BufferedWriter bw;


	@Before
	public void setUp() throws Exception
	{
		String MessageVersion="MESSAGE_1.0\n";
		String EV3Version = "EV3LineTracer_1.0\n";
		String BlankLine = "\n";
		
		String Command = "SetMDP\n"
			+"11"+"\n"
			+"10"+"\n"
			+"0	0.1	1"+"\n"
			+"1	0.2	2"+"\n"
			+"2	0.3	2"+"\n"
			+"3	0.4	2"+"\n"
			+"4	0.5	2"+"\n"
			+"5	0.6	2"+"\n"
			+"6	0.7	2"+"\n"
			+"7	0.8	2"+"\n"
			+"8	0.9	2"+"\n"
			+"9	1.0	2"+"\n"
			+"0	0	10	10"+"\n"
			+"1	0	10	5"+"\n"
			+"1	1	5	10"+"\n"
			+"2	0	10	5"+"\n"
			+"2	1	5	10"+"\n"
			+"3	0	10	5"+"\n"
			+"3	1	5	10"+"\n"
			+"4	0	10	5"+"\n"
			+"4	1	5	10"+"\n"
			+"5	0	10	5"+"\n"
			+"5	1	5	10"+"\n"
			+"6	0	10	5"+"\n"
			+"6	1	5	10"+"\n"
			+"7	0	10	5"+"\n"
			+"7	1	5	10"+"\n"
			+"8	0	10	5"+"\n"
			+"8	1	5	10"+"\n"
			+"9	0	10	5"+"\n"
			+"9	1	5	10"+"\n"
			+"0	0"+"\n"
			+"1	1"+"\n"
			+"2	1"+"\n"
			+"3	0"+"\n"
			+"4	0"+"\n"
			+"5	1"+"\n"
			+"6	1"+"\n"
			+"7	0"+"\n"
			+"8	1"+"\n"
			+"9	1"+"\n";

		
		NormalMessage
			=MessageVersion
			+EV3Version
			+Command
			+BlankLine;
		
		InvalidMessageVersionMessage
		="AAAAAAAAAAAA\n"
		+EV3Version
		+Command
		+BlankLine;
	
		
		NoBlankLineMessage
		=MessageVersion
		+EV3Version
		+Command;
		
		
	}

	@After
	public void tearDown() throws Exception
	{
	}
	
	private void setUpContext(String message)
	{
		sr= new StringReader(message);
		br = new BufferedReader(sr);
		sw = new StringWriter();
		bw = new BufferedWriter(sw);

		try
		{
			mic = new TSVInputContext(br);
			moc = new TSVOutputContext(bw);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	private void closeContext()
	{
		try{br.close();} catch (IOException e){}
		try{sr.close();} catch (IOException e){}
		try{bw.close();} catch (IOException e){}
	}
	
	//正常系
	@Test
	public void testMessage()
	{
		setUpContext(NormalMessage);
		
		BufferedReader r = null;
		
		try
		{
			
			Message m = new Message();
			m.process(mic, moc);
			
			//ここではclass Messageのテストのみ行うので
			//例外が発生しないことと出力の末尾に空行があることのみ検証する
			
			//出力結果の読み取り用バッファ
			r = new BufferedReader(new StringReader(sw.toString()));
			
			String previousLine = null;
			String line;
			while((line=r.readLine())!=null)
			{
				previousLine = line;
			}
			if(previousLine!=null)
			{
				if(previousLine.equals(""))
				{
					assertTrue(true);
				}
				else
				{
					fail();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
		finally
		{
			try{r.close();}catch(IOException e){}
		}
		
		closeContext();
		
	}
	//異常系(メッセージバージョンの不正)
	@Test
	public void testMessage_InvalidMessageVersion()
	{
		//ここではclass Messageのテストのみ行う
		//例外が発生することを検証する

		setUpContext(InvalidMessageVersionMessage);
		try
		{
			Message m = new Message();
			m.process(mic, moc);
			
			//例外が発生しなければ失敗
			fail();
		}
		catch(Exception e)
		{
			//例外が発生すれば成功
			e.printStackTrace();
			assertTrue(true);
		}
		
		closeContext();
		
	}
	//異常系(メッセージ末尾の空行無し)
	@Test
	public void testMessage_NoBlankLine()
	{
		//ここではclass Messageのテストのみ行う
		//例外が発生することを検証する

		setUpContext(NoBlankLineMessage);
		try
		{
			
			Message m = new Message();
			m.process(mic, moc);
			
			//例外が発生しなければ失敗
			fail();
		}
		catch(Exception e)
		{
			//例外が発生すれば成功
			e.printStackTrace();
			assertTrue(true);
		}
		
		closeContext();
		

	}

}
