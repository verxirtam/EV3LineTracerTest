package rl.linetracer.communication;

import rl.Episode;

//テスト用のCommandExecEpisode
//EV3の実機を制御しないようにするためにこのクラスを使用する
//ダミーのエピソードを出力する
public class TestCommandExecEpisode extends CommandExecEpisode
{
	private Episode episode;
	
	public static final Episode DEFAULT_EPISODE;
	public static final String NORMAL_OUTPUT=
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
	
	static
	{
		DEFAULT_EPISODE = new Episode();
		DEFAULT_EPISODE.AddStep(1, 11, 101.0);
		DEFAULT_EPISODE.AddStep(2, 12, 102.0);
		DEFAULT_EPISODE.AddStep(3, 13, 103.0);
		DEFAULT_EPISODE.AddStep(4, 14, 104.0);
		DEFAULT_EPISODE.AddStep(5, 15, 105.0);
		DEFAULT_EPISODE.AddStep(6, 16, 106.0);
		DEFAULT_EPISODE.AddStep(7, 17, 107.0);
		DEFAULT_EPISODE.AddStep(8, 18, 108.0);
		DEFAULT_EPISODE.AddStep(9, 19, 109.0);
		DEFAULT_EPISODE.AddStep(0, 10, 100.0);
	}

	public TestCommandExecEpisode()
	{
		episode = DEFAULT_EPISODE;
	}
	
	public void setOutputEpisode(Episode e)
	{
		episode = new Episode(e);
	}

	@Override
	protected void doExecEpisode(Episode e)
	{
		e.setEpisode(episode);
	}
}
