package rl.communication.message;

import java.util.ArrayList;

import rl.linetracer.Control;
import rl.linetracer.State;
import rl.linetracer.communication.EV3LineTracer_1_0_Command;

public class TestMessage
{
	//メッセージバージョン
	public static final String MessageVersion=
			Message_1_0_Body.VERSION_STRING+"\n";
	//EV3のバージョン
	public static final String EV3Version = 
			EV3LineTracer_1_0_Command.VERSION_STRING + "\n";



	public static final DefaultMDPParameter CommandSetMDPParameter;
	
	public static final String CommandSetMDPBody;
	/*				= ""
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
	*/
	
	static
	{
		CommandSetMDPParameter = new DefaultMDPParameter();
		
		DefaultMDPParameter mdpp = CommandSetMDPParameter;
		
		mdpp.interval=11;
		mdpp.stateCount=10;
		mdpp.states.add(new State(0.1,1));
		mdpp.states.add(new State(0.2,2));
		mdpp.states.add(new State(0.3,2));
		mdpp.states.add(new State(0.4,2));
		mdpp.states.add(new State(0.5,2));
		mdpp.states.add(new State(0.6,2));
		mdpp.states.add(new State(0.7,2));
		mdpp.states.add(new State(0.8,2));
		mdpp.states.add(new State(0.9,2));
		mdpp.states.add(new State(1.0,2));
		
		for(int i=0;i<10;i++)
		{
			mdpp.controls.add(new ArrayList<Control>());
		}
		mdpp.controls.get(0).add(new Control(10,10));
		mdpp.controls.get(1).add(new Control(10, 5));
		mdpp.controls.get(1).add(new Control( 5,10));
		mdpp.controls.get(2).add(new Control(10, 5));
		mdpp.controls.get(2).add(new Control( 5,10));
		mdpp.controls.get(3).add(new Control(10, 5));
		mdpp.controls.get(3).add(new Control( 5,10));
		mdpp.controls.get(4).add(new Control(10, 5));
		mdpp.controls.get(4).add(new Control( 5,10));
		mdpp.controls.get(5).add(new Control(10, 5));
		mdpp.controls.get(5).add(new Control( 5,10));
		mdpp.controls.get(6).add(new Control(10, 5));
		mdpp.controls.get(6).add(new Control( 5,10));
		mdpp.controls.get(7).add(new Control(10, 5));
		mdpp.controls.get(7).add(new Control( 5,10));
		mdpp.controls.get(8).add(new Control(10, 5));
		mdpp.controls.get(8).add(new Control( 5,10));
		mdpp.controls.get(9).add(new Control(10, 5));
		mdpp.controls.get(9).add(new Control( 5,10));
		
		mdpp.regularPolicy = new int[]{0,1,1,0,0,1,1,0,1,1};
		
		
		String setmdpbody=""
				+mdpp.interval+"\n"
				+mdpp.stateCount+"\n";
		for (int i = 0; i < mdpp.stateCount; i++)
		{
			State s = mdpp.states.get(i);
			setmdpbody += "" + i + "\t" + s.RefMax + "\t" + s.ControlCount + "\n";
			
		}
		for (int i = 0; i < mdpp.stateCount; i++)
		{
			State s = mdpp.states.get(i);

			for (int u = 0; u < s.ControlCount; u++)
			{
				Control c = mdpp.controls.get(i).get(u);
				setmdpbody += "" + i + "\t" + u + "\t" + c.LMotorSpeed + "\t"
						+ c.RMotorSpeed + "\n";
			}
		}
		for (int i = 0; i < mdpp.stateCount; i++)
		{
			int rp=mdpp.regularPolicy[i];
			setmdpbody += "" + i + "\t" + rp + "\n";
		}
		
		CommandSetMDPBody=setmdpbody;
		
	}
	
	
	//コマンド(SetMDP)
	public static final String CommandSetMDP =
			rl.linetracer.communication.CommandSetMDP.COMMAND_STRING + "\n"
			+CommandSetMDPBody;

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
