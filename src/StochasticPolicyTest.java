import static org.junit.Assert.*;

import org.junit.Test;
import rl.StochasticPolicy;

public class StochasticPolicyTest {

	@Test
	public void testConstructor()
	{
		int statecount=100;
		double[][] prob=new double[statecount][];
		prob[0]=new double[1];
		prob[0][0]=1.0;
		for(int i=1;i<statecount;i++)
		{
				prob[i]=new double[i];
				double sum=0.0;
				for(int u=0;u<prob[i].length;u++)
				{
					prob[i][u]=i+u+1;
					sum+=prob[i][u];
				}
				for(int u=0;u<prob[i].length;u++)
				{
					prob[i][u]/=sum;
				}
		}
		
		StochasticPolicy sp=new StochasticPolicy(prob);
		for(int i=0;i<statecount;i++)
		{
			for(int u=0;u<prob[i].length;u++)
			{
				assertEquals(sp.At(i).GetProbability(u),prob[i][u],0.125*0.125);
			}
		}
	}

	@Test(expected = java.lang.IllegalStateException.class)
	public void testConstructorException()
	{
		int statecount=100;
		double[][] prob=new double[statecount][];
		prob[0]=new double[2];
		prob[0][0]=0.5;
		prob[0][1]=0.5;
		for(int i=1;i<statecount;i++)
		{
				prob[i]=new double[i];
				double sum=0.0;
				for(int u=0;u<prob[i].length;u++)
				{
					prob[i][u]=i+u+1;
					sum+=prob[i][u];
				}
				for(int u=0;u<prob[i].length;u++)
				{
					prob[i][u]/=sum;
				}
		}
		
		StochasticPolicy sp=new StochasticPolicy(prob);
		for(int i=0;i<statecount;i++)
		{
			for(int u=0;u<prob[i].length;u++)
			{
				assertEquals(sp.At(i).GetProbability(u),prob[i][u],0.125*0.125);
			}
		}
	}
}
