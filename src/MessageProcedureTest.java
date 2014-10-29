import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rl.communication.Command;
import rl.communication.MessageProcedure;
import rl.linetracer.CommandExecEpisode;
import rl.linetracer.CommandSetMDP;
import rl.linetracer.CommandSetPolicy;


public class MessageProcedureTest
{
	private String SetMDPBody;
	private String SetPolicyBody;

	@Before
	public void setUp() throws Exception
	{
		//SetMDP用body
		SetMDPBody
			="EV3LineTracer_1.0\n"
			+"SetMDP\n"
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
			+"9	1";

		SetPolicyBody
			="EV3LineTracer_1.0\n"
			+"SetPolicy\n"
			+"0	1.0\n"
			+"1	0.25	0.75\n"
			+"2	0.25	0.75\n"
			+"3	0.75	0.25\n"
			+"4	0.75	0.25\n"
			+"5	0.25	0.75\n"
			+"6	0.25	0.75\n"
			+"7	0.75	0.25\n"
			+"8	0.25	0.75\n"
			+"9	0.25	0.75";

	}

	@After
	public void tearDown() throws Exception
	{
	}


	@Test
	public void testMessageProcess()
	{
		BufferedReader br=new BufferedReader(new StringReader(SetMDPBody));
		//結果出力用BufferedWriter
		BufferedWriter bw=new BufferedWriter(new StringWriter());
		MessageProcedure mp=new MessageProcedure();
		try
		{
			mp.MessageProcess(br, bw);
			assertTrue(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			fail("Exception occored.");
		}
		br=new BufferedReader(new StringReader(SetPolicyBody));
		try
		{
			mp.MessageProcess(br, bw);
			assertTrue(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Exception occored.");
		}
		String SetMDPBodyError="EV3LineTracer_1.1\n";
		br=new BufferedReader(new StringReader(SetMDPBodyError));
		try
		{
			mp.MessageProcess(br, bw);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
			assertTrue(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Exception occored.");
		}
	}

	//GetCommandのテスト
	//メッセージのコマンド部を読み取り、対応するコマンドオブジェクトを取得できるかどうかを確認する
	//存在しないコマンドに対しては例外が発生することを確認する
	@Test
	public void testGetCommand()
	{
		try
		{
			//SetMDP
			//-----------------------------------
			String messagebody="SetMDP\n";
			BufferedReader br=new BufferedReader(new StringReader(messagebody));
			MessageProcedure mp=new MessageProcedure();
			Command c=mp.GetCommand(br);
			assertTrue(c.getClass().equals(CommandSetMDP.class));
			//-----------------------------------
			
			//SetPolicy
			//-----------------------------------
			messagebody="SetPolicy\n";
			br=new BufferedReader(new StringReader(messagebody));
			mp=new MessageProcedure();
			c=mp.GetCommand(br);
			assertTrue(c.getClass().equals(CommandSetPolicy.class));
			//-----------------------------------
			
			//ExecEpisode
			//-----------------------------------
			messagebody="ExecEpisode\n";
			br=new BufferedReader(new StringReader(messagebody));
			mp=new MessageProcedure();
			c=mp.GetCommand(br);
			assertTrue(c.getClass().equals(CommandExecEpisode.class));
			//-----------------------------------
			
			//存在しないコマンド
			//例外IllegalArgumentExceptionが発生することを確認する
			//-----------------------------------
			messagebody="NotExistCommand\n";
			br=new BufferedReader(new StringReader(messagebody));
			mp=new MessageProcedure();
			try
			{
				c=mp.GetCommand(br);
				fail();
			}catch(IllegalArgumentException e)
			{
				assertTrue(true);
			}
			//-----------------------------------

		}
		catch(Exception e)
		{
			//ここに到達した場合はテスト失敗
			e.printStackTrace();
			fail();
		}
	}

}
