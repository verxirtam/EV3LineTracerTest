package rl.linetracer.communication;

import static org.junit.Assert.*;

import org.junit.Test;

import rl.Episode;
import rl.Step;

public class TestCommandExecEpisodeTest
{

	@Test
	public void testDoExecEpisode()
	{
		//TestCommandExecEpisodeを引数なしで初期化
		TestCommandExecEpisode t = null;
		t = new TestCommandExecEpisode();
		
		//doExecEpisode()の実行
		Episode e = new Episode();
		t.doExecEpisode(e);
		
		//デフォルトのエピソードを取得
		Episode d = TestCommandExecEpisode.defaultEpisode;
		
		//doExecEpisode()で取得したエピソードが
		//デフォルトのエピソードと一致することを確認する
		if (e.GetStepCount() == d.GetStepCount())
		{
			int step_count = e.GetStepCount();
			for (int i = 0; i < step_count; i++)
			{
				Step s0=e.GetStep(i);
				Step s1=d.GetStep(i);
				
				if((s0.State==s1.State)&&(s0.Control==s1.Control)&&(s0.Cost==s1.Cost))
				{
					
				}
				else
				{
					fail();
				}
			}
		}
	}

	@Test
	public void testProcess()
	{
		//TestCommandExecEpisodeを引数なしで初期化
		TestCommandExecEpisode t = null;
		t = new TestCommandExecEpisode();
		
		fail("テスト未作成");
	}

}
