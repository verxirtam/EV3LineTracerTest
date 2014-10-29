import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rl.communication.TCPServer;


//TODO コメントを書くこと

public class TCPServerTest
{

	private String MsgText;
	private String MsgBody;

	@Before
	public void setUp() throws Exception
	{
		MsgBody = "TESTTEST\n"
				+"TESTTEST\n"
				+"TESTTEST\n"
				+"TESTTEST\n"
				+"TESTTEST\n";

		MsgText = "MESSAGE_1.0\n"
				+MsgBody
				+"\n";
		
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testConstructor()
	{
		TCPServer t = new TCPServer();
		t.getClass();
	}

	@Test
	public void testCheckVersion()
	{
		TCPServer t = new TCPServer();
		
		//バージョンが不正な場合
		try
		{
			t.CheckVersion("");
			fail();
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(true);
		}
		
		//バージョンが正しい場合
		try
		{
			t.CheckVersion("MESSAGE_1.0");
			assertTrue(true);
		}
		catch(IllegalArgumentException e)
		{
			fail();
		}
	}

	@Test
	public void testReadMassageBody()
	{
		
		try
		(
				BufferedReader in = new BufferedReader(new StringReader(MsgText));
				StringWriter sw = new StringWriter();
				BufferedWriter messagebody = new BufferedWriter(sw);
		)
		{
			TCPServer t = new TCPServer();
			String line=in.readLine();
			t.CheckVersion(line);
			t.ReadMassageBody(in, messagebody);
			messagebody.flush();
			String messagebodystirng=sw.toString();
			if(MsgBody.equals(messagebodystirng))
			{
				assertTrue(true);
			}
			else
			{
				fail();
			}
		}
		catch(IOException e)
		{
			fail();
		}
	}
	
	@Test
	public void testWriteVersion()
	{
		try
		(
				StringWriter sw = new StringWriter();
				BufferedWriter br = new BufferedWriter(sw);
		)
		{
			TCPServer t = new TCPServer();
			t.WriteVersion(br);
			br.flush();
			if(sw.toString().equals("MESSAGE_1.0\n"));
		}
		catch(IOException e)
		{
			fail();
		}
		
	}
}
