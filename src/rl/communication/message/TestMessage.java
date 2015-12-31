package rl.communication.message;

import java.util.ArrayList;

import rl.linetracer.ControlNormal;
import rl.linetracer.MDPManagerRefmax;
import rl.linetracer.State;
import rl.linetracer.StateRefMax;
import rl.linetracer.communication.EV3LineTracer_1_0_Command;
import rl.linetracer.communication.EV3LineTracer_1_1_Command;

public class TestMessage
{
	//メッセージバージョン
	public static final String MessageVersion=
			Message_1_0_Body.VERSION_STRING+"\n";
	//EV3のバージョン
	public static final String EV3Version = 
			EV3LineTracer_1_0_Command.VERSION_STRING + "\n";
	//EV3のバージョン(1.1用)
	public static final String EV3Version_1_1 = 
			EV3LineTracer_1_1_Command.VERSION_STRING + "\n";



	public static final DefaultMDPParameter DefaultSetMDPParameter;
	
	public static final String CommandSetMDPBody;
	public static final String CommandSetMDPBodyInterval;
	public static final String CommandSetMDPBodyCostMax;
	public static final String CommandSetMDPBodyStateCount;
	public static final String CommandSetMDPBodyState;
	public static final String CommandSetMDPBodyControl;
	public static final String CommandSetMDPBodyRegularPolicy;
	
