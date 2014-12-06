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
import rl.linetracer.Control;
import rl.linetracer.EV3LineTracer;

public class CommandSetMDPTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
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
	public void testProcess()
	{
		CommandSetMDP com = null;
		
		com = new CommandSetMDP();
		String message=TestMessage.CommandSetMDPBody;
		
		
		try(TestMessageContext tmc = new TestMessageContext(message))
		{
			
			MessageInputContext input = tmc.getMessageInputContext();
			MessageOutputContext output = tmc.getMessageOutputContext();
			
			
			com.process(input, output);
			
			
			output.flush();
			StringWriter sw = tmc.getStringWriter();
			String output_string = sw.toString();
			String normal_output_string = CommandSetMDP.COMMAND_STRING+"\n"
					+CommandSetMDP.RESULT_OK+"\n";
			if(!output_string.equals(normal_output_string))
			{
				fail();
			}
			
			EV3LineTracer ev3 = EV3LineTracer.getInstance();
			DefaultMDPParameter mdpp=TestMessage.CommandSetMDPParameter;
			assertEquals(ev3.GetInterval(),mdpp.interval);
			assertEquals(ev3.GetStateCount(),mdpp.stateCount);
			
			StochasticPolicy ev3_regularpolicy = ev3.GetRegularPolicy();
			int[] mdpp_regularpolicy = mdpp.regularPolicy;
			
			for(int i=0; i < ev3.GetStateCount();i++)
			{
				assertEquals(ev3.GetState(i).RefMax,mdpp.states.get(i).RefMax,0.001);
				assertEquals(ev3.GetState(i).ControlCount,mdpp.states.get(i).ControlCount);
				for(int u=0;u<ev3.GetState(i).ControlCount;u++)
				{
					Control ev3_control = ev3.GetControl(i,u);
					Control mdpp_control = mdpp.controls.get(i).get(u);
					assertEquals(ev3_control.LMotorSpeed,mdpp_control.LMotorSpeed);
					assertEquals(ev3_control.RMotorSpeed,mdpp_control.RMotorSpeed);
					
					double ev3_rpprob = ev3_regularpolicy.At(i).GetProbability(u);
					if(u == mdpp_regularpolicy[i])
					{
						assertEquals(ev3_rpprob,1.0,0.00001);
					}
					else
					{
						assertEquals(ev3_rpprob,0.0,0.00001);
					}
				}
				
			}
			
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testProcess_Exception()
	{
		fail("未作成。正しく例外が発生することを確認する。");
	}
}
