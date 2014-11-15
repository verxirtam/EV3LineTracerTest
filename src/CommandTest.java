import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rl.StochasticPolicy;
import rl.linetracer.EV3LineTracer;
import rl.linetracer.communication.old.CommandExecEpisode;
import rl.linetracer.communication.old.CommandSetMDP;
import rl.linetracer.communication.old.CommandSetPolicy;

public class CommandTest
{
	private EV3LineTracer ev3linetracer;

	//テスト実行の準備としてEV3LineTracerを初期化する
	@Before
	public void setUp() throws Exception
	{
		ev3linetracer=new EV3LineTracer();
		CommandSetMDP c=new CommandSetMDP(ev3linetracer);
		//SetMDP用body
		String body;
		body ="11"+"\n"
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
		//メッセージ読み込み用BufferedReader
		BufferedReader brbody=new BufferedReader(new StringReader(body));
		//結果出力用BufferedWriter
		BufferedWriter bwresult=new BufferedWriter(new StringWriter());
		//コマンド(SretMDP)実行
		c.DoCommand(brbody, bwresult);
		//ストリームを閉じる
		brbody.close();
		//ストリームを閉じる
		bwresult.close();
		
		//CurrentPolicyを設定する
		CommandSetPolicy c1=new CommandSetPolicy(ev3linetracer);
		body="0	1.0\n"
			+"1	0.25	0.75\n"
			+"2	0.25	0.75\n"
			+"3	0.75	0.25\n"
			+"4	0.75	0.25\n"
			+"5	0.25	0.75\n"
			+"6	0.25	0.75\n"
			+"7	0.75	0.25\n"
			+"8	0.25	0.75\n"
			+"9	0.25	0.75";
		brbody=new BufferedReader(new StringReader(body));
		bwresult=new BufferedWriter(new StringWriter());
		c1.DoCommand(brbody, bwresult);
		//ストリームを閉じる
		brbody.close();
		//ストリームを閉じる
		bwresult.close();

	}
	
	@After
	public void tearDown()
	{
	}
	
	@Test
	public void testSetMDP()
	{
		try
		{
			//ev3linetracerの各パラメータがSetMDP、SetPolicyで定めた値になっていることを確認する
			assertEquals(ev3linetracer.GetInterval(),11);
			assertEquals(ev3linetracer.GetStateCount(),10);
			assertEquals(ev3linetracer.GetControlCount(0),1);
			for(int i=1;i<ev3linetracer.GetStateCount();i++)
			{
				assertEquals(ev3linetracer.GetControlCount(i),2);
			}
			StochasticPolicy sp=ev3linetracer.GetRegularPolicy();
			assertEquals(sp.At(0).GetProbability(0),1.0,0.0001);
			assertEquals(sp.At(1).GetProbability(1),1.0,0.0001);
			assertEquals(sp.At(2).GetProbability(1),1.0,0.0001);
			assertEquals(sp.At(3).GetProbability(0),1.0,0.0001);
			assertEquals(sp.At(4).GetProbability(0),1.0,0.0001);
			assertEquals(sp.At(5).GetProbability(1),1.0,0.0001);
			assertEquals(sp.At(6).GetProbability(1),1.0,0.0001);
			assertEquals(sp.At(7).GetProbability(0),1.0,0.0001);
			assertEquals(sp.At(8).GetProbability(1),1.0,0.0001);
			assertEquals(sp.At(9).GetProbability(1),1.0,0.0001);
			
		}
		catch(Exception e)
		{
			fail("Test Failor!");
		}
		finally
		{

		}
	}
	//SetMDPで、body部が不完全な場合に例外が発生することを確認する
	@Test
	public void testSetMDP_Exception()
	{
		EV3LineTracer ev3=new EV3LineTracer();
		CommandSetMDP c=new CommandSetMDP(ev3);
		//不完全なbody部(Controlの定義が途中で終わっている)
		String body ="11"+"\n"
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
				+"3	1	5	10"+"\n";
		BufferedReader brbody=new BufferedReader(new StringReader(body));
		BufferedWriter brresult=new BufferedWriter(new StringWriter());
		try
		{
			//コマンドの実行
			c.DoCommand(brbody, brresult);
			//ストリームを閉じる
			brbody.close();
			//ストリームを閉じる
			brresult.close();
		}
		catch(Exception e)
		{
			System.out.println(brresult);
		}
		
	}
	