	public static final String CommandSetCurrentPolicyBody;

	
	static
	{
		DefaultSetMDPParameter = new DefaultMDPParameter();
		
		DefaultMDPParameter mdpp = DefaultSetMDPParameter;
		
		mdpp.interval=11;
		mdpp.costMax=600.0;
		mdpp.stateCount=10;
		mdpp.states.add(new StateRefMax(0.1,1));
		mdpp.states.add(new StateRefMax(0.2,2));
		mdpp.states.add(new StateRefMax(0.3,2));
		mdpp.states.add(new StateRefMax(0.4,2));
		mdpp.states.add(new StateRefMax(0.5,2));
		mdpp.states.add(new StateRefMax(0.6,2));
		mdpp.states.add(new StateRefMax(0.7,2));
		mdpp.states.add(new StateRefMax(0.8,2));
		mdpp.states.add(new StateRefMax(0.9,2));
		mdpp.states.add(new StateRefMax(1.0,2));
		
		for(int i=0;i<10;i++)
		{
			mdpp.controls.add(new ArrayList<ControlNormal>());
		}
		mdpp.controls.get(0).add(new ControlNormal(10,10));
		mdpp.controls.get(1).add(new ControlNormal(10, 5));
		mdpp.controls.get(1).add(new ControlNormal( 5,10));
		mdpp.controls.get(2).add(new ControlNormal(10, 5));
		mdpp.controls.get(2).add(new ControlNormal( 5,10));
		mdpp.controls.get(3).add(new ControlNormal(10, 5));
		mdpp.controls.get(3).add(new ControlNormal( 5,10));
		mdpp.controls.get(4).add(new ControlNormal(10, 5));
		mdpp.controls.get(4).add(new ControlNormal( 5,10));
		mdpp.controls.get(5).add(new ControlNormal(10, 5));
		mdpp.controls.get(5).add(new ControlNormal( 5,10));
		mdpp.controls.get(6).add(new ControlNormal(10, 5));
		mdpp.controls.get(6).add(new ControlNormal( 5,10));
		mdpp.controls.get(7).add(new ControlNormal(10, 5));
		mdpp.controls.get(7).add(new ControlNormal( 5,10));
		mdpp.controls.get(8).add(new ControlNormal(10, 5));
		mdpp.controls.get(8).add(new ControlNormal( 5,10));
		mdpp.controls.get(9).add(new ControlNormal(10, 5));
		mdpp.controls.get(9).add(new ControlNormal( 5,10));
		
		mdpp.regularPolicy = new int[]{0,1,1,0,0,1,1,0,1,1};
		
		for(int i=0;i<10;i++)
		{
			mdpp.currentPolicy.add(new ArrayList<Double>());
		}
		mdpp.currentPolicy.get(0).add(1.0);
		mdpp.currentPolicy.get(1).add(0.0);mdpp.currentPolicy.get(1).add(1.0);
		mdpp.currentPolicy.get(2).add(0.0);mdpp.currentPolicy.get(2).add(1.0);
		mdpp.currentPolicy.get(3).add(1.0);mdpp.currentPolicy.get(3).add(0.0);
		mdpp.currentPolicy.get(4).add(1.0);mdpp.currentPolicy.get(4).add(0.0);
		mdpp.currentPolicy.get(5).add(0.0);mdpp.currentPolicy.get(5).add(1.0);
		mdpp.currentPolicy.get(6).add(0.0);mdpp.currentPolicy.get(6).add(1.0);
		mdpp.currentPolicy.get(7).add(1.0);mdpp.currentPolicy.get(7).add(0.0);
		mdpp.currentPolicy.get(8).add(0.0);mdpp.currentPolicy.get(8).add(1.0);
		mdpp.currentPolicy.get(9).add(0.0);mdpp.currentPolicy.get(9).add(1.0);

		CommandSetMDPBodyInterval=""+mdpp.interval+"\n";
		CommandSetMDPBodyCostMax=""+mdpp.costMax+"\n";
		CommandSetMDPBodyStateCount=""+mdpp.stateCount+"\n";
		
		
		String states="";
		for (int i = 0; i < mdpp.stateCount; i++)
		{
			State s = mdpp.states.get(i);
			states += "" + i + "\t" + ((StateRefMax)s).RefMax + "\t" + s.ControlCount + "\n";
			
		}
		CommandSetMDPBodyState=states;
		
		String controls="";
		for (int i = 0; i < mdpp.stateCount; i++)
		{
			State s = mdpp.states.get(i);

			for (int u = 0; u < s.ControlCount; u++)
			{
				ControlNormal c = mdpp.controls.get(i).get(u);
				controls += "" + i + "\t" + u + "\t" + c.LMotorSpeed + "\t"
						+ c.RMotorSpeed + "\n";
			}
		}
		CommandSetMDPBodyControl=controls;
		
		String regularPolicy="";
		for (int i = 0; i < mdpp.stateCount; i++)
		{
			int rp=mdpp.regularPolicy[i];
			regularPolicy += "" + i + "\t" + rp + "\n";
		}
		CommandSetMDPBodyRegularPolicy=regularPolicy;
		
		CommandSetMDPBody=CommandSetMDPBodyInterval
				+CommandSetMDPBodyCostMax
				+CommandSetMDPBodyStateCount
				+CommandSetMDPBodyState
				+CommandSetMDPBodyControl
				+CommandSetMDPBodyRegularPolicy;
		
		String currentPolicy="";
		for (int i = 0; i < mdpp.stateCount; i++)
		{
			currentPolicy += "" + i;
			int control_count = mdpp.currentPolicy.get(i).size();
			for(int u = 0; u < control_count; u++)
			{
				currentPolicy += "\t" + mdpp.currentPolicy.get(i).get(u);
			}
			currentPolicy += "\n";
		}
		CommandSetCurrentPolicyBody=currentPolicy;
	}
	
	
	//コマンド(SetMDP)
	public static final String CommandSetMDP =
			rl.linetracer.communication.CommandSetMDP.COMMAND_STRING + "\n"
			+ CommandSetMDPBody;

	//コマンド(SetMDP_1_1)
	public static final String CommandSetMDP_1_1 =
			rl.linetracer.communication.CommandSetMDP.COMMAND_STRING + "\n"
			+ MDPManagerRefmax.MANAGER_NAME + "\n"
			+ CommandSetMDPBody;

	//コマンド(SetCurrentPolicy)
	public static final String CommandSetCurrentPolicy =
			rl.linetracer.communication.CommandSetCurrentPolicy.COMMAND_STRING + "\n"
			+ CommandSetCurrentPolicyBody;

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
	
	//通常のメッセージ(SetCurrentPolicy)
	public static final String NormalMessageSetCurrentPolicy
		=TestMessage.MessageVersion
			+TestMessage.EV3Version
			+TestMessage.CommandSetCurrentPolicyBody
			+TestMessage.BlankLine;
}
