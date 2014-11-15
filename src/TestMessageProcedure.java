import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import rl.communication.old.MessageProcedure;


public class TestMessageProcedure extends MessageProcedure
{
	@Override
	public void MessageProcess(BufferedReader messagebody, BufferedWriter result)
			throws IOException
	{
		result.write("OK\n");
	}
}
