package rl.linetracer.communication;

//テスト用のEV3LineTracer_1_0_Command
//ダミーのエピソードを出力するTestCommandExecEpisodeを返却する
public class TestEV3LineTracer_1_0_Command extends EV3LineTracer_1_0_Command
{
	//TestCommandExecEpisodeのインスタンスを返す
	@Override
	protected CommandExecEpisode createCommandExecEpisode()
	{
		return new TestCommandExecEpisode();
	}
}
