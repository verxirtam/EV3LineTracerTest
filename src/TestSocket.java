import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class TestSocket extends Socket
{
	private InputStream Input;
	private OutputStream Output;

	public TestSocket(String s) throws UnsupportedEncodingException
	{
		Input = new ByteArrayInputStream(s.getBytes("UTF-8"));
		Output = new ByteArrayOutputStream();
	}

	// getInputStream
	@Override
	public InputStream getInputStream() throws IOException
	{
		return Input;

	}

	// getOutputStream
	@Override
	public OutputStream getOutputStream() throws IOException
	{
		return Output;
	}
	
	
}
