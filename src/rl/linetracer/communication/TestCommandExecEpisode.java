package rl.linetracer.communication;

import rl.Episode;

public class TestCommandExecEpisode extends CommandExecEpisode
{
	private Episode episode;
	
	public static final Episode defaultEpisode;
	
	static
	{
		defaultEpisode = new Episode();
		defaultEpisode.AddStep(1, 11, 101.0);
		defaultEpisode.AddStep(2, 12, 102.0);
		defaultEpisode.AddStep(3, 13, 103.0);
		defaultEpisode.AddStep(4, 14, 104.0);
		defaultEpisode.AddStep(5, 15, 105.0);
		defaultEpisode.AddStep(6, 16, 106.0);
		defaultEpisode.AddStep(7, 17, 107.0);
		defaultEpisode.AddStep(8, 18, 108.0);
		defaultEpisode.AddStep(9, 19, 109.0);
		defaultEpisode.AddStep(0, 10, 100.0);
	}

	public TestCommandExecEpisode()
	{
		episode = defaultEpisode;
	}
	
	public void setOutputEpisode(Episode e)
	{
		episode = new Episode(e);
	}

	@Override
	protected void doExecEpisode(Episode e)
	{
		e = new Episode(episode);
	}
}