	//SetPolicyコマンドのテスト
	//CurrentPolicyを設定し、指定したとおりに設定されているかを確認する
	@Test
	public void testSetPolicy()
	{
		//コマンドの初期化
		//SetPolicyコマンド
		CommandSetPolicy c=new CommandSetPolicy(ev3linetracer);
		//コマンドのBody部の設定
		String body;
		body="0	1.0\n"
			+"1	0.25	0.75\n"
			+"2	0.25	0.75\n"
			+"3	0.75	0.25\n"
			+"4	0.75	0.25\n"
			+"5	0.25	0.75\n"
			+"6	0.25	0.75\n"
			+"7	0.75	0.25\n"
			+"8	0.25	0.75\n"
			+"9	0.25	0.75";
		//ストリームの初期化
		BufferedReader brbody=new BufferedReader(new StringReader(body));
		BufferedWriter brresult=new BufferedWriter(new StringWriter());
		try
		{
			//コマンドの実行
			c.DoCommand(brbody, brresult);
			//指定したとおりに設定されているかを確認する
			StochasticPolicy sp = ev3linetracer.GetCurrentPolicy();
			assertEquals(sp.At(0).GetValueMax(),1);
			assertEquals(sp.At(0).GetProbability(0),1.0,0.0);
			assertEquals(sp.At(1).GetProbability(0),0.25,0.0);assertEquals(sp.At(1).GetProbability(1),0.75,0.0);
			assertEquals(sp.At(2).GetProbability(0),0.25,0.0);assertEquals(sp.At(2).GetProbability(1),0.75,0.0);
			assertEquals(sp.At(3).GetProbability(0),0.75,0.0);assertEquals(sp.At(3).GetProbability(1),0.25,0.0);
			assertEquals(sp.At(4).GetProbability(0),0.75,0.0);assertEquals(sp.At(4).GetProbability(1),0.25,0.0);
			assertEquals(sp.At(5).GetProbability(0),0.25,0.0);assertEquals(sp.At(5).GetProbability(1),0.75,0.0);
			assertEquals(sp.At(6).GetProbability(0),0.25,0.0);assertEquals(sp.At(6).GetProbability(1),0.75,0.0);
			assertEquals(sp.At(7).GetProbability(0),0.75,0.0);assertEquals(sp.At(7).GetProbability(1),0.25,0.0);
			assertEquals(sp.At(8).GetProbability(0),0.25,0.0);assertEquals(sp.At(8).GetProbability(1),0.75,0.0);
			assertEquals(sp.At(9).GetProbability(0),0.25,0.0);assertEquals(sp.At(9).GetProbability(1),0.75,0.0);
			
			brbody.close();
			brresult.close();
		}
		catch(IOException e)
		{
			//例外が発生したらテスト失敗
			fail("Test Failor!");
		}
		finally
		{
		}
	}
	//SeetPolicyのテスト
	//Body部が不正な場合に例外が発生することを確認する
	@Test
	public void testSetPolicy_Exception()
	{
		CommandSetPolicy c=new CommandSetPolicy(ev3linetracer);
		//Body部を不正にする(値の個数がControlCount以上の行が在る)
		String body="0	1.0\n"
				+"1	0.25	0.75\n"
				+"2	0.25	0.75\n"
				+"3	0.75	0.25	0.25\n"
				+"4	0.75	0.25\n"
				+"5	0.25	0.75\n"
				+"6	0.25	0.75\n"
				+"7	0.75	0.25\n"
				+"8	0.25	0.75\n"
				+"9	0.25	0.75";
		BufferedReader brbody=new BufferedReader(new StringReader(body));
		BufferedWriter brresult=new BufferedWriter(new StringWriter());
		try
		{
			//次の行で例外が発生する
			c.DoCommand(brbody, brresult);
			brbody.close();
			brresult.close();
			fail();
		}
		catch(Exception e)
		{
			//発生した例外の内容を出力
			System.out.println(brresult);
		}
		
	}
	//エピソードの実行テスト
	//エピソードを実行して、エピソードが正しく取得できていることを確認する
	@Test
	public void testExecEpisode()
	{
		//コマンドExecEpisodeの初期化
		CommandExecEpisode c=new CommandExecEpisode(ev3linetracer);
		//Body部の初期化(空文字列)
		String body;
		body="";
		//ストリームの初期化
		BufferedReader brbody=new BufferedReader(new StringReader(body));
		StringWriter sw=new StringWriter();
		BufferedWriter bwresult=new BufferedWriter(sw);
		try
		{
			//コマンドの実行
			c.DoCommand(brbody, bwresult);
			//bwresultの内容を取得するため、フラッシュする
			bwresult.flush();
			//bwresultの内容(sw)を取得
			String result=sw.getBuffer().toString();
			//resultの内容を標準出力に出力
			System.out.println("result-----------------");
			System.out.print(result);
			System.out.println("result end-------------");
			//resultの内容を読み取るため、BufferedReaderとして初期化
			BufferedReader brresult=new BufferedReader(new StringReader(result));
			//resultの1行目(文字列"OK")を読み取り
			String line=brresult.readLine();
			System.out.println("first line:"+line);
			//1行目が"OK"でなければテスト失敗
			if(!(line.equals("OK")))
			{
				fail("invalid result.");
			}
			//2行目(StepCountの取得)の取得
			line=brresult.readLine();
			int stepcount=Integer.parseInt(line);
			//StateCountの取得(取得したStepの妥当性の評価に使用)
			int statecount=ev3linetracer.GetStateCount();
			//エピソードのサイズ(stepcount)分だけループ
			for(int m=0;m<stepcount;m++)
			{
				//タブ文字で区切る
				String[] t=brresult.readLine().split("\t");
				//1行が4つに区切られることを確認
				assertEquals(t.length,4);
				//StepIndexの確認
				assertEquals(Integer.parseInt(t[0]),m);
				//stateの確認
				int i = Integer.parseInt(t[1]);
				if((i<0)||(i>=statecount))
				{
					fail("invalid state index.");
				}
				int controlcount=ev3linetracer.GetControlCount(i);
				//controlの確認
				int u=Integer.parseInt(t[2]);
				if((u<0)||(u>=controlcount))
				{
					fail("invalid control index.");
				}
				//costの確認(parseDouble()で例外が発生しないことを確認)
				Double.parseDouble(t[3]);
			}
			
			
			brresult.close();
			brbody.close();
			bwresult.close();
			
		}
		catch(Exception e)
		{
			//例外が発生したらテスト失敗
			e.printStackTrace();
			fail("Test Failor!");
		}
		finally
		{
		}
	}

}
