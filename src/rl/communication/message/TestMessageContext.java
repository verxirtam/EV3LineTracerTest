package rl.communication.message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.communication.message.context.TSVInputContext;
import rl.communication.message.context.TSVOutputContext;

public class TestMessageContext implements Closeable
{
	private Reader sr;
	private BufferedReader br;
	private StringWriter sw;
	private BufferedWriter bw;
	
	private MessageInputContext mic;
	private MessageOutputContext moc;

	public TestMessageContext(String message)
	{
		sr= new StringReader(message);
		br = new BufferedReader(sr);
		sw = new StringWriter();
		bw = new BufferedWriter(sw);


		mic = new TSVInputContext(br);
		moc = new TSVOutputContext(bw);
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
	
	@Override
	public void close()
	{
		try{br.close();} catch (IOException e){}
		try{sr.close();} catch (IOException e){}
		try{bw.close();} catch (IOException e){}
	}
}
