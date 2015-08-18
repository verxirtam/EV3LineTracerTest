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
					+CommandSetMDP.RESULT_OK+"\n"
					+TestMessage.CommandSetMDPBody;
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
	public void testProcess_Exception1_Interval()
	{
		String message="13\t123\n"
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+TestMessage.CommandSetMDPBodyState
				+TestMessage.CommandSetMDPBodyControl
				+TestMessage.CommandSetMDPBodyRegularPolicy;
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception2_StateCount()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+"10\tAAA\n"
				+TestMessage.CommandSetMDPBodyState
				+TestMessage.CommandSetMDPBodyControl
				+TestMessage.CommandSetMDPBodyRegularPolicy;
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception3_State1()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+"0	0.1	1\n"
				+"1	0.2	2\n"
				+"2	0.3	2\n"
				+"3	0.4	2\n"
				+"4	0.5	2\n"
				+"5	0.6	2\n"
				+"6	0.7	2\n"
				+"7	0.8	2\n"
				+"8	0.9	2\n"
				//+"9	1.0	2\n"//一行足りない
				+TestMessage.CommandSetMDPBodyControl
				+TestMessage.CommandSetMDPBodyRegularPolicy;
		
		testMessage_Exception(message);
	}	
	@Test
	public void testProcess_Exception3_State2()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+"0	0.1	1\n"
				+"1	0.2	2\n"
				+"2	0.3	2\n"
				+"3	0.4	2\n"
				+"5	0.5	2\n"//StateIndexが不正
				+"5	0.6	2\n"
				+"6	0.7	2\n"
				+"7	0.8	2\n"
				+"8	0.9	2\n"
				+"9	1.0	2\n"
				+TestMessage.CommandSetMDPBodyControl
				+TestMessage.CommandSetMDPBodyRegularPolicy;
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception3_State3()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+"0	0.1	1\n"
				+"1	0.2	2\n"
				+"2	0.3	2\n"
				+"3	0.4	2\n"
				+"4	AAA	2\n"//RefMaxが不正
				+"5	0.6	2\n"
				+"6	0.7	2\n"
				+"7	0.8	2\n"
				+"8	0.9	2\n"
				+"9	1.0	2\n"
				+TestMessage.CommandSetMDPBodyControl
				+TestMessage.CommandSetMDPBodyRegularPolicy;
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception3_State4()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+"0	0.1	1\n"
				+"1	0.2	2\n"
				+"2	0.3	2\n"
				+"3	0.4	2\n"
				+"4	0.5	-1\n"//ControlCountが不正
				+"5	0.6	2\n"
				+"6	0.7	2\n"
				+"7	0.8	2\n"
				+"8	0.9	2\n"
				+"9	1.0	2\n"
				+TestMessage.CommandSetMDPBodyControl
				+TestMessage.CommandSetMDPBodyRegularPolicy;
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception4_Control1()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+TestMessage.CommandSetMDPBodyState
				+"0	10	10\n"
				+"1	10	5\n"
				+"1	5	10\n"
				+"2	10	5\n"
				+"2	5	10\n"
				+"3	10	5\n"
				+"3	5	10\n"
				+"4	10	5\n"
				+"4	5	10\n"
				//+"5	10	5\n"//Controlが足りない
				+"5	5	10\n"
				+"6	10	5\n"
				+"6	5	10\n"
				+"7	10	5\n"
				+"7	5	10\n"
				+"8	10	5\n"
				+"8	5	10\n"
				+"9	10	5\n"
				+"9	5	10\n"
				+TestMessage.CommandSetMDPBodyRegularPolicy;
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception4_Control2()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+TestMessage.CommandSetMDPBodyState
				+"0	10	10\n"
				+"1	10	5\n"
				+"1	5	10\n"
				+"2	10	5\n"
				+"2	5	10\n"
				+"2	10	10\n"//Controlが多い
				+"3	10	5\n"
				+"3	5	10\n"
				+"4	10	5\n"
				+"4	5	10\n"
				+"5	10	5\n"
				+"5	5	10\n"
				+"6	10	5\n"
				+"6	5	10\n"
				+"7	10	5\n"
				+"7	5	10\n"
				+"8	10	5\n"
				+"8	5	10\n"
				+"9	10	5\n"
				+"9	5	10\n"
				+TestMessage.CommandSetMDPBodyRegularPolicy;
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception4_Control3()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+TestMessage.CommandSetMDPBodyState
				+"0	10	10\n"
				+"1	10	5\n"
				+"1	5	10\n"
				+"2	10	5\n"
				+"2	5	10\n"
				+"3	10	5\n"
				+"3	5	10\n"
				+"4	AA	5\n"//LMoterSpeedが不正
				+"4	5	10\n"
				+"5	10	5\n"
				+"5	5	10\n"
				+"6	10	5\n"
				+"6	5	10\n"
				+"7	10	5\n"
				+"7	5	10\n"
				+"8	10	5\n"
				+"8	5	10\n"
				+"9	10	5\n"
				+"9	5	10\n"
				+TestMessage.CommandSetMDPBodyRegularPolicy;
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception4_Control4()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+TestMessage.CommandSetMDPBodyState
				+"0	10	10\n"
				+"1	10	5\n"
				+"1	5	10\n"
				+"2	10	5\n"
				+"2	5	10\n"
				+"3	10	5\n"
				+"3	5	10\n"
				+"4	10	AA\n"//RMoterSpeedが不正
				+"4	5	10\n"
				+"5	10	5\n"
				+"5	5	10\n"
				+"6	10	5\n"
				+"6	5	10\n"
				+"7	10	5\n"
				+"7	5	10\n"
				+"8	10	5\n"
				+"8	5	10\n"
				+"9	10	5\n"
				+"9	5	10\n"
				+TestMessage.CommandSetMDPBodyRegularPolicy;
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception4_Control5()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+TestMessage.CommandSetMDPBodyState
				+"0	10	10\n"
				+"1	10	5\n"
				+"1	5	10\n"
				+"2	10	5\n"
				+"2	5	10\n"
				+"3	10	5\n"
				+"3	5	10\n"
				+"4	10	5	AAA\n"//Controlの項目が多い
				+"4	5	10\n"
				+"5	10	5\n"
				+"5	5	10\n"
				+"6	10	5\n"
				+"6	5	10\n"
				+"7	10	5\n"
				+"7	5	10\n"
				+"8	10	5\n"
				+"8	5	10\n"
				+"9	10	5\n"
				+"9	5	10\n"
				+TestMessage.CommandSetMDPBodyRegularPolicy;
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception5_RegularPolicy1()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+TestMessage.CommandSetMDPBodyState
				+TestMessage.CommandSetMDPBodyControl
				+"0	0"
				+"1	1"
				+"2	1"
				+"3	0"
				+"4	0"
				+"5	1"
				+"5	0"//余計な行が含まれている
				+"6	1"
				+"7	0"
				+"8	1"
				+"9	1";
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception5_RegularPolicy2()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+TestMessage.CommandSetMDPBodyState
				+TestMessage.CommandSetMDPBodyControl
				+"0	0"
				+"1	1"
				+"2	1"
				+"3	0"
				+"4	0"
				+"6	1"//StateIndexが不正
				+"6	1"
				+"7	0"
				+"8	1"
				+"9	1";
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception5_RegularPolicy3()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+TestMessage.CommandSetMDPBodyState
				+TestMessage.CommandSetMDPBodyControl
				+"0	0"
				+"1	1"
				+"2	1"
				+"3	0"
				+"4	0"
				+"5	5"//ControlIndexが不正
				+"6	1"
				+"7	0"
				+"8	1"
				+"9	1";
		
		testMessage_Exception(message);
	}
	@Test
	public void testProcess_Exception5_RegularPolicy4()
	{
		String message=TestMessage.CommandSetMDPBodyInterval
				+TestMessage.CommandSetMDPBodyCostMax
				+TestMessage.CommandSetMDPBodyStateCount
				+TestMessage.CommandSetMDPBodyState
				+TestMessage.CommandSetMDPBodyControl
				+"0	0"
				+"1	1"
				+"2	1"
				+"3	0"
				+"4	0"
				+"5	1	AAA"//余計な項目が含まれている
				+"6	1"
				+"7	0"
				+"8	1"
				+"9	1";
		
		testMessage_Exception(message);
	}
	private void testMessage_Exception(String message)
	{
		try (TestMessageContext tmc = new TestMessageContext(message))
		{
			CommandSetMDP com = null;
			com = new CommandSetMDP();
			
			MessageInputContext input = tmc.getMessageInputContext();
			MessageOutputContext output = tmc.getMessageOutputContext();

			com.process(input, output);
			
			fail();
			
		} catch (Exception e)
		{
			e.printStackTrace();
			assertTrue(true);
		}
	}
}
