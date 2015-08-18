package rl.communication.message;

import java.util.ArrayList;

import rl.linetracer.Control;
import rl.linetracer.State;

public class DefaultMDPParameter
{
	public int interval;
	public double costMax;	
	public int stateCount;
	public ArrayList<State> states;
	public ArrayList<ArrayList<Control>> controls;
	public int[] regularPolicy;
	public DefaultMDPParameter()
	{
		states = new ArrayList<State>();
		controls = new ArrayList<ArrayList<Control>>();
	}
}