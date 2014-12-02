package rl.linetracer.communication;

import static org.junit.Assert.*;

import org.junit.Test;

import rl.Episode;
import rl.Step;
import rl.communication.message.TestMessageContext;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

public class TestCommandExecEpisodeTest
{

	@Test
	public void testDoExecEpisode()
	{
		// TestCommandExecEpisodeを引数なしで初期化
		TestCommandExecEpisode t = null;
		t = new TestCommandExecEpisode();

		// doExecEpisode()の実行
		Episode e = new Episode();
		t.doExecEpisode(e);

		// デフォルトのエピソードを取得
		Episode d = TestCommandExecEpisode.DEFAULT_EPISODE;

		// doExecEpisode()で取得したエピソードが
		// デフォルトのエピソードと一致することを確認する
		if (e.GetStepCount() == d.GetStepCount())
		{
			int step_count = e.GetStepCount();
			for (int i = 0; i < step_count; i++)
			{
				Step s0 = e.GetStep(i);
				Step s1 = d.GetStep(i);

				if ((s0.State == s1.State) && (s0.Control == s1.Control)
						&& (s0.Cost == s1.Cost))
				{

				} else
				{
					fail();
				}
			}
		}
	}

	@Test
	public void testProcess()
	{


		String message = "";
		
		String normal_output=
		"ExecEpisode\n"
		+"OK\n"
		+"10\n"
		+"0	1	11	101.0\n"
		+"1	2	12	102.0\n"
		+"2	3	13	103.0\n"
		+"3	4	14	104.0\n"
		+"4	5	15	105.0\n"
		+"5	6	16	106.0\n"
		+"6	7	17	107.0\n"
		+"7	8	18	108.0\n"
		+"8	9	19	109.0\n"
		+"9	0	10	100.0\n";


		
		TestMessageContext tmc = null;
		try
		{
			tmc = new TestMessageContext(message);
			MessageInputContext input = tmc.getMessageInputContext();
			MessageOutputContext output = tmc.getMessageOutputContext();
			
			// TestCommandExecEpisodeを引数なしで初期化
			TestCommandExecEpisode t = null;
			t = new TestCommandExecEpisode();
			
			t.process(input, output);
			
			output.flush();
			String m = tmc.getStringWriter().toString();
			if(m.equals(normal_output))
			{
				assertTrue(true);
			}
			else
			{
				fail();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			fail();
		} finally
		{
			tmc.close();
		}
	}

}
