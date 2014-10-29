import static org.junit.Assert.*;

import org.junit.Test;

import rl.Episode;
import rl.Step;

public class EpisodeTest
{
	@Test
	public void testConstructor()
	{
		Episode e=new Episode();
		Step step=new Step(1,2,3.0);
		assertEquals(e.GetStepCount(), 0);
		e.AddStep(step);
		assertEquals(e.GetStepCount(), 1);
		e.AddStep(step);
		assertEquals(e.GetStepCount(), 2);
		
		assertEquals(e.GetStep(0).State, e.GetStep(1).State);
		e.GetStep(0).State=10;
		assertNotEquals(e.GetStep(0).State, e.GetStep(1).State);
		
	}
	@Test
	public void testAddStep()
	{
		int stepcount=100;
		Episode e=new Episode();
		Step step=new Step(1,2,3.0);
		int i;
		for(i=0;i<stepcount;i++)
		{
			step.State	=    i;
			step.Control=100+i;
			step.Cost	=1000.0+((double)i);
			e.AddStep(step);
		}
		for(;i<2*stepcount;i++)
		{
			step.State	=    i;
			step.Control=100+i;
			step.Cost	=1000.0+((double)i);
			e.AddStep(step.State,step.Control,step.Cost);
		}
		for(i=0;i<2*stepcount;i++)
		{
			step.State	=    i;
			step.Control=100+i;
			step.Cost	=1000+((double)i);
			assertEquals(e.GetStep(i).State  ,               i);
			assertEquals(e.GetStep(i).Control, 100+          i);
			assertEquals(e.GetStep(i).Cost   ,1000.0+((double)i),0.0);
		}

	}

}
