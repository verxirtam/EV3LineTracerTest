import static org.junit.Assert.*;

import org.junit.Test;
import rl.RandomInt;

public class RandomIntTest
{

	@Test
	public void testConstructor()
	{
		double[] prob=new double[5];
		int part_count=16;
		prob[0]=1.0;
		prob[1]=2.0;
		prob[2]=3.0;
		prob[3]=4.0;
		prob[4]=5.0;
		double sum=0.0;
		for(int i=0;i<prob.length;i++)
		{
			sum+=prob[i];
		}
		for(int i=0;i<prob.length;i++)
		{
			prob[i]/=sum;
		}
		
		rl.RandomInt ri=new RandomInt(prob, part_count);
		
		assertEquals(ri.GetValueMax(),5);
		for(int i=0;i<prob.length;i++)
		{
			//System.out.println(""+i+":\t"+ri.GetProbability(i)+"\t:"+prob[i]);
			assertEquals(ri.GetProbability(i),prob[i],0.125*0.125);
		}
		
		
	}
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testConstructorException0()
	{
		double[] prob=new double[5];
		int part_count=16;
		prob[0]=-1.0;
		prob[1]= 2.0;
		prob[2]= 3.0;
		prob[3]= 4.0;
		prob[4]= 5.0;
		double sum=0.0;
		for(int i=0;i<prob.length;i++)
		{
			sum+=prob[i];
		}
		for(int i=0;i<prob.length;i++)
		{
			prob[i]/=sum;
		}
		
		rl.RandomInt ri=new RandomInt(prob, part_count);
		
		assertEquals(ri.GetValueMax(),5);
		for(int i=0;i<prob.length;i++)
		{
			//System.out.println(""+i+":\t"+ri.GetProbability(i)+"\t:"+prob[i]);
			assertEquals(ri.GetProbability(i),prob[i],0.125*0.125);
		}
	}
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testConstructorException1()
	{
		double[] prob=new double[5];
		int part_count=16;
		prob[0]= 0.0;
		prob[1]= 0.0;
		prob[2]= 0.0;
		prob[3]= 0.0;
		prob[4]= 0.0;
		double sum=0.0;
		for(int i=0;i<prob.length;i++)
		{
			sum+=prob[i];
		}
		for(int i=0;i<prob.length;i++)
		{
			prob[i]/=sum;
		}
		
		rl.RandomInt ri=new RandomInt(prob, part_count);
		
		assertEquals(ri.GetValueMax(),5);
		for(int i=0;i<prob.length;i++)
		{
			//System.out.println(""+i+":\t"+ri.GetProbability(i)+"\t:"+prob[i]);
			assertEquals(ri.GetProbability(i),prob[i],0.125*0.125);
		}
	}
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testConstructorException2()
	{
		double[] prob=new double[5];
		int part_count=16;
		prob[0]= 1.0;
		prob[1]= 2.0;
		prob[2]= 3.0;
		prob[3]= 4.0;
		prob[4]= 5.0;
		double sum=0.0;
		for(int i=0;i<prob.length;i++)
		{
			sum+=prob[i];
		}
		for(int i=0;i<prob.length;i++)
		{
			prob[i]/=sum;
		}
		
		rl.RandomInt ri=new RandomInt(prob, - part_count);
		
		assertEquals(ri.GetValueMax(),5);
		for(int i=0;i<prob.length;i++)
		{
			//System.out.println(""+i+":\t"+ri.GetProbability(i)+"\t:"+prob[i]);
			assertEquals(ri.GetProbability(i),prob[i],0.125*0.125);
		}
	}

	
	
	@Test
	public void testStat()
	{
		int trycount=100000;
		int count=100;
		double[] prob=new double[count];
		int[] stat=new int[count];
		
		int part_count=16;
		java.util.Random r=new java.util.Random();
		
		double sum=0.0;
		for(int i=0;i<prob.length;i++)
		{
			prob[i]=r.nextDouble();
			sum+=prob[i];
			
			stat[i]=0;
		}
		for(int i=0;i<prob.length;i++)
		{
			prob[i]/=sum;
		}
		
		rl.RandomInt ri=new RandomInt(prob, part_count);
		
		for(int n=0;n<trycount;n++)
		{
			int i=ri.Get();
			stat[i]++;
		}
		for(int i=0;i<prob.length;i++)
		{
			assertEquals(prob[i],
					((double)stat[i])/((double)trycount),
					0.125*0.125);
			assertEquals(ri.GetProbability(i),
					((double)stat[i])/((double)trycount),
					0.125*0.125);
		}
	}

}
