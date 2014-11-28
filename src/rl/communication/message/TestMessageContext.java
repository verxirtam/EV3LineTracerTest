package rl.communication.message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.communication.message.context.TSVInputContext;
import rl.communication.message.context.TSVOutputContext;

public class TestMessageContext
{
	private Reader sr;
	private BufferedReader br;
	private StringWriter sw;
	private BufferedWriter bw;
	
	private MessageInputContext mic;
	private MessageOutputContext moc;

	public TestMessageContext(String message) throws IOException
	{
		sr= new StringReader(message);
		br = new BufferedReader(sr);
		sw = new StringWriter();
		bw = new BufferedWriter(sw);

		try
		{
			mic = new TSVInputContext(br);
			moc = new TSVOutputContext(bw);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public MessageInputContext getMessageInputContext()
	{
		return mic;
	}

	public MessageOutputContext getMessageOutputContext()
	{
		return moc;
	}
	
	public StringWriter getStringWriter()
	{
		return sw;
	}
	
	public void close()
	{
		try{br.close();} catch (IOException e){}
		try{sr.close();} catch (IOException e){}
		try{bw.close();} catch (IOException e){}
	}
}
