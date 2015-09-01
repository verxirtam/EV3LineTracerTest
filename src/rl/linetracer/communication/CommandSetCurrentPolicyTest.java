package rl.linetracer.communication;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rl.StochasticPolicy;
import rl.communication.message.DefaultMDPParameter;
import rl.communication.message.TestMessage;
import rl.communication.message.TestMessageContext;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.EV3LineTracer;

public class CommandSetCurrentPolicyTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		//EV3を初期化するため下記を実行
		CommandSetMDPTest setmdp = new CommandSetMDPTest();
		setmdp.testProcess();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testConstructor()
	{
		CommandSetCurrentPolicy cscp = null;
		cscp = new CommandSetCurrentPolicy();
		cscp.toString();
	}	
	@Test
	public void testProcess()
	{
		CommandSetCurrentPolicy com = null;
		com = new CommandSetCurrentPolicy();
		
		String message=TestMessage.CommandSetCurrentPolicyBody;
		
		
		try(TestMessageContext tmc = new TestMessageContext(message))
		{
			
			MessageInputContext input = tmc.getMessageInputContext();
			MessageOutputContext output = tmc.getMessageOutputContext();
			
			
			com.process(input, output);
			
			//出力した文字列のチェック
			output.flush();
			StringWriter sw = tmc.getStringWriter();
			String output_string = sw.toString();
			String normal_output_string = CommandSetCurrentPolicy.COMMAND_STRING+"\n"
					+CommandSetCurrentPolicy.RESULT_OK+"\n"
					+TestMessage.CommandSetCurrentPolicyBody;
			if(!output_string.equals(normal_output_string))
			{
				fail();
			}
			
			//ev3へCurrentPolicyが問題なく反映されたかのチェック
			EV3LineTracer ev3 = EV3LineTracer.getInstance();
			StochasticPolicy ev3cp = ev3.GetCurrentPolicy();
			DefaultMDPParameter mdpp = TestMessage.DefaultSetMDPParameter;
			int state_count = mdpp.currentPolicy.size();
			assertEquals(ev3cp.GetStateCount(),state_count);
			for (int i = 0; i < state_count; i++)
			{
				int control_count = mdpp.currentPolicy.get(i).size();
				assertEquals(ev3cp.GetControlCount(i),control_count);
				for(int u = 0; u < control_count; u++)
				{
					double ev3cp_i_u = ev3cp.At(i).GetProbability(u);
					double cp_i_u = mdpp.currentPolicy.get(i).get(u);
					assertEquals(ev3cp_i_u,cp_i_u,0.00001);
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

}
