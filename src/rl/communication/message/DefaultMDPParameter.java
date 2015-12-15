package rl.communication.message;

import java.util.ArrayList;

import rl.linetracer.ControlNormal;
import rl.linetracer.State;

public class DefaultMDPParameter
{
	public int interval;
	public double costMax;	
	public int stateCount;
	public ArrayList<State> states;
	public ArrayList<ArrayList<ControlNormal>> controls;
	public int[] regularPolicy;
	public ArrayList<ArrayList<Double>> currentPolicy;
	public DefaultMDPParameter()
	{
		states = new ArrayList<State>();
		controls = new ArrayList<ArrayList<ControlNormal>>();
		currentPolicy = new ArrayList<ArrayList<Double>>();
	}
}