package rl.communication.message;

import rl.linetracer.communication.EV3LineTracer_1_0_Command;

public class TestMessage
{
	//メッセージバージョン
	public static final String MessageVersion=
			Message_1_0_Body.VERSION_STRING+"\n";
	//EV3のバージョン
	public static final String EV3Version = 
			EV3LineTracer_1_0_Command.VERSION_STRING + "\n";
	//コマンド(SetMDP)
	public static final String CommandSetMDP =
			rl.linetracer.communication.CommandSetMDP.COMMAND_STRING + "\n"
		+"11"+"\n"
		+"10"+"\n"
		+"0	0.1	1"+"\n"
		+"1	0.2	2"+"\n"
		+"2	0.3	2"+"\n"
		+"3	0.4	2"+"\n"
		+"4	0.5	2"+"\n"
		+"5	0.6	2"+"\n"
		+"6	0.7	2"+"\n"
		+"7	0.8	2"+"\n"
		+"8	0.9	2"+"\n"
		+"9	1.0	2"+"\n"
		+"0	0	10	10"+"\n"
		+"1	0	10	5"+"\n"
		+"1	1	5	10"+"\n"
		+"2	0	10	5"+"\n"
		+"2	1	5	10"+"\n"
		+"3	0	10	5"+"\n"
		+"3	1	5	10"+"\n"
		+"4	0	10	5"+"\n"
		+"4	1	5	10"+"\n"
		+"5	0	10	5"+"\n"
		+"5	1	5	10"+"\n"
		+"6	0	10	5"+"\n"
		+"6	1	5	10"+"\n"
		+"7	0	10	5"+"\n"
		+"7	1	5	10"+"\n"
		+"8	0	10	5"+"\n"
		+"8	1	5	10"+"\n"
		+"9	0	10	5"+"\n"
		+"9	1	5	10"+"\n"
		+"0	0"+"\n"
		+"1	1"+"\n"
		+"2	1"+"\n"
		+"3	0"+"\n"
		+"4	0"+"\n"
		+"5	1"+"\n"
		+"6	1"+"\n"
		+"7	0"+"\n"
		+"8	1"+"\n"
		+"9	1"+"\n";

	// コマンド(ExecEpisode)
	public static final String CommandExecEpisode = 
			rl.linetracer.communication.CommandExecEpisode.COMMAND_STRING
			+ "\n";

	// コマンド(NullCommand)
	public static final String CommandNullCommand = 
			rl.linetracer.communication.CommandNullCommand.COMMAND_STRING
			+ "\n";

	//空行
	public static final String BlankLine = "\n";

	//通常のメッセージ(SetMDP)
	public static final String NormalMessageSetMDP
		=TestMessage.MessageVersion
			+TestMessage.EV3Version
			+TestMessage.CommandSetMDP
			+TestMessage.BlankLine;
	
	//通常のメッセージ(NullCommand)
	public static final String NormalMessageNullCommand
		=TestMessage.MessageVersion
			+TestMessage.EV3Version
			+TestMessage.CommandNullCommand
			+TestMessage.BlankLine;
		
}
