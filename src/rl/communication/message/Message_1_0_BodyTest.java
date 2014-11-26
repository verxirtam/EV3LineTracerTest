package rl.communication.message;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Message_1_0_BodyTest
{
	String normalMessage;

	@Before
	public void setUp() throws Exception
	{
		normalMessage = 
				TestMessage.EV3Version
				+ TestMessage.CommandSetMDP
				+ TestMessage.BlankLine;
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testProcess()
	{
		TestMessageContext tmc = null;
		try
		{
			tmc = new TestMessageContext(normalMessage);
			Message_1_0_Body m = new Message_1_0_Body();
			
			m.process(tmc.getMessageInputContext(), tmc.getMessageOutputContext());
			
			String out = tmc.getStringWriter().toString();
			String normalout="MESSAGE_1.0"+"\n";
			if(out.equals(normalout))
			{
				assertTrue(true);
			}
			else
			{
				fail();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
}
